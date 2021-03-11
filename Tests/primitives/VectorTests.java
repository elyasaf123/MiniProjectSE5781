package primitives;

import org.junit.jupiter.api.Test;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;




/**
 * Unit tests for primitives.Vector class
 * @author Dan
 */

class VectorTests {

    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void testSubtract() {
        assertEquals(new Vector(3,6,9), v1.subtract(v2), "subtract() wrong result");
    }

    @Test
    void testAdd() {
        assertEquals(new Vector(-1,-2,-3), v1.add(v2), "add() wrong result");
    }

    @Test
    void testScale() {
        assertEquals(new Vector(2,4,6), v1.scale(2), "scale() wrong result");
    }

    @Test
    void testDotProduct() {
        // test Dot-Product
        if (!isZero(v1.dotProduct(v3)))
            out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            out.println("ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    // test length..
    @Test
    void testLengthSquared() {
        if (!isZero(v1.lengthSquared() - 14))
            out.println("ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            out.println("ERROR: length() wrong value");
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(3.5, -5, 10);
        v.normalize();
        assertEquals(1, v.length(), 1e-10);
        try {
            v = new Vector(0, 0, 0);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            v.normalize();
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }


    @Test
    void testNormalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        if (u == v)
            out.println("ERROR: normalized() function does not create a new vector");
    }
}