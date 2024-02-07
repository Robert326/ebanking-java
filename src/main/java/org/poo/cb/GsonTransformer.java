package org.poo.cb;

import com.google.gson.Gson;

public class GsonTransformer {
    public static <T> String convertToJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
