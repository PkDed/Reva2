package app.revanced.patches.moneymanager

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val unlockProPatch = bytecodePatch(
    name = "Unlock pro",
) {
    compatibleWith("com.ithebk.expensemanager")

    val unlockProMatch by unlockProFingerprint()

    execute {
        unlockProMatch.mutableMethod.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """,
        )
    }
}
