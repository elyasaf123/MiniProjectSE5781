package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * class that reperesents a plane in 3d
 */
public class Plane implements Geometry {
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
     * @param x first point
     * @param y second point
     * @param z third point
     */
    public Plane(Point3D x,Point3D y,Point3D z){
        q0 = x;
        Vector a = x.subtract(y);
        Vector b = x.subtract(z);
        normal = a.crossProduct(b);
    }

    /**
     * Ctor for plane that excepts a point and a vector
     * @param q0 first point
     * @param normal normal of the plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     * getter for point
     * @return init point of plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     * getter for the normal
     * @return normal of the point
     */
    public Vector getNormal() {
        return normal;
    }


    /**
     * getter for normal
     * @param point3D init point
     * @return normal of the point
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    /**
     *to string for plane function
     * @return a string that represents plane class
     */
    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
}
