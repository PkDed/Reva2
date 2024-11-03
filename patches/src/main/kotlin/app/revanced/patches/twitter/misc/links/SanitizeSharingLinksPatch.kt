package app.revanced.patches.twitter.misc.links

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.matchOrThrow

@Suppress("unused")
val sanitizeSharingLinksPatch = bytecodePatch(
    name = "Sanitize sharing links",
    description = "Removes the tracking query parameters from links before they are shared.",
) {
    compatibleWith("com.twitter.android")

    execute {
        sanitizeSharingLinksFingerprint.matchOrThrow.method.addInstructions(
            0,
            """
                # Method takes in a link (string, param 0) and then appends the tracking query params,
                # so all we need to do is return back the passed-in string
                return-object p0
            """,
        )
    }
}
