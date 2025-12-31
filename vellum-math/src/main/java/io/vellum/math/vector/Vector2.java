package io.vellum.math.vector;

/**
 * A 2-dimensional vector with single-precision floating-point components.
 * <p>
 * This class represents a point or direction in 2D space and provides comprehensive
 * operations for vector arithmetic, transformations, and geometric calculations.
 * Vector2 offers both immutable operations (returning new vectors) and mutable operations
 * (modifying the vector in place) for flexibility and performance.
 * </p>
 * <p>
 * The class follows a naming convention where methods ending in past participles
 * (e.g., {@code normalized()}, {@code rotatedBy()}) return new vectors, while
 * present-tense methods (e.g., {@code normalize()}, {@code rotate()}) modify
 * the current vector.
 * </p>
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 *Vector2 position = new Vector2(10, 20);
 *Vector2 velocity = Vector2.fromAngle(Math.PI / 4).multiply(5);
 *position.add(velocity);
 *
 *Vector2 direction = new Vector2(3, 4);
 *Vector2 unit = direction.normalized();
 *float length = direction.magnitude();
 * }</pre>
 *
 * @see #zero()
 * @see #one()
 * @see #fromAngle(float)
 */
public final class Vector2 {
    private static final float DEFAULT_EPSILON = 1e-6f;

    /**
     * The x-component of this vector.
     */
    public float x;

    /**
     * The y-component of this vector.
     */
    public float y;

    /**
     * Constructs a vector with the specified x and y components.
     *
     * @param x the x-component
     * @param y the y-component
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a vector by copying another vector.
     *
     * @param other the vector to copy
     */
    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Constructs a vector with both components set to the same value.
     *
     * @param uniform the value for both x and y components
     */
    public Vector2(float uniform) {
        this.x = uniform;
        this.y = uniform;
    }

    /**
     * Constructs a vector from an array of values.
     * Uses the first two elements of the array for x and y components.
     *
     * @param values the array containing at least 2 elements
     * @throws IllegalArgumentException if the array has fewer than 2 elements
     */
    public Vector2(float[] values) {
        if (values.length < 2) {
            throw new IllegalArgumentException("Array must have at least 2 elements.");
        }
        this.x = values[0];
        this.y = values[1];
    }

    /**
     * Returns a new vector with both components set to zero (0, 0).
     *
     * @return a zero vector
     */
    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    /**
     * Returns a new vector with both components set to one (1, 1).
     *
     * @return a vector with all components equal to one
     */
    public static Vector2 one() {
        return new Vector2(1, 1);
    }

    /**
     * Returns a new unit vector pointing along the positive X-axis (1, 0).
     *
     * @return a unit vector in the x direction
     */
    public static Vector2 unitX() {
        return new Vector2(1, 0);
    }

    /**
     * Returns a new unit vector pointing along the positive Y-axis (0, 1).
     *
     * @return a unit vector in the y direction
     */
    public static Vector2 unitY() {
        return new Vector2(0, 1);
    }

    /**
     * Creates a unit vector from an angle in radians.
     * The angle is measured counter-clockwise from the positive X-axis.
     *
     * @param angleRadians the angle in radians
     * @return a unit vector pointing in the specified direction
     */
    public static Vector2 fromAngle(float angleRadians) {
        return new Vector2((float) Math.cos(angleRadians), (float) Math.sin(angleRadians));
    }

    /**
     * Creates a copy of the specified vector.
     * This is equivalent to calling the copy constructor.
     *
     * @param v the vector to copy
     * @return a new vector with the same components as the input
     */
    public static Vector2 of(Vector2 v) {
        return new Vector2(v);
    }

    /**
     * Sets the components of this vector to the specified values.
     *
     * @param x the new x-component
     * @param y the new y-component
     * @return this vector for method chaining
     */
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Sets the components of this vector to match another vector.
     *
     * @param other the vector to copy values from
     * @return this vector for method chaining
     */
    public Vector2 set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    /**
     * Returns a new vector that is the sum of this vector and another vector.
     * This vector remains unchanged.
     *
     * @param other the vector to add
     * @return a new vector containing the sum
     */
    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    /**
     * Returns a new vector with a scalar value added to both components.
     * This vector remains unchanged.
     *
     * @param scalar the value to add to both components
     * @return a new vector with the scalar added
     */
    public Vector2 plus(float scalar) {
        return new Vector2(this.x + scalar, this.y + scalar);
    }

