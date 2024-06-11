package app.revanced.patches.youtube.layout.player.overlay

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.patch.resourcePatch
import app.revanced.patches.all.misc.resources.addResources
import app.revanced.patches.all.misc.resources.addResourcesPatch
import app.revanced.patches.shared.misc.mapping.get
import app.revanced.patches.shared.misc.mapping.resourceMappingPatch
import app.revanced.patches.shared.misc.mapping.resourceMappings
import app.revanced.patches.shared.misc.settings.preference.InputType
import app.revanced.patches.shared.misc.settings.preference.TextPreference
import app.revanced.patches.youtube.misc.settings.PreferenceScreen
import app.revanced.patches.youtube.misc.settings.settingsPatch
import app.revanced.util.indexOfFirstWideLiteralInstructionValueOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

private const val INTEGRATIONS_CLASS_DESCRIPTOR = "Lapp/revanced/integrations/youtube/patches/CustomPlayerOverlayOpacityPatch;"

@Suppress("unused")
val customPlayerOverlayOpacityPatch = bytecodePatch(
    name = "Custom player overlay opacity",
    description = "Adds an option to change the opacity of the video player background when player controls are visible.",
) {
    dependsOn(customPlayerOverlayOpacityResourcePatch)

    compatibleWith("com.google.android.youtube")

    val createPlayerOverviewResult by createPlayerOverviewFingerprint

    execute {
        createPlayerOverviewResult.mutableMethod.apply {
            val viewRegisterIndex =
                indexOfFirstWideLiteralInstructionValueOrThrow(scrimOverlayId) + 3
            val viewRegister =
                getInstruction<OneRegisterInstruction>(viewRegisterIndex).registerA

            val insertIndex = viewRegisterIndex + 1
            addInstruction(
                insertIndex,
                "invoke-static { v$viewRegister }, " +
                    "$INTEGRATIONS_CLASS_DESCRIPTOR->changeOpacity(Landroid/widget/ImageView;)V",
            )
        }
    }
}

internal var scrimOverlayId = -1L
    private set

internal val customPlayerOverlayOpacityResourcePatch = resourcePatch {
    dependsOn(
        settingsPatch,
        resourceMappingPatch,
        addResourcesPatch,
    )

    execute {
        addResources("youtube", "layout.player.overlay.customPlayerOverlayOpacityResourcePatch")

        PreferenceScreen.PLAYER.addPreferences(
            TextPreference("revanced_player_overlay_opacity", inputType = InputType.NUMBER),
        )

        scrimOverlayId = resourceMappings[
            "id",
            "scrim_overlay",
        ]
    }
}
