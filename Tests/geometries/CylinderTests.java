package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTests {

    //test normal of cylinder
    @Test
    void testGetNormal() {
        Cylinder cylinder =
                new Cylinder(
                    new Ray(
                        new Point3D(0,0,0),
                        new Vector(0,0,1)),
              1,
              1);
        Point3D point3D = new Point3D(1,0,0);
        assertEquals(new Vector(new Point3D(1,0,0)) ,cylinder.getNormal(point3D), "Bad normal to cylinder");
    }
}