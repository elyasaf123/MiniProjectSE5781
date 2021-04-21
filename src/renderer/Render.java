package renderer;

import elements.*;
import primitives.*;
import scene.*;
import java.util.*;

/**
 * todo
 */
public class Render {
    private ImageWriter imageWriter = null;
    private Scene scene = null;
    private Camera camera = null;
    private BasicRayTracer basicRayTracer = null;

    /**
     * todo
     * @param imageWriter
     * @return
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * todo
     * @param scene
     * @return
     */
    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    /**
     * todo
     * @param camera
     * @return
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * todo
     * @param basicRayTracer
     * @return
     */
    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        this.basicRayTracer = basicRayTracer;
        return this;
    }

    /**
     * todo
     */
    public void renderImage() {
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is null","Render","imageWriter");
        else if(scene == null)
            throw new MissingResourceException("scene is null","Render","scene");
        else if(camera == null )
            throw new MissingResourceException("camera is null","Render","camera");
        else if(basicRayTracer == null)
            throw new MissingResourceException("basicRayTracer is null","Render","basicRayTracer");

        for(int i = 0; i< imageWriter.getNy(); i++)
        {
            for(int j = 0; j< imageWriter.getNx(); j++){
                Ray ray = camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(),j,i);
                BasicRayTracer basicRayTracer = new BasicRayTracer(scene);
                imageWriter.writePixel(j,i,basicRayTracer.traceRay(ray));
            }
            imageWriter.writeToImage();
        }
    }

    /**
     * todo
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is null","Render","imageWriter");
        for(int i = 0; i< imageWriter.getNy(); i++)
        {
            for(int j = 0; j< imageWriter.getNx(); j++){
                if(i%interval == 0 || j%interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
            imageWriter.writeToImage();
        }
    }

    /**
     * todo
     */
    public void writeToImage() {
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is null","Render","imageWriter");
        imageWriter.writeToImage();
    }
}