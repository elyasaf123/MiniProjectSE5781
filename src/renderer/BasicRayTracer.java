package renderer;

import primitives.*;
import scene.*;
import java.util.List;

/**
 * Class which its role is to look for cuts between the ray and the 3D model of the scene,
 * and calculates the color in the closest intersection
 * (because what is not closest is "behind" and in fact is hidden and not of interest to us)
 */
public class BasicRayTracer extends RayTraceBase {

    /**
     * CTOR
     *
     * @param scene The scene where we look for the cuts
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * A method to find the intersections between the ray and the 3D model of the scene.
     * First we find the point of intersection which is closest to the camera
     * (because what is not closest is "behind" and in fact is hidden and not of interest to us),
     * in which we calculate the color by an helper function
     *
     * @param ray 3D ray
     *
     * @return the color at this point using method  - calcColor(point) : Color
     */
    @Override
    Color traceRay(Ray ray) {
        List<Point3D> points = scene.geometries.findIntersections(ray);
        Point3D point;
        if(points == null)
            return scene.background;
        else {
            point = ray.getClosestPoint(points);
            return calcColor(point);
        }
    }

    /**
     * calculates the Color at the given point
     *
     * @param point the point where we are interested in knowing what her color is in the scene
     *
     * @return The color at the given point
     */
    public Color calcColor (Point3D point){
        return scene.ambientLight.getIntensity();
    }
}