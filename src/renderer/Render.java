package renderer;

import elements.*;
import primitives.*;
import scene.*;
import java.util.*;

/**
 * Class which create the color matrix of the image from the scene
 */
public class Render {
    private Render(RenderBuilder renderBuilder) {
        this.imageWriter = renderBuilder.imageWriter;
        this.scene = renderBuilder.scene;
        this.basicRayTracer = renderBuilder.basicRayTracer;
        this.camera = renderBuilder.camera;
    }

    private ImageWriter imageWriter;
    private Scene scene;
    private Camera camera;
    private RayTraceBase basicRayTracer;

    public static class RenderBuilder {

        private ImageWriter imageWriter;
        private Scene scene;
        private Camera camera;
        private RayTraceBase basicRayTracer;

        /**
         * setter for imageWriter
         *
         * @param imageWriter the given imageWriter
         *
         * @return Render (So that we can easily chain when we work on the class)
         */
        public RenderBuilder setImageWriter(ImageWriter imageWriter) {
            this.imageWriter = imageWriter;
            return this;
        }

        /**
         * setter for scene
         *
         * @param scene the given scene
         *
         * @return Render (So that we can easily chain when we work on the class)
         */
        public RenderBuilder setScene(Scene scene) {
            this.scene = scene;
            return this;
        }

        /**
         * setter for camera
         *
         * @param camera the given camera
         *
         * @return Render (So that we can easily chain when we work on the class)
         */
        public RenderBuilder setCamera(Camera camera) {
            this.camera = camera;
            return this;
        }

        /**
         * setter for rayTracer
         *
         * @param basicRayTracer the given rayTracer
         *
         * @return Render (So that we can easily chain when we work on the class)
         */
        public RenderBuilder setRayTracer(BasicRayTracer basicRayTracer) {
            this.basicRayTracer = basicRayTracer;
            return this;
        }

        public Render build() {
            return new Render(this);
        }
    }



    /**
     * For all the pixels of the ViewPlane, a ray will be built,
     * and for each ray we will get a color from the ray's tracer.
     * We will put the color in the appropriate pixel of the image maker (using method writePixel)
     *
     * @throws MissingResourceException if not all fields a non-empty value was entered
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
     * A method to create a grid of lines
     *
     * @param interval Indicates for which quantity of pixels a grid line is passed,
     *                 both horizontally and vertically
     * @param color the grid's color
     *
     * @throws MissingResourceException if imageWriter is null
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
            this.writeToImage();
        }
    }

    /**
     * Activates (delegate!) The appropriate image maker's method
     *
     * @throws MissingResourceException if imageWriter is null
     */
    public void writeToImage() {
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is null","Render","imageWriter");
        imageWriter.writeToImage();
    }
}