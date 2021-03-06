package primitives;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTests {

    /**
     * Test method for {@link primitives.Ray#getTargetPoint(double)}.
     */
    @Test
    void testGetTargetPoint() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: regular point
        Ray ray1 = new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(0, 0, 1)));
        assertEquals(
                new Point3D(0,0,2),
                ray1.getTargetPoint(1),
               "ERROR - TC01: regular point. bad calculate for target point");

        // =============== Boundary Values Tests ==================

        // TC02: Zero-point
        Ray ray2 = new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(0, 0, 1)));
        assertEquals(
                new Point3D(0,0,1),
                ray2.getTargetPoint(0),
                "ERROR - TC02: Zero-point. bad calculate for target point");
    }
}