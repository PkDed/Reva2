package app.revanced.patches.reddit.customclients.sync.detection.piracy

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.matchOrThrow

val disablePiracyDetectionPatch = bytecodePatch(
    description = "Disables detection of modified versions.",
) {

    execute {
        // Do not throw an error if the fingerprint is not resolved.
        // This is fine because new versions of the target app do not need this patch.
        piracyDetectionFingerprint.matchOrThrow.method.addInstruction(0, "return-void")
    }
}
