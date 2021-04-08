package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersection() {
        Plane plane = new Plane(new Point3D(0,0,1),new Vector(0,0,1));
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);
        Triangle tri = new Triangle(new Point3D(1,0,0),new Point3D(1,1,0),new Point3D(2,0,0));
        Geometries geometries = new Geometries(plane,sphere,tri);
        Geometries geometriesEmpty = new Geometries();

        // ============ Equivalence Partitions Tests ==============

        // TC01: some shapes but not all intersect
        Ray ray1 = new Ray(new Point3D(0.5,0,2),new Vector(0,0,-1));
        assertEquals(
                3,
                geometries.findIntersections(ray1).size(),
                "ERROR - TC01: some shapes but not all intersect");

        // =============== Boundary Values Tests ==================

        // TC02: geometries is empty
        assertNull(geometriesEmpty.findIntersections(ray1), "ERROR - TC02: geometries is empty");

        // TC03: no shapes intersect
        Ray ray3 = new Ray(new Point3D(0.5,0,2),new Vector(0,0,1));
        assertNull(geometries.findIntersections(ray3), "ERROR - TC03: no shapes intersect");

        // TC04: only one shape intersects
        Ray ray4 = new Ray(new Point3D(0.5,0,0),new Vector(0,0,-1));
        assertEquals(
                1,
                geometries.findIntersections(ray4).size(),
                "ERROR - TC04: only one shape intersects");

        // TC05: all shapes intersect
        Ray ray5 = new Ray(new Point3D(1.1,0.1,2),new Vector(0,0,-1));
        assertEquals(4, geometries.findIntersections(ray5).size(), "ERROR - TC05: all shapes intersect");
    }
}