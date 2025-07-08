package io.vellum.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VellumTest {
    private static class TestApp extends Vellum {
        @Override
        public void start() { }

        @Override
        public void update() { }
    }

    @Nested
    @DisplayName("Basic Application Tests")
    class BasicAppTests {
        private TestApp app;

        @BeforeEach
        void setUp() {
            app = new TestApp();
        }

        @Test
        @DisplayName("Vellum can be instantiated with default values")
        void testVellumCreation() {
            assertEquals(800, app.getWidth());
            assertEquals(600, app.getHeight());
            assertEquals(0, app.getFrameCount());
            assertEquals("Vellum Application", app.getTitle());
        }

        @Test
        @DisplayName("Window size can be changed")
        void testWindowSizeChange() {
            app.size(1024, 768);
            assertEquals(1024, app.getWidth());
            assertEquals(768, app.getHeight());
        }

        @Test
        @DisplayName("Title can be set")
        void testTitleSetting() {
            app.setTitle("Test Application");
            assertEquals("Test Application", app.getTitle());
        }
    }

    @Nested
    @DisplayName("Graphics State Tests")
    class GraphicsStateTests {
        private TestApp app;
        
        @BeforeEach
        void setUp() {
            app = new TestApp();
        }
        
        @Test
        @DisplayName("Fill color can be set (RGB)")
        void testFillColorRGB() {
            app.fill(1.0f, 0.5f, 0.0f);
            assertEquals(1.0f, app.fillR, 0.001f);
            assertEquals(0.5f, app.fillG, 0.001f);
            assertEquals(0.0f, app.fillB, 0.001f);
            assertEquals(1.0f, app.fillA, 0.001f);
            assertTrue(app.hasFill);
        }
        
        @Test
        @DisplayName("Fill color can be set (RGBA)")
        void testFillColorRGBA() {
            app.fill(1.0f, 0.5f, 0.0f, 0.7f);
            assertEquals(1.0f, app.fillR, 0.001f);
            assertEquals(0.5f, app.fillG, 0.001f);
            assertEquals(0.0f, app.fillB, 0.001f);
            assertEquals(0.7f, app.fillA, 0.001f);
            assertTrue(app.hasFill);
        }
        
        @Test
        @DisplayName("Fill color can be set (grayscale)")
        void testFillColorGray() {
            app.fill(0.5f);
            assertEquals(0.5f, app.fillR, 0.001f);
            assertEquals(0.5f, app.fillG, 0.001f);
            assertEquals(0.5f, app.fillB, 0.001f);
            assertEquals(1.0f, app.fillA, 0.001f);
            assertTrue(app.hasFill);
        }
        
        @Test
        @DisplayName("Fill can be disabled")
        void testNoFill() {
            app.fill(1.0f, 0.0f, 0.0f);
            assertTrue(app.hasFill);
            
            app.noFill();
            assertFalse(app.hasFill);
        }
        
        @Test
        @DisplayName("Stroke color can be set")
        void testStrokeColor() {
            app.stroke(0.0f, 1.0f, 0.0f);
            assertEquals(0.0f, app.strokeR, 0.001f);
            assertEquals(1.0f, app.strokeG, 0.001f);
            assertEquals(0.0f, app.strokeB, 0.001f);
            assertEquals(1.0f, app.strokeA, 0.001f);
            assertTrue(app.hasStroke);
        }
        
        @Test
        @DisplayName("Stroke can be disabled")
        void testNoStroke() {
            app.stroke(1.0f, 0.0f, 0.0f);
            assertTrue(app.hasStroke);
            
            app.noStroke();
            assertFalse(app.hasStroke);
        }
        
        @Test
        @DisplayName("Stroke weight can be set")
        void testStrokeWeight() {
            app.strokeWeight(3.5f);
            assertEquals(3.5f, app.strokeWeight, 0.001f);
        }
    }

    @Nested
    @DisplayName("Input State Tests")
    class InputStateTests {
        private TestApp app;
        
        @BeforeEach
        void setUp() {
            app = new TestApp();
        }
        
        @Test
        @DisplayName("Mouse position is tracked")
        void testMousePosition() {
            app.mouseX = 150.0f;
            app.mouseY = 200.0f;
            
            assertEquals(150.0f, app.getMouseX(), 0.001f);
            assertEquals(200.0f, app.getMouseY(), 0.001f);
        }
        
        @Test
        @DisplayName("Key state is tracked")
        void testKeyState() {
            // Simulate key press
            app.keys[32] = true; // Space key
            assertTrue(app.isKeyPressed(32));
            
            app.keys[32] = false;
            assertFalse(app.isKeyPressed(32));
        }
        
        @Test
        @DisplayName("Mouse button state is tracked")
        void testMouseButtonState() {
            app.mouseButtons[0] = true; // Left button
            assertTrue(app.isMousePressed(0));
            
            app.mouseButtons[0] = false;
            assertFalse(app.isMousePressed(0));
        }
        
        @Test
        @DisplayName("Key bounds checking works")
        void testKeyBoundsChecking() {
            assertFalse(app.isKeyPressed(-1));
            assertFalse(app.isKeyPressed(1000));
        }
        
        @Test
        @DisplayName("Mouse button bounds checking works")
        void testMouseButtonBoundsChecking() {
            assertFalse(app.isMousePressed(-1));
            assertFalse(app.isMousePressed(10));
        }
    }

    @Nested
    @DisplayName("Callback Tests")
    class CallbackTests {
        private TestApp app;
        private boolean callbackCalled;
        
        @BeforeEach
        void setUp() {
            app = new TestApp();
            callbackCalled = false;
        }
        
        @Test
        @DisplayName("Key callback can be overridden")
        void testKeyCallback() {
            TestApp testApp = new TestApp() {
                @Override
                public void keyPressed(int key, int action, int mods) {
                    callbackCalled = true;
                }
            };
            
            testApp.keyPressed(32, 1, 0);
            assertTrue(callbackCalled);
        }
        
        @Test
        @DisplayName("Mouse callback can be overridden")
        void testMouseCallback() {
            TestApp testApp = new TestApp() {
                @Override
                public void mousePressed(int button, int action, int mods) {
                    callbackCalled = true;
                }
            };
            
            testApp.mousePressed(0, 1, 0);
            assertTrue(callbackCalled);
        }
    }


}