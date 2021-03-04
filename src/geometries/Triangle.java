package geometries;

import primitives.Point3D;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Triangle extends Polygon{

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