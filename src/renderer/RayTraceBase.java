package renderer;

import primitives.*;
import scene.*;

/**
 * todo javadoc
 */
public abstract class RayTraceBase {
    protected Scene scene;

    /**
     * todo javadoc
     * @param scene
     */
    public RayTraceBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * abstract trace ray function
     * @param ray 3D ray
     * @return color
     */
    abstract Color traceRay(Ray ray);
}