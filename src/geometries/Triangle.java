package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;

/**
 *class that represents polygon in 3D
 */
public class Triangle extends Polygon{
    /**
     *Ctor that accepts 3 points
     * @param x first point
     * @param y seconde point
     * @param z third point
     */
    public Triangle(Point3D x,Point3D y,Point3D z) {
        super(x,y,z);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Plane plane = new Plane(super.vertices.get(0),super.vertices.get(1),super.vertices.get(2));
        List<Point3D> ret = plane.findIntersections(ray);
        if(ret != null){
            Point3D P0 = ray.getP0();
            Point3D P1 = super.vertices.get(0);
            Point3D P2 = super.vertices.get(1);
            Point3D P3 = super.vertices.get(2);
            Vector v1 = P1.subtract(P0);
            Vector v2 = P2.subtract(P0);
            Vector v3 = P3.subtract(P0);
            Vector v = ret.get(0).subtract(P0);
            Vector n1 = v1.crossProduct(v2);
            Vector n2 = v2.crossProduct(v3);
            Vector n3 = v3.crossProduct(v1);
            if(     (alignZero(v.dotProduct(n1)) > 0
                    &&
                     alignZero(v.dotProduct(n2)) > 0
                    &&
                     alignZero(v.dotProduct(n3)) > 0)
                        ||
                    (alignZero(v.dotProduct(n1)) < 0
                    &&
                     alignZero(v.dotProduct(n2)) < 0
                    &&
                     alignZero(v.dotProduct(n3)) < 0)) {
                return ret;
            }
        }
        return null;
    }

    /**
     * to string for class polygon
     * @return a string that represents polygon
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}