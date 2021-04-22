package primitives;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test to check getClosestPoint function in RAY class
 */
class RayTest {
    Ray ray = new Ray(Point3D.ZERO, new Vector(0,0,1));

    /**
     * the closest point is the middle point in the list
     */
    @Test
    void testGetClosestPointEP() {
        List<Point3D> point3DS = new  LinkedList<>();
        point3DS.add(new Point3D(-1000,90,100));
        point3DS.add(new Point3D(50,48,1000));
        point3DS.add(new Point3D(0,.5,1));
        point3DS.add(new Point3D(-20,60,50));
        point3DS.add(new Point3D(0,0,-90));
        assertEquals(point3DS.get(2),ray.getClosestPoint(point3DS),"supposed to be null");
    }

    /**
     * empty/null list
     */
    @Test
    void testGetClosestPointBVA1() {
        List<Point3D> point3DS = null;
        assertNull(ray.getClosestPoint(null),"supposed to be null");
    }

    /**
     * the closest point is the first point in the list
     */
    @Test
    void testGetClosestPointBVA2() {
        List<Point3D> point3DS = new  LinkedList<>();
        point3DS.add(new Point3D(0,.5,1));
        point3DS.add(new Point3D(-1000,90,100));
        point3DS.add(new Point3D(50,48,1000));
        point3DS.add(new Point3D(-20,60,50));
        point3DS.add(new Point3D(0,0,-90));
        assertEquals(point3DS.get(0),ray.getClosestPoint(point3DS),"supposed to be null");
    }

    /**
     * the closest point is the last point in the list
     */
    @Test
    void testGetClosestPointBVA3() {
        List<Point3D> point3DS = new  LinkedList<>();
        point3DS.add(new Point3D(-1000,90,100));
        point3DS.add(new Point3D(50,48,1000));
        point3DS.add(new Point3D(-20,60,50));
        point3DS.add(new Point3D(0,0,-90));
        point3DS.add(new Point3D(0,.5,1));
        assertEquals(point3DS.get(4),ray.getClosestPoint(point3DS),"supposed to be null");
    }
}