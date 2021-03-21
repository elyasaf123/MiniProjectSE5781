package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

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
        assertTrue(
                (new Vector(new Point3D(0,0,1))).equals(pl.getNormal(new Point3D(0,0,1))) ||
                        (new Vector(new Point3D(0,0,-1))).equals(pl.getNormal(new Point3D(0,0,1))),
                "Bad normal to plane");
    }

    @Test
    void findIntersection() {
        Plane plane = new Plane(new Point3D(0,0,1),new Vector(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersect the plane (1 points)
        Ray ray1 = new Ray(new Point3D(1,0,0),new Vector(-1,0,1).normalize());
        LinkedList<Point3D> list1 = new LinkedList<>();
        list1.add(new Point3D(0,0,1));
        assertEquals(list1, plane.findIntersections(ray1),"ERROR TC01: Ray intersect the plane");




        // TC02: Ray does not intersect the plane (0 points)
//        Ray ray2 = new Ray(new Point3D(1,0,0),new Vector(-1,0,-1).normalize());
//        assertNull(plane.findIntersections(ray2),"Error - TC02: Ray does not intersect the plane");



//        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
//        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
//        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
//                new Vector(3, 1, 0)));
//        assertEquals(2, result.size(), "Wrong number of points");
//        if (result.get(0).getXDouble() > result.get(1).getXDouble()) {
//            result = List.of(result.get(1), result.get(0));
//        }
//        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // =============== Boundary Values Tests ==================

        // TC03: Ray starts inside the sphere (1 point)





        // TC04: Ray starts after the sphere (0 points)

        // TC05: Ray starts after the sphere (0 points)

        // TC06: Ray starts after the sphere (0 points)

        // TC07: Ray starts after the sphere (0 points)

        // TC08: Ray starts after the sphere (0 points)

        // TC09: Ray starts after the sphere (0 points)

        // **** Group: Ray's line crosses the sphere (but not the center)
    }
}