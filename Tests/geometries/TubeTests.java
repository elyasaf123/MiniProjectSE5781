package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    @Test
    void testGetNormal() {
        Tube tube = new Tube(new Ray(new Point3D(0,0,0), new Vector(0,0,1)), 1);
        assertEquals(new Vector(new Point3D(1,0,0)),tube.getNormal(new Point3D(1,0,0)),"Bad normal to tube");
    }
}