pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
