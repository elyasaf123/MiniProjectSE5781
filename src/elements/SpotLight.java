package elements;

import primitives.*;

/**
 * A class representing a spot light source that illuminates around it in a half-spherical shape
 */
public class SpotLight extends PointLight implements LightSource{

    /**
     * The direction in which the spot is aimed
     */
    private final Vector direction;

    /**
     * CTOR
     *
     * @param intensity the spot's intensity
     * @param position the spot's position
     * @param direction the spot's direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    /**
     * Method for calculating the spot color according to the Pong model
     *
     * @param p the given point
     *
     * @return the color at this point by this function:
     * intensity = [original-intensity * max(0, direction * l)] / (kc + kl * d + kq * d ^ 2)
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0,direction.dotProduct(getL(p))));
    }
}