package app.revanced.patches.music.layout.premium

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.matchOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction

@Suppress("unused")
val hideGetPremiumPatch = bytecodePatch(
    name = "Hide 'Get Music Premium' label",
    description = "Hides the \"Get Music Premium\" label from the account menu and settings.",
) {
    compatibleWith("com.google.android.apps.youtube.music")

    execute {
        val hideGetPremiumMatch by hideGetPremiumFingerprint

        hideGetPremiumMatch.method.apply {
            val insertIndex = hideGetPremiumMatch.patternMatch!!.endIndex

            val setVisibilityInstruction = getInstruction<FiveRegisterInstruction>(insertIndex)
            val getPremiumViewRegister = setVisibilityInstruction.registerC
            val visibilityRegister = setVisibilityInstruction.registerD

            replaceInstruction(
                insertIndex,
                "const/16 v$visibilityRegister, 0x8",
            )

            addInstruction(
                insertIndex + 1,
                "invoke-virtual {v$getPremiumViewRegister, v$visibilityRegister}, " +
                    "Landroid/view/View;->setVisibility(I)V",
            )
        }

        membershipSettingsFingerprint.matchOrThrow.method.addInstructions(
            0,
            """
            const/4 v0, 0x0
            return-object v0
        """,
        )
    }
}
