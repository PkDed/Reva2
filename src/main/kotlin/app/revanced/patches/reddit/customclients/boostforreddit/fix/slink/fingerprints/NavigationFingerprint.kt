package app.revanced.patches.reddit.customclients.boostforreddit.fix.slink.fingerprints
import app.revanced.patcher.fingerprint.MethodFingerprint

internal object NavigationFingerprint: MethodFingerprint (
    strings = listOf("android.intent.action.SEARCH", "subscription", "sort", "period", "boostforreddit.com/themes"),
)