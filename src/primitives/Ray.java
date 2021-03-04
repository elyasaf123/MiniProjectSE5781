package primitives;

import java.util.Objects;

public class Ray {
    private Point3D p0;
    private Vector dir;


    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir;
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }
}
