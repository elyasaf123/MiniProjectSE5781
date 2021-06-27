package geometries;

import primitives.*;
import java.util.LinkedList;
import java.util.List;

/**
 * class that represents triangle in 3D
 */
public class Rectangle extends Polygon{

    /**
     * CTOR that accepts 3 points
     *
     * @param x first point
     * @param y second point
     * @param z third point
     * @param w fourth point
     */
    public Rectangle(Point3D x,Point3D y,Point3D z,Point3D w) {
        super(x,y,z,w);
    }

    /**
     *
     * @param width
     * @return
     */
    public List<Geometry> getCube(double width){
        Point3D x = this.vertices.get(0);
        Point3D y = this.vertices.get(1);
        Point3D z = this.vertices.get(2);
        Point3D w = this.vertices.get(3);
        Vector normal = this.getNormal(null);
        List<Geometry> ret = new LinkedList<>();
        ret.add(//bottom rectangle
                new Rectangle(x, y, z, w));
        ret.add(//top rectangle
                new Rectangle(x.add(normal.scale(width)), y.add(normal.scale(width)), z.add(normal.scale(width)), w.add(normal.scale(width))));
        ret.add(//sides
                new Rectangle(y, y.add(normal.scale(width)), x.add(normal.scale(width)),x));
        ret.add(new Rectangle(z, w, w.add(normal.scale(width)), z.add(normal.scale(width))));
        ret.add(new Rectangle(x, x.add(normal.scale(width)), w.add(normal.scale(width)), w));
        ret.add(new Rectangle(y, y.add(normal.scale(width)), z.add(normal.scale(width)), z));
        return ret;
    }

    public Rectangle moveByZ(double zMove) {
        Point3D x = this.vertices.get(0);
        Point3D y = this.vertices.get(1);
        Point3D z = this.vertices.get(2);
        Point3D w = this.vertices.get(3);
        Point3D newX =
                new Point3D(
                        x.getXDouble() - Math.sqrt(x.getXDouble() * x.getXDouble() - zMove * zMove -2 * zMove * x.getZDouble()),
                        x.getYDouble(),
                        x.getZDouble() + zMove);
        Point3D newY = this.vertices.get(1);
        Point3D newZ = this.vertices.get(2);
        Point3D newW =
                new Point3D(
                        w.getXDouble() - Math.sqrt(w.getXDouble() * w.getXDouble() - zMove * zMove -2 * zMove * w.getZDouble()),
                        w.getYDouble(),
                        w.getZDouble() + zMove);

        return new Rectangle(newX, newY, newZ, newW);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}