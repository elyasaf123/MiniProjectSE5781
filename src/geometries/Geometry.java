package geometries;

import primitives.*;

/**
 * interface for any geometric object
 */
public abstract class Geometry implements Intersectable {

    protected Color emission =  Color.BLACK;

    //Todo
    public Color getEmission() {
        return emission;
    }

    //Todo
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * function to get the normal
     *
     * @param point3D a 3d point
     *
     * @return normal of the point
     */
    abstract public Vector getNormal(Point3D point3D);
}