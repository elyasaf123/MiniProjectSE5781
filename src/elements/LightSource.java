package elements;

import primitives.*;

/**
 * An interface that unifies all light sources,
 * because for all of them we need a function that calculates the intensity of light according to a point
 * and in addition we need a function to calculate the direction vector for the point.
 */
public interface LightSource {

    /**
     * calculates the intensity of light according to a point
     *
     * @param p the given point
     *
     * @return the intensity at this point
     */
    public Color getIntensity(Point3D p);

    /**
     * calculates the direction vector to the point
     *
     * @param p the given point
     *
     * @return vector to the point (normalized)
     */
    public Vector getL(Point3D p);

    /**
     * calculate the distance from the light's position to the point given
     *
     * @param point3D the given point (probably a point on one of the scene's shape)
     *
     * @return the distance from the light's position to the point
     */
    public double getDistance(Point3D point3D);
}