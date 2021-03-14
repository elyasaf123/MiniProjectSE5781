package geometries;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * class cylinder which adds height to tube
 */
public class Cylinder extends Tube  {

    /**
     * height of cylinder
     */
    private double height;

//    /**
//     * ctor of cylinder  which has to call super(Tube)
//     * @param axisRay axis ray for super(Tube)
//     * @param radius radius for super(Tube)
//     */
//    public Cylinder(Ray axisRay, double radius) {
//        super(axisRay, radius);
//    }

    /**
     * ctor of cylinder  which has to call super(Tube)
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
        //The vector from the point of the cylinder to the given point
        Point3D o = axisRay.getP0();//at this point o = p0
        Vector v = axisRay.getDir();

        Vector vector1  = point3D.subtract(o);
        //Ax + By + Cz = d
        double d = -1d*(v.getHead().getXDouble()*o.getXDouble() + v.getHead().getYDouble()*o.getYDouble() + v.getHead().getZDouble()*o.getZDouble());

        //we need the projection to multiply the direction until unit vector
        double projection = vector1.dotProduct(v);
        if(!(projection <= 0) && (projection <= height)){
            //projection of p0 on the ray:
            o = o.add(v.scale(projection));
        }

        double DGiven = -1d*(v.getHead().getXDouble()*point3D.getXDouble() + v.getHead().getYDouble()*point3D.getYDouble() + v.getHead().getZDouble()*point3D.getZDouble());

        if (DGiven == d || DGiven == d + height){
            return v.normalized();
        }
        //this vector is orthogonal to the dir vector
        Vector check = point3D.subtract(o);
        return check.normalize();
    }

    /**
     * to string for cylinder
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