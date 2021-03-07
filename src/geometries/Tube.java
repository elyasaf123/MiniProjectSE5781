package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class that represents a tube in 32
 */
public class Tube implements Geometry {

    /**
     * Ray includes a point and a vector which represents an axis
     */
    protected Ray axisRay;
    /**
     * radius of tube
     */
    protected double radius;

    /**
     *ctor of tube
     * @param axisRay Ray(point 3D, vector)
     * @param radius radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     *getter for axis of Tube
     * @return axis ray of tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter for tubes radius
     * @return radius of tube
     */
    public double getRadius() {
        return radius;
    }

    /**
     *getter for tubes normal
     * @param point3D init point
     * @return normal of tube
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    /**
     *tostring of tube
     * @return a string that represents a tube
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}