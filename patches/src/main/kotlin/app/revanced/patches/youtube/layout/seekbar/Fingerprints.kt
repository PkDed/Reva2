package app.revanced.patches.youtube.layout.seekbar

import app.revanced.patcher.fingerprint
import app.revanced.util.containsLiteralInstruction
import app.revanced.util.literal
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val fullscreenSeekbarThumbnailsFingerprint = fingerprint {
    returns("Z")
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    parameters()
    literal { 45398577 }
}

internal val playerSeekbarColorFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    custom { method, _ ->
        method.containsLiteralInstruction(inlineTimeBarColorizedBarPlayedColorDarkId) &&
            method.containsLiteralInstruction(inlineTimeBarPlayedNotHighlightedColorId)
    }
}

internal val setSeekbarClickedColorFingerprint = fingerprint {
    opcodes(Opcode.CONST_HIGH16)
    strings("YOUTUBE", "PREROLL", "POSTROLL")
    custom { _, classDef ->
        classDef.endsWith("ControlsOverlayStyle;")
    }
}

internal val shortsSeekbarColorFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    literal { reelTimeBarPlayedColorId }
}

internal const val PLAYER_SEEKBAR_GRADIENT_FEATURE_FLAG = 45617850L

internal val playerSeekbarGradientConfigFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Z")
    parameters()
    literal { PLAYER_SEEKBAR_GRADIENT_FEATURE_FLAG }
}

internal val lithoLinearGradientFingerprint = fingerprint {
    accessFlags(AccessFlags.STATIC)
    returns("Landroid/graphics/LinearGradient;")
    parameters("F", "F", "F", "F", "[I", "[F")
}

internal const val launchScreenLayoutTypeLotteFeatureFlag = 268507948L

internal val launchScreenLayoutTypeFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    returns("V")
    parameters("Lcom/google/android/apps/youtube/app/watchwhile/MainActivity;", "L", "L", "L", "L", "L", "L", "L")
    literal { launchScreenLayoutTypeLotteFeatureFlag }
}

internal val launchScreenOptimizedFeatureFlagFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.STATIC)
    returns("Z")
    parameters("L")
    custom { method, _ ->
        method.containsLiteralInstruction(268639016)
                && method.containsLiteralInstruction(4)
    }
}

internal val launchScreenBuenosAiresFeatureFlagFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.STATIC)
    returns("Z")
    parameters("L")
    custom { method, _ ->
        method.containsLiteralInstruction(268639016)
                && method.containsLiteralInstruction(1)
    }
}

