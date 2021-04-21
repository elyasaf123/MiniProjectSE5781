package renderer;

import primitives.*;
import scene.*;

import java.util.List;

/**
 *
 */
public class BasicRayTracer extends RayTraceBase {

    /**
     *
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
        //todo
    }



    /**
     *todo
     * @param ray 3D ray
     * @return
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
     *todo
     * @param point
     * @return
     */
    public Color calcColor (Point3D point){
        return scene.ambientLight.getIntensity();
    }
}