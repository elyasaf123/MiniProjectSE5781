package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;

/**
 * class cylinder which adds height to tube
 */
public class Cylinder extends Tube  {

    /**
     * height of cylinder
     */
    private double height;

    /**
     * CTOR of cylinder which has to call super (Tube)
     * @param axisRay axis which the cylinder will be there
     * @param radius radius of cylinder
     * @param height height of cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getter for height
     * @return height of cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     *getter for cylinder normal
     * @param point3D init point
     * @return normal of cylinder
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        Point3D o = axisRay.getP0();//at this point o = p0
        Vector v = axisRay.getDir();//v == dir

        //The vector from the point of the cylinder to the given point
        Vector vector1  = point3D.subtract(o);

        //Based on the plane equation (Ax + By + Cz = d) Calculate the sliding vector value.
        //According to this sliding vector we will check if the given point is on the planes that block the cylinder from its 2 ends (d or d+height).
        //then the normal vector will be a dir vector (and so also in boundary cases).
        //If the given point is not on planes then it will necessarily be across the round cylinder shell,
        //and the normal will be an orthogonal vector to dir
        double d = alignZero(-1d*(v.getHead().getXDouble()*o.getXDouble() + v.getHead().getYDouble()*o.getYDouble() + v.getHead().getZDouble()*o.getZDouble()));

        //we need the projection to multiply the direction until unit vector
        double projection = alignZero(vector1.dotProduct(v));
        // Check that the point is not outside the cylinder
        if(!(projection <= 0) && (projection <= height)){
            //projection of p0 on the ray:
            o = o.add(v.scale(projection));
        }
        // sliding vector of the point given
        double DGiven = alignZero(-1d*(v.getHead().getXDouble()*point3D.getXDouble() +
                v.getHead().getYDouble()*point3D.getYDouble() + v.getHead().getZDouble()*point3D.getZDouble()));

        // ============ Equivalence Partitions Tests ==============
        if (DGiven == d || DGiven == d + height){
            return v.normalized();
        }
        //this vector is orthogonal to the dir vector
        Vector check = point3D.subtract(o);
        return check.normalize();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    /**
     * to-string for cylinder
     * @return string that repersents a cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}