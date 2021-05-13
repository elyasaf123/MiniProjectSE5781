package geometries;

import primitives.*;

/**
 * class that represents triangle in 3D
 */
public class Triangle extends Polygon{

    /**
     * CTOR that accepts 3 points
     *
     * @param x first point
     * @param y second point
     * @param z third point
     */
    public Triangle(Point3D x,Point3D y,Point3D z) {
        super(x,y,z);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}