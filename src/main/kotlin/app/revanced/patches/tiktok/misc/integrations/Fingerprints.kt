package app.revanced.patches.tiktok.misc.integrations

import app.revanced.patches.shared.misc.integrations.integrationsHook
import com.android.tools.smali.dexlib2.AccessFlags

internal val initFingerprint = integrationsHook(
    insertIndexResolver = { 1 }, // Insert after call to super class.
) {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    custom { method, classDef ->
        classDef.endsWith("/AwemeHostApplication;") &&
            method.name == "<init>"
    }
}
