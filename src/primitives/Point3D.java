package primitives;

import static primitives.Util.*;

/**
 * basic geometric object for 3D point
 */
public class Point3D {

    /**
     * This point represents a coordinate in a position on a horizontal axis
     */
    final Coordinate x;

    /**
     * This point represents a coordinate in a position on a vertical axis
     */
    final Coordinate y;

    /**
     * This point represents a coordinate in a three-dimensional position
     */
    final Coordinate z;

    /**
     * The zero point is intended to equate an empty point to it
     */
    public static Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * x getter as the value of a coordinate
     * @return x
     */
    public Coordinate getX() {
        return x;
    }

    /**
     * y getter as the value of a coordinate
     * @return y
     */
    public Coordinate getY() {
        return y;
    }

    /**
     * z getter as the value of a coordinate
     * @return z
     */
    public Coordinate getZ() {
        return z;
    }

    /**
     * x getter as the value of a double
     * @return x
     */
    public double getXDouble() {
        return alignZero(x.coord);
    }

    /**
     * y getter as the value of a double
     * @return y
     */
    public double getYDouble() {
        return alignZero(y.coord);
    }

    /**
     * z getter as the value of a double
     * @return z
     */
    public double getZDouble() {
        return alignZero(z.coord);
    }

    /**
     * CTOR Which gets 3 types of Coordinate type
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(alignZero(x.coord), alignZero(y.coord), alignZero(z.coord));
    }

    /**
     * CTOR Which gets 3 types of Coordinate double
     * @param _x Position on the x-axis
     * @param _y Position on the y-axis
     * @param _z Position on the z-axis
     */
    public Point3D(double _x, double _y, double _z) {
        x = new Coordinate(alignZero(_x));
        y = new Coordinate(alignZero(_y));
        z = new Coordinate(alignZero(_z));
    }

    /**
     * Calculate the square of the distance between 2 three-dimensional points
     * @param point3D The point to which the distance should be calculated
     * @return the square of the distance between 2 three-dimensional points
     */
    public double distanceSquared(Point3D point3D) {
        double x1 = alignZero(x.coord);
        double y1 = alignZero(y.coord);
        double z1 = alignZero(z.coord);
        double x2 = alignZero(point3D.x.coord);
        double y2 = alignZero(point3D.y.coord);
        double z2 = alignZero(point3D.z.coord);

        return alignZero(
                (x2 - x1) * (x2 - x1) +
                        (y2 - y1) * (y2 - y1) +
                        (z2 - z1) * (z2 - z1));
    }

    /**
     * Calculate the distance between 2 three-dimensional points
     * @param point3D The point to which the distance should be calculated
     * @return the distance between 2 three-dimensional
     */
    public double distance(Point3D point3D) {
        return alignZero(Math.sqrt(distanceSquared(point3D)));
    }

    /**
     * A function that makes a vector connection between 2 vectors
     * @param vector Vector to be added
     * @return A three-dimensional point that represents the point to which the new vector reaches,
     * assuming that the vector exits the beginning of the axes
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                alignZero(x.coord + vector.getHead().x.coord),
                alignZero(y.coord + vector.getHead().y.coord),
                alignZero(z.coord + vector.getHead().z.coord));
    }

    /**
     * A function that performs a vector subtraction between 2 vectors
     * @param point3D A point that represents the missing vector
     * @return New vector calculated according to vector subtraction
     */
    public Vector subtract(Point3D point3D) {
        return new Vector(
                  alignZero(x.coord - point3D.x.coord),
                  alignZero(y.coord - point3D.y.coord),
                  alignZero(z.coord - point3D.z.coord));
    }

    /**
     * override function to check if two objects are equal
     * @param o to compare
     * @return true if equal and false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    /**
     * string that represents the class
     * @return string that represents the class
     */
    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}