package geometries;

import primitives.*;
import java.util.LinkedList;
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
     *
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
     *
     * @return height of cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * getter for cylinder normal
     *
     * @param point3D init point
     *
     * @return normal of cylinder
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        Point3D o = axisRay.getP0();//at this point o = p0
        Vector v = axisRay.getDir();//v == dir

        // if the given point equals the ray's head it produce a ZERO vector, so we return the normal (dir)
        if (point3D.equals(o)) {
            return v.normalized();
        }

        //The vector from the point of the cylinder to the given point
        Vector vector1  = point3D.subtract(o);

        //Based on the plane equation (Ax + By + Cz = d) Calculate the sliding vector value.
        //According to this sliding vector we will check
        // if the given point is on the planes that block the cylinder from its 2 ends
        // (d or d+height).
        // then the normal vector will be a dir vector.
        // Additionally we decided that if the point received is exactly at the junction
        // between the round cylinder shell and one of the planes that block the shell (boundary case)
        // then we will calculate its normal from the plane.
        //If the given point is not on planes then it will necessarily be across the round cylinder shell,
        //and the normal will be an orthogonal vector to dir
        double d = alignZero(
                -1d*(
                        v.getHead().getXDouble()*o.getXDouble() +
                        v.getHead().getYDouble()*o.getYDouble() +
                        v.getHead().getZDouble()*o.getZDouble()));

        //we need the projection to multiply the direction until unit vector
        double projection = alignZero(vector1.dotProduct(v));

        // Check that the point is not outside the cylinder
        if(!(projection <= 0) && (projection <= height)){
            //projection of p0 on the ray:
            o = o.add(v.scale(projection));
        }

        // sliding vector of the point given
        double DGiven = alignZero(
                -1d*(
                        v.getHead().getXDouble()*point3D.getXDouble() +
                        v.getHead().getYDouble()*point3D.getYDouble() +
                        v.getHead().getZDouble()*point3D.getZDouble()));

        if (DGiven == d || DGiven == d - height){
            return v.normalized();
        }

        //this vector is orthogonal to the dir vector
        Vector check = point3D.subtract(o);
        return check.normalize();
    }

    /**
     * A method that receives a ray and checks the points of intersection of the ray with the cylinder
     *
     * @param ray the ray received
     *
     * @return null / list that includes all the intersection points (Point3D)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        // The procedure is as follows:
        //P1 and P2 in the cylinder, the center of the bottom and upper bases
        Point3D p1 = axisRay.getP0();
        Point3D p2 = axisRay.getTargetPoint(height);
        Vector Va = axisRay.getDir();

        List<Point3D> list = super.findIntersections(ray);

        //the intersections with the cylinder
        List<Point3D> result = new LinkedList<>();

        //Step 1 - checking if the intersections with the tube are points on the cylinder
        if (list != null) {
            for (Point3D p : list) {
                if (Va.dotProduct(p.subtract(p1)) > 0 && Va.dotProduct(p.subtract(p2)) < 0)
                    result.add(0, p);
            }
        }

        //Step 2 - checking the intersections with the bases

        //cannot be more than 2 intersections
        if(result.size() < 2) {
            //creating 2 planes for the 2 bases
            Plane bottomBase = new Plane(p1, Va);
            Plane upperBase = new Plane(p2, Va);
            Point3D p;

            // ======================================================
            // intersection with the bases:

            //intersections with the bottom bases
            list = bottomBase.findIntersections(ray);

            if (list != null) {
                p = list.get(0);
                //checking if the intersection is on the cylinder base
                if (p.distanceSquared(p1) < radius * radius)
                    result.add(p);
            }

            //intersections with the upper bases
            list = upperBase.findIntersections(ray);

            if (list != null) {
                p = list.get(0);
                //checking if the intersection is on the cylinder base
                if (p.distanceSquared(p2) < radius * radius)
                    result.add(p);
            }
        }
        //return null if there are no intersections.
        return result.size() == 0 ? null : result;
    }

    /**
     * to-string for cylinder
     *
     * @return string that represents a cylinder
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