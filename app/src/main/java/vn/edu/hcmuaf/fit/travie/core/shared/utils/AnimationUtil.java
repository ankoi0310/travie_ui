package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import android.view.View;

public class AnimationUtil {

    public static void animateView(final View view, final int toVisibility, float toAlpha, long duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.setClickable(false);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .withEndAction(() -> {
                    if (!show) {
                        view.setVisibility(View.GONE);
                    }
                });
    }
}