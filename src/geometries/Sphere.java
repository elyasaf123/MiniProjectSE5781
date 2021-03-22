package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;

/**
 *class to represent a sphere in 3D
 */
public class Sphere extends RadialGeometry implements Geometry {
    /**
     *center of sphere in 3D
     */
    private Point3D center;
    /**
     *Ctor of sphere
     * @param center center of sphere in 3D
     * @param radius radius of sphere in 3D
     */
    public Sphere(Point3D center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     *getter for spheres center
     * @return center of sphere in 3D
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     *getter for spheres normal
     * We will find the vector which is the radius of the given point and on which we will perform a normalIze
     * @param point3D given point
     * @return normal to the sphere at the given point
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return point3D.subtract(getCenter()).normalize();
    }

    /**
     * to string to represent sphere class
     * @return toString
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (getCenter().equals(ray.getP0())) {
            return List.of(ray.getTargetPoint(getRadius()));
        }

        Vector u = getCenter().subtract(ray.getP0());
        Vector v = ray.getDir();
        double tm = alignZero(u.dotProduct(v));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm*tm));
        if (d > getRadius()) {
            return null;
        }
        double th = alignZero(Math.sqrt(getRadius()*getRadius() - d*d));
        if (isZero(th)) {
            return null;
        }

        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getTargetPoint(t1), ray.getTargetPoint(t2));
        }

        if (t1> 0 ) {
            return List.of(ray.getTargetPoint(t1));
        }

        if (t2 > 0) {
            return List.of(ray.getTargetPoint(t2));
        }

        return null;
    }
}