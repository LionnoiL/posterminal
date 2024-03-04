package ua.gaponov.posterminal.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static <T> List<T> convertJsonStringToList(String response,
                                                      Class<T> typeListElementClass) {
        Type type = TypeToken.getParameterized(List.class,
                typeListElementClass
        ).getType();
        return GSON.fromJson(response, type);
    }
}
