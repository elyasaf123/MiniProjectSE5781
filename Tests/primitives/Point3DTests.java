package primitives;

import org.junit.jupiter.api.*;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Point3D.*;

/**
 * Unit tests for primitives.Point3D class
 */
class Point3DTests {

    /**
     * point for tests
     */
    Point3D p1 = new Point3D(1.0d, 2.0d, 3.0d);
    /**
     * point for tests
     */
    Point3D p2 = new Point3D(1.0000000000000001, 2, 3);

    /**
     * Test method for {@link primitives.Point3D#equals(Object)} (Vector)} (primitives.Vector)}.
     */
    @Test
    void testEquals() {
        assertEquals(p1, p2);
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D)} (Object)} (Vector)} (primitives.Vector)}.
     */
    @Test
    void testDistanceSquared() {
        Point3D p3 = new Point3D(-1, -1.52, -3);
        double result = p1.distanceSquared(p3);
        assertEquals(52.39, result, 0.001);
        //System.out.println(result);
    }

    /**
     * Test method for {@link primitives.Point3D#distance(Point3D)} (Object)} (Vector)} (primitives.Vector)}.
     */
    @Test
    void testDistance() {
        Point3D p3 = new Point3D(-1, -1.52, -3);
        double result = p1.distance(p3);
        assertEquals(sqrt(52.39), result, 0.001);
        //System.out.println(result);
    }

    /**
     * Test method for {@link primitives.Point3D#add(Vector)} (Object)} (Vector)} (primitives.Vector)}.
     *
     * Test operations with points and vectors
     */
    @Test
    void testAdd() {
        assertEquals(ZERO,(new Point3D(1, 2, 3)).add(new Vector(-1, -2, -3)),"ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)} (Object)} (Vector)} (primitives.Vector)}.
     *
     * Test operations with points and vectors
     */
    @Test
    void testSubtract() {
        assertEquals(new Vector(1, 1, 1),new Point3D(2, 3, 4).subtract(new Point3D(1, 2, 3)),"ERROR: Point - Point does not work correctly");
    }
}