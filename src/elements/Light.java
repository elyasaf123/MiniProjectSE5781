package elements;

import primitives.Color;

/**
 * A class that represents all the lighting sources that all have intensity
 */
abstract class Light {

    /**
     * the light's intensity
     */
    protected Color intensity;

    /**
     * CTOR
     *
     * @param intensity the light's intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for the intensity
     *
     * @return the original intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}