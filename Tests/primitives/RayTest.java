package primitives;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test to check getClosestPoint functions in RAY class
 */
class RayTest {
    Ray ray = new Ray(Point3D.ZERO, new Vector(0,0,1));

    /**
     * Test method for {@link primitives.Ray#getClosestPoint(List)}.
     */
    @Test
    void testGetClosestPoint() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: the closest point is the middle point in the list
        List<Point3D> point3DS1 = new  LinkedList<>();
        point3DS1.add(new Point3D(-1000,90,100));
        point3DS1.add(new Point3D(50,48,1000));
        point3DS1.add(new Point3D(0,.5,1));
        point3DS1.add(new Point3D(-20,60,50));
        point3DS1.add(new Point3D(0,0,-90));
        assertEquals(
                point3DS1.get(2),
                ray.getClosestPoint(point3DS1),
                "ERROR - TC01: the closest point is the middle point in the list");

        // =============== Boundary Values Tests ==================

        //TC02: empty/null list
        List<Point3D> point3DS2 = null;
        assertNull(ray.getClosestPoint(point3DS2),"ERROR - TC02: empty/null list");

        //TC03: the closest point is the first point in the list
        List<Point3D> point3DS3 = new  LinkedList<>();
        point3DS3.add(new Point3D(0,.5,1));
        point3DS3.add(new Point3D(-1000,90,100));
        point3DS3.add(new Point3D(50,48,1000));
        point3DS3.add(new Point3D(-20,60,50));
        point3DS3.add(new Point3D(0,0,-90));
        assertEquals(
                point3DS3.get(0),
                ray.getClosestPoint(point3DS3),
                "ERROR - TC03: the closest point is the first point in the list");

        //TC04: the closest point is the last point in the list
        List<Point3D> point3DS4 = new  LinkedList<>();
        point3DS4.add(new Point3D(-1000,90,100));
        point3DS4.add(new Point3D(50,48,1000));
        point3DS4.add(new Point3D(-20,60,50));
        point3DS4.add(new Point3D(0,0,-90));
        point3DS4.add(new Point3D(0,.5,1));
        assertEquals(
                point3DS4.get(4),
                ray.getClosestPoint(point3DS4),
                "ERROR - TC04: the closest point is the last point in the list");
    }

    /**
     * Test method for {@link primitives.Ray#getClosestGeoPoint(List)}.
     */
    @Test
    void testGetClosestGeoPoint() {

        // ============ Equivalence Partitions Tests ==============

        //TC01: the closest GeoPoint is the middle point in the list
        List<Point3D> point3DS1 = new  LinkedList<>();
        point3DS1.add(new Point3D(-1000,90,100));
        point3DS1.add(new Point3D(50,48,1000));
        point3DS1.add(new Point3D(0,.5,1));
        point3DS1.add(new Point3D(-20,60,50));
        point3DS1.add(new Point3D(0,0,-90));
        assertEquals(
                point3DS1.get(2),
                ray.getClosestPoint(point3DS1),
                "ERROR - TC01: the closest point is the middle point in the list");

        // =============== Boundary Values Tests ==================

        //TC02: empty/null list
        List<Point3D> point3DS2 = null;
        assertNull(ray.getClosestPoint(point3DS2),"ERROR - TC02: empty/null list");

        //TC03: the closest GeoPoint is the first point in the list
        List<Point3D> point3DS3 = new  LinkedList<>();
        point3DS3.add(new Point3D(0,.5,1));
        point3DS3.add(new Point3D(-1000,90,100));
        point3DS3.add(new Point3D(50,48,1000));
        point3DS3.add(new Point3D(-20,60,50));
        point3DS3.add(new Point3D(0,0,-90));
        assertEquals(
                point3DS3.get(0),
                ray.getClosestPoint(point3DS3),
                "ERROR - TC03: the closest point is the first point in the list");

        //TC04: the closest GeoPoint is the last point in the list
        List<Point3D> point3DS4 = new  LinkedList<>();
        point3DS4.add(new Point3D(-1000,90,100));
        point3DS4.add(new Point3D(50,48,1000));
        point3DS4.add(new Point3D(-20,60,50));
        point3DS4.add(new Point3D(0,0,-90));
        point3DS4.add(new Point3D(0,.5,1));
        assertEquals(
                point3DS4.get(4),
                ray.getClosestPoint(point3DS4),
                "ERROR - TC04: the closest point is the last point in the list");
    }
}