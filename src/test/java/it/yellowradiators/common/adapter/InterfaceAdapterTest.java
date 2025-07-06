package it.yellowradiators.common.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterfaceAdapterTest {

    interface Shape {
        String getType();
    }

    static class Circle implements Shape {
        private final String type = "circle";
        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        public String getType() {
            return type;
        }
    }

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new InterfaceAdapter<>(Circle.class))
                .create();
    }

    @Test
    void testSerialize() {
        Shape shape = new Circle(5.0);
        String json = gson.toJson(shape, Shape.class);

        assertTrue(json.contains("radius"));
        assertTrue(json.contains("5.0"));
    }

    @Test
    void testDeserialize() {
        String json = "{\"radius\": 10.5}";
        Shape shape = gson.fromJson(json, Shape.class);

        assertNotNull(shape);
        assertInstanceOf(Circle.class, shape);
        assertEquals("circle", shape.getType());
        assertEquals(10.5, ((Circle) shape).getRadius());
    }
}
