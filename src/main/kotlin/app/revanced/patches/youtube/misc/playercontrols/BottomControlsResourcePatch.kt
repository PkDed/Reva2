package app.revanced.patches.youtube.misc.playercontrols

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.Patch
import java.io.Closeable

@Patch(
    dependencies = [PlayerControlsBytecodePatch::class],
)
@Deprecated("Obsolete", replaceWith = ReplaceWith("PlayerControlsBytecodePatch"))
object BottomControlsResourcePatch : ResourcePatch(), Closeable {
    override fun execute(context: ResourceContext) {}

    fun addControls(resourceDirectoryName: String) {
        PlayerControlsResourcePatch.addControls(resourceDirectoryName)
    }

    override fun close() {}
}