package elements;

import primitives.Color;

public class AmbientLight {
    private final Color intensity;

    public AmbientLight (Color iA, double kA){
        intensity = iA.scale(kA);
    }

    public Color getIntensity(){
        return intensity;
    }
}
