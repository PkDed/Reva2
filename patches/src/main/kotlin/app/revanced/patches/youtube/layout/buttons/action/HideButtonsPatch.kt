package app.revanced.patches.youtube.layout.buttons.action

import app.revanced.patcher.patch.resourcePatch
import app.revanced.patches.all.misc.resources.addResources
import app.revanced.patches.all.misc.resources.addResourcesPatch
import app.revanced.patches.shared.misc.mapping.resourceMappingPatch
import app.revanced.patches.shared.misc.settings.preference.PreferenceScreenPreference
import app.revanced.patches.shared.misc.settings.preference.SwitchPreference
import app.revanced.patches.youtube.misc.litho.filter.addLithoFilter
import app.revanced.patches.youtube.misc.litho.filter.lithoFilterPatch
import app.revanced.patches.youtube.misc.settings.PreferenceScreen

@Suppress("unused")
val hideButtonsPatch = resourcePatch(
    name = "Hide video action buttons",
    description = "Adds options to hide action buttons (such as the Download button) under videos.",
) {
    dependsOn(
        resourceMappingPatch,
        lithoFilterPatch,
        addResourcesPatch,
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
        addResources("youtube", "layout.buttons.action.hideButtonsPatch")

        PreferenceScreen.PLAYER.addPreferences(
            PreferenceScreenPreference(
                "revanced_hide_buttons_screen",
                preferences = setOf(
                    SwitchPreference("revanced_hide_like_dislike_button"),
                    SwitchPreference("revanced_hide_share_button"),
                    SwitchPreference("revanced_hide_report_button"),
                    SwitchPreference("revanced_hide_remix_button"),
                    SwitchPreference("revanced_hide_download_button"),
                    SwitchPreference("revanced_hide_thanks_button"),
                    SwitchPreference("revanced_hide_clip_button"),
                    SwitchPreference("revanced_hide_playlist_button"),
                ),
            ),
        )

        addLithoFilter("Lapp/revanced/extension/youtube/patches/components/ButtonsFilter;")
    }
}
