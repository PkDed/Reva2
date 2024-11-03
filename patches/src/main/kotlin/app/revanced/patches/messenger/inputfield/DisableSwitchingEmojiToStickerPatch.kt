package app.revanced.patches.messenger.inputfield

import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

@Suppress("unused")
val disableSwitchingEmojiToStickerPatch = bytecodePatch(
    name = "Disable switching emoji to sticker",
    description = "Disables switching from emoji to sticker search mode in message input field.",
) {
    compatibleWith("com.facebook.orca"("439.0.0.29.119"))

    execute {
        val switchMessangeInputEmojiButtonMatch by switchMessangeInputEmojiButtonFingerprint
        val setStringIndex = switchMessangeInputEmojiButtonMatch.patternMatch!!.startIndex + 2

        switchMessangeInputEmojiButtonMatch.method.apply {
            val targetRegister = getInstruction<OneRegisterInstruction>(setStringIndex).registerA

            replaceInstruction(setStringIndex, "const-string v$targetRegister, \"expression\"")
        }
    }
}
