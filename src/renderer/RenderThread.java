package renderer;

import primitives.*;
import elements.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 */
public class RenderThread {
    /**
     * the information about camera & view - plane
     */
    private Camera camera;
    /**
     * accumulation of pixel color matrix,
     * and holding image related parameters of View Plane - pixel matrix size and resolution
     */
    private ImageWriter imageWriter;
    /**
     * the intersections of ray with the scene
     */
    private BasicRayTracer tracer;
    /**
     * true if we want to use super sampling
     */
    private boolean superS = false;
    /**
     * true if we want to use adaptive super sampling
     */
    private boolean adaptiveSS = false;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public RenderThread setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public RenderThread setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (RenderThread.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (RenderThread.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (RenderThread.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (RenderThread.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (RenderThread.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (RenderThread.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     *
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public RenderThread setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public RenderThread setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public RenderThread setRayTracer(BasicRayTracer tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     *todo
     * @param superS
     *
     * @return
     */
    public RenderThread setSuperS(boolean superS) {
        this.superS = superS;
        return this;
    }

    /**
     * todo
     * @param adaptiveSS
     * @return
     */
    public RenderThread setAdaptiveSS(boolean adaptiveSS) {
        this.adaptiveSS = adaptiveSS;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
        Color color = tracer.traceRay(ray);
        imageWriter.writePixel(col, row, color);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (tracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();

        if (threadsCount == 0) {
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    if (superS) {
                        if (adaptiveSS) {
                            Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
                            HashMap<Integer, Ray> myRays = new HashMap<>();
                            myRays.put(3, ray);
                            imageWriter.writePixel(j, i, renderPixel(nX, nY, 15, myRays));
                        } else {
                            double divide = 8;
                            double rColor = 0;
                            double gColor = 0;
                            double bColor = 0;

                            LinkedList<Ray> beam = camera.constructBeam(nX, nY, j, i, divide);
                            rColor = 0;
                            gColor = 0;
                            bColor = 0;
                            for (Ray ray : beam) {
                                rColor += tracer.traceRay(ray).getColor().getRed();
                                gColor += tracer.traceRay(ray).getColor().getGreen();
                                bColor += tracer.traceRay(ray).getColor().getBlue();
                            }
                            imageWriter.writePixel(
                                    j, i, new Color(
                                            rColor / (divide * divide + 1),
                                            gColor / (divide * divide + 1),
                                            bColor / (divide * divide + 1)));
                        }
                    }
                    else {
                        castRay(nX, nY, j, i);
                    }
                }
            }
        }
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
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

    private Color renderPixelRecursive(HashMap<Integer, Ray> myRays, double nX, double nY, int depth) {

        boolean flag = false;
        Ray mainRay = myRays.get(3);
        Color mainColor = tracer.traceRay(mainRay);
        if (depth >= 1) {
            for (Integer integer : myRays.keySet()) {
                if (integer != 3) {
                    Color tmpColor = tracer.traceRay(myRays.get(integer));
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

    public BufferedImage getImage(){
        return this.imageWriter.getImage();
    }

}