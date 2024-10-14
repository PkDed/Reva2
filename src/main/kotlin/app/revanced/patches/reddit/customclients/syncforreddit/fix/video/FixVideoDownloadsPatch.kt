package app.revanced.patches.reddit.customclients.syncforreddit.fix.video

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.reddit.customclients.syncforreddit.fix.video.fingerprints.ParseRedditVideoNetworkResponseFingerprint
import app.revanced.util.resultOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction35c

@Patch(
    name = "Fix video downloads",
    description = "Fixes a bug in Sync's MPD parser resulting in only the audio-track being saved.",
    compatiblePackages = [
        CompatiblePackage("com.laurencedawson.reddit_sync"),
        CompatiblePackage("com.laurencedawson.reddit_sync.pro"),
        CompatiblePackage("com.laurencedawson.reddit_sync.dev"),
    ],
    requiresIntegrations = true
)
@Suppress("unused")
object FixVideoDownloadsPatch : BytecodePatch(
    fingerprints = setOf(ParseRedditVideoNetworkResponseFingerprint),
) {
    private const val INTEGRATIONS_CLASS_DESCRIPTOR =
        "Lapp/revanced/integrations/syncforreddit/FixRedditVideoDownloadPatch;"
    private const val GET_LINKS_METHOD = "getLinks([B)[Ljava/lang/String;"

    override fun execute(context: BytecodeContext) {
        val fingerprint = ParseRedditVideoNetworkResponseFingerprint.resultOrThrow()
        val scanResult = fingerprint.scanResult.patternScanResult!!
        val newInstanceIndex = scanResult.startIndex
        val invokeDirectIndex = scanResult.endIndex - 1

        fingerprint.mutableMethod.apply {
            val responseBuilderInstruction = getInstruction<Instruction35c>(invokeDirectIndex)

            addInstructions(newInstanceIndex + 1, """
                # Get byte array from response.
                iget-object v2, p1, Lcom/android/volley/NetworkResponse;->data:[B
                    
                # Parse the videoUrl and audioUrl from the byte array.
                invoke-static { v2 }, $INTEGRATIONS_CLASS_DESCRIPTOR->$GET_LINKS_METHOD
                move-result-object v2
        
                # Get videoUrl (Index 0) and move it into register (v3).
                const/4 v5, 0x0
                aget-object v${responseBuilderInstruction.registerE}, v2, v5
        
                # Get audioUrl (Index 1) and move it into register (v4).
                const/4 v6, 0x1
                aget-object v${responseBuilderInstruction.registerF}, v2, v6

                # v3 and v4 are used to build the response after this.
            """)
        }
    }
}
