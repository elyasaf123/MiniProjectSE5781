package scene;
import elements.*;
import geometries.*;
import primitives.*;


/**
 * A class that represents the scene, that is, the positions of the shapes in 3D, and the background lighting
 */
public class Scene {



    private Scene(SceneBuilder sceneBuilder){
        this.name = sceneBuilder.name;
        this.background = sceneBuilder.background;
        this.ambientLight = sceneBuilder.ambientLight;
        this.geometries = sceneBuilder.geometries;
    }
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;



    public static class SceneBuilder{
        private final String name;
        private Color background;
        private AmbientLight ambientLight;
        private Geometries geometries;

        public SceneBuilder(String name){
            this.name = name;
        }

        /**
         * setter for background
         *
         * @param background the given background
         *
         * @return Scene (So that we can easily chain when we work on the class)
         */
        public SceneBuilder setBackground(Color background) {
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
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
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
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build(){return new Scene(this);}
    }
}