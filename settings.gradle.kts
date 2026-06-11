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
include(":app")
project(":app").projectDir = File(rootDir, "VSCodroid-main/android/app")

// On-demand toolchain asset packs (Play Asset Delivery)
include(":toolchain_go")
project(":toolchain_go").projectDir = File(rootDir, "VSCodroid-main/android/toolchain_go")

include(":toolchain_ruby")
project(":toolchain_ruby").projectDir = File(rootDir, "VSCodroid-main/android/toolchain_ruby")

include(":toolchain_java")
project(":toolchain_java").projectDir = File(rootDir, "VSCodroid-main/android/toolchain_java")
