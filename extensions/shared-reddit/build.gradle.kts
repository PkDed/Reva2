extension {
    name = "extensions/shared-googlenews.rve"
}

android {
    namespace = "app.revanced.extension"

    buildTypes["release"].isMinifyEnabled = true
}

dependencies {
    compileOnly(project(":extensions:shared-reddit:stub"))
}
