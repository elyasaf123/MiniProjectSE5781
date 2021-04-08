package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Netanel & Elyasaf
 */
class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point3D)}.
     * We decided that if the point received is exactly at the junction
     * between the round cylinder shell and one of the planes that block the shell (boundary case)
     * then we will calculate its normal from the plane.
     */
    @Test
    void testGetNormal() {
        Cylinder cylinder =
                new Cylinder(
                    new Ray(
                        new Point3D(0,0,0),
                        new Vector(0,0,1)),
              1,
              1);

        // ============ Equivalence Partitions Tests ==============

        // TC01: the point is on the bottom plane
        Point3D point3D1 = new Point3D(0.5,0,0);
        assertEquals(
                new Vector(new Point3D(0,0,1)),
                cylinder.getNormal(point3D1),
        "ERROR - TC01: TC01: the point is on the bottom plane");

        // TC02: the point is on the top plane
        Point3D point3D2 = new Point3D(0.5,0,1);
        assertEquals(
                new Vector(new Point3D(0,0,1)),
                cylinder.getNormal(point3D2),
                "ERROR - TC02: TC02: the point is on the top plane");

        // TC03: the point is on the shell
        Point3D point3D3 = new Point3D(1,0,0.5);
        assertEquals(
                new Vector(new Point3D(1,0,0)),
                cylinder.getNormal(point3D3),
                "ERROR - TC03: the point is on the shell. Bad normal to cylinder");

        // =============== Boundary Values Tests ==================

        Cylinder cylinder4 =
                new Cylinder(
                        new Ray(
                                new Point3D(0,0,1),
                                new Vector(0,0,1)),
                        1,
                        1);

        // TC04: the point is on the bottom center
        Point3D point3D4 = new Point3D(0,0,1);
        assertEquals(
                new Vector(new Point3D(0,0,1)),
                cylinder4.getNormal(point3D4),
                "ERROR - TC04: the point is on the bottom center");

        // TC05: the point is on the top center
        Point3D point3D5 = new Point3D(0,0,1);
        assertEquals(
                new Vector(new Point3D(0,0,1)),
                cylinder.getNormal(point3D5),
                "ERROR - TC05: the point is on the top center");
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}.
     * Not implemented yet!
     */
    @Test
    void testFindIntersection() {

    }
}