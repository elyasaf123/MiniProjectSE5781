package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Netanel & Elyasaf
 */
class TubeTests {
    
    /**
     * Test method for {@link geometries.Tube#getNormal(Point3D)}.
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(new Ray(new Point3D(0,0,0), new Vector(0,0,1)), 1);
        assertEquals(new Vector(new Point3D(1,0,0)),tube.getNormal(new Point3D(1,0,0)),"Bad normal to tube");
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)}.
     * Not implemented yet!
     */
    @Test
    void findIntersections() {

    }
}