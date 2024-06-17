package app.revanced.patches.candylinkvpn

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val unlockProPatch = bytecodePatch(
    name = "Unlock pro",
) {
    compatibleWith("com.candylink.openvpn")

    val isPremiumPurchasedMatch by isPremiumPurchasedFingerprint()

    execute {
        isPremiumPurchasedMatch.mutableMethod.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """,
        )
    }
}
