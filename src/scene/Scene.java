package scene;

import elements.*;
import geometries.*;
import primitives.*;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that represents the scene, that is, the positions of the shapes in 3D,
 * and the background lighting
 */
public class Scene {

    /**
     * Private CTOR of the class built by Builder Pattern only
     *
     * @param sceneBuilder A virtual entity of the class we are currently implementing
     */
    private Scene(SceneBuilder sceneBuilder){
        this.name = sceneBuilder.name;
        this.background = sceneBuilder.background;
        this.ambientLight = sceneBuilder.ambientLight;
        this.geometries = sceneBuilder.geometries;
        this.lights = sceneBuilder.lights;
    }

    // list of light-sources we got in the scene
    public List<LightSource> lights;

    // The scene's name
    public String name;

    // The background's color
    public Color background;

    // The light's ambient in the scene (expressed as color)
    public AmbientLight ambientLight;

    // The positions of the shapes in 3D
    public Geometries geometries;

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }


    /**
     * Builder Pattern, by this class we are updating the parent class (Scene),
     * instance that we are interested in creating even before we create it
     */
    public static class SceneBuilder{

        // list of light-sources we got in the scene
        private List<LightSource> lights;

        // The scene's name
        private final String name;

        // The background's color
        private Color background;

        // The light's ambient in the scene (expressed as color)
        private AmbientLight ambientLight;

        // The positions of the shapes in 3D
        private Geometries geometries;

        /**
         * SceneBuilder's CTOR,
         * gets one component he needs to create a show
         *
         * @param name the scene's name
         */
        public SceneBuilder(String name){
            this.ambientLight = new AmbientLight();
            this.background = Color.BLACK;
            this.name = name;
            this.lights = new LinkedList<>();
        }

        /**
         * setter for background
         *
         * @param background the given background
         *
         * @return SceneBuilder (So that we can easily chain when we work on the class)
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * setter for the list of light-sources we got in the scene
         *
         * @param lights the list given
         *
         * @return this (for chaining)
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        /**
         * setter for ambientLight
         *
         * @param ambientLight the given ambientLight
         *
         * @return SceneBuilder (So that we can easily chain when we work on the class)
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * setter for geometries
         *
         * @param geometries the given geometries
         *
         * @return SceneBuilder (So that we can easily chain when we work on the class)
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * We call this function when we have finished giving values to all the fields of the class,
         * and we are interested in creating an entity
         *
         * @return Scene
         */
        public Scene build(){
            return new Scene(this);
        }
    }
}