    /**
     * Adds another vector to this vector, modifying this vector.
     *
     * @param other the vector to add
     * @return this vector for method chaining
     */
    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    /**
     * Adds a scalar value to both components of this vector.
     *
     * @param scalar the value to add to both components
     * @return this vector for method chaining
     */
    public Vector2 add(float scalar) {
        this.x += scalar;
        this.y += scalar;
        return this;
    }

    /**
     * Returns a new vector that is the difference between this vector and another vector.
     * This vector remains unchanged.
     *
     * @param other the vector to subtract
     * @return a new vector containing the difference
     */
    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    /**
     * Returns a new vector with a scalar value subtracted from both components.
     * This vector remains unchanged.
     *
     * @param scalar the value to subtract from both components
     * @return a new vector with the scalar subtracted
     */
    public Vector2 minus(float scalar) {
        return new Vector2(this.x - scalar, this.y - scalar);
    }

    /**
     * Subtracts another vector from this vector, modifying this vector.
     *
     * @param other the vector to subtract
     * @return this vector for method chaining
     */
    public Vector2 subtract(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    /**
     * Subtracts a scalar value from both components of this vector.
     *
     * @param scalar the value to subtract from both components
     * @return this vector for method chaining
     */
    public Vector2 subtract(float scalar) {
        this.x -= scalar;
        this.y -= scalar;
        return this;
    }

    /**
     * Returns a new vector with all components multiplied by a scalar value.
     * This vector remains unchanged.
     *
     * @param scalar the value to multiply by
     * @return a new vector scaled by the scalar
     */
    public Vector2 times(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    /**
     * Returns a new vector with components multiplied element-wise with another vector.
     * This vector remains unchanged.
     *
     * @param other the vector to multiply with
     * @return a new vector with element-wise multiplication
     */
    public Vector2 times(Vector2 other) {
        return new Vector2(this.x * other.x, this.y * other.y);
    }

    /**
     * Multiplies all components of this vector by a scalar value.
     *
     * @param scalar the value to multiply by
     * @return this vector for method chaining
     */
    public Vector2 multiply(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    /**
     * Multiplies the components of this vector element-wise with another vector.
     *
     * @param other the vector to multiply with
     * @return this vector for method chaining
     */
    public Vector2 multiply(Vector2 other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    /**
     * Returns a new vector with all components divided by a scalar value.
     * This vector remains unchanged.
     *
     * @param scalar the value to divide by
     * @return a new vector divided by the scalar
     * @throws ArithmeticException if scalar is zero
     */
    public Vector2 dividedBy(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Vector2(this.x / scalar, this.y / scalar);
    }

    /**
     * Returns a new vector with components divided element-wise by another vector.
     * This vector remains unchanged.
     *
     * @param other the vector to divide by
     * @return a new vector with element-wise division
     * @throws ArithmeticException if any component of the divisor is zero
     */
    public Vector2 dividedBy(Vector2 other) {
        if (other.x == 0 || other.y == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return new Vector2(this.x / other.x, this.y / other.y);
    }

    /**
     * Divides all components of this vector by a scalar value.
     *
     * @param scalar the value to divide by
     * @return this vector for method chaining
     * @throws ArithmeticException if scalar is zero
     */
    public Vector2 divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    /**
     * Divides the components of this vector element-wise by another vector.
     *
     * @param other the vector to divide by
     * @return this vector for method chaining
     * @throws ArithmeticException if any component of the divisor is zero
     */
    public Vector2 divide(Vector2 other) {
        if (other.x == 0 || other.y == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    /**
     * Returns a new normalized (unit length) version of this vector.
     * This vector remains unchanged. If this vector is zero, returns a zero vector.
     *
     * @return a new unit vector pointing in the same direction
     */
    public Vector2 normalized() {
        float mag = magnitude();
        if (mag == 0) {
            return Vector2.zero();
        }
        return dividedBy(mag);
    }

    /**
     * Normalizes this vector to unit length.
     * If this vector is zero, it remains unchanged.
     *
     * @return this vector for method chaining
     */
    public Vector2 normalize() {
        float mag = magnitude();
        if (mag == 0) {
            this.x = 0;
            this.y = 0;
        } else {
            this.x /= mag;
            this.y /= mag;
        }
        return this;
    }

    /**
     * Returns a new vector rotated by the specified angle.
     * This vector remains unchanged. The rotation is counter-clockwise.
     *
     * @param angleRadians the angle to rotate by, in radians
     * @return a new rotated vector
     */
    public Vector2 rotatedBy(float angleRadians) {
        float cos = (float) Math.cos(angleRadians);
        float sin = (float) Math.sin(angleRadians);
        return new Vector2(
            this.x * cos - this.y * sin,
            this.x * sin + this.y * cos
        );
    }

    /**
     * Rotates this vector by the specified angle counter-clockwise.
     *
     * @param angleRadians the angle to rotate by, in radians
     * @return this vector for method chaining
     */
    public Vector2 rotate(float angleRadians) {
        float cos = (float) Math.cos(angleRadians);
        float sin = (float) Math.sin(angleRadians);
        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;
        this.x = newX;
        this.y = newY;
        return this;
    }

    /**
     * Returns a new vector with all components negated.
     * This vector remains unchanged.
     *
     * @return a new vector pointing in the opposite direction
     */
    public Vector2 negated() {
        return new Vector2(-this.x, -this.y);
    }

    /**
     * Negates all components of this vector.
     *
     * @return this vector for method chaining
     */
    public Vector2 negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    /**
     * Returns a new vector with the absolute value of each component.
     * This vector remains unchanged.
     *
     * @return a new vector with all positive components
     */
    public Vector2 absolute() {
        return new Vector2(Math.abs(this.x), Math.abs(this.y));
    }

    /**
     * Takes the absolute value of each component of this vector.
     *
     * @return this vector for method chaining
     */
    public Vector2 abs() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        return this;
    }

    /**
     * Calculates the dot product between this vector and another vector.
     * The dot product is useful for determining the angle between vectors
     * and for projection operations.
     *
     * @param other the other vector
     * @return the dot product of the two vectors
     */
    public float dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Calculates the 2D cross product (scalar perpendicular product) between this
     * vector and another vector. The result is the z-component of the 3D cross product
     * if both vectors were extended to 3D with z=0.
     * <p>
     * A positive result indicates a counter-clockwise rotation from this vector to the
     * other vector, while a negative result indicates a clockwise rotation.
     * </p>
     *
     * @param other the other vector
     * @return the scalar cross product
     */
    public float cross(Vector2 other) {
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Calculates the magnitude (length) of this vector.
     *
     * @return the magnitude of this vector
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculates the squared magnitude of this vector.
     * This is more efficient than {@link #magnitude()} when comparing lengths,
     * as it avoids the square root calculation.
     *
     * @return the squared magnitude of this vector
     */
    public float magnitudeSquared() {
        return x * x + y * y;
    }

    /**
     * Calculates the Euclidean distance between this vector and another vector.
     *
     * @param other the other vector
     * @return the distance between the two vectors
     */
    public float distance(Vector2 other) {
        return (float) Math.sqrt(distanceSquared(other));
    }

    /**
     * Calculates the squared Euclidean distance between this vector and another vector.
     * This is more efficient than {@link #distance(Vector2)} when comparing distances,
     * as it avoids the square root calculation.
     *
     * @param other the other vector
     * @return the squared distance between the two vectors
     */
    public float distanceSquared(Vector2 other) {
        float dx = x - other.x;
        float dy = y - other.y;
        return (dx * dx + dy * dy);
    }

    /**
     * Calculates the angle of this vector in radians.
     * The angle is measured counter-clockwise from the positive X-axis.
     * Returns a value in the range [-PI, PI].
     *
     * @return the angle in radians
     */
    public float angle() {
        return (float) Math.atan2(y, x);
    }

    /**
     * Calculates the angle between this vector and another vector in radians.
     * Returns a value in the range [0, PI].
     * If either vector is zero, returns 0.
     *
     * @param other the other vector
     * @return the angle between the vectors in radians
     */
    public float angleTo(Vector2 other) {
        float denom = magnitude() * other.magnitude();
        if (denom == 0) {
            return 0f;
        }
        float cos = dot(other) / denom;
        cos = Math.max(-1f, Math.min(1f, cos));
        return (float) Math.acos(cos);
    }

    /**
     * Returns a new vector with the minimum components from two vectors.
     * Each component is the minimum of the corresponding components.
     *
     * @param a the first vector
     * @param b the second vector
     * @return a new vector with minimum components
     */
    public static Vector2 min(Vector2 a, Vector2 b) {
        return new Vector2(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    /**
     * Returns a new vector with the maximum components from two vectors.
     * Each component is the maximum of the corresponding components.
     *
     * @param a the first vector
     * @param b the second vector
     * @return a new vector with maximum components
     */
    public static Vector2 max(Vector2 a, Vector2 b) {
        return new Vector2(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }

    /**
     * Returns a new vector perpendicular to this vector.
     * The perpendicular vector is rotated 90 degrees counter-clockwise.
     *
     * @return a new perpendicular vector
     */
    public Vector2 perpendicular() {
        return new Vector2(-y, x);
    }

    /**
     * Checks if this vector is exactly zero.
     *
     * @return true if both components are exactly 0, false otherwise
     */
    public boolean isZero() {
        return x == 0 && y == 0;
    }

    /**
     * Checks if this vector is approximately zero within a tolerance.
     *
     * @param epsilon the tolerance value
     * @return true if both components are within epsilon of 0, false otherwise
     */
    public boolean isZero(float epsilon) {
        return Math.abs(x) < epsilon && Math.abs(y) < epsilon;
    }

    /**
     * Checks if all components of this vector are finite (not NaN or infinite).
     *
     * @return true if both components are finite, false otherwise
     */
    public boolean isFinite() {
        return Float.isFinite(x) && Float.isFinite(y);
    }

    /**
     * Checks if this vector is approximately equal to another vector within a tolerance.
     *
     * @param other the other vector
     * @param epsilon the tolerance value
     * @return true if the difference in each component is less than epsilon, false otherwise
     */
    public boolean epsilonEquals(Vector2 other, float epsilon) {
        return Math.abs(this.x - other.x) < epsilon && Math.abs(this.y - other.y) < epsilon;
    }

    /**
     * Checks if this vector is equal to another object.
     * Two vectors are considered equal if their components are within a small epsilon.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vector2 other = (Vector2) obj;
        return epsilonEquals(other, DEFAULT_EPSILON);
    }

    /**
     * Returns a hash code value for this vector.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        int result = Float.hashCode(x);
        result = 31 * result + Float.hashCode(y);
        return result;
    }

    /**
     * Returns a string representation of this vector.
     * The format is "Vector2(x, y)" with components formatted to 2 decimal places.
     *
     * @return a string representation of this vector
     */
    @Override
    public String toString() {
        return String.format("Vector2(%.2f, %.2f)", x, y);
    }

    // --- Swizzles ---

    /**
     * Returns the x-component of this vector.
     *
     * @return the x-component
     */
    public float x() {
        return x;
    }

    /**
     * Returns the y-component of this vector.
     *
     * @return the y-component
     */
    public float y() {
        return y;
    }

    /**
     * Returns a new vector with components (x, y).
     * This is equivalent to creating a copy of this vector.
     *
     * @return a new vector (x, y)
     */
    public Vector2 xy() {
        return new Vector2(x, y);
    }

    /**
     * Returns a new vector with swapped components (y, x).
     *
     * @return a new vector (y, x)
     */
    public Vector2 yx() {
        return new Vector2(y, x);
    }

    /**
     * Returns a new vector with both components set to the x-component (x, x).
     *
     * @return a new vector (x, x)
     */
    public Vector2 xx() {
        return new Vector2(x, x);
    }

    /**
     * Returns a new vector with both components set to the y-component (y, y).
     *
     * @return a new vector (y, y)
     */
    public Vector2 yy() {
        return new Vector2(y, y);
    }
}
