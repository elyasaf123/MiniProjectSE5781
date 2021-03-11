package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.Math.sqrt;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class Point3DTests {

    Point3D p1 = new Point3D(1.0d, 2.0d, 3.0d);
    Point3D p2 = new Point3D(1.0000000000000001, 2, 3);

    @Test
    void testEquals() {
    //boolean equality = p1.equals(p2);
    //assertTrue(equality);
        assertEquals(p1, p2);
    }

    @Test
    void testDistanceSquared() {
        Point3D p3 = new Point3D(-1, -1.52, -3);
        double result = p1.distanceSquared(p3);
        assertEquals(52.39, result, 0.001);
        System.out.println(result);
    }

    @Test
    void testDistance() {
        Point3D p3 = new Point3D(-1, -1.52, -3);
        double result = p1.distance(p3);
        assertEquals(sqrt(52.39), result, 0.001);
        System.out.println(result);
    }

    @Test
    void testAdd() {
        // Test operations with points and vectors
        Point3D p1 = new Point3D(1, 2, 3);
        if (!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))))
            out.println("ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        // Test operations with points and vectors
        Point3D p1 = new Point3D(1, 2, 3);
        if (!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)))
            out.println("ERROR: Point - Point does not work correctly");
    }
}