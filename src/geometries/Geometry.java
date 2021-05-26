package geometries;

import primitives.*;

/**
 * interface for any geometric object
 */
public abstract class Geometry implements Intersectable {

    // The color of the shape (black in default)
    protected Color emission =  Color.BLACK;

    // The material from which the shape is made
    private Material material = new Material();

    /**
     * getter for the material
     *
     * @return the material
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
     * setter for the material
     * @param material the material we get
     *
     * @return this (for chaining)
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}