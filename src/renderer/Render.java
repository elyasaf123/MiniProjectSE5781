package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Class which create the color matrix of the image from the scene
 */
public class Render {

    // accumulation of pixel color matrix,
    // and holding image related parameters of View Plane - pixel matrix size and resolution
    private ImageWriter imageWriter;
    // the intersections of ray with the scene
    private RayTraceBase basicRayTracer;
    // the information about camera & view - plane
    private Camera camera;
    /**
     * true if we want to use super sampling
     */
    private boolean superS = false;
    /**
     * true if we want to use adaptive super sampling
     */
    private boolean adaptiveSS = false;

    /**
     * Private CTOR of the class built by Builder Pattern only
     *
     * @param renderBuilder A virtual entity of the class we are currently implementing
     */
    private Render(RenderBuilder renderBuilder) {
        this.imageWriter = renderBuilder.imageWriter;
        this.basicRayTracer = renderBuilder.basicRayTracer;
        this.camera = renderBuilder.camera;
        this.superS = renderBuilder.superS;
        this.adaptiveSS = renderBuilder.adaptiveSS;
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
        else if(camera == null )
            throw new MissingResourceException("camera is null","Render","camera");
        else if(basicRayTracer == null)
            throw new MissingResourceException("basicRayTracer is null","Render","basicRayTracer");

        double divide = 8;
        double rColor = 0;
        double gColor = 0;
        double bColor = 0;


        for(int i = 0; i < imageWriter.getNy(); i++) {
            for(int j = 0; j < imageWriter.getNx(); j++) {
                if(superS) {
                    if (adaptiveSS) {
                        Ray ray = camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i);
//                    Color  color = renderPixel(...?);
                        HashMap<Integer,Ray> myRays = new HashMap<>();
                        myRays.put(3,ray);
                        imageWriter.writePixel(j, i, renderPixel(imageWriter.getNx(),imageWriter.getNy(),15,myRays));
                    }
                    else {
                        LinkedList<Ray> beam = camera.constructBeam(imageWriter.getNx(), imageWriter.getNy(), j, i, divide);
                        rColor = 0;
                        gColor = 0;
                        bColor = 0;
                        for (Ray ray : beam) {
                            rColor += basicRayTracer.traceRay(ray).getColor().getRed();
                            gColor += basicRayTracer.traceRay(ray).getColor().getGreen();
                            bColor += basicRayTracer.traceRay(ray).getColor().getBlue();
                        }
                        imageWriter.writePixel(
                                j, i, new Color(
                                        rColor / (divide*divide + 1),
                                        gColor / (divide*divide + 1),
                                        bColor / (divide*divide + 1)));
                    }
                }
                else {
                    Ray ray = camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i);
                    imageWriter.writePixel(j,i,basicRayTracer.traceRay(ray));
                }
            }
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
                if(i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
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

    /**
     * Builder Pattern, by this class we are updating the parent class (Render),
     * instance that we are interested in creating even before we create it
     */
    public static class RenderBuilder {

        // accumulation of pixel color matrix,
        // and holding image related parameters of View Plane - pixel matrix size and resolution
        private ImageWriter imageWriter;

        // the intersections of ray with the scene
        private RayTraceBase basicRayTracer;

        // the information about camera & view - plane
        private Camera camera;
        /**
         *todo
         */
        private boolean superS = false;
        /**
         *todo
         */
        private boolean adaptiveSS = false;

        /**
         * setter for imageWriter
         *
         * @param imageWriter the given imageWriter
         *
         * @return RenderBuilder (So that we can easily chain when we work on the class)
         */
        public RenderBuilder setImageWriter(ImageWriter imageWriter) {
            this.imageWriter = imageWriter;
            return this;
        }

        /**
         * setter for camera
         *
         * @param camera the given camera
         *
         * @return RenderBuilder (So that we can easily chain when we work on the class)
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
         * @return RenderBuilder (So that we can easily chain when we work on the class)
         */
        public RenderBuilder setRayTracer(BasicRayTracer basicRayTracer) {
            this.basicRayTracer = basicRayTracer;
            return this;
        }

        /**
         *todo
         * @param superS
         *
         * @return
         */
        public RenderBuilder setSuperS(boolean superS) {
            this.superS = superS;
            return this;
        }

        /**
         * todo
         * @param adaptiveSS
         * @return
         */
        public RenderBuilder setAdaptiveSS(boolean adaptiveSS) {
            this.adaptiveSS = adaptiveSS;
            return this;
        }

        /**
         * We call this function when we have finished giving values to all the fields of the class,
         * and we are interested in creating an entity
         *
         * @return Render
         */
        public Render build() {
            return new Render(this);
        }


    }

    /**
     *
     * @return
     */
    public BufferedImage getImage(){
        return this.imageWriter.getImage();
    }


    private Color renderPixelRecursive(HashMap<Integer, Ray> myRays, double nX, double nY, int depth) {

        boolean flag = false;
        Ray mainRay = myRays.get(3);
        Color mainColor = basicRayTracer.traceRay(mainRay);
        if (depth >= 1) {
            for (Integer integer : myRays.keySet()) {
                if (integer != 3) {
                    Color tmpColor = basicRayTracer.traceRay(myRays.get(integer));
                    if (!tmpColor.equals(mainColor)) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                List<Ray> newRays = camera.construct4RaysThroughPixel(myRays.get(3), nX, nY);
                HashMap<Integer, Ray> rays = new HashMap<>();
                rays.put(1, myRays.get(1));
                rays.put(2, newRays.get(0));
                rays.put(3, camera.constructPixelCenterRay(myRays.get(1), nX * 2, nY * 2));
                rays.put(4, newRays.get(1));
                rays.put(5, myRays.get(3));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                rays = new HashMap<>();
                rays.put(1, newRays.get(0));
                rays.put(2, myRays.get(2));
                rays.put(3, camera.constructPixelCenterRay(newRays.get(0), nX * 2, nY * 2));
                rays.put(4, myRays.get(3));
                rays.put(5, newRays.get(2));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                rays = new HashMap<>();
                rays.put(1, newRays.get(1));
                rays.put(2, myRays.get(3));
                rays.put(3, camera.constructPixelCenterRay(newRays.get(1), nX * 2, nY * 2));
                rays.put(4, myRays.get(4));
                rays.put(5, newRays.get(3));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                rays = new HashMap<>();
                rays.put(1, myRays.get(3));
                rays.put(2, newRays.get(2));
                rays.put(3, camera.constructPixelCenterRay(myRays.get(3), nX * 2, nY * 2));
                rays.put(4, newRays.get(3));
                rays.put(5, myRays.get(5));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                mainColor = mainColor.reduce(5);
            }
        }
        return mainColor;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param depth
     * @param firstRays
     * @return
     */
    private Color renderPixel(double nX, double nY, int depth, HashMap<Integer, Ray> firstRays) {
        HashMap<Integer, Ray> myRays = camera.construct5RaysFromRay(firstRays, nX, nY);
        return renderPixelRecursive(myRays, nX, nY, depth);
    }
}