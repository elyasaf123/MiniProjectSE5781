package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraRayIntersectionsIntegrationTests {
    private void assertCountIntersections(Camera camera, Intersectable geo, int expected, String string) {
        int count = 0;
        camera.setViewPlaneSize(3,3).setDistance(1);
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++) {
                var intersections = geo.findIntersections(camera.constructRayThroughPixel(3,3,j,i));
                count += intersections == null ? 0 : intersections.size();
            }
        }
        assertEquals(expected,count, string);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Sphere Intersections
     */
    @Test
    public void cameraRaySphereIntegration(){
        Camera cam1 = new Camera(Point3D.ZERO, new Vector(0,0,-1), new Vector(0,-1,0));
        Camera cam2 = new Camera(new Point3D(0,0,0.5), new Vector(0,0,-1),new Vector(0,-1,0));

        //TC01: Small sphere (2 points)
        assertCountIntersections(cam1, new Sphere(new Point3D(0,0,-3),1),4, "ERROR - TC01: Small sphere (2 points)");

        //TC02: Big sphere (18 points)
        assertCountIntersections(cam2,new Sphere(new Point3D(0,0,-100),90),18);

        //TC03: Medium sphere (10 points)
        assertCountIntersections(cam2,new Sphere(new Point3D(0,0,-2),2),10);

        //TC04: Inside sphere (9 points)
        assertCountIntersections(cam2,new Sphere(new Point3D(0,0,-1),4),9);

        //TC05: Beyond Sphere (0 points)
        assertCountIntersections(cam1,new Sphere(new Point3D(0,0,1),0.5),0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane Intersections
     */
    @Test
    public void cameraRayPlaneIntegration(){
        Camera cam = new Camera(Point3D.ZERO, new Vector(0,0,-1), new Vector(0,-1,0));

        //TC01: Plane against camera (9 points)
        assertCountIntersections(cam, new Plane(new Point3D(0,0,-5),new Vector(0,0,1)),9);

        //TC02: Plane with small angle (9 points)
        assertCountIntersections(cam, new Plane(new Point3D(0,0,-5),new Vector(0,1,2)),9);

        //TC03: Plane parallel to lower rays (6 points)
        assertCountIntersections(cam, new Plane(new Point3D(0,0,-5),new Vector(0,1,1)),6);

        //TC04: Beyond plane (0 points)
        assertCountIntersections(cam, new Plane(new Point3D(0,0,5),new Vector(0,1,1)),0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle Intersections
     */
    @Test
    public void cameraRayTriangleIntegration(){
        Camera cam = new Camera(Point3D.ZERO, new Vector(0,0,-1), new Vector(0,-1,0));

        //TC01: small triangle (1 point)

        assertCountIntersections(cam, new Triangle(new Point3D(1,1,-2),new Point3D(-1,1,-2),new Point3D(0,-1,-2)),1);
        //TC02: medium triangle (2 points)
        assertCountIntersections(cam, new Triangle(new Point3D(1,1,-2),new Point3D(-1,1,-2),new Point3D(0,-20,-2)),2);
  }
}