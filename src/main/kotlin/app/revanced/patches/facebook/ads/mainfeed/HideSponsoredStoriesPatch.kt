package app.revanced.patches.facebook.ads.mainfeed

import app.revanced.util.exception
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.facebook.ads.mainfeed.fingerprints.GetStoryVisibilityFingerprint
import app.revanced.patches.facebook.ads.mainfeed.fingerprints.GraphQLStorySponsoredDataGetterFingerprint
import app.revanced.util.resultOrThrow

@Patch(
    name = "Hide Sponsored Stories",
    compatiblePackages = [CompatiblePackage("com.facebook.katana")]
)
@Suppress("unused")
object HideSponsoredStoriesPatch : BytecodePatch(setOf(GetStoryVisibilityFingerprint, GraphQLStorySponsoredDataGetterFingerprint)) {
    override fun execute(context: BytecodeContext) {
        GetStoryVisibilityFingerprint.result?.apply {

            // Get the sponsored data model getter.
            val sponsoredDataModelGetter = GraphQLStorySponsoredDataGetterFingerprint.resultOrThrow()

            // Check if the parameter type is GraphQLStory and if sponsoredDataModelGetter returns a non-null value.
            // If so, hide the story by setting the visibility to StoryVisibility.GONE.
            mutableMethod.addInstructionsWithLabels(
                scanResult.patternScanResult!!.startIndex,
                """
                    instance-of v0, p0, Lcom/facebook/graphql/model/GraphQLStory;
                    if-eqz v0, :resume_normal
                    invoke-virtual {p0}, Lcom/facebook/graphql/model/GraphQLStory;->${sponsoredDataModelGetter.method.name}()${sponsoredDataModelGetter.method.returnType}
                    move-result-object v0 
                    if-eqz v0, :resume_normal
                    const-string v0, "GONE"
                    return-object v0
                    :resume_normal
                    nop
                """
            )
        } ?: throw GetStoryVisibilityFingerprint.exception
    }
}