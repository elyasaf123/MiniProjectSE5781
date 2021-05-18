package geometries;

import primitives.*;

/**
 * interface for any geometric object
 */
public abstract class Geometry implements Intersectable {

    // todo
    protected Color emission =  Color.BLACK;

    // todo
    private Material material = new Material();

    /**
     * todo
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Getter for emission
     *
     * @return the emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter for the emission
     *
     * @param emission The emission we get
     *
     * @return this (for chaining)
     */
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

    /**
     * todo
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}