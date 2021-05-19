package primitives;

import static primitives.Point3D.*;
import static primitives.Util.*;

/**
 * A basic object in geometry with direction and size, defined by the end point
 * (when the starting point - the beginning of the axes).
 */
public class Vector {

    /**
     * the end point of the vector
     */
    private Point3D head;

    /**
     * CTOR who gets 3 types of double types
     *
     * @param x The X-axis component of the end point
     * @param y The Y-axis component of the end point
     * @param z The Z-axis component of the end point
     *
     * @throws IllegalArgumentException if the given point is (0, 0, 0)
     */
    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(alignZero(x), alignZero(y), alignZero(z));
        if (temp.equals(ZERO)) {
            throw new IllegalArgumentException("head cannot be point(0,0,0)");
        }
        head = temp;
    }

    /**
     * CTOR who gets 1 type of the Point3D (the end point of the vector)
     *
     * @param head 1 type of the Point3D
     *
     * @throws IllegalArgumentException if the given point is (0, 0, 0)
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        this.head = head;
    }

    /**
     * CTOR who gets 3 types of Coordinate types
     *
     * @param x The X-axis component of the end point
     * @param y The Y-axis component of the end point
     * @param z The Z-axis component of the end point
     *
     * @throws IllegalArgumentException if the given point is (0, 0, 0)
     */
    public Vector(Coordinate x, Coordinate y , Coordinate z) {
        Point3D temp = new Point3D(x,y,z);
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }

    /**
     * getter for Point3D
     *
     * @return head
     */
    public Point3D getHead() {
        return new Point3D(head.getX(), head.getY(), head.getZ());
    }

    /**
     * A function that performs a vector subtraction between 2 vectors
     *
     * @param vector A vector that represents the missing vector
     *
     * @return New vector calculated according to vector subtraction
     */
    public Vector subtract(Vector vector) {
        return this.head.subtract(vector.head);
    }

    /**
     * A function that makes a vector connection between 2 vectors
     *
     * @param vector Vector to be added
     *
     * @return a new vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.head.add(vector));
    }

    /**
     * Vector Multiplier - Scalar
     *
     * @param scalar The multiplier number
     *
     * @return New vector after multiplication
     *
     * @throws IllegalArgumentException if the scalar = 0
     */
    public Vector scale(double scalar) {
        if (isZero(scalar)) {
            throw new IllegalArgumentException("can't scale by Zero");
        }
        return new Vector(
                alignZero(this.head.getX().coord * scalar),
                alignZero(this.head.getY().coord * scalar),
                alignZero(this.head.getZ().coord * scalar));
    }

    /**
     * Scalar product between vectors
     *
     * @param vector Vector for multiplication
     *
     * @return The result of the scalar product (double)
     */
    public double dotProduct(Vector vector) {
        return  alignZero(
         this.head.getX().coord * vector.head.getX().coord +
                this.head.getY().coord * vector.head.getY().coord +
                this.head.getZ().coord * vector.head.getZ().coord);
    }

    /**
     * Vector product
     *
     * @param vector Vector for multiplication
     *
     * @return a new vector perpendicular to the existing two vectors
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                alignZero(
                        this.head.getY().coord * vector.head.getZ().coord -
                                this.head.getZ().coord * vector.head.getY().coord),
                alignZero(
                        this.head.getZ().coord * vector.head.getX().coord -
                                this.head.getX().coord * vector.head.getZ().coord),
                alignZero(
                        this.head.getX().coord * vector.head.getY().coord -
                                this.head.getY().coord * vector.head.getX().coord));
    }

    /**
     * Calculate the square of the distance between 2 vectors
     *
     * @return the square of the distance between 2 vectors
     */
    public double lengthSquared() {
        return alignZero(this.head.distanceSquared(new Point3D(0,0,0)));
    }

    /**
     * Calculate the distance between 2 vectors
     *
     * @return the distance between 2 vectors
     */
    public double length() {
        return alignZero(this.head.distance(new Point3D(0,0,0)));
    }

    /**
     * The process of finding the unit vector
     *
     * @return the vector himself after the normalization operation
     *
     * @throws ArithmeticException if the vector's length is 0
     */
    public Vector normalize() {
        if (isZero(length())) {
            throw new ArithmeticException("cannot divide by zero!!");
        }
        this.head = (scale(alignZero(1/length())).head);
        return this;
    }

    /**
     * A normalization operation
     *
     * @return a new normalized vector in the same direction as the original vector
     */
    public Vector normalized() {
        Vector vector = new Vector(this.head);
        return new Vector (vector.normalize().head);
    }

    /**
     * Sliding and rotating method
     *
     * @param axis The axis on which the rotating is based
     * @param theta the angle of the rotate
     *
     * @return new Vector
     */
    public Vector rotateVector( Vector axis, double theta) {

        double x, y, z;
        double u, v, w;

        x = alignZero(this.getHead().getXDouble());
        y = alignZero(this.getHead().getYDouble());
        z = alignZero(this.getHead().getZDouble());

        u = alignZero(axis.getHead().getXDouble());
        v = alignZero(axis.getHead().getXDouble());
        w = alignZero(axis.getHead().getXDouble());

        double xPrime = alignZero(u*(axis.dotProduct(this))*(1d - Math.cos(theta))
                + x*Math.cos(theta)
                + (-w*y + v*z)*Math.sin(theta));

        double yPrime = alignZero(v*(axis.dotProduct(this))*(1d - Math.cos(theta))
                + y*Math.cos(theta)
                + (w*x - u*z)*Math.sin(theta));

        double zPrime = alignZero(w*(axis.dotProduct(this))*(1d - Math.cos(theta))
                + z*Math.cos(theta)
                + (-v*x + u*y)*Math.sin(theta));

        return new Vector(alignZero(xPrime), alignZero(yPrime), alignZero(zPrime));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }
    
    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }
}