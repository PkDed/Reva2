package app.revanced.patches.pixiv.ads

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val hideAdsPatch = bytecodePatch(
    name = "Hide ads",
) {
    compatibleWith("jp.pxv.android")

    val shouldShowAdsMatch by shouldShowAdsFingerprint()

    execute {
        shouldShowAdsMatch.mutableMethod.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """,
        )
    }
}
