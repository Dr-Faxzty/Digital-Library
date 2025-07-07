package com.example.common.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalDateAdapterTest {

    private Gson gson;

    @BeforeEach
    public void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @Test
    public void testSerialize() {
        LocalDate date = LocalDate.of(2023, 10, 1);
        String json = gson.toJson(date);
        assertEquals("\"2023-10-01\"", json);
    }

    @Test
    public void testDeserialize() {
        String json = "\"2023-10-01\"";
        LocalDate date = gson.fromJson(json, LocalDate.class);
        assertEquals(LocalDate.of(2023, 10, 1), date);
    }

    @Test
    public void testDeserializeInvalid() {
        String invalidJson = "\"not-a-date\"";

        assertThrows(DateTimeParseException.class, () -> {
            gson.fromJson(invalidJson, LocalDate.class);
        });
    }
}