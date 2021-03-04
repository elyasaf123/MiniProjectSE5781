package primitives;

import static primitives.Point3D.ZERO;

public class Vector {
    private Point3D head;

    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y, z);
        if (temp.equals(ZERO)) {
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }

    public Vector(Point3D head) {
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        this.head = head;
    }

    public Vector(Coordinate x, Coordinate y , Coordinate z) {
        Point3D temp = new Point3D(x,y,z);
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }

    public Point3D getHead() {
        return new Point3D(head.getX(), head.getY(), head.getZ());
    }

    public Vector subtract(Vector vector) {
        return this.head.subtract(vector.head);
    }

    public Vector add(Vector vector) {
        return new Vector(this.head.add(vector));
    }

    public Vector scale(double scalar) {
        return new Vector(
                this.head.getX().coord * scalar,
                this.head.getY().coord * scalar,
                this.head.getZ().coord * scalar);
    }

    public double dotProduct(Vector vector) {
        return  this.head.getX().coord * vector.head.getX().coord +
                this.head.getY().coord * vector.head.getY().coord +
                this.head.getZ().coord * vector.head.getZ().coord;
    }

    public Vector crossProduct(Vector vector) {
        return new Vector(
                this.head.getY().coord * vector.head.getZ().coord - this.head.getZ().coord * vector.head.getY().coord,
                this.head.getZ().coord * vector.head.getX().coord - this.head.getX().coord * vector.head.getZ().coord,
                this.head.getX().coord * vector.head.getY().coord - this.head.getY().coord * vector.head.getX().coord);
    }

    public double lengthSquared() {
        return this.head.distanceSquared(new Point3D(0,0,0));
    }

    public double length() {
        return this.head.distance(new Point3D(0,0,0));
    }

    public Vector normalize() {
        this.head = (scale(1/length()).head);
        return this;
    }

    public Vector normalized() {
        return new Vector (this.normalize().head);
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