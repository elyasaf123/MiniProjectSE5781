package geometries;

import primitives.*;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

/**
 * class that represents triangle in 3D
 */
public class Rectangle extends Polygon{

    /**
     * CTOR that accepts 3 points and send them to the polygon's ctor
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
     * A function that receives a rectangle and constructs from it a cube according to the width it receives
     *
     * @param width the cube's width
     *
     * @return List of points that make up the cube
     */
    public List<Geometry> getCube(double width){
        Point3D x = this.vertices.get(0);
        Point3D y = this.vertices.get(1);
        Point3D z = this.vertices.get(2);
        Point3D w = this.vertices.get(3);
        // Find the normal of the rectangle to create the new cube's points by multiplying the width by the normal
        Vector normal = this.getNormal(null);
        List<Geometry> ret = new LinkedList<>();

        // bottom rectangle
        ret.add(new Rectangle(x, y, z, w));

        // top rectangle
        ret.add(new Rectangle(
                x.add(normal.scale(width)),
                y.add(normal.scale(width)),
                z.add(normal.scale(width)),
                w.add(normal.scale(width))));

        // 4 sides
        ret.add(new Rectangle(y, y.add(normal.scale(width)), x.add(normal.scale(width)),x));
        ret.add(new Rectangle(z, w, w.add(normal.scale(width)), z.add(normal.scale(width))));
        ret.add(new Rectangle(x, x.add(normal.scale(width)), w.add(normal.scale(width)), w));
        ret.add(new Rectangle(y, y.add(normal.scale(width)), z.add(normal.scale(width)), z));

        return ret;
    }

    /**
     * the rectangle class call to this function in purpose of use it to move the rectangle like it was a book's page
     *
     * @param zMove The height to which we lift the top cover of the book
     *
     * @return new rectangle in the new position according to the distance we lifted the page
     */
    public Rectangle moveByJ(double zMove) {

        Point3D x = this.vertices.get(0); // The lower left corner of the rectangle
        Point3D y = this.vertices.get(1); // The lower right corner of the rectangle
        Point3D z = this.vertices.get(2); // The upper right corner of the rectangle
        Point3D w = this.vertices.get(3); // The upper left corner of the rectangle

        // Limit the page lift distance by its width
        if (zMove > alignZero(Math.abs(x.getXDouble() - y.getXDouble()))) {
            zMove = alignZero(Math.abs(x.getXDouble() - y.getXDouble()));
        }
        // Limit the distance the page is downloaded by its width
        else if (zMove < -alignZero(Math.abs(x.getXDouble() - y.getXDouble()))) {
            zMove = -alignZero(Math.abs(x.getXDouble() - y.getXDouble()));
        }

        // The new position of the point is calculated according to the displacement of Z in height,
        // Y is in the same place,
        // and X changes according to the level of Z according to Pythagoras' theorem
        Point3D newX =
                new Point3D(
                        x.getXDouble() - Math.sqrt(x.getXDouble() * x.getXDouble() - zMove * zMove -2 * zMove * x.getZDouble()),
                        x.getYDouble(),
                        x.getZDouble() + zMove);

        // The new position of the point is calculated according to the displacement of Z in height,
        // Y is in the same place,
        // and X changes according to the level of Z according to Pythagoras' theorem
        Point3D newW =
                new Point3D(
                        w.getXDouble() - Math.sqrt(w.getXDouble() * w.getXDouble() - zMove * zMove -2 * zMove * w.getZDouble()),
                        w.getYDouble(),
                        w.getZDouble() + zMove);

        // Note: The position of the page bases (book center) does not change when opened or closed

        return new Rectangle(newX, y, z, newW);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}