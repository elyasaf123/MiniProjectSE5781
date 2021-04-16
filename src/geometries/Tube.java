package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * class that represents a tube in 3D
 */
public class Tube extends RadialGeometry implements Geometry {

    /**
     * Ray includes a point and a vector which represents an axis
     */
    protected Ray axisRay;

    /**
     * CTOR of tube
     *
     * @param axisRay Ray(point 3D, vector)
     * @param radius  radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
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
     * getter for tubes radius
     *
     * @return radius of tube
     */
    public double getRadius() {
        return alignZero(radius);
    }

    /**
     * getter for tubes normal
     * @param point3D init point
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
//
//    /**
//     * A method that receives a ray and checks the points of intersection of the ray with the tube
//     * @param ray the ray received
//     * @return null / list that includes all the intersection points (Point3D)
//     */
//    @Override
//    public List<Point3D> findIntersections(Ray ray) {
//
//        Point3D P0 = ray.getP0();
//        Vector v = ray.getDir();
//
//        Point3D Pa = axisRay.getP0();
//        Vector Va = axisRay.getDir();
//
//        //At^2 + Bt + C equation.
//        double A, B, C;
//
//        //(v,u) = v dot product u
//
//        //calculate the vector: V-(V,Va)Va
//        Vector VecA = v;
//        double Vva = v.dotProduct(Va);
//
//        try {
//            if (!isZero(Vva))
//                VecA = v.subtract(Va.scale(Vva));
//
//            //A = (V-(V,Va)Va)^2
//            A = VecA.lengthSquared();
//        }
//
//        //if A = 0 return null (there are no intersections)
//        catch (IllegalArgumentException ex) { // the ray is parallel to the axisRay
//            return null;
//        }
//
//        try {
//
//            //calculate deltaP (delP) vector, P-Pa
//            Vector DeltaP = P0.subtract(Pa);
//
//            //The vector: delP - (delP,Va)Va
//            Vector DeltaPMinusDeltaPVaVa = DeltaP;
//            double DeltaPVa = DeltaP.dotProduct(Va);
//
//            if (!isZero(DeltaPVa))
//                DeltaPMinusDeltaPVaVa = DeltaP.subtract(Va.scale(DeltaPVa));
//
//            //B = 2(V - (V,Va)Va , delP - (delP,Va)Va)
//            B = 2 * (VecA.dotProduct(DeltaPMinusDeltaPVaVa));
//
//            //C = (delP - (delP,Va)Va)^2 - r^2
//            C = DeltaPMinusDeltaPVaVa.lengthSquared() - radius * radius;
//        }
//
//        //in case delP = 0, or delP - (delP,Va)Va = (0, 0, 0)
//        catch (IllegalArgumentException ex) {
//            B = 0;
//            C = -1 * radius * radius;
//        }
//
//        //solving At^2 + Bt + C = 0
//
//        //the discrimation, B^2 - 4AC
//        double Disc = alignZero(B * B - 4 * A * C);
//
//        //no solutions for the equation. disc = 0 means that the ray parallel to the tube
//        if (Disc <= 0)
//            return null;
//
//        //the solutions for the equation
//        double t1, t2;
//
//        t1 = alignZero(-B + Math.sqrt(Disc)) / (2 * A);
//        t2 = alignZero(-B - Math.sqrt(Disc)) / (2 * A);
//
//        //taking all positive solutions
//        if (t1 > 0 && t2 > 0)
//            return List.of(ray.getTargetPoint(t1), ray.getTargetPoint(t2));
//        if (t1 > 0)
//            return List.of(ray.getTargetPoint(t1));
//        if (t2 > 0)
//            return List.of(ray.getTargetPoint(t2));
//
//        //all non-positive solutions
//        return null;
//    }

    /**
     * A method that receives a ray and checks the points of intersection of the ray with the tube
     * @param ray the ray received
     * @return null / list that includes all the intersection points (Point3D)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        /*
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
                return List.of(p1, p2);
            }
            else if (t1 > 0) {
                Point3D p1 = ray.getTargetPoint(t1);
                return List.of(p1);
            }
            else if (t2 > 0) {
                Point3D p2 = ray.getTargetPoint(t2);
                return List.of(p2);
            }
        }
        return null;
    }

    /**
     * toString of tube
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