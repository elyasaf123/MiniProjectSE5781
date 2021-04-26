package geometries;

import static primitives.Util.*;

/**
 * According to the Composite Design template all components that have a radius
 * will inherit from this abstract class
 * (sphere,tube, and cylinder that already inherits from tube)
 */
public abstract class RadialGeometry {

    /**
     * radius in 3D
     */
    final protected double radius;

    /**
     * CTOR
     *
     * @param radius radius
     */
    public RadialGeometry(double radius) {
        this.radius = alignZero(radius);
    }

    /**
     * getter for radius
     *
     * @return radius in 3D
     */
    public double getRadius() {
        return alignZero(radius);
    }
}