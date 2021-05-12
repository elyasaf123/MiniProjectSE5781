package geometries;

import primitives.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Since our model will be based on scanning rays,
 * we would like to define in all geometric shapes an action that will receive a Ray
 * and return points of intersection with the geometry
 */
public interface Intersectable {
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point3D;

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
    default List<Point3D> findIntersections(Ray ray){
        var geoList = findGeoIntersections(ray);
        return geoList == null?null
                :geoList
                .stream()
                .map(gp->gp.point3D)
                .collect(Collectors.toList());
    }
    List<GeoPoint> findGeoIntersections(Ray ray);



}