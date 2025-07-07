package com.example.common.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class InterfaceAdapter<T> implements JsonDeserializer<T>, JsonSerializer<T> {
    private final Class<? extends T> implementation;

    public InterfaceAdapter(Class<? extends T> implementation) {
        this.implementation = implementation;
    }

    @Override
    public JsonElement serialize(T object, Type type, JsonSerializationContext context) {
        return context.serialize(object);
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(jsonElement, implementation);
    }
}
