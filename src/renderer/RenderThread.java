package renderer;

import elements.Camera;
import primitives.*;
import java.util.*;

/**
 * Class which create the color matrix of the image from the scene
 */
public class RenderThread {

    // accumulation of pixel color matrix,
    // and holding image related parameters of View Plane - pixel matrix size and resolution
    private ImageWriter imageWriter;
    // the intersections of ray with the scene
    private RayTraceBase basicRayTracer;
    // the information about camera & view - plane
    private Camera camera;

    /**
     *
     */
    private boolean superS = false;
    /**
     *
     */
    private boolean adaptiveSS = false;

    private ThreadPool<Pixel> _threadPool = null;
    private Pixel _nextPixel = null;
    private boolean _printPercent = false;

    /**
     * Private CTOR of the class built by Builder Pattern only
     *
     * @param renderBuilder A virtual entity of the class we are currently implementing
     */
    private RenderThread(RenderBuilder renderBuilder) {
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



//        for(int i = 0; i < imageWriter.getNy(); i++) {
//            for(int j = 0; j < imageWriter.getNx(); j++) {
//                if(superS) {
//                    LinkedList<Ray> beam = camera.constructBeam(imageWriter.getNx(), imageWriter.getNy(), j, i, divide);
//                    rColor = 0;
//                    gColor = 0;
//                    bColor = 0;
//                    for (Ray ray : beam) {
//                        rColor += basicRayTracer.traceRay(ray).getColor().getRed();
//                        gColor += basicRayTracer.traceRay(ray).getColor().getGreen();
//                        bColor += basicRayTracer.traceRay(ray).getColor().getBlue();
//                    }
//                    imageWriter.writePixel(
//                            j, i, new Color(
//                                    rColor / (divide*divide + 1),
//                                    gColor / (divide*divide + 1),
//                                    bColor / (divide*divide + 1)));
//                }
//                else {
//                    Ray ray = camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i);
//                    imageWriter.writePixel(j, i, basicRayTracer.traceRay(ray));
//                }
//            }
//        }
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
         *
         */
        private boolean adaptiveSS = false;

        /**
         *
         */
        private boolean superS = false;

        private ThreadPool<Pixel> _threadPool = null;
        private Pixel _nextPixel = null;
        private boolean _printPercent = false;

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

        public RenderBuilder setAdaptiveSS(boolean adaptiveSS) {
            this.adaptiveSS = adaptiveSS;
            return this;
        }

        /**
         * todo
         * @param superS
         * @return
         */
        public RenderBuilder setSuperS(boolean superS) {
            this.superS = superS;
            return this;
        }

        /**
         * Chaining method for setting number of threads.
         * If set to 1, the render won't use the thread pool.
         * If set to greater than 1, the render will use the thread pool with the given threads.
         * If set to 0, the thread pool will pick the number of threads.
         * @param threads number of threads to use
         * @exception IllegalArgumentException when threads is less than 0
         * @return the current render
         */
        public RenderBuilder setMultithreading(int threads) {
            if (threads < 0) {
                throw new IllegalArgumentException("threads can be equals or greater to 0");
            }

            // run as single threaded without the thread pool
            if (threads == 1) {
                _threadPool = null;
                return this;
            }

            _threadPool = new ThreadPool<Pixel>() // the thread pool choose the number of threads (in case threads is 0)
                    .setParamGetter(this::getNextPixel)
                    .setTarget(this::renderImageMultithreaded);
            if (threads > 0) {
                _threadPool.setNumThreads(threads);
            }

            return this;
        }

        /**
         * Chaining method for making the render to print the progress of the rendering in percents.
         * @param print if true, prints the percents
         * @return the current render
         */
        public RenderBuilder setPrintPercent(boolean print) {
            _printPercent = print;
            return this;
        }

        /**
         * Returns the next pixel to draw on multithreaded rendering.
         * If finished to draw all pixels, returns {@code null}.
         */
        private synchronized Pixel getNextPixel() {
            if (_printPercent) {
                // notifies the main thread in order to print the percent
                notifyAll();
            }

            Pixel result = new Pixel();
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            // updates the row of the next pixel to draw
            // if got to the end, returns null
            if (_nextPixel.col >= nX) {
                if (++_nextPixel.row >= nY) {
                    return null;
                }
                _nextPixel.col = 0;
            }

            result.col = _nextPixel.col++;
            result.row = _nextPixel.row;
            return result;
        }

        /**
         * Renders a given pixel on multithreaded rendering.
         * If the given pixel is null, returns false which means kill the thread.
         * @param p the pixel to render
         */
        private boolean renderImageMultithreaded(Pixel p) {
            if (p == null) {
                return false; // kill the thread
            }

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            castRay(nX, nY, p.col, p.row);

            return true; // continue the rendering
        }


        /**todo - antialysing
         * Casts a ray through a given pixel and writes the color to the image.
         * @param nX the number of columns in the picture
         * @param nY the number of rows in the picture
         * @param col the column of the current pixel
         * @param row the row of the current pixel
         */
        private void castRay(int nX, int nY, int col, int row) {
            Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
            Color pixelColor = basicRayTracer.traceRay(ray);
            imageWriter.writePixel(col, row, pixelColor);
        }

        /**
         * We call this function when we have finished giving values to all the fields of the class,
         * and we are interested in creating an entity
         *
         * @return Render
         */
        public RenderThread build() {
            return new RenderThread(this);
        }
    }

    /**
     * Prints the progress in percents only if it is greater than the last time printed the progress.
     * @param currentPixel the index of the current pixel
     * @param pixels the number of pixels in the image
     * @param lastPercent the percent of the last time printed the progress
     * @return If printed the new percent, returns the new percent. Else, returns {@code lastPercent}.
     */
    private int printPercent(int currentPixel, int pixels, int lastPercent) {
        int percent = currentPixel * 100 / pixels;
        if (percent > lastPercent) {
            System.out.printf("%02d%%\n", percent);
            System.out.flush();
            return percent;
        }
        return lastPercent;
    }

    /**
     * Helper class to represent a pixel to draw in a multithreading rendering.
     */
    private static class Pixel {
        public int col, row;

        public Pixel(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public Pixel() {}
    }
}