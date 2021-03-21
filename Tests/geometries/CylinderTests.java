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
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
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
        Point3D point3D = new Point3D(1,0,0);
        assertEquals(new Vector(new Point3D(0,0,1)) ,cylinder.getNormal(point3D), "Bad normal to cylinder");

        // =============== Equivalence Values Tests ==================
        Point3D point3D2 = new Point3D(1,0,0.5);
        assertEquals(new Vector(new Point3D(1,0,0)) ,cylinder.getNormal(point3D2), "Bad normal to cylinder");
    }

    @Test
    void findIntersections() {

    }
}