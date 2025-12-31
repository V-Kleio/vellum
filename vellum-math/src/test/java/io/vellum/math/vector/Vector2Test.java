package io.vellum.math.vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vector2 Tests")
class Vector2Test {

    private static final float EPSILON = 1e-6f;

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Two-parameter constructor creates vector with correct values")
        void testTwoParameterConstructor() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            assertEquals(3.0f, v.x, EPSILON);
            assertEquals(4.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("Copy constructor creates identical vector")
        void testCopyConstructor() {
            Vector2 original = new Vector2(5.0f, 7.0f);
            Vector2 copy = new Vector2(original);
            assertEquals(original.x, copy.x, EPSILON);
            assertEquals(original.y, copy.y, EPSILON);
        }

        @Test
        @DisplayName("Uniform constructor sets both components to same value")
        void testUniformConstructor() {
            Vector2 v = new Vector2(2.5f);
            assertEquals(2.5f, v.x, EPSILON);
            assertEquals(2.5f, v.y, EPSILON);
        }

        @Test
        @DisplayName("Array constructor creates vector from array")
        void testArrayConstructor() {
            float[] values = {1.5f, 2.5f};
            Vector2 v = new Vector2(values);
            assertEquals(1.5f, v.x, EPSILON);
            assertEquals(2.5f, v.y, EPSILON);
        }

        @Test
        @DisplayName("Array constructor with extra elements uses first two")
        void testArrayConstructorWithExtraElements() {
            float[] values = {1.0f, 2.0f, 3.0f, 4.0f};
            Vector2 v = new Vector2(values);
            assertEquals(1.0f, v.x, EPSILON);
            assertEquals(2.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("Array constructor throws exception for array with less than 2 elements")
        void testArrayConstructorThrowsOnInvalidArray() {
            float[] values = {1.0f};
            assertThrows(IllegalArgumentException.class, () -> new Vector2(values));
        }

        @Test
        @DisplayName("Array constructor throws exception for empty array")
        void testArrayConstructorThrowsOnEmptyArray() {
            float[] values = {};
            assertThrows(IllegalArgumentException.class, () -> new Vector2(values));
        }
    }

    @Nested
    @DisplayName("Static Factory Method Tests")
    class StaticFactoryTests {

        @Test
        @DisplayName("zero() creates vector with both components zero")
        void testZero() {
            Vector2 v = Vector2.zero();
            assertEquals(0.0f, v.x, EPSILON);
            assertEquals(0.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("one() creates vector with both components one")
        void testOne() {
            Vector2 v = Vector2.one();
            assertEquals(1.0f, v.x, EPSILON);
            assertEquals(1.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("unitX() creates unit vector along X axis")
        void testUnitX() {
            Vector2 v = Vector2.unitX();
            assertEquals(1.0f, v.x, EPSILON);
            assertEquals(0.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("unitY() creates unit vector along Y axis")
        void testUnitY() {
            Vector2 v = Vector2.unitY();
            assertEquals(0.0f, v.x, EPSILON);
            assertEquals(1.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("fromAngle() creates unit vector at specified angle")
        void testFromAngle() {
            // Test 0 radians (points right)
            Vector2 v1 = Vector2.fromAngle(0);
            assertEquals(1.0f, v1.x, EPSILON);
            assertEquals(0.0f, v1.y, EPSILON);

            // Test PI/2 radians (points up)
            Vector2 v2 = Vector2.fromAngle((float) Math.PI / 2);
            assertEquals(0.0f, v2.x, EPSILON);
            assertEquals(1.0f, v2.y, EPSILON);

            // Test PI radians (points left)
            Vector2 v3 = Vector2.fromAngle((float) Math.PI);
            assertEquals(-1.0f, v3.x, EPSILON);
            assertEquals(0.0f, v3.y, EPSILON);
        }

        @Test
        @DisplayName("of() creates copy of vector")
        void testOf() {
            Vector2 original = new Vector2(3.0f, 4.0f);
            Vector2 copy = Vector2.of(original);
            assertEquals(original.x, copy.x, EPSILON);
            assertEquals(original.y, copy.y, EPSILON);
            assertNotSame(original, copy);
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {

        @Test
        @DisplayName("set(x, y) updates vector components")
        void testSetXY() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            v.set(5.0f, 6.0f);
            assertEquals(5.0f, v.x, EPSILON);
            assertEquals(6.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("set(x, y) returns this for chaining")
        void testSetXYReturnsThis() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            Vector2 result = v.set(3.0f, 4.0f);
            assertSame(v, result);
        }

        @Test
        @DisplayName("set(Vector2) copies values from another vector")
        void testSetVector() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            Vector2 other = new Vector2(7.0f, 8.0f);
            v.set(other);
            assertEquals(7.0f, v.x, EPSILON);
            assertEquals(8.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("set(Vector2) returns this for chaining")
        void testSetVectorReturnsThis() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            Vector2 result = v.set(new Vector2(3.0f, 4.0f));
            assertSame(v, result);
        }
    }

    @Nested
    @DisplayName("Immutable Addition Tests")
    class ImmutableAdditionTests {

        @Test
        @DisplayName("plus(Vector2) adds vectors correctly")
        void testPlusVector() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(3.0f, 4.0f);
            Vector2 result = v1.plus(v2);

            assertEquals(4.0f, result.x, EPSILON);
            assertEquals(6.0f, result.y, EPSILON);
            assertEquals(1.0f, v1.x, EPSILON);
            assertEquals(2.0f, v1.y, EPSILON);
        }

        @Test
        @DisplayName("plus(scalar) adds scalar to both components")
        void testPlusScalar() {
            Vector2 v = new Vector2(2.0f, 3.0f);
            Vector2 result = v.plus(5.0f);

            assertEquals(7.0f, result.x, EPSILON);
            assertEquals(8.0f, result.y, EPSILON);
            assertEquals(2.0f, v.x, EPSILON);
            assertEquals(3.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("plus() with negative values works correctly")
        void testPlusNegative() {
            Vector2 v1 = new Vector2(5.0f, 6.0f);
            Vector2 v2 = new Vector2(-2.0f, -3.0f);
            Vector2 result = v1.plus(v2);

            assertEquals(3.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Mutable Addition Tests")
    class MutableAdditionTests {

        @Test
        @DisplayName("add(Vector2) modifies vector in place")
        void testAddVector() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(3.0f, 4.0f);
            Vector2 result = v1.add(v2);

            assertEquals(4.0f, v1.x, EPSILON);
            assertEquals(6.0f, v1.y, EPSILON);
            assertSame(v1, result);
        }

        @Test
        @DisplayName("add(scalar) modifies vector in place")
        void testAddScalar() {
            Vector2 v = new Vector2(2.0f, 3.0f);
            Vector2 result = v.add(5.0f);

            assertEquals(7.0f, v.x, EPSILON);
            assertEquals(8.0f, v.y, EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("add() can be chained")
        void testAddChaining() {
            Vector2 v = new Vector2(1.0f, 1.0f);
            v.add(new Vector2(2.0f, 2.0f)).add(3.0f);

            assertEquals(6.0f, v.x, EPSILON);
            assertEquals(6.0f, v.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Immutable Subtraction Tests")
    class ImmutableSubtractionTests {

        @Test
        @DisplayName("minus(Vector2) subtracts vectors correctly")
        void testMinusVector() {
            Vector2 v1 = new Vector2(5.0f, 7.0f);
            Vector2 v2 = new Vector2(2.0f, 3.0f);
            Vector2 result = v1.minus(v2);

            assertEquals(3.0f, result.x, EPSILON);
            assertEquals(4.0f, result.y, EPSILON);
            assertEquals(5.0f, v1.x, EPSILON);
            assertEquals(7.0f, v1.y, EPSILON);
        }

        @Test
        @DisplayName("minus(scalar) subtracts scalar from both components")
        void testMinusScalar() {
            Vector2 v = new Vector2(10.0f, 8.0f);
            Vector2 result = v.minus(3.0f);

            assertEquals(7.0f, result.x, EPSILON);
            assertEquals(5.0f, result.y, EPSILON);
            assertEquals(10.0f, v.x, EPSILON);
            assertEquals(8.0f, v.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Mutable Subtraction Tests")
    class MutableSubtractionTests {

        @Test
        @DisplayName("subtract(Vector2) modifies vector in place")
        void testSubtractVector() {
            Vector2 v1 = new Vector2(5.0f, 7.0f);
            Vector2 v2 = new Vector2(2.0f, 3.0f);
            Vector2 result = v1.subtract(v2);

            assertEquals(3.0f, v1.x, EPSILON);
            assertEquals(4.0f, v1.y, EPSILON);
            assertSame(v1, result);
        }

        @Test
        @DisplayName("subtract(scalar) modifies vector in place")
        void testSubtractScalar() {
            Vector2 v = new Vector2(10.0f, 8.0f);
            Vector2 result = v.subtract(3.0f);

            assertEquals(7.0f, v.x, EPSILON);
            assertEquals(5.0f, v.y, EPSILON);
            assertSame(v, result);
        }
    }

    @Nested
    @DisplayName("Immutable Multiplication Tests")
    class ImmutableMultiplicationTests {

        @Test
        @DisplayName("times(scalar) multiplies both components")
        void testTimesScalar() {
            Vector2 v = new Vector2(2.0f, 3.0f);
            Vector2 result = v.times(4.0f);

            assertEquals(8.0f, result.x, EPSILON);
            assertEquals(12.0f, result.y, EPSILON);
            assertEquals(2.0f, v.x, EPSILON);
            assertEquals(3.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("times(Vector2) multiplies component-wise")
        void testTimesVector() {
            Vector2 v1 = new Vector2(2.0f, 3.0f);
            Vector2 v2 = new Vector2(4.0f, 5.0f);
            Vector2 result = v1.times(v2);

            assertEquals(8.0f, result.x, EPSILON);
            assertEquals(15.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("times() with zero creates zero vector")
        void testTimesZero() {
            Vector2 v = new Vector2(5.0f, 7.0f);
            Vector2 result = v.times(0.0f);

            assertEquals(0.0f, result.x, EPSILON);
            assertEquals(0.0f, result.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Mutable Multiplication Tests")
    class MutableMultiplicationTests {

        @Test
        @DisplayName("multiply(scalar) modifies vector in place")
        void testMultiplyScalar() {
            Vector2 v = new Vector2(2.0f, 3.0f);
            Vector2 result = v.multiply(4.0f);

            assertEquals(8.0f, v.x, EPSILON);
            assertEquals(12.0f, v.y, EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("multiply(Vector2) modifies vector in place")
        void testMultiplyVector() {
            Vector2 v1 = new Vector2(2.0f, 3.0f);
            Vector2 v2 = new Vector2(4.0f, 5.0f);
            Vector2 result = v1.multiply(v2);

            assertEquals(8.0f, v1.x, EPSILON);
            assertEquals(15.0f, v1.y, EPSILON);
            assertSame(v1, result);
        }
    }

    @Nested
    @DisplayName("Immutable Division Tests")
    class ImmutableDivisionTests {

        @Test
        @DisplayName("dividedBy(scalar) divides both components")
        void testDividedByScalar() {
            Vector2 v = new Vector2(8.0f, 12.0f);
            Vector2 result = v.dividedBy(4.0f);

            assertEquals(2.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
            assertEquals(8.0f, v.x, EPSILON);
            assertEquals(12.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("dividedBy(Vector2) divides component-wise")
        void testDividedByVector() {
            Vector2 v1 = new Vector2(8.0f, 15.0f);
            Vector2 v2 = new Vector2(4.0f, 5.0f);
            Vector2 result = v1.dividedBy(v2);

            assertEquals(2.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("dividedBy(0) throws ArithmeticException")
        void testDividedByZeroScalar() {
            Vector2 v = new Vector2(5.0f, 7.0f);
            assertThrows(ArithmeticException.class, () -> v.dividedBy(0.0f));
        }

        @Test
        @DisplayName("dividedBy(Vector2) with zero component throws ArithmeticException")
        void testDividedByZeroVector() {
            Vector2 v1 = new Vector2(5.0f, 0.0f);
            Vector2 v2 = new Vector2(0.0f, 2.0f);
            assertThrows(ArithmeticException.class, () -> v2.dividedBy(v1));
            assertThrows(ArithmeticException.class, () -> v1.dividedBy(v2));
        }
    }

    @Nested
    @DisplayName("Mutable Division Tests")
    class MutableDivisionTests {

        @Test
        @DisplayName("divide(scalar) modifies vector in place")
        void testDivideScalar() {
            Vector2 v = new Vector2(8.0f, 12.0f);
            Vector2 result = v.divide(4.0f);

            assertEquals(2.0f, v.x, EPSILON);
            assertEquals(3.0f, v.y, EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("divide(Vector2) modifies vector in place")
        void testDivideVector() {
            Vector2 v1 = new Vector2(8.0f, 15.0f);
            Vector2 v2 = new Vector2(4.0f, 5.0f);
            Vector2 result = v1.divide(v2);

            assertEquals(2.0f, v1.x, EPSILON);
            assertEquals(3.0f, v1.y, EPSILON);
            assertSame(v1, result);
        }

        @Test
        @DisplayName("divide(0) throws ArithmeticException")
        void testDivideByZeroScalar() {
            Vector2 v = new Vector2(5.0f, 7.0f);
            assertThrows(ArithmeticException.class, () -> v.divide(0.0f));
        }

        @Test
        @DisplayName("divide(Vector2) with zero component throws ArithmeticException")
        void testDivideByZeroVector() {
            Vector2 v1 = new Vector2(0.0f, 7.0f);
            Vector2 v2 = new Vector2(2.0f, 0.0f);
            assertThrows(ArithmeticException.class, () -> v2.divide(v1));
            assertThrows(ArithmeticException.class, () -> v1.divide(v2));
        }
    }

    @Nested
    @DisplayName("Normalization Tests")
    class NormalizationTests {

        @Test
        @DisplayName("normalized() returns unit vector")
        void testNormalized() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.normalized();

            assertEquals(0.6f, result.x, EPSILON);
            assertEquals(0.8f, result.y, EPSILON);
            assertEquals(1.0f, result.magnitude(), EPSILON);
            assertEquals(3.0f, v.x, EPSILON);
            assertEquals(4.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("normalized() on zero vector returns zero vector")
        void testNormalizedZeroVector() {
            Vector2 v = Vector2.zero();
            Vector2 result = v.normalized();

            assertEquals(0.0f, result.x, EPSILON);
            assertEquals(0.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("normalize() modifies vector to unit length")
        void testNormalize() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.normalize();

            assertEquals(0.6f, v.x, EPSILON);
            assertEquals(0.8f, v.y, EPSILON);
            assertEquals(1.0f, v.magnitude(), EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("normalize() on zero vector returns zero vector")
        void testNormalizeZeroVector() {
            Vector2 v = Vector2.zero();
            v.normalize();

            assertEquals(0.0f, v.x, EPSILON);
            assertEquals(0.0f, v.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Rotation Tests")
    class RotationTests {

        @Test
        @DisplayName("rotatedBy() rotates vector by angle")
        void testRotatedBy() {
            Vector2 v = new Vector2(1.0f, 0.0f);
            Vector2 result = v.rotatedBy((float) Math.PI / 2); // 90 degrees

            assertEquals(0.0f, result.x, EPSILON);
            assertEquals(1.0f, result.y, EPSILON);
            assertEquals(1.0f, v.x, EPSILON);
            assertEquals(0.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("rotatedBy() with 180 degrees reverses vector")
        void testRotatedBy180() {
            Vector2 v = new Vector2(1.0f, 0.0f);
            Vector2 result = v.rotatedBy((float) Math.PI);

            assertEquals(-1.0f, result.x, EPSILON);
            assertEquals(0.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("rotate() modifies vector in place")
        void testRotate() {
            Vector2 v = new Vector2(1.0f, 0.0f);
            Vector2 result = v.rotate((float) Math.PI / 2);

            assertEquals(0.0f, v.x, EPSILON);
            assertEquals(1.0f, v.y, EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("rotate() with zero angle does not change vector")
        void testRotateZero() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            v.rotate(0.0f);

            assertEquals(3.0f, v.x, EPSILON);
            assertEquals(4.0f, v.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Negation Tests")
    class NegationTests {

        @Test
        @DisplayName("negated() returns negated vector")
        void testNegated() {
            Vector2 v = new Vector2(3.0f, -4.0f);
            Vector2 result = v.negated();

            assertEquals(-3.0f, result.x, EPSILON);
            assertEquals(4.0f, result.y, EPSILON);
            assertEquals(3.0f, v.x, EPSILON);
            assertEquals(-4.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("negate() modifies vector in place")
        void testNegate() {
            Vector2 v = new Vector2(3.0f, -4.0f);
            Vector2 result = v.negate();

            assertEquals(-3.0f, v.x, EPSILON);
            assertEquals(4.0f, v.y, EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("negating zero vector returns zero vector")
        void testNegateZero() {
            Vector2 v = Vector2.zero();
            Vector2 result = v.negated();

            assertTrue(result.isZero());
        }
    }

    @Nested
    @DisplayName("Absolute Value Tests")
    class AbsoluteTests {

        @Test
        @DisplayName("absolute() returns vector with absolute values")
        void testAbsolute() {
            Vector2 v = new Vector2(-3.0f, -4.0f);
            Vector2 result = v.absolute();

            assertEquals(3.0f, result.x, EPSILON);
            assertEquals(4.0f, result.y, EPSILON);
            assertEquals(-3.0f, v.x, EPSILON);
            assertEquals(-4.0f, v.y, EPSILON);
        }

        @Test
        @DisplayName("abs() modifies vector in place")
        void testAbs() {
            Vector2 v = new Vector2(-3.0f, -4.0f);
            Vector2 result = v.abs();

            assertEquals(3.0f, v.x, EPSILON);
            assertEquals(4.0f, v.y, EPSILON);
            assertSame(v, result);
        }

        @Test
        @DisplayName("absolute() on positive values does not change them")
        void testAbsolutePositive() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.absolute();

            assertEquals(3.0f, result.x, EPSILON);
            assertEquals(4.0f, result.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Dot Product Tests")
    class DotProductTests {

        @Test
        @DisplayName("dot() calculates dot product correctly")
        void testDot() {
            Vector2 v1 = new Vector2(2.0f, 3.0f);
            Vector2 v2 = new Vector2(4.0f, 5.0f);
            float result = v1.dot(v2);

            assertEquals(23.0f, result, EPSILON); // 2*4 + 3*5 = 23
        }

        @Test
        @DisplayName("dot() with perpendicular vectors returns zero")
        void testDotPerpendicular() {
            Vector2 v1 = new Vector2(1.0f, 0.0f);
            Vector2 v2 = new Vector2(0.0f, 1.0f);
            float result = v1.dot(v2);

            assertEquals(0.0f, result, EPSILON);
        }

        @Test
        @DisplayName("dot() with parallel vectors returns product of magnitudes")
        void testDotParallel() {
            Vector2 v1 = new Vector2(2.0f, 0.0f);
            Vector2 v2 = new Vector2(3.0f, 0.0f);
            float result = v1.dot(v2);

            assertEquals(6.0f, result, EPSILON);
        }

        @Test
        @DisplayName("dot() with zero vector returns zero")
        void testDotZero() {
            Vector2 v1 = new Vector2(5.0f, 7.0f);
            Vector2 v2 = Vector2.zero();
            float result = v1.dot(v2);

            assertEquals(0.0f, result, EPSILON);
        }
    }

    @Nested
    @DisplayName("Cross Product Tests")
    class CrossProductTests {

        @Test
        @DisplayName("cross() calculates 2D cross product correctly")
        void testCross() {
            Vector2 v1 = new Vector2(2.0f, 3.0f);
            Vector2 v2 = new Vector2(4.0f, 5.0f);
            float result = v1.cross(v2);

            assertEquals(-2.0f, result, EPSILON); // 2*5 - 3*4 = -2
        }

        @Test
        @DisplayName("cross() with parallel vectors returns zero")
        void testCrossParallel() {
            Vector2 v1 = new Vector2(2.0f, 4.0f);
            Vector2 v2 = new Vector2(1.0f, 2.0f);
            float result = v1.cross(v2);

            assertEquals(0.0f, result, EPSILON);
        }

        @Test
        @DisplayName("cross() is anti-commutative")
        void testCrossAntiCommutative() {
            Vector2 v1 = new Vector2(3.0f, 5.0f);
            Vector2 v2 = new Vector2(7.0f, 11.0f);

            assertEquals(-v1.cross(v2), v2.cross(v1), EPSILON);
        }
    }

    @Nested
    @DisplayName("Magnitude Tests")
    class MagnitudeTests {

        @Test
        @DisplayName("magnitude() calculates vector length correctly")
        void testMagnitude() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            assertEquals(5.0f, v.magnitude(), EPSILON);
        }

        @Test
        @DisplayName("magnitudeSquared() avoids square root calculation")
        void testMagnitudeSquared() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            assertEquals(25.0f, v.magnitudeSquared(), EPSILON);
        }

        @Test
        @DisplayName("magnitude() of zero vector is zero")
        void testMagnitudeZero() {
            Vector2 v = Vector2.zero();
            assertEquals(0.0f, v.magnitude(), EPSILON);
        }

        @Test
        @DisplayName("magnitude() of unit vector is one")
        void testMagnitudeUnit() {
            Vector2 v = Vector2.unitX();
            assertEquals(1.0f, v.magnitude(), EPSILON);
        }
    }

    @Nested
    @DisplayName("Distance Tests")
    class DistanceTests {

        @Test
        @DisplayName("distance() calculates distance between vectors")
        void testDistance() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(4.0f, 6.0f);
            float result = v1.distance(v2);

            assertEquals(5.0f, result, EPSILON); // sqrt(3^2 + 4^2) = 5
        }

        @Test
        @DisplayName("distanceSquared() avoids square root calculation")
        void testDistanceSquared() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(4.0f, 6.0f);
            float result = v1.distanceSquared(v2);

            assertEquals(25.0f, result, EPSILON);
        }

        @Test
        @DisplayName("distance() to self is zero")
        void testDistanceSelf() {
            Vector2 v = new Vector2(5.0f, 7.0f);
            assertEquals(0.0f, v.distance(v), EPSILON);
        }

        @Test
        @DisplayName("distance() is symmetric")
        void testDistanceSymmetric() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(4.0f, 6.0f);

            assertEquals(v1.distance(v2), v2.distance(v1), EPSILON);
        }
    }

    @Nested
    @DisplayName("Angle Tests")
    class AngleTests {

        @Test
        @DisplayName("angle() returns angle from positive X axis")
        void testAngle() {
            // Vector pointing right (0 radians)
            Vector2 v1 = new Vector2(1.0f, 0.0f);
            assertEquals(0.0f, v1.angle(), EPSILON);

            // Vector pointing up (PI/2 radians)
            Vector2 v2 = new Vector2(0.0f, 1.0f);
            assertEquals(Math.PI / 2, v2.angle(), EPSILON);

            // Vector pointing left (PI radians)
            Vector2 v3 = new Vector2(-1.0f, 0.0f);
            assertEquals(Math.PI, Math.abs(v3.angle()), EPSILON);
        }

        @Test
        @DisplayName("angleTo() calculates angle between vectors")
        void testAngleTo() {
            Vector2 v1 = new Vector2(1.0f, 0.0f);
            Vector2 v2 = new Vector2(0.0f, 1.0f);
            float result = v1.angleTo(v2);

            assertEquals(Math.PI / 2, result, EPSILON);
        }

        @Test
        @DisplayName("angleTo() with parallel vectors returns zero")
        void testAngleToParallel() {
            Vector2 v1 = new Vector2(2.0f, 3.0f);
            Vector2 v2 = new Vector2(4.0f, 6.0f);
            float result = v1.angleTo(v2);

            assertEquals(0.0f, result, EPSILON);
        }

        @Test
        @DisplayName("angleTo() with opposite vectors returns π")
        void testAngleToOpposite() {
            Vector2 v1 = new Vector2(1.0f, 0.0f);
            Vector2 v2 = new Vector2(-1.0f, 0.0f);
            float result = v1.angleTo(v2);

            assertEquals(Math.PI, result, EPSILON);
        }

        @Test
        @DisplayName("angleTo() with zero vector returns zero")
        void testAngleToZero() {
            Vector2 v1 = new Vector2(5.0f, 7.0f);
            Vector2 v2 = Vector2.zero();
            float result = v1.angleTo(v2);

            assertEquals(0.0f, result, EPSILON);
        }
    }

    @Nested
    @DisplayName("Static Utility Tests")
    class StaticUtilityTests {

        @Test
        @DisplayName("min() returns component-wise minimum")
        void testMin() {
            Vector2 v1 = new Vector2(2.0f, 7.0f);
            Vector2 v2 = new Vector2(5.0f, 3.0f);
            Vector2 result = Vector2.min(v1, v2);

            assertEquals(2.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("max() returns component-wise maximum")
        void testMax() {
            Vector2 v1 = new Vector2(2.0f, 7.0f);
            Vector2 v2 = new Vector2(5.0f, 3.0f);
            Vector2 result = Vector2.max(v1, v2);

            assertEquals(5.0f, result.x, EPSILON);
            assertEquals(7.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("min() with negative values")
        void testMinNegative() {
            Vector2 v1 = new Vector2(-2.0f, -7.0f);
            Vector2 v2 = new Vector2(-5.0f, -3.0f);
            Vector2 result = Vector2.min(v1, v2);

            assertEquals(-5.0f, result.x, EPSILON);
            assertEquals(-7.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("max() with negative values")
        void testMaxNegative() {
            Vector2 v1 = new Vector2(-2.0f, -7.0f);
            Vector2 v2 = new Vector2(-5.0f, -3.0f);
            Vector2 result = Vector2.max(v1, v2);

            assertEquals(-2.0f, result.x, EPSILON);
            assertEquals(-3.0f, result.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Perpendicular Tests")
    class PerpendicularTests {

        @Test
        @DisplayName("perpendicular() returns perpendicular vector")
        void testPerpendicular() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.perpendicular();

            assertEquals(-4.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("perpendicular() result is perpendicular to original")
        void testPerpendicularOrthogonal() {
            Vector2 v = new Vector2(5.0f, 7.0f);
            Vector2 perp = v.perpendicular();

            assertEquals(0.0f, v.dot(perp), EPSILON);
        }

        @Test
        @DisplayName("perpendicular() preserves magnitude")
        void testPerpendicularMagnitude() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 perp = v.perpendicular();

            assertEquals(v.magnitude(), perp.magnitude(), EPSILON);
        }
    }

    @Nested
    @DisplayName("Query Tests")
    class QueryTests {

        @Test
        @DisplayName("isZero() returns true for zero vector")
        void testIsZero() {
            Vector2 v = Vector2.zero();
            assertTrue(v.isZero());
        }

        @Test
        @DisplayName("isZero() returns false for non-zero vector")
        void testIsZeroNonZero() {
            Vector2 v1 = new Vector2(0.0f, 0.001f);
            Vector2 v2 = new Vector2(0.001f, 0.0f);
            assertFalse(v1.isZero());
            assertFalse(v2.isZero());
        }

        @Test
        @DisplayName("isZero(epsilon) uses epsilon tolerance")
        void testIsZeroEpsilon() {
            Vector2 v = new Vector2(0.0001f, 0.0001f);
            Vector2 v2 = new Vector2(0.0f, 0.00001f);
            assertTrue(v.isZero(0.001f));
            assertFalse(v.isZero(0.00001f));
            assertFalse(v2.isZero(0.00001f));
        }

        @Test
        @DisplayName("isFinite() returns true for finite values")
        void testIsFinite() {
            Vector2 v = new Vector2(5.0f, 7.0f);
            assertTrue(v.isFinite());
        }

        @Test
        @DisplayName("isFinite() returns false for infinite values")
        void testIsFiniteInfinite() {
            Vector2 v = new Vector2(5.0f, Float.POSITIVE_INFINITY);
            assertFalse(v.isFinite());
        }

        @Test
        @DisplayName("isFinite() returns false for NaN values")
        void testIsFiniteNaN() {
            Vector2 v = new Vector2(Float.NaN, 5.0f);
            assertFalse(v.isFinite());
        }
    }

    @Nested
    @DisplayName("Equality Tests")
    class EqualityTests {

        @Test
        @DisplayName("epsilonEquals() returns true for nearly equal vectors")
        void testEpsilonEquals() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(1.00001f, 2.00001f);

            assertTrue(v1.epsilonEquals(v2, 0.001f));
        }

        @Test
        @DisplayName("epsilonEquals() returns false for different vectors")
        void testEpsilonEqualsDifferent() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(1.1f, 2.0f);

            assertFalse(v1.epsilonEquals(v2, 0.001f));
        }

        @Test
        @DisplayName("equals() uses default epsilon")
        void testEquals() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(1.0f, 2.0f);

            assertEquals(v1, v2);
        }

        @Test
        @DisplayName("equals() returns false for different vectors")
        void testEqualsDifferent() {
            Vector2 v1 = new Vector2(1.0f, 2.0f);
            Vector2 v2 = new Vector2(1.0f, 3.0f);

            assertNotEquals(v1, v2);
        }

        @Test
        @DisplayName("equals() returns true for same instance")
        void testEqualsSame() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            assertEquals(v, v);
        }

        @Test
        @DisplayName("equals() returns false for null")
        void testEqualsNull() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            assertNotEquals(v, null);
        }

        @Test
        @DisplayName("equals() returns false for different class")
        void testEqualsDifferentClass() {
            Vector2 v = new Vector2(1.0f, 2.0f);
            assertNotEquals(v, "not a vector");
        }
    }

    @Nested
    @DisplayName("Hash Code Tests")
    class HashCodeTests {

        @Test
        @DisplayName("hashCode() is consistent")
        void testHashCodeConsistent() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            int hash1 = v.hashCode();
            int hash2 = v.hashCode();

            assertEquals(hash1, hash2);
        }

        @Test
        @DisplayName("equal vectors have equal hash codes")
        void testHashCodeEqual() {
            Vector2 v1 = new Vector2(3.0f, 4.0f);
            Vector2 v2 = new Vector2(3.0f, 4.0f);

            assertEquals(v1.hashCode(), v2.hashCode());
        }

        @Test
        @DisplayName("different vectors likely have different hash codes")
        void testHashCodeDifferent() {
            Vector2 v1 = new Vector2(3.0f, 4.0f);
            Vector2 v2 = new Vector2(5.0f, 6.0f);

            // Note: hash collisions are possible but unlikely for these values
            assertNotEquals(v1.hashCode(), v2.hashCode());
        }
    }

    @Nested
    @DisplayName("String Representation Tests")
    class ToStringTests {

        @Test
        @DisplayName("toString() returns formatted string")
        void testToString() {
            Vector2 v = new Vector2(3.14159f, 2.71828f);
            String result = v.toString();

            assertTrue(result.contains("3.14"));
            assertTrue(result.contains("2.72"));
            assertTrue(result.contains("Vector2"));
        }

        @Test
        @DisplayName("toString() formats zero correctly")
        void testToStringZero() {
            Vector2 v = Vector2.zero();
            String result = v.toString();

            assertTrue(result.contains("0.00"));
        }
    }

    @Nested
    @DisplayName("Swizzle Tests")
    class SwizzleTests {

        @Test
        @DisplayName("x() returns x component")
        void testX() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            assertEquals(3.0f, v.x(), EPSILON);
        }

        @Test
        @DisplayName("y() returns y component")
        void testY() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            assertEquals(4.0f, v.y(), EPSILON);
        }

        @Test
        @DisplayName("xy() returns copy of vector")
        void testXY() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.xy();

            assertEquals(3.0f, result.x, EPSILON);
            assertEquals(4.0f, result.y, EPSILON);
            assertNotSame(v, result);
        }

        @Test
        @DisplayName("yx() returns swapped components")
        void testYX() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.yx();

            assertEquals(4.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("xx() returns x for both components")
        void testXX() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.xx();

            assertEquals(3.0f, result.x, EPSILON);
            assertEquals(3.0f, result.y, EPSILON);
        }

        @Test
        @DisplayName("yy() returns y for both components")
        void testYY() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 result = v.yy();

            assertEquals(4.0f, result.x, EPSILON);
            assertEquals(4.0f, result.y, EPSILON);
        }
    }

    @Nested
    @DisplayName("Method Chaining Tests")
    class MethodChainingTests {

        @Test
        @DisplayName("Mutable operations can be chained")
        void testMutableChaining() {
            Vector2 v = new Vector2(1.0f, 1.0f);
            v.add(new Vector2(2.0f, 2.0f))
             .multiply(2.0f)
             .subtract(1.0f)
             .normalize();

            float mag = v.magnitude();
            assertEquals(1.0f, mag, EPSILON);
        }

        @Test
        @DisplayName("Set operations can be chained")
        void testSetChaining() {
            Vector2 v = new Vector2(0.0f, 0.0f);
            Vector2 result = v.set(5.0f, 7.0f).add(1.0f).multiply(2.0f);

            assertEquals(12.0f, v.x, EPSILON);
            assertEquals(16.0f, v.y, EPSILON);
            assertSame(v, result);
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Operations with very small values")
        void testVerySmallValues() {
            Vector2 v = new Vector2(1e-10f, 1e-10f);
            Vector2 result = v.times(2.0f);

            assertEquals(2e-10f, result.x, 1e-15f);
            assertEquals(2e-10f, result.y, 1e-15f);
        }

        @Test
        @DisplayName("Operations with very large values")
        void testVeryLargeValues() {
            Vector2 v = new Vector2(1e10f, 1e10f);
            Vector2 result = v.dividedBy(2.0f);

            assertEquals(5e9f, result.x, 1.0f);
            assertEquals(5e9f, result.y, 1.0f);
        }

        @Test
        @DisplayName("Normalized vector maintains unit length after operations")
        void testNormalizedMaintainsLength() {
            Vector2 v = new Vector2(3.0f, 4.0f);
            Vector2 normalized = v.normalized();
            Vector2 scaled = normalized.times(5.0f);

            assertEquals(5.0f, scaled.magnitude(), EPSILON);
        }

        @Test
        @DisplayName("Multiple rotations accumulate correctly")
        void testMultipleRotations() {
            Vector2 v = new Vector2(1.0f, 0.0f);
            float angle = (float) Math.PI / 4; // 45 degrees

            v = v.rotatedBy(angle).rotatedBy(angle);

            // Should be at 90 degrees
            assertEquals(0.0f, v.x, EPSILON);
            assertEquals(1.0f, v.y, EPSILON);
        }
    }
}
