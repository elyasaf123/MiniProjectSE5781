package primitives;

import geometries.Intersectable.*;
import java.util.List;
import static primitives.Util.*;

/**
 * The set of points on a straight line that are on one side relative to a given point
 */
public class Ray {

    /**
     * The point from which the ray start
      */
    private Point3D p0;

    /**
     * The unit vector that emerges from the point
     */
    private Vector dir;

    /**
     * CTOR
     *
     * @param p0 Point 3D
     * @param dir the vector
     */
    public Ray(Point3D p0, Vector dir) {
        Vector vector = dir.normalized();
        this.p0 = p0;
        this.dir = vector;
    }

    /**todo
     * calculate and return closest point from the list of points to the head of the ray
     *
     * @param geoPoints list of points in 3D
     *
     * @return closest point from the list of points to the head of the ray
     */
    public GeoPoint getClosestGeoPoint(List<GeoPoint> geoPoints){
        GeoPoint minPoint = null;
        if(geoPoints != null) {
            double distance = Double.POSITIVE_INFINITY;
            for (GeoPoint p : geoPoints) {
                double temp = p.point3D.distance(p0);
                if (temp < distance) {
                    distance = temp;
                    minPoint = p;
                }
            }
        }
        return minPoint;
    }

    /**
     * getter for p0
     *
     * @return p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter for vector 'dir'
     *
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Refactoring must be performed for the calculation code of a point on a ray:
     * P = p0 + t∙v.
     * Used wherever required in the implementations of findIntersections function.
     *
     * @param t The distance to be calculated for the ray from its head
     *
     * @return The 3D-point on the ray that is at a distance of t from the head of the ray
     */
    public Point3D getTargetPoint(double t) {
        if (!isZero(alignZero(t))) {
            return getP0().add(dir.scale(alignZero(t)));
        }
        return getP0();
    }

    /**
     * calculate and return closest point from the list of points to the head of the ray
     *
     * @param points list of points in 3D
     *
     * @return closest point from the list of points to the head of the ray
     */
    public Point3D getClosestPoint(List<Point3D> points){
        Point3D minPoint = null;
        if(points != null) {
            double distance = Double.POSITIVE_INFINITY;
            for (Point3D p : points) {
                double temp = p.distance(p0);
                if (temp < distance) {
                    distance = temp;
                    minPoint = p;
                }
            }
        }
        return minPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}