package primitives;

import java.util.Objects;

/**
 *
 */
public class Ray {
    /**
     *
     */
    private Point3D p0;
    /**
     *
     */
    private Vector dir;

    /**
     *
     * @param p0
     * @param dir
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir;
    }

    /**
     *
     * @return
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     *
     * @return
     */
    public Vector getDir() {
        return dir;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }
}
