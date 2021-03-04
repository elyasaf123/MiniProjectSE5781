package primitives;

import java.util.Objects;

/**
 * basic geometric object for 3D point
 */
public class Point3D {
    Coordinate x;
    Coordinate y;
    Coordinate z;


    public static Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Point3D(double _x, double _y, double _z) {
        x = new Coordinate(_x);
        y = new Coordinate(_y);
        z = new Coordinate(_z);
    }


    /**
     *
     * @param point3D
     * @return
     */
    public double distanceSquared(Point3D point3D) {
        double x1 = x.coord;
        double y1 = y.coord;
        double z1 = z.coord;
        double x2 = point3D.x.coord;
        double y2 = point3D.y.coord;
        double z2 = point3D.z.coord;

        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
    }

    /**
     *
     * @param point3D
     * @return
     */
    public double distance(Point3D point3D){
        return Math.sqrt(distanceSquared(point3D));
    }

    public Point3D add(Vector vector)
    {
        return new Point3D(
                x.coord + vector.head.x.coord,
                y.coord + vector.head.y.coord,
                z.coord + vector.head.z.coord);
    }

    public Vector subtract(Point3D point3D)
    {
        return new Vector(
                x.coord - point3D.x.coord,
                y.coord - point3D.y.coord,
                z.coord - point3D.z.coord);
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x.toString() +
                ", y=" + y.toString() +
                ", z=" + z.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x.coord, y.coord, z.coord);
    }

}