package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class GsonUtil {
    private static final GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json
                    , typeOfT, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getSimpleDateFormat()))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json
                    , typeOfT, context) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString()));

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = gsonBuilder.create();
        }
        return gson;
    }
}
