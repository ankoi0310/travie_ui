package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.Locale;

public class AppUtil {
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getDateTimeFormatter()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy")))
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString()))
                .create();
    }

    public static void showSnackbar(View view, String text) {
        // show 3 seconds
        Duration duration = Duration.ofSeconds(3);
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setDuration(duration.toMillis() > Integer.MAX_VALUE ? Snackbar.LENGTH_LONG : (int) duration.toMillis())
                .show();
    }

    public static String formatCurrency(int price) {
        // format currency in Vietnam
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return numberFormat.format(price);
    }

    public static String toTimeString(long hour) {
        // if < 10, add 0 before
        return (hour < 10 ? "0" : "") + hour;
    }
}
