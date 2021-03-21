package primitives;
/**
 * The set of points on a straight line that are on one side relative to a given point
 */
public class Ray {
    /**
     * The point from which the ray start
      */
    private Point3D p0;
    /**
     * The unit vector that emerges from the point
     */
    private Vector dir;

    /**
     * CTOR
     * @param p0 Point 3D
     * @param dir the vector
     */
    public Ray(Point3D p0, Vector dir) {
        Vector vector = dir.normalized();
        this.p0 = p0;
        this.dir = vector;
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
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
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * string that represents the class
     * @return string that represents the class
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point3D getTargetPoint(double t) {
        return getP0().add(dir.scale(t));
    }
}