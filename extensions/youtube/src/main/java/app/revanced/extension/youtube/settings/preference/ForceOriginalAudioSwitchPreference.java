package app.revanced.extension.youtube.settings.preference;

import static app.revanced.extension.shared.StringRef.str;

import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;

import app.revanced.extension.youtube.patches.ForceOriginalAudioPatch;

@SuppressWarnings({"deprecation", "unused"})
public class ForceOriginalAudioSwitchPreference extends SwitchPreference {

    {
        if (!ForceOriginalAudioPatch.PATCH_AVAILABLE) {
            // Show why force audio is not available.
            String summary = str("revanced_force_original_audio_not_available");
            setSummary(summary);
            setSummaryOn(summary);
            setSummaryOff(summary);
        }
    }

    public ForceOriginalAudioSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public ForceOriginalAudioSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ForceOriginalAudioSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ForceOriginalAudioSwitchPreference(Context context) {
        super(context);
    }
}
