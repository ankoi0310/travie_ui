package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import android.graphics.Color;

import androidx.annotation.StringRes;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;

public class AppUtil {
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static @StringRes int getErrorMessage(DataError error) {
        if (error.equals(DataError.NETWORK.UNKNOWN)) {
            return R.string.error_network_unknown;
        }
        return R.string.error_network_unknown;
    }
}
