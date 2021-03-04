package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    private  Point3D q0;
    private Vector normal;

    public Plane(Point3D x,Point3D y,Point3D z){
        q0 = x;
        Vector a = x.subtract(y);
        Vector b = x.subtract(z);
        normal = a.crossProduct(b);
    }

    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Point3D getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
}
