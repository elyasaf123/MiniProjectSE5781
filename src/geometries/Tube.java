package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

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

        Point3D o = axisRay.getP0();//at this point o = p0
        Vector v = axisRay.getDir();
        //The vector from the point of the cylinder to the given point
        Vector vector1  = point3D.subtract(o);

        //we need the projection to multiply the direction until unit vector
        double projection = vector1.dotProduct(v);
        if(!isZero(projection)){
            //projection of p0 on the ray:
            o = o.add(v.scale(projection));
        }

        //this vector is orthogonal to the dir vector
        Vector check = point3D.subtract(o);
        return check.normalize();
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