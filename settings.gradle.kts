import java.io.File
import java.net.URL
import java.util.zip.ZipInputStream

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Restore VSCodroid template files if missing in the local workspace
val appSrc = File(rootDir, "app/src")
if (!appSrc.exists()) {
    println("=== VSCodroid: Restoring template source assets ===")
    val candidates = listOf(
        "https://github.com/rmyndharis/VSCodroid/archive/refs/tags/v1.0.0.zip",
        "https://github.com/rmyndharis/VSCodroid/archive/refs/heads/main.zip",
        "https://github.com/rmyndharis/VSCodroid/archive/refs/heads/master.zip",
        "https://codeload.github.com/rmyndharis/VSCodroid/zip/refs/heads/main",
        "https://codeload.github.com/rmyndharis/VSCodroid/zip/refs/heads/master"
    )
    var success = false
    for (zipUrl in candidates) {
        if (success) break
        try {
            println("Trying download URL: $zipUrl")
            @Suppress("DEPRECATION")
            var connection = java.net.URI(zipUrl).toURL().openConnection() as java.net.HttpURLConnection
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
            connection.instanceFollowRedirects = true
            connection.connectTimeout = 20000
            connection.readTimeout = 45000
            
            var status = connection.responseCode
            var redirects = 0
            while ((status == java.net.HttpURLConnection.HTTP_MOVED_TEMP || 
                    status == java.net.HttpURLConnection.HTTP_MOVED_PERM || 
                    status == 307 || status == 308) && redirects < 5) {
                val newUrl = connection.getHeaderField("Location")
                println("Redirecting to: $newUrl")
                connection = java.net.URI(newUrl).toURL().openConnection() as java.net.HttpURLConnection
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                connection.connectTimeout = 20000
                connection.readTimeout = 45000
                status = connection.responseCode
                redirects++
            }
            if (status != java.net.HttpURLConnection.HTTP_OK) {
                throw java.io.IOException("HTTP $status returned from server")
            }
            
            connection.inputStream.use { inputStream ->
                val zipStream = ZipInputStream(inputStream)
                var entry = zipStream.nextEntry
                while (entry != null) {
                    val name = entry.name
                    val parts = name.split("/", limit = 2)
                    if (parts.size >= 2) {
                        val relPath = parts[1]
                        if (relPath.startsWith("android/") && relPath.length > 8) {
                            val targetRel = relPath.substring(8) // strip "android/"
                            val targetFile = File(rootDir, targetRel)
                            if (entry.isDirectory) {
                                targetFile.mkdirs()
                            } else {
                                targetFile.parentFile.mkdirs()
                                targetFile.outputStream().use { zipStream.copyTo(it) }
                            }
                        } else if (relPath.startsWith("scripts/") || relPath.startsWith("docs/") || relPath.startsWith("patches/") || relPath.startsWith("test/") || relPath.startsWith("toolchains/")) {
                            val targetFile = File(rootDir, relPath)
                            if (entry.isDirectory) {
                                targetFile.mkdirs()
                            } else {
                                targetFile.parentFile.mkdirs()
                                targetFile.outputStream().use { zipStream.copyTo(it) }
                            }
                        }
                    }
                    zipStream.closeEntry()
                    entry = zipStream.nextEntry
                }
                zipStream.close()
            }
            println("=== VSCodroid: Restore complete ===")
            success = true
        } catch (e: Exception) {
            println("Candidate $zipUrl failed:")
            e.printStackTrace()
        }
    }
    if (!success) {
        println("=== VSCodroid ERROR: Failed to restore templates from all candidate URLs ===")
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VSCodroid"

// Ensure all subproject directories exist so Gradle configuration never fails
val hasSubmodule = File(rootDir, "VSCodroid-main/android/app").exists()
if (hasSubmodule) {
    include(":app")
    project(":app").projectDir = File(rootDir, "VSCodroid-main/android/app")
    
    include(":toolchain_go")
    project(":toolchain_go").projectDir = File(rootDir, "VSCodroid-main/android/toolchain_go")
    
    include(":toolchain_ruby")
    project(":toolchain_ruby").projectDir = File(rootDir, "VSCodroid-main/android/toolchain_ruby")
    
    include(":toolchain_java")
    project(":toolchain_java").projectDir = File(rootDir, "VSCodroid-main/android/toolchain_java")
} else {
    File(rootDir, "app").mkdirs()
    File(rootDir, "toolchain_go").mkdirs()
    File(rootDir, "toolchain_ruby").mkdirs()
    File(rootDir, "toolchain_java").mkdirs()
    
    include(":app")
    include(":toolchain_go")
    include(":toolchain_ruby")
    include(":toolchain_java")
}

