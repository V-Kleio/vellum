package io.vellum.math.vector;

public final class Vector3 {
    private static final float DEFAULT_EPSILON = 1e-6f;

    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector3(Vector2 other, float z) {
        this.x = other.x;
        this.y = other.y;
        this.z = z;
    }

    public Vector3(float uniform) {
        this.x = uniform;
        this.y = uniform;
        this.z = uniform;
    }

    public Vector3(float[] values) {
        if (values.length < 3) {
            throw new IllegalArgumentException("Array must have at least 3 elements.");
        }
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
    }

    public static Vector3 zero() {
        return new Vector3(0);
    }

    public static Vector3 one() {
        return new Vector3(1);
    }

    public static Vector3 unitX() {
        return new Vector3(1, 0, 0);
    }

    public static Vector3 unitY() {
        return new Vector3(0, 1, 0);
    }

    public static Vector3 unitZ() {
        return new Vector3(0, 0, 1);
    }

    public static Vector3 fromAngles(float yaw, float pitch) {
        double x = Math.cos(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch);
        double z = Math.cos(pitch) * Math.sin(yaw);
        return new Vector3((float) x, (float) y, (float) z);
    }

    public static Vector3 of(Vector3 v) {
        return new Vector3(v);
    }

    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 set(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Vector3 plus(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3 plus(float scalar) {
        return new Vector3(this.x + scalar, this.y + scalar, this.z + scalar);
    }

    public Vector3 add(Vector3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public Vector3 add(float scalar) {
        this.x += scalar;
        this.y += scalar;
        this.z += scalar;
        return this;
    }

    public Vector3 minus(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3 minus(float scalar) {
        return new Vector3(this.x - scalar, this.y - scalar, this.z - scalar);
    }

    public Vector3 subtract(Vector3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public Vector3 subtract(float scalar) {
        this.x -= scalar;
        this.y -= scalar;
        this.z -= scalar;
        return this;
    }

    public Vector3 times(float scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 times(Vector3 other) {
        return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vector3 multiply(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vector3 multiply(Vector3 other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public Vector3 dividedBy(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Vector3(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    public Vector3 dividedBy(Vector3 other) {
        if (other.x == 0 || other.y == 0 || other.z == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Vector3(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public Vector3 divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

    public Vector3 divide(Vector3 other) {
        if (other.x == 0 || other.y == 0 || other.z == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        return this;
    }
}
