package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * class that represents a tube in 3D
 */
public class Tube extends Geometry {

    /**
     * Ray includes a point and a vector which represents an axis
     */
    protected Ray axisRay;

    /**
     * the tube's radius
     */
    protected double radius;

    /**
     * CTOR of tube
     *
     * @param axisRay Ray(point 3D, vector)
     * @param radius  radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = alignZero(radius);
    }

    /**
     * getter for axis of Tube
     *
     * @return axis ray of tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter for radius of Tube
     *
     * @return tube's radius
     */
    public double getRadius() {
        return alignZero(radius);
    }

    /**
     * getter for tubes normal
     *
     * @param point3D init point
     *
     * @return normal of tube
     */
    @Override
    public Vector getNormal(Point3D point3D) {

        Point3D o = axisRay.getP0();//at this point o = p0
        Vector v = axisRay.getDir();

        //The vector from the point of the cylinder to the given point
        Vector vector1 = point3D.subtract(o);

        //we need the projection to multiply the direction until unit vector
        double projection = alignZero(vector1.dotProduct(v));
        if (!isZero(projection)) {
            //projection of p0 on the ray:
            o = o.add(v.scale(projection));
        }

        //this vector is orthogonal to the dir vector
        Vector check = point3D.subtract(o);
        return check.normalize();
    }

    /**
     * todo
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        /*
        The procedure is as follows:
        The equation for a tube of radius r oriented along a line pa + vat:
        (q - pa - (va,q - pa)va)2 - r2 = 0
        get intersections using formula : (p - pa + vt - (va,p - pa + vt)va)^2 - r^2 = 0
        reduces to at^2 + bt + c = 0
        with a = (v - (v,va)va)^2
             b = 2 * (v - (v,va)va,∆p - (∆p,va)va)
             c = (∆p - (∆p,va)va)^2 - r^2
        where  ∆p = p - pa
        */

        Vector v = ray.getDir();
        Vector va = this.getAxisRay().getDir();

        //if vectors are parallel then there is no intersections possible
        if (v.normalize().equals(va.normalize()))
            return null;

        //use of calculated variables to avoid vector ZERO
        double vva;
        double pva;
        double a;
        double b;
        double c;

        //check every variables to avoid ZERO vector
        if (ray.getP0().equals(this.getAxisRay().getP0())){
            vva = v.dotProduct(va);
            if (vva == 0){
                a = v.dotProduct(v);
            }
            else{
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
            }
            b = 0;
            c = - getRadius() * getRadius();
        }
        else{
            Vector deltaP = ray.getP0().subtract(this.getAxisRay().getP0());
            vva = v.dotProduct(va);
            pva = deltaP.dotProduct(va);

            if (vva == 0 && pva == 0){
                a = v.dotProduct(v);
                b = 2 * v.dotProduct(deltaP);
                c = deltaP.dotProduct(deltaP) - getRadius() * getRadius();
            }
            else if (vva == 0){
                a = v.dotProduct(v);
                if (deltaP.equals(va.scale(deltaP.dotProduct(va)))){
                    b = 0;
                    c = - getRadius() * getRadius();
                }
                else{
                    b = 2 * v.dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))));
                    c = (deltaP.subtract(va.scale(deltaP.dotProduct(va))).dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))))) - this.getRadius() * this.getRadius();
                }
            }
            else if (pva == 0){
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
                b = 2 * v.subtract(va.scale(vva)).dotProduct(deltaP);
                c = (deltaP.dotProduct(deltaP)) - this.getRadius() * this.getRadius();
            }
            else {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
                if (deltaP.equals(va.scale(deltaP.dotProduct(va)))){
                    b = 0;
                    c = - getRadius() * getRadius();
                }
                else{
                    b = 2 * v.subtract(va.scale(vva)).dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))));
                    c = (deltaP.subtract(va.scale(deltaP.dotProduct(va))).dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))))) - this.getRadius() * this.getRadius();
                }
            }
        }

        //calculate delta for result of equation
        double delta = b * b - 4 * a * c;

        if (delta <= 0) {
            return null; // no intersections
        }
        else {
            //calculate points taking only those with t > 0
            double t1 = alignZero((- b - Math.sqrt(delta)) / (2 * a));
            double t2 = alignZero((- b + Math.sqrt(delta)) / (2 * a));
            if (t1 > 0 && t2 > 0) {
                Point3D p1 = ray.getTargetPoint(t1);
                Point3D p2 = ray.getTargetPoint(t2);
                return List.of(new GeoPoint(this,p1),new GeoPoint(this, p2));
            }
            else if (t1 > 0) {
                Point3D p1 = ray.getTargetPoint(t1);
                return List.of(new GeoPoint(this,p1));
            }
            else if (t2 > 0) {
                Point3D p2 = ray.getTargetPoint(t2);
                return List.of(new GeoPoint(this,p2));
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}