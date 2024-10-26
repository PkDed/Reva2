package app.revanced.patches.youtube.layout.hide.shorts

import app.revanced.patcher.fingerprint
import app.revanced.util.literal
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val bottomNavigationBarFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("V")
    parameters("Landroid/view/View;", "Landroid/os/Bundle;")
    opcodes(
        Opcode.CONST, // R.id.app_engagement_panel_wrapper
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.IF_EQZ,
        Opcode.IGET_OBJECT,
        Opcode.IGET_OBJECT,
        Opcode.IGET_OBJECT,
    )
    strings("ReelWatchPaneFragmentViewModelKey")
}

internal val legacyRenderBottomNavigationBarParentFingerprint = fingerprint {
    parameters(
        "I",
        "I",
        "L",
        "L",
        "J",
        "L",
    )
    strings("aa")
}

internal val shortsBottomBarContainerFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("V")
    parameters("Landroid/view/View;", "Landroid/os/Bundle;")
    strings("r_pfvc")
    literal { bottomBarContainer }
}

internal val createShortsButtonsFingerprint = fingerprint {
    returns("V")
    literal { reelPlayerRightCellButtonHeight }
}

internal val reelConstructorFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    opcodes(Opcode.INVOKE_VIRTUAL)
    literal { reelMultipleItemShelfId }
}

internal val renderBottomNavigationBarFingerprint = fingerprint {
    returns("V")
    parameters("Ljava/lang/String;")
    opcodes(
        Opcode.IGET_OBJECT,
        Opcode.MONITOR_ENTER,
        Opcode.IGET_OBJECT,
        Opcode.IF_EQZ,
        Opcode.INVOKE_INTERFACE,
        Opcode.MONITOR_EXIT,
        Opcode.RETURN_VOID,
        Opcode.MOVE_EXCEPTION,
        Opcode.MONITOR_EXIT,
        Opcode.THROW,
    )
}

/**
 * Identical to [legacyRenderBottomNavigationBarParentFingerprint]
 * except this has an extra parameter.
 */
internal val renderBottomNavigationBarParentFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    parameters(
        "I",
        "I",
        "L", // ReelWatchEndpointOuterClass
        "L",
        "J",
        "Ljava/lang/String;",
        "L",
    )
    strings("aa")
}

internal val setPivotBarVisibilityFingerprint = fingerprint {
    accessFlags(AccessFlags.PRIVATE, AccessFlags.FINAL)
    returns("V")
    parameters("Z")
    opcodes(
        Opcode.CHECK_CAST,
        Opcode.IF_EQZ,
    )
}

internal val setPivotBarVisibilityParentFingerprint = fingerprint {
    parameters("Z")
    strings("FEnotifications_inbox")
}