package elements;

import primitives.*;

/**
 * A class that represents light that comes from very far away (like the sun),
 * we have no meaning to distance in this case because it is always infinite,
 * there is meaning only to the direction of light
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * The direction from which the light comes
     */
    private Vector direction;

    /**
     * CTOR
     *
     * @param intensity The light intensity
     * @param direction The direction from which the light comes
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    /**
     * getter for the intensity
     *
     * @param p The point in the scene where we want to calculate the intensity
     *
     * @return The original intensity of the light source (it does not change depending on the distance)
     */
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /**
     * getter for direction
     * @param p The point in the scene where we want to calculate the vector from the light source to it
     *
     * @return the normalized direction vector (it does not change depending on the position)
     */
    @Override
    public Vector getL(Point3D p) {
        return direction.normalized();
    }

    @Override
    public double getDistance(Point3D point3D) {
        return Double.POSITIVE_INFINITY;
    }
}