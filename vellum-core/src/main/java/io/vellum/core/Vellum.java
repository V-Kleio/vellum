package io.vellum.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import org.lwjgl.glfw.GLFWErrorCallback;
import static org.lwjgl.system.MemoryUtil.NULL;


public final class Vellum {
    private static final List<Canvas> canvases = new ArrayList<>();

    private static boolean isRunning = false;
    private static long startTime;
    private static long frameCount;
    private static long primaryWindow = NULL;
    private static boolean capabilitiesCreated = false;

    @SuppressWarnings("unused")
    private Vellum() { } // prevent instantiation

    public static void start() {
        if (canvases.isEmpty()) {
            throw new RuntimeException("Window not found, cannot start");
        }
        isRunning = true;
        startTime = System.nanoTime();
        frameCount = 0;
        try {
            loop();
        } finally {
            clean();
        }
    }

    public static void initialize() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }

    public static Canvas createCanvas(String title, int width, int height) {
        Canvas canvas = new Canvas(title, width, height);
        addCanvas(canvas);
        return canvas;
    }



    // private void setup2DProjection() {
    //     glViewport(0, 0, width, height);
    //     glMatrixMode(GL_PROJECTION);
    //     glLoadIdentity();
    //     glOrtho(0, width, height, 0, -1, 1);
    //     glMatrixMode(GL_MODELVIEW);
    //     glLoadIdentity();

    //     glDisable(GL_DEPTH_TEST);
    // }

    private static void loop() {
        while (!canvases.isEmpty() && isRunning()) {
            glfwPollEvents();

            for (Iterator<Canvas> iterator = canvases.iterator(); iterator.hasNext();) {
                Canvas canvas = iterator.next();
                if (canvas.shouldClose()) {
                    canvas.destroy();
                    iterator.remove();
                } else {
                    canvas.update();
                }
            }

            frameCount++;
        }
    }

    private static void clean() {
        if (!canvases.isEmpty()) {
            for (Canvas canvas : canvases) {
                canvas.destroy();
            }
        }

        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    static void addCanvas(Canvas canvas) {
        canvases.add(canvas);
    }

    static void removeCanvas(Canvas canvas) {
        canvases.remove(canvas);
    }

    static long getPrimaryWindow() {
        return primaryWindow;
    }

    static void setPrimaryWindow(long primaryWindow) {
        Vellum.primaryWindow = primaryWindow;
    }

    static boolean isCapabilitiesCreated() {
        return capabilitiesCreated;
    }

    static void setCapabilitiesCreated(boolean capabilitiesCreated) {
        Vellum.capabilitiesCreated = capabilitiesCreated;
    }

    static long getFrameCount() {
        return frameCount;
    }

    static boolean isRunning() {
        return isRunning;
    }
}
