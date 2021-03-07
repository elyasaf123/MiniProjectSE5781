package geometries;

import primitives.Point3D;
import primitives.Vector;


/**
 *class to represent a sphere in 3D
 */
public class Sphere implements Geometry {

    /**
     *center of sphere in 3D
     */
    private Point3D center;
    /**
     *radius of sphere in 3D
     */
    private double radius;


    /**
     *Ctor of sphere
     * @param center center of sphere in 3D
     * @param radius radius of sphere in 3D
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }


    /**
     *getter for spheres center
     * @return center of sphere in 3D
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     *getter for spheres radius
     * @return radius of sphere in 3D
     */
    public double getRadius() {
        return radius;
    }

    /**
     *getter for spheres normal
     * @param point3D
     * @return
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    /**
     * to string to represent sphere class
     * @return
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}
