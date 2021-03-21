package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Plane class
 * @author Netanel & Elyasaf
 */
class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        Plane pl = new Plane(
                new Point3D(0,0,1),
                new Point3D(1,0,1),
                new Point3D(0,1,1));
        assertTrue((new Vector(new Point3D(0,0,1))).equals(pl.getNormal(new Point3D(0,0,1))) || (new Vector(new Point3D(0,0,-1))).equals(pl.getNormal(new Point3D(0,0,1))),"Bad normal to plane");
        //assertEquals(new Vector(new Point3D(0,0,1)),pl.getNormal(new Point3D(0,0,1)),"Bad normal to plane");
    }

    @Test
    void findIntersection() {
        //  // TODO: 21/03/2021  
    }
}