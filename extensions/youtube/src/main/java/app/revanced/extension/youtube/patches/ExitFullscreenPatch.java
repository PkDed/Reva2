package app.revanced.extension.youtube.patches;

import android.widget.ImageView;

import app.revanced.extension.shared.Logger;
import app.revanced.extension.shared.Utils;
import app.revanced.extension.youtube.settings.Settings;
import app.revanced.extension.youtube.shared.PlayerType;

@SuppressWarnings("unused")
public class ExitFullscreenPatch {

    public enum FullscreenMode {
        DISABLED,
        PORTRAIT,
        LANDSCAPE,
        PORTRAIT_LANDSCAPE,
    }

    /**
     * Injection point.
     */
    public static void endOfVideoReached() {
        try {
            FullscreenMode mode = Settings.EXIT_FULLSCREEN.get();
            if (mode == FullscreenMode.DISABLED) {
                return;
            }

            if (PlayerType.getCurrent() == PlayerType.WATCH_WHILE_FULLSCREEN) {
                if (Utils.isLandscapeOrientation()) {
                    if (mode == FullscreenMode.PORTRAIT) {
                        return;
                    }
                } else if (mode == FullscreenMode.LANDSCAPE) {
                    return;
                }

                ImageView fullscreenButton = PlayerControlsPatch.fullscreenButtonRef.get();
                if (fullscreenButton != null) {
                    Logger.printDebug(() -> "Clicking fullscreen button");
                    fullscreenButton.performClick();
                }
            }
        } catch (Exception ex) {
            Logger.printException(() -> "endOfVideoReached failure", ex);
        }
    }
}