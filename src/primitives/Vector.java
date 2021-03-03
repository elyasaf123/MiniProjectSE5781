package primitives;

import static primitives.Point3D.pointZERO;

public class Vector {
    Point3D head;

    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y, z);
        if (head.equals(pointZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }

    public Vector(Coordinate x, Coordinate y , Coordinate z){
        Point3D temp = new Point3D(x,y,z);
        if (head.equals(pointZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        head = temp;
    }
    public Point3D getHead() {
        return new Point3D(head.x, head.y, head.z);
    }

    public Vector(Point3D head) {
        if (head.equals(pointZERO)){
            throw new IllegalArgumentException("head cannot be point(0,0,0,)");
        }
        this.head = head;
    }


    public Vector subStract(Vector vector)
    {
        Vector newVec = new Vector(this.head.subStract(vector));
        return newVec;
    };

    public Vector add(Vector vector)
    {
        Vector newVec = new Vector(this.head.add(vector));
        return newVec;
    };
}