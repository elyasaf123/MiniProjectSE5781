package scene;

import elements.*;
import geometries.*;
import primitives.*;

/**
 * A class that represents the scene, that is, the positions of the shapes in 3D, and the background lighting
 */
public class Scene {
    // The scene's name
    public final String name;
    public Color background;
    public AmbientLight ambientLight;
    // List of the shapes in the scene
    public Geometries geometries;

    /**
     * CTOR
     *
     * @param name the scene's name
     */
    public Scene (String name) {
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight(Color.BLACK,0);
        geometries = new Geometries();
    }

    /**
     * setter for background
     *
     * @param background the given background
     *
     * @return Scene (So that we can easily chain when we work on the class)
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * setter for ambientLight
     *
     * @param ambientLight the given ambientLight
     *
     * @return Scene (So that we can easily chain when we work on the class)
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * setter for geometries
     *
     * @param geometries the given geometries
     *
     * @return Scene (So that we can easily chain when we work on the class)
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}