package app.revanced.patches.all.misc.packagename

import app.revanced.patcher.patch.Option
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.resourcePatch
import app.revanced.patcher.patch.stringOption
import org.w3c.dom.Element

lateinit var packageNameOption: Option<String>

/**
 * Set the package name to use.
 * If this is called multiple times, the first call will set the package name.
 *
 * @param fallbackPackageName The package name to use if the user has not already specified a package name.
 * @return The package name that was set.
 * @throws OptionException.ValueValidationException If the package name is invalid.
 */
fun setOrGetFallbackPackageName(fallbackPackageName: String): String {
    val packageName = packageNameOption.value!!

    return if (packageName == packageNameOption.default) {
        fallbackPackageName.also { packageNameOption.value = it }
    } else {
        packageName
    }
}

val changePackageNamePatch = resourcePatch(
    name = "Change package name",
    description = "Appends \".revanced\" to the package name by default. Changing the package name of the app can lead to unexpected issues.",
    use = false,
) {
    packageNameOption = stringOption(
        key = "packageName",
        default = "Default",
        values = mapOf("Default" to "Default"),
        title = "Package name",
        description = "The name of the package to rename the app to.",
        required = true,
    ) {
        it == "Default" || it!!.matches(Regex("^[a-z]\\w*(\\.[a-z]\\w*)+\$"))
    }

    /**
     * Apps that are confirmed to not work correctly when used with this patch.
     */
    val incompatibleAppPackages = setOf(
        "com.facebook.orca",
        "com.instagram.android",
        "com.twitter.android",
        "tv.twitch.android.app"
    )

    finalize {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.getElementsByTagName("manifest").item(0) as Element
            val originalPackageName = manifest.getAttribute("package")

            if (incompatibleAppPackages.contains(originalPackageName)) {
                // Instead of throwing an exception this could print a more readable severe log statement,
                // but then the patcher would show "INFO: "Change package name" succeeded" which is not correct.
                throw PatchException("'$originalPackageName' does not work correctly with \"Change package name\"")
            }

            val replacementPackageName = packageNameOption.value
            manifest.setAttribute(
                "package",
                if (replacementPackageName != packageNameOption.default) {
                    replacementPackageName
                } else {
                    "${originalPackageName}.revanced"
                },
            )
        }
    }
}
