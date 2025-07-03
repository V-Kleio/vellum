package io.vellum.core.examples;

import io.vellum.core.Vellum;

public class Example extends Vellum {
    private float time = 0;
    public static void main(String[] args) {
        new Example().run();
    }

    @Override
    public void start() {
        size(1200, 900);
    }

    @Override
    public void update() {
        time += 0.016f;

        float r = (float) Math.sin(time) * 0.5f + 0.5f;
        float g = (float) Math.sin(time * 0.7f) * 0.5f + 0.5f;
        float b = (float) Math.sin(time * 1.3f) * 0.5f + 0.5f;

        background(r, g, b);
    }
}