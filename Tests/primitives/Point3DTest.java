package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    Point3D p1 = new Point3D(1.0d, 2.0d, 3.0d);
    Point3D p2 = new Point3D(1.0000000000000001, 2, 3);

    @Test
    void testEquals() {
//        boolean equality = p1.equals(p2);
//        assertTrue(equality);
        assertEquals(p1, p2);
    }

    @Test
    void distanceSquared() {
        Point3D p3 = new Point3D(-1, -1.52, -3);
        double result = p1.distanceSquared(p3);
        assertEquals(52.39, result, 0.001);
        System.out.println(result);
    }
}