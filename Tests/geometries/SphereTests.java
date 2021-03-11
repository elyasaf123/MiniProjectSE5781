package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,0), 1);
        assertEquals(new Vector(new Point3D(0,0,1)),sphere.getNormal(new Point3D(0,0,1)),"Bad normal to sphere");
    }
}