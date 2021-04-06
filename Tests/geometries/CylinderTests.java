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

        // TC01: the point is on one of the planes
        Point3D point3D = new Point3D(0.5,0,0);
        assertEquals(
                new Vector(new Point3D(0,0,1)),
                cylinder.getNormal(point3D),
        "ERROR - TC01: the point is on one of the planes. Bad normal to cylinder");

        // TC02: the point is on the shell
        Point3D point3D2 = new Point3D(1,0,0.5);
        assertEquals(new Vector(new Point3D(1,0,0)), cylinder.getNormal(point3D2), "ERROR - TC02: the point is on the shell. Bad normal to cylinder");

        // =============== Boundary Values Tests ==================
        // TC03:
        Point3D point3D3 = new Point3D(1,0,0);
        assertEquals(new Vector(new Point3D(0,0,1)), cylinder.getNormal(point3D), "ERROR - TC03: Bad normal to cylinder");
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}.
     * Not implemented yet!
     */
    @Test
    void findIntersections() {

    }
}