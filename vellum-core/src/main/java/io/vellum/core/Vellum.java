package io.vellum.core;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Vellum {
    /*
     * Design to consider
     * 1. Fail gracefully for graphics, fail fast for logic (error handling)
     * 2. Immediate mode rendering with auto-cleanup (resource management)
     * 3. Single-threaded or allow multi-threading?
     * 4. Immutable graphics state
     * 5. Screen coordinates (top-left origin for screen or bottom-left origin for math)
     * 6. Debug mode
     * 
     */


    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final float NANO_TO_SECOND = 1.0f / 1_000_000_000.0f;

    // Properties
    protected int width = DEFAULT_WIDTH;
    protected int height = DEFAULT_HEIGHT;
    protected String title = "Vellum Application";
    protected long window;

    // Input properties
    protected float mouseX;
    protected float mouseY;
    protected boolean[] keys = new boolean[512];
    protected boolean[] mouseButtons = new boolean[8];

    protected float fillR = 1.0f, fillG = 1.0f, fillB = 1.0f, fillA = 1.0f;
    protected float strokeR = 0.0f, strokeG = 0.0f, strokeB = 1.0f, strokeA = 1.0f;
    protected float strokeWeight = 1.0f;
    protected boolean hasFill = true;
    protected boolean hasStroke = true;

    private boolean isRunning = false;
    private long frameCount = 0;
    private long startTime;


    public final void run() {
        try {
            initialize();
            loop();
        } finally {
            clean();
        }
    }

    private void initialize() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create a GLFW window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        setupInputCallbacks();

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        setup2DProjection();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        startTime = System.nanoTime();
        isRunning = true;

        start();
    }

    private void setup2DProjection() {
        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glDisable(GL_DEPTH_TEST);
    }

    public void start() { }

    private void loop() {
        while (!glfwWindowShouldClose(window) && isRunning) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            update();

            glfwSwapBuffers(window);
            glfwPollEvents();

            frameCount++;
        }
    }

    public void update() { }

    private void clean() {
        if (window != NULL) {
            glfwDestroyWindow(window);
        }

        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public void size(int width, int height) {
        this.width = width;
        this.height = height;
        if (window != NULL) {
            glfwSetWindowSize(window, width, height);
            setup2DProjection();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void background(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public void background(float r, float g, float b) {
        background(r, g, b, 1.0f);
    }

    public void background(float gray) {
        background(gray, gray, gray, 1.0f);
    }

    public void exit() {
        isRunning = false;
    }

    private void setupInputCallbacks() {
        glfwSetCursorPosCallback(window, (windowHandle, x, y) -> {
            mouseX = (float) x;
            mouseY = (float) y;
            mouseMoved(x, y);
        });

        glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
            if (key >= 0 && key < keys.length) {
                keys[key] = action != GLFW_RELEASE;
            }
            keyPressed(key, action, mods);
        });

        glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            if (button >= 0 && button < mouseButtons.length) {
                mouseButtons[button] = action != GLFW_RELEASE;
            }
            mousePressed(button, action, mods);
        });

        glfwSetWindowSizeCallback(window, (windowHandle, w, h) -> {
            width = w;
            height = h;
            setup2DProjection();
            windowResized(w, h);
        });
    }

    public void fill(float r, float g, float b, float a) {
        fillR = r; fillG = g; fillB = b; fillA = a;
        hasFill = true;
    }

    public void fill(float r, float g, float b) {
        fill(r, g, b, 1.0f);
    }

    public void fill(float gray) {
        fill(gray, gray, gray, 1.0f);
    }

    public void noFill() {
        hasFill = false;
    }

    public void stroke(float r, float g, float b, float a) {
        strokeR = r; strokeG = g; strokeB = b; strokeA = a;
        hasStroke = true;
    }

    public void stroke(float r, float g, float b) {
        stroke(r, g, b, 1.0f);
    }
    
    public void stroke(float gray) {
        stroke(gray, gray, gray, 1.0f);
    }

    public void noStroke() {
        hasStroke = false;
    }

    public void strokeWeight(float weight) {
        strokeWeight = weight;
    }


    public int getWidth() { 
        return width;
    }
    public int getHeight() { 
        return height;
    }
    public String getTitle() {
        return title;
    }
    public long getFrameCount() {
        return frameCount;
    }
    public float getFrameRate() {
        long currentTime = System.nanoTime();
        long timeElapsed = currentTime - startTime;
        return frameCount / (timeElapsed * NANO_TO_SECOND);
    }

    public float getMouseX() {
        return mouseX;
    }
    public float getMouseY() {
        return mouseY;
    }
    public boolean isKeyPressed(int key) {
        return key >= 0 && key < keys.length && keys[key];
    }
    public boolean isMousePressed(int button) {
        return button >= 0 && button < mouseButtons.length && mouseButtons[button];
    }

    public void keyPressed(int key, int action, int mods) { }
    public void mousePressed(int button, int action, int mods) { }
    public void mouseMoved(double x, double y) { }
    public void windowResized(int width, int height) { }
}
