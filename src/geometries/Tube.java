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
     *
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

    /**
     * A method that receives a ray and checks the points of intersection of the ray with the tube
     * @param ray the ray received
     * @return null / list that includes all the intersection points (Point3D)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        Point3D Pa = axisRay.getP0();
        Vector Va = axisRay.getDir();

        //coefficient for the At^2 + Bt + C equation.
        double A, B, C;

        //(v,u) = v dot product u

        //calculate the vector: V-(V,Va)Va
        Vector VecA = v;
        double Vva = v.dotProduct(Va);

        try {
            if (!isZero(Vva))
                VecA = v.subtract(Va.scale(Vva));

            //A = (V-(V,Va)Va)^2
            A = VecA.lengthSquared();
        }
        //if A = 0 return null (there are no intersections)
        catch (IllegalArgumentException ex) { // the ray is parallel to the axisRay
            return null;
        }

        try {
            //calculate deltaP (delP) vector, P-Pa
            Vector DeltaP = P0.subtract(Pa);
            //The vector: delP - (delP,Va)Va
            Vector DeltaPMinusDeltaPVaVa = DeltaP;
            double DeltaPVa = DeltaP.dotProduct(Va);

            if (!isZero(DeltaPVa))
                DeltaPMinusDeltaPVaVa = DeltaP.subtract(Va.scale(DeltaPVa));

            //B = 2(V - (V,Va)Va , delP - (delP,Va)Va)
            B = 2 * (VecA.dotProduct(DeltaPMinusDeltaPVaVa));

            //C = (delP - (delP,Va)Va)^2 - r^2
            C = DeltaPMinusDeltaPVaVa.lengthSquared() - radius * radius;
        }
        //in case delP = 0, or delP - (delP,Va)Va = (0, 0, 0)
        catch (IllegalArgumentException ex) {
            B = 0;
            C = -1 * radius * radius;
        }
        //solving At^2 + Bt + C = 0

        //the discrimation, B^2 - 4AC
        double Disc = alignZero(B * B - 4 * A * C);

        //no solutions for the equation. disc = 0 means that the ray parallel to the tube
        if (Disc <= 0)
            return null;

        //the solutions for the equation
        double t1, t2;

        t1 = alignZero(-B + Math.sqrt(Disc)) / (2 * A);
        t2 = alignZero(-B - Math.sqrt(Disc)) / (2 * A);

        //taking all positive solutions
        if (t1 > 0 && t2 > 0)
            return List.of(ray.getTargetPoint(t1), ray.getTargetPoint(t2));
        if (t1 > 0)
            return List.of(ray.getTargetPoint(t1));
        if (t2 > 0)
            return List.of(ray.getTargetPoint(t2));

        //all non-positive solutions
        return null;
    }

    /**
     * tostring of tube
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