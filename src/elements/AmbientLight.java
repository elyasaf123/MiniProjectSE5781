package elements;

import primitives.*;

/**
 * Class for the lighting setting that illuminates the scene
 */
public class AmbientLight {
    protected final Color intensity;

    /**
     * CTOR
     *
     * @param iA The color of the light
     * @param kA The light intensity
     */
    public AmbientLight (Color iA, double kA){
        intensity = iA.scale(kA);
    }

    /**
     * getter for intensity
     *
     * @return intensity
     */
    public Color getIntensity(){
        return intensity;
    }
}