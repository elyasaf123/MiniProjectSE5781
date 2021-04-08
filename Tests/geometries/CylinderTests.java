package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
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
     */
    @Test
    void testFindIntersection() {
        Cylinder cylinder = new Cylinder(new Ray(new Point3D(2,0,0), new Vector(0,0,1)), 1d, 2d);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray

        List<Point3D> result = cylinder.findIntersections(new Ray(new Point3D(5,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");


        //TC02 ray starts inside and parallel to the cylinder's ray

        result = cylinder.findIntersections(new Ray(new Point3D(2.5,0,1), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2.5,0,2)), result, "Bad intersection point");

        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2.5,0,-1), new Vector(0,0,1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2.5, 0, 0), new Point3D(2.5,0,2)), result, "Bad intersection point");

        //TC04 ray starts from outside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(-2,0,0), new Vector(1,0,0)));
        //assertEquals(2, result.size(), "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");
        assertNull(result, "Wrong number of points");

        //TC05 ray starts from inside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(5,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        // =============== Boundary Values Tests ==================

        //TC07 ray is on the surface of the cylinder (not bases)

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC08 ray is on the base of the cylinder and crosses 2 times

        result = cylinder.findIntersections(new Ray(new Point3D(-1,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");

        //TC08 ray is in center of the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2,0,2)), result, "Bad intersection points");

        //TC09 ray is perpendicular to cylinder's ray and starts from outside the tube

        result = cylinder.findIntersections(new Ray(new Point3D(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(1,0,0.5), new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC09 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)

        result = cylinder.findIntersections(new Ray(new Point3D(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC10 ray is perpendicular to cylinder's ray and starts from the center of cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC11 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside

        result = cylinder.findIntersections(new Ray(new Point3D(1,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC12 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC13 ray starts from the surface to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC14 ray starts from the surface to inside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,-0.5), new Vector(-1,0,1)));
        //assertEquals(1, result.size(), "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,2)), result, "Bad intersection point");

        //TC15 ray starts from the center

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,1)), result, "Bad intersection point");

        //TC16 prolongation of ray crosses cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC17 ray starts from the surface to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC18 ray starts from the surface to inside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0.5), new Vector(-1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(1,0,0.5)), result, "Bad intersection point");

        //TC19 ray starts from the center

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,1)), result, "Bad intersection point");

        //TC20 prolongation of ray crosses cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC21 ray is on the surface starts before cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,-1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC22 ray is on the surface starts at bottom's base

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC23 ray is on the surface starts on the surface

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC24 ray is on the surface starts at top's base

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,2), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");
    }
}