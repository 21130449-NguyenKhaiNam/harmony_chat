pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "harmony-chat"
include(":app")
// chưa biết ý nghĩa của 2 dòng include dưới là gì nên tạm thời comment lại cho 21130438
include(":myapplication")
include(":myapplication2")
