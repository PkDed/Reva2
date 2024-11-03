package app.revanced.patches.youtube.layout.seekbar

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.all.misc.resources.addResources
import app.revanced.patches.all.misc.resources.addResourcesPatch
import app.revanced.patches.shared.misc.settings.preference.SwitchPreference
import app.revanced.patches.youtube.interaction.seekbar.fullscreenSeekbarThumbnailsQualityFingerprint
import app.revanced.patches.youtube.misc.extension.sharedExtensionPatch
import app.revanced.patches.youtube.misc.playservice.is_19_17_or_greater
import app.revanced.patches.youtube.misc.playservice.versionCheckPatch
import app.revanced.patches.youtube.misc.settings.PreferenceScreen
import app.revanced.util.matchOrThrow

private const val EXTENSION_CLASS_DESCRIPTOR =
    "Lapp/revanced/extension/youtube/patches/SeekbarThumbnailsPatch;"

@Suppress("unused")
val seekbarThumbnailsPatch = bytecodePatch(
    name = "Seekbar thumbnails",
    description = "Adds an option to use high quality fullscreen seekbar thumbnails.",
) {
    dependsOn(
        sharedExtensionPatch,
        addResourcesPatch,
        versionCheckPatch,
    )

    compatibleWith(
        "com.google.android.youtube"(
            "18.38.44",
            "18.49.37",
            "19.16.39",
            "19.25.37",
            "19.34.42",
        ),
    )

    execute {
        addResources("youtube", "layout.seekbar.seekbarThumbnailsPatch")

        PreferenceScreen.SEEKBAR.addPreferences(
            if (!is_19_17_or_greater) {
                SwitchPreference(
                    key = "revanced_seekbar_thumbnails_high_quality",
                    summaryOnKey = "revanced_seekbar_thumbnails_high_quality_legacy_summary_on",
                    summaryOffKey = "revanced_seekbar_thumbnails_high_quality_legacy_summary_on",
                )
            } else {
                SwitchPreference("revanced_seekbar_thumbnails_high_quality")
            },
        )

        fullscreenSeekbarThumbnailsQualityFingerprint.matchOrThrow.method.addInstructions(
            0,
            """
                invoke-static { }, $EXTENSION_CLASS_DESCRIPTOR->useHighQualityFullscreenThumbnails()Z
                move-result v0
                return v0
            """,
        )
    }
}
