package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;

/**
 * Since our model will be based on scanning rays,
 * we would like to define in all geometric shapes an action that will receive a Ray
 * and return points of intersection with the geometry
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}