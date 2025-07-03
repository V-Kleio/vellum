package io.vellum.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VellumTest {
    private static class TestApp extends Vellum {
        @Override
        public void start() {}

        @Override
        public void update() {}
    }


    @Test
    @DisplayName("Vellum can be instantiated")
    void testVellumCreation() {
        TestApp app = new TestApp();
        assertEquals(800, app.getWidth());
        assertEquals(600, app.getHeight());
        assertEquals(0, app.getFrameCount());
    }
}