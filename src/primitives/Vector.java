package primitives;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D head;

    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y, z);
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }

    public Vector(Coordinate x, Coordinate y , Coordinate z){
        Point3D temp = new Point3D(x,y,z);
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }
    public Point3D getHead() {
        return new Point3D(head.x, head.y, head.z);
    }

    public Vector(Point3D head) {
        if (head.equals(ZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        this.head = head;
    }


    public Vector subtract(Vector vector) {
        return this.head.subtract(vector.head);
    }

    public Vector add(Vector vector) {
        return new Vector(this.head.add(vector));
    }

    public Vector scale(double scalar){
        return new Vector(
                this.head.x.coord * scalar,
                this.head.y.coord * scalar,
                this.head.z.coord * scalar);
    }

    public double dotProduct(Vector vector){
        return  this.head.x.coord * vector.head.x.coord +
                this.head.y.coord * vector.head.y.coord +
                this.head.z.coord * vector.head.z.coord;
    }

    public Vector crossProduct(Vector vector){
        return new Vector(
                this.head.y.coord * vector.head.z.coord - this.head.z.coord * vector.head.y.coord,
                this.head.z.coord * vector.head.x.coord - this.head.x.coord * vector.head.z.coord,
                this.head.x.coord * vector.head.y.coord - this.head.y.coord * vector.head.x.coord);
    }

    public double lengthSquared(){
        return this.head.distanceSquared(new Point3D(0,0,0));
    }

    public double length(){
        return this.head.distance(new Point3D(0,0,0));
    }

    public Vector normalized(){
        double dist = this.length();
        this.head.x.coord /= dist;
        this.head.y.coord /= dist;
        this.head.z.coord /= dist;
        return this;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head.toString() +
                '}';
    }
}