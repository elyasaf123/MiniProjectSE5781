package geometries;

import org.junit.jupiter.api.*;
import primitives.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Triangle tr = new Triangle(
                new Point3D(0,0,1),
                new Point3D(1,0,1),
                new Point3D(0,1,1));
        assertTrue(
                (new Vector(new Point3D(0,0,1))).equals(tr.getNormal(new Point3D(0,0,1))) ||
                        (new Vector(new Point3D(0,0,-1))).equals(tr.getNormal(new Point3D(0,0,1)))
                        ,"Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersection() {
        Triangle tri = new Triangle(
                new Point3D(1,0,0),
                new Point3D(1,1,0),
                new Point3D(2,0,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01:Ray is intersecting in triangle (1 Points)
        Ray ray1 = new Ray(new Point3D(1,0,1), new Vector(new Point3D(0.1,0.1,-1)));
        assertEquals(
                List.of(new Point3D(1.1,0.1,0)),
                tri.findIntersections(ray1),
                "ERROR - TC01:Ray is intersecting in triangle (1 Points)");

        // TC02:Ray is not intersecting with triangle and is parallel to the edge (0 Points)
        Ray ray2 = new Ray(new Point3D(1.5,-1,1), new Vector(new Point3D(0,0,-1)));
        assertNull(
                tri.findIntersections(ray2),
                "ERROR - TC02:Ray is not intersecting with triangle and is parallel to the edge (0 Points)");

        // TC03:Ray is not intersecting with triangle and is parallel to the vertx (0 Points)
        Ray ray3 = new Ray(new Point3D(0.5,-1,1), new Vector(new Point3D(0,0,-1)));
        assertNull(
                tri.findIntersections(ray3),
                "ERROR - TC03:Ray is not intersecting with triangle and is parallel to the vertx (0 Points)");

        // =============== Boundary Values Tests ==================

        // TC04:Ray is intersecting with triangle on edge (0 Points)
        Ray ray4 = new Ray(new Point3D(1.5,0,1), new Vector(new Point3D(0,0,-1)));
        assertNull(
                tri.findIntersections(ray4),
                "ERROR - TC04:Ray is intersecting with triangle on edge (0 Points)");

        // TC05:Ray is intersecting with triangle on vertx (0 Points)
        Ray ray5 = new Ray(new Point3D(1,0,1), new Vector(new Point3D(0,0,-1)));
        assertNull(
                tri.findIntersections(ray5),
                "ERROR - TC05:Ray is intersecting with triangle on vertx (0 Points)");

        // TC06:Ray is intersecting with the continuation of the edge (0 Points)
        Ray ray6 = new Ray(new Point3D(0.5,0,1), new Vector(new Point3D(0,0,-1)));
        assertNull(
                tri.findIntersections(ray6),
                "ERROR - TC06:Ray is intersecting with the continuation of the edge (0 Points)");
    }
}