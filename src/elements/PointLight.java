package elements;

import primitives.*;

/**
 * A class representing a point light source that illuminates around it in a spherical shape
 */
public class PointLight extends Light implements LightSource{

    /**
     *     // the point-light's position
     */
    private final Point3D position;


    /**
     *     // (according to the Pong model there are three attenuation coefficients - constant, linear and quadratic)
     *     //
     *     // Constant attenuation coefficient for light intensity (constant = 1)
     *     //
     *     // It is a constant equal to 1 because in the calculation we put it in the denominator
     *     // and thus make sure that the intensity does not increase with the distance from the light
     *     // (does not make sense, so the denominator must be greater than 1
     *     // in order for a result smaller or equal to the original intensity)
     */
    private double kC = 1;

    /**
     *     // linear attenuation coefficient for light intensity
     */
    private double kL = 0;

    /**
     *     // quadratic attenuation coefficient for light intensity
     */
    private double kQ = 0;

    /**
     * setter for constant attenuation coefficient for light intensity
     *
     * @param kC the given kC
     *
     * @return this (for chaining)
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter for linear attenuation coefficient for light intensity
     *
     * @param kL the given kL
     *
     * @return this (for chaining)
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter for quadratic attenuation coefficient for light intensity
     *
     * @param kQ the given kQ
     *
     * @return this (for chaining)
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * CTOR
     *
     * @param intensity the given intensity
     * @param position the given point-light's position
     * @param kC the given constant attenuation coefficient for light intensity
     * @param kL the given linear attenuation coefficient for light intensity
     * @param kQ the given quadratic attenuation coefficient for light intensity
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    /**
     * CTOR
     * (uses when we want to create an instance of the class when the attenuation coefficients get a default value)
     *
     * @param intensity the given intensity
     * @param position the given light-source's position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Method for calculating the point color according to the Pong model
     *
     * @param p the given point
     *
     * @return the color at this point by this function:
     * intensity = original-intensity / (kc + kl * d + kq * d ^ 2)
     */
    public Color getIntensity(Point3D p) {
        double d = position.distance(p);
        return intensity.scale(1/(kC + kL * d + kQ*d*d));
    }

    /**
     * getter for direction vector from the point-light's position to the given point in the scene
     *
     * @param p the given point in the scene
     *
     * @return normalized direction vector from the point-light's position to the given point in the scene
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalized();
    }

    @Override
    public double getDistance(Point3D point3D) {
        return position.distance(point3D);
    }
}