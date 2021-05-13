package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * class that represents a plane in 3d
 */
public class Plane extends Geometry {

    /**
     * a point for the axis
     */
    private  Point3D q0;

    /**
     * a vector to know the normal of the plane
     */
    private Vector normal;

    /**
     * ctor for plane that excepts 3 points
     *
     * @param x first point
     * @param y second point
     * @param z third point
     *
     * @throws IllegalArgumentException in 2 cases:
     *      1 - All three points are on the same line.
     *      2 - not all points are different
     */
    public Plane(Point3D x,Point3D y,Point3D z) {
        q0 = x;
        if (x != y && x != z && y != z) {
            Vector a = x.subtract(y);
            Vector b = y.subtract(z);
            if (a.normalized() != b.normalized())
                normal = a.crossProduct(b);
            else
                throw new IllegalArgumentException("All three points are on the same line");
            normal.normalize();
        }
        else
            throw new IllegalArgumentException("All points must be different");
    }

    /**
     * Ctor for plane that excepts a point and a vector
     *
     * @param q0 first point
     * @param normal normal of the plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalized();
    }

    /**
     * getter for point
     *
     * @return init point of plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     * getter for the normal
     *
     * @return normal of the point
     */
    public Vector getThisNormal() {
        return normal;
    }

    /**
     * getter for normal
     *
     * @param point3D init point
     *
     * @return normal of the point
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return getThisNormal();
    }

    /**
     * todo
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        // the ray's components
        Point3D p0 = ray.getP0();
        Vector v = ray.getDir();

        //the ray is intersecting with starting point p0 which is not called intersecting!
        if (q0.equals(p0)) {
            return null;
        }

        //P is the point which the vector intersects with the plane
        //Ray points: P = P0 + t*v
        //Plane points: normal*(q0-p) = 0
        //...
        //t = normal*(Q - p0)/n*v
        double nv = alignZero(normal.dotProduct(v));

        //the ray is parallel to the plane doesn't matter if contained or not
        if(isZero(nv)) {
            return null;
        }

        //t = normal*(Q - p0)/n*v
        double t = alignZero(normal.dotProduct(q0.subtract(p0)));

        //check if exiting point is on plane
        if(isZero(t)) {
            return null;
        }

        //we checked already that nv isn't zero! so we can divide it
        t = alignZero(t / nv);

        // if t is negative there are no intersections
        if (alignZero(t) <= 0) {
            return null;
        }

        //P = P0 + t*v
        Point3D p = ray.getTargetPoint(t);
        return List.of(new GeoPoint(this,p));
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
}