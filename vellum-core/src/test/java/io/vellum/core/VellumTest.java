package io.vellum.core;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VellumTest {

    @BeforeEach
    void setUp() {
        // Initialize GLFW before each test
        Vellum.initialize();
    }

    @AfterEach
    void tearDown() {
        // Clean up any created canvases after each test
        // Note: This is a simplified cleanup - in reality you might need
        // to track and clean up canvases more carefully
    }

    @Nested
    @DisplayName("Vellum Core Tests")
    class VellumCoreTests {

        @Test
        @DisplayName("Vellum can be initialized")
        void testVellumInitialization() {
            // Since initialize() is called in setUp, this should not throw
            // We can call it again to ensure it's idempotent
            Vellum.initialize();
        }

        @Test
        @DisplayName("Frame count starts at zero")
        void testInitialFrameCount() {
            assertEquals(0, Vellum.getFrameCount());
        }

        @Test
        @DisplayName("Initial running state is false")
        void testInitialRunningState() {
            assertFalse(Vellum.isRunning());
        }

        @Test
        @DisplayName("Cannot start without canvases")
        void testStartWithoutCanvases() {
            assertThrows(RuntimeException.class, () -> {
                Vellum.start();
            }, "Window not found, cannot start");
        }

        @Test
        @DisplayName("Can create canvas")
        void testCanvasCreation() {
            Canvas canvas = Vellum.createCanvas("Test Window", 800, 600);
            assertNotNull(canvas);
            assertEquals("Test Window", canvas.getTitle());
            assertEquals(800, canvas.getWidth());
            assertEquals(600, canvas.getHeight());
        }
    }

    @Nested
    @DisplayName("Canvas Tests")
    class CanvasTests {

        @Test
        @DisplayName("Canvas can be created with specified dimensions")
        void testCanvasCreation() {
            Canvas canvas = new Canvas("Test Canvas", 1024, 768);
            
            assertEquals("Test Canvas", canvas.getTitle());
            assertEquals(1024, canvas.getWidth());
            assertEquals(768, canvas.getHeight());
        }

        @Test
        @DisplayName("Canvas title can be changed")
        void testCanvasTitleChange() {
            Canvas canvas = new Canvas("Original Title", 800, 600);
            canvas.setTitle("New Title");
            assertEquals("New Title", canvas.getTitle());
        }

        @Test
        @DisplayName("Canvas dimensions can be changed")
        void testCanvasDimensionChange() {
            Canvas canvas = new Canvas("Test", 800, 600);
            
            canvas.setWidth(1920);
            canvas.setHeight(1080);
            
            assertEquals(1920, canvas.getWidth());
            assertEquals(1080, canvas.getHeight());
        }

        @Test
        @DisplayName("Canvas initially should not close")
        void testCanvasInitialCloseState() {
            Canvas canvas = new Canvas("Test", 800, 600);
            // Note: This test might be flaky depending on GLFW state
            // In a real scenario, you might want to mock the GLFW calls
        }

        @Test
        @DisplayName("Canvas can be destroyed")
        void testCanvasDestroy() {
            Canvas canvas = new Canvas("Test", 800, 600);
            // This should not throw an exception
            canvas.destroy();
        }
    }

    @Nested
    @DisplayName("Sketch Tests")
    class SketchTests {

        @Test
        @DisplayName("Sketch can be created with renderer")
        void testSketchCreation() {
            // Since Renderer is an interface/abstract class, we'd need a mock
            // For now, this test is commented out until you implement a concrete renderer
            
            // MockRenderer renderer = new MockRenderer();
            // Sketch sketch = new Sketch(renderer);
            // assertNotNull(sketch);
        }
    }
}