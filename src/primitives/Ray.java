package primitives;

public class Ray {
    /**
     *
     */
    private Point3D p0;
    /**
     *
     */
    private Vector dir;


    public Ray(Point3D p0, Vector dir) {
        Vector vector = dir.normalized();
        this.p0 = p0;
        this.dir = vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}