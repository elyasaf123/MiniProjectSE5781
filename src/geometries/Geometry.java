package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for any geometric object
 */
public interface Geometry extends Intersectable {

    /**
     * function to get the normal
     * @param point3D a 3d poing
     * @return normal of the point
     */
    Vector getNormal(Point3D point3D);
}