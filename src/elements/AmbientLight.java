package elements;

import primitives.*;

/**
 *
 */
public class AmbientLight {
    private final Color intensity;

    /**
     *
     * @param iA
     * @param kA
     */
    public AmbientLight (Color iA, double kA){
        intensity = iA.scale(kA);
    }

    /**
     *
     * @return
     */
    public Color getIntensity(){
        return intensity;
    }
}
