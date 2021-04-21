package scene;

import elements.*;
import geometries.*;
import primitives.*;

/**
 * todo
 */
public class Scene {
    public final String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    /**
     * todo
     * @param name
     */
    public Scene(String name){
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight(Color.BLACK,0);
        geometries = new Geometries();
    }

    /**
     * todo
     * //chaining message
     * @param background
     * @return
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * todo
     * @param ambientLight
     * @return
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * todo
     * @param geometries
     * @return
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}