package elements;

import primitives.*;

/**
 * Class for the lighting setting that illuminates the scene
 */
public class AmbientLight extends Light{

    /**
     * CTOR
     *
     * @param iA The color of the light
     * @param kA The light intensity
     */
    public AmbientLight (Color iA, double kA){
        super(iA.scale(kA));
    }

    /**
     * Default CTOR that initializes the intensity field to black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}