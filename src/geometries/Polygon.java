package geometries;

import primitives.*;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate system
 */
public class Polygon extends Geometry {

    /**
     * List of polygon's vertices
     */
    protected List<Point3D> vertices;

    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     *
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getThisNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {

            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");

            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * A method that receives a Point3D on the polygon and checks the normal to this point
     *
     * @param point the Point3D received
     *
     * @return normal at this point to the plane which contains the polygon
     */
    @Override
    public Vector getNormal(Point3D point) {
        return plane.getThisNormal();
    }

    /**
     * A method that receives a ray and checks the points of GeoIntersection of the ray with the polygon
     *
     * @param ray the ray received
     *
     * @return null / list that includes all the GeoIntersection points (contains the geometry (shape) and the point in 3D)
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
        // First of all we check that there is a point of intersection
        // with the plane where the polygon is
        Plane plane = new Plane(vertices.get(0), vertices.get(1), vertices.get(2));

        // We find all the vectors from head of the Ray
        // until each vertex of the polygon.
        // Then we find all the normals from each side,
        // and if our Ray is cut with all the above normals at a sharp angle,
        // or with all of them at an obtuse angle
        // (depending on the normal direction, out of the body or towards the polygon)
        // then the Ray intersects the polygon.
        // We will check the angles using a scalar product between the Ray and the normal

        if (plane.findGeoIntersections(ray,maxDistance) != null) {

            // Ray's head
            Point3D P0 = ray.getP0();

            // Vector from the beginning of the Ray to the point of intersection with the plane
            Vector v = plane.findGeoIntersections(ray,maxDistance).get(0).point3D.subtract(P0);

            GeoPoint geoPoint = new GeoPoint(this, plane.findGeoIntersections(ray,maxDistance).get(0).point3D);

            // all the vectors that are between the head of the Ray and the vertices of the polygon.
            LinkedList<Vector> vectorList = new LinkedList<>();
            for (Point3D point3D : vertices) {
                vectorList.add(point3D.subtract(P0));
            }

            // all the normals that come out of the sides between every 2 adjacent vectors
            LinkedList<Vector> normalList = new LinkedList<>();
            for (int i = 0; i < vectorList.size() - 1; i++) {
                normalList.add(vectorList.get(i).crossProduct(vectorList.get(i + 1)));
            }

            // The last normal is calculated as a cross product
            // between the last vector in the list and the first in the list
            normalList.add(vectorList.get(vectorList.size() - 1).crossProduct(vectorList.get(0)));

            // Check if everyone has the same type of angle between normal and Ray
            // (sharp in case of negative result and blunt in case of positive result).
            //
            // If the result of the product is equal to zero
            // it means that the Ray intersects the polygon exactly on the polygon itself
            // in the side or vertex
            boolean flag = true;
            for (Vector normal : normalList) {
                if (v.dotProduct(normal) <= 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return List.of(geoPoint);
            }

            flag = true;
            for (Vector normal : normalList) {
                if (v.dotProduct(normal) >= 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return List.of(geoPoint);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}