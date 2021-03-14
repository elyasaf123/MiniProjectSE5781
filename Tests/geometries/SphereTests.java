package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Sphere class
 * @author Netanel & Elyasaf
 */
class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,0), 1);
        assertEquals(new Vector(new Point3D(0,0,1)),sphere.getNormal(new Point3D(0,0,1)),"Bad normal to sphere");
    }
}