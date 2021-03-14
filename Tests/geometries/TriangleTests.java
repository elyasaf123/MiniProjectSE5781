package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Triangle class
 * @author Netanel & Elyasaf
 */
class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        Triangle tr = new Triangle(
                new Point3D(0,0,1),
                new Point3D(1,0,1),
                new Point3D(0,1,1));
        assertEquals(new Vector(new Point3D(0,0,1)),tr.getNormal(new Point3D(0,0,1)),"Bad normal to plane");
    }
}