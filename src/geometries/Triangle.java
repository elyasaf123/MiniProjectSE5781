package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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