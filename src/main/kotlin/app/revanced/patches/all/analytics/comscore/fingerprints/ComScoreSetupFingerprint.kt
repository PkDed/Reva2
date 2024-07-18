package app.revanced.patches.all.analytics.comscore.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

object ComScoreSetupFingerprint : MethodFingerprint(
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    customFingerprint = { methodDef, classDef ->
        classDef.type == "Lcom/comscore/util/setup/Setup;" && methodDef.name == "setUp"
    }
)