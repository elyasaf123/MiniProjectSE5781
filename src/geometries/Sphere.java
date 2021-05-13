package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * class to represent a sphere in 3D
 */
public class Sphere extends Geometry {

    /**
     * center of sphere in 3D
     */
    private Point3D center;

    /**
     * radius of sphere
     */
    private double radius;

    /**
     * Ctor of sphere
     *
     * @param center center of sphere in 3D
     * @param radius radius of sphere in 3D
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = alignZero(radius);
    }

    /**
     * getter for sphere's center
     *
     * @return center of sphere in 3D
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * getter for sphere's radius
     *
     * @return radius of sphere
     */
    public double getRadius() {
        return alignZero(radius);
    }

    /**
     * getter for spheres normal
     * We will find the vector which is the radius of the given point
     * and on which we will perform a normalIze
     *
     * @param point3D given point
     *
     * @return normal to the sphere at the given point
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return point3D.subtract(getCenter()).normalize();
    }

    /**
     * A method that receives a ray and checks the points of GeoIntersection of the ray with the sphere
     *
     * @param ray the ray received
     *
     * @return null / list that includes all the GeoIntersection points (contains the geometry (shape) and the point in 3D)
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        // In case the Ray exits the center of the ball then surely there is only one intersecting point
        // within a radius of the beginning of the Ray
        if (getCenter().equals(ray.getP0())) {
            return List.of(new GeoPoint(this,ray.getTargetPoint(getRadius())));
        }

        //The procedure is as follows:
        //We will find the projection of the vector (that connects the head of the ray and the center of the sphere) on the ray,
        //then we will build the vertical between the center of the sphere and the continuation of the ray.
        //Then, calculate with the help of Pythagoras:
        //the length that exists between the point where the vertical meets the ray
        //and the point where the ray meets the sphere.
        //Now we will know to add this distance to reach the second point of intersection,
        //or alternatively subtract this distance to reach the first point of intersection

        // Vector from the top of the ray to the center of the sphere
        Vector u = getCenter().subtract(ray.getP0());

        // ray's vector
        Vector v = ray.getDir();

        // the projection of the vector (that connects the head of the ray and the center of the sphere) on the ray
        double tm = alignZero(u.dotProduct(v));

        // The length of the vertical between the center of the sphere and the continuation of the ray
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm*tm));

        // The ray passes out of the sphere
        if (d > getRadius()) {
            return null;
        }

        // calculate with the help of Pythagoras:
        // the length that exists between the point where the vertical meets the ray
        // and the point where the ray meets the sphere.
        double th = alignZero(Math.sqrt(getRadius()*getRadius() - d*d));

        // The ray is tangent to the sphere
        if (isZero(th)) {
            return null;
        }

        // add th to reach the second point of intersection
        double t1 = alignZero(tm + th);

        // subtract th to reach the first point of intersection
        double t2 = alignZero(tm - th);

        // Two points of intersection
        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this,(ray.getTargetPoint(t1))),new GeoPoint(this,ray.getTargetPoint(t2)));
        }

        // one point of intersection
        if (t1> 0 ) {
            return List.of(new GeoPoint(this,ray.getTargetPoint(t1)));
        }

        // one point of intersection
        if (t2 > 0) {
            return List.of(new GeoPoint(this,ray.getTargetPoint(t2)));
        }

        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}