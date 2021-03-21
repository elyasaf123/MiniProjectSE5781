package primitives;

import static primitives.Point3D.ZERO;

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
     * @param x The X-axis component of the end point
     * @param y The Y-axis component of the end point
     * @param z The Z-axis component of the end point
     */
    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y, z);
        if (temp.equals(ZERO)) {
            throw new IllegalArgumentException("head cannot be point(0,0,0)");
        }
        head = temp;
    }

    /**
     * CTOR who gets 1 type of the Point3D (the end point of the vector)
     * @param head
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        this.head = head;
    }

    /**
     * CTOR who gets 3 types of Coordinate types
     * @param x The X-axis component of the end point
     * @param y The Y-axis component of the end point
     * @param z The Z-axis component of the end point
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
     * @return head
     */
    public Point3D getHead() {
        return new Point3D(head.getX(), head.getY(), head.getZ());
    }

    /**
     * A function that performs a vector subtraction between 2 vectors
     * @param vector A vector that represents the missing vector
     * @return New vector calculated according to vector subtraction
     */
    public Vector subtract(Vector vector) {
        return this.head.subtract(vector.head);
    }

    /**
     * A function that makes a vector connection between 2 vectors
     * @param vector Vector to be added
     * @return a new vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.head.add(vector));
    }

    /**
     * Vector Multiplier - Scalar
     * @param scalar The multiplier number
     * @return New vector after multiplication
     */
    public Vector scale(double scalar) {
        if(scalar == 0){
            throw new IllegalArgumentException("can't scale by Zero");
        }
        return new Vector(
                this.head.getX().coord * scalar,
                this.head.getY().coord * scalar,
                this.head.getZ().coord * scalar);
    }

    /**
     * Scalar product between vectors
     * @param vector Vector for multiplication
     * @return The result of the scalar product (double)
     */
    public double dotProduct(Vector vector) {
        return  this.head.getX().coord * vector.head.getX().coord +
                this.head.getY().coord * vector.head.getY().coord +
                this.head.getZ().coord * vector.head.getZ().coord;
    }

    /**
     * Vector product
     * @param vector Vector for multiplication
     * @return a new vector perpendicular to the existing two vectors
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.head.getY().coord * vector.head.getZ().coord - this.head.getZ().coord * vector.head.getY().coord,
                this.head.getZ().coord * vector.head.getX().coord - this.head.getX().coord * vector.head.getZ().coord,
                this.head.getX().coord * vector.head.getY().coord - this.head.getY().coord * vector.head.getX().coord);
    }

    /**
     * Calculate the square of the distance between 2 vectors
     * @return the square of the distance between 2 vectors
     */
    public double lengthSquared() {
        return this.head.distanceSquared(new Point3D(0,0,0));
    }

    /**
     * Calculate the distance between 2 vectors
     * @return the distance between 2 vectors
     */
    public double length() {
        return this.head.distance(new Point3D(0,0,0));
    }

    /**
     * The process of finding the unit vector
     * @return the vector himself after the normalization operation
     */
    public Vector normalize() {
        if(length() == 0){
            throw new ArithmeticException("cannot devide by zero!!");
        }
        this.head = (scale(1/length()).head);
        return this;
    }

    /**
     * A normalization operation
     * @return a new normalized vector in the same direction as the original vector
     */
    public Vector normalized() {
        Vector vector = new Vector(this.head);
        return new Vector (vector.normalize().head);
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
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }


    /**
     * string that represents the class
     * @return string that represents the class
     */
    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }
}