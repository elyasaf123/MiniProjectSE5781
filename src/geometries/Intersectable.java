package geometries;

import primitives.*;
import java.util.List;
import java.util.stream.*;

/**
 * Since our model will be based on scanning rays,
 * we would like to define in all geometric shapes an action that will receive a Ray
 * and return points of intersection with the geometry
 */
public interface Intersectable {

    /**
     * Since we calculate for each point its color regarding to the color of the shape itself,
     * we would like to save each point of intersection along with the shape it cuts,
     * so we built a static internal help class (in the form of PDS - Passive Data Structure)
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point3D;

        /**
         * CTOR
         *
         * @param geometry The shape
         * @param point3D The intersection's point
         */
        public GeoPoint(Geometry geometry, Point3D point3D) {
            this.geometry = geometry;
            this.point3D = point3D;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point3D.equals(geoPoint.point3D);
        }
    }

    /**
     * @param ray The ray for which we calculates the intersections
     *
     * @return List of the points of the intersections
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null?null
                :geoList
                .stream()
                .map(gp->gp.point3D)
                .collect(Collectors.toList());
    }

    /**
     * A method that receives a ray and checks the points of GeoIntersection of the ray with the geometry
     * (with Unlimited distance)
     *
     * @param ray The ray for which we calculates the GeoIntersections
     *
     * @return List of the points of the GeoIntersections (contains the geometry (shape) and the point in 3D)
     */
    default List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * A method that receives a ray and distance and checks the points of GeoIntersection of the ray with the geometry
     * within the given distance
     *
     * @param ray The ray for which we calculates the GeoIntersections
     * @param maxDistance The distance within which we look for the intersections
     *
     * @return List of the points of the GeoIntersections (contains the geometry (shape) and the point in 3D)
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);
}