package renderer;

import primitives.*;
import scene.*;

/**
 * todo
 */
public abstract class RayTraceBase {
    protected Scene scene;

    /**
     * todo
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