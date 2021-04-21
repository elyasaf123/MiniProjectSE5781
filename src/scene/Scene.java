package scene;

import elements.*;
import geometries.*;
import primitives.*;

public class Scene {
    public final String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public Scene(String name){
        this.name = name;
        background = Color.BLACK;
        ambientLight = new AmbientLight(Color.BLACK,0);
        geometries = new Geometries();
    }

    //chaining message
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}