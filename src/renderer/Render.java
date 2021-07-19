package renderer;

import elements.*;
import primitives.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 */
public class Render {
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
    /**
     * uses when Renderer resource not set
     */
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    /**
     * uses in case of exception
     */
    private static final String RENDER_CLASS = "Render";
    /**
     * uses in case of exception
     */
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    /**
     * uses in case of exception
     */
    private static final String CAMERA_COMPONENT = "Camera";
    /**
     * uses in case of exception
     */
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";
    /**
     * counter for number of threads
     */
    private int threadsCount = 0;
    /**
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 2;
    /**
     * printing progress percentage
     */
    private boolean print = false;

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     *
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            /**
             * Number of available cores less SPARE_THREADS, we will use what we can
             */
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
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     */
    private class Pixel {
        /**
         * the amount of pixel rows
         */
        private long maxRows = 0;
        /**
         * the amount of pixel columns
         */
        private long maxCols = 0;
        /**
         * maxRows * maxCols
         */
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        /**
         * pixels / 100
         */
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
            if (Render.this.print)
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
         *
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
                if (Render.this.print && this.counter == this.nextCounter) {
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
                if (Render.this.print && this.counter == this.nextCounter) {
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
         *
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
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
     *
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     *
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     *
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(BasicRayTracer tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     * super sampling setter
     *
     * @param superS our determination - using it or not
     *
     * @return renderer itself - for chaining
     */
    public Render setSuperS(boolean superS) {
        this.superS = superS;
        return this;
    }

    /**
     * adaptive super sampling setter
     *
     * @param adaptiveSS our determination - using it or not
     *
     * @return renderer itself - for chaining
     */
    public Render setAdaptiveSS(boolean adaptiveSS) {
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
        /**
         * ray from camera to the specific pixel
         */
        Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
        /**
         * this ray's color
         */
        Color color = tracer.traceRay(ray);
        imageWriter.writePixel(col, row, color);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {

        /**
         * number of pixels in row
         */
        final int nX = imageWriter.getNx();
        /**
         * number of pixels in column
         */
        final int nY = imageWriter.getNy();
        /**
         * the main follow up Pixel object
         */
        final Pixel thePixel = new Pixel(nY, nX);
        /**
         * Generate threads
         */
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                /**
                 * our Pixel object
                 */
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                        if (superS) {
                            if (adaptiveSS) {
                                /**
                                 * the central ray from camera to pixel.
                                 * we save it in the middle of the array and all the others we will save later
                                 */
                                Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row);
                                /**
                                 * we use array to save all the rays we check
                                 */
                                Ray[] myRays = new Ray[6];
                                myRays[3] = ray;
                                imageWriter.writePixel(pixel.col, pixel.row, renderPixel(nX, nY, 3, myRays));
                            } else { // simple SS
                                /**
                                 * 64 pieces
                                 */
                                double divide = 8;
                                /**
                                 * counter for red color
                                 */
                                double rColor = 0;
                                /**
                                 * counter for green color
                                 */
                                double gColor = 0;
                                /**
                                 * counter for blue color
                                 */
                                double bColor = 0;

                                LinkedList<Ray> beam = camera.constructBeam(nX, nY, pixel.col, pixel.row, divide);
                                for (Ray ray : beam) {
                                    rColor += tracer.traceRay(ray).getColor().getRed();
                                    gColor += tracer.traceRay(ray).getColor().getGreen();
                                    bColor += tracer.traceRay(ray).getColor().getBlue();
                                }
                                imageWriter.writePixel(
                                        pixel.col, pixel.row, new Color(
                                                rColor / (divide * divide + 1),
                                                gColor / (divide * divide + 1),
                                                bColor / (divide * divide + 1)));
                            }
                        }
                        else {
                            castRay(nX, nY, pixel.col, pixel.row);
                        }
                    }
            });
        }
        /**
         * Start threads
         */
        for (Thread thread : threads)
            thread.start();
        /**
         * Print percents on the console
         */
        thePixel.print();
        /**
         * Ensure all threads have finished
         */
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

        /**
         * number of pixels in row
         */
        final int nX = imageWriter.getNx();
        /**
         * number of pixels in column
         */
        final int nY = imageWriter.getNy();

        if (threadsCount == 0) {
            // iteration by columns and rows
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    if (superS) {
                        if (adaptiveSS) {
                            /**
                             * the central ray from camera to pixel.
                             * we save it in the middle of the array and all the others we will save later
                             */
                            Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
                            /**
                             * we use array to save all the rays we check
                             */
                            Ray[] myRays = new Ray[6];
                            myRays[3] = ray;
                            imageWriter.writePixel(j, i, renderPixel(nX, nY, 4, myRays));
                        } else { // simple SS
                            /**
                             * 64 pieces
                             */
                            double divide = 8;
                            /**
                             * counter for red color
                             */
                            double rColor = 0;
                            /**
                             * counter for green color
                             */
                            double gColor = 0;
                            /**
                             * counter for blue color
                             */
                            double bColor = 0;

                            LinkedList<Ray> beam = camera.constructBeam(nX, nY, j, i, divide);
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
     * the render function when we use adaptive
     *
     * @param nX number of pixels in row
     * @param nY number of pixels in column
     * @param depth the recursive depth
     * @param central array of the rays, at first it contains only the central ray in the third index
     *
     * @return the color of the whole pixel
     */
    private Color renderPixel(double nX, double nY, int depth, Ray[] central) {
        /**
         * array of the first 5 rays
         */
        Ray[] rays = camera.constructFiveRays(central, nX, nY);
        /**
         * calls the recursive func to calculate the color
         */
        return renderPixelRecursive(rays, nX, nY, depth);
    }

    /**
     * Function for calculating the color of a pixel by using a subdivision - squares when necessary,
     * until stopped when we reached the maximum depth
     *
     * @param myRays array of rays we gonna full it
     *               (or he is already full and we just compare it to know if new colors are added)
     * @param nX number of pixels in row
     * @param nY number of pixels in column
     * @param depth the depth of the recursive
     *
     * @return the average color of the pixel
     */
    private Color renderPixelRecursive(Ray[] myRays, double nX, double nY, int depth) {

        boolean flag = false;
        Ray centralRay = myRays[3];
        Color central = tracer.traceRay(centralRay);
        if (depth >= 1) {
            for (int integer = 1; integer < 6; integer++) {
                if (integer != 3) {
                    Color tmpColor = tracer.traceRay(myRays[integer]);
                    if (!tmpColor.equals(central)) {
                        flag = true;
                        break;
                    }
                }
            }
            // This means that there is at least one ray that has a different color
            // from the center of the square we examined so we will do more iterative check
            if (flag) {
                List<Ray> newRays = camera.constructFourRays(myRays[3], nX, nY);
                Ray[] rays = new Ray[6];
                rays[1] = myRays[1];
                rays[2] = newRays.get(0);
                rays[3] = camera.constructPixelCenterRay(myRays[1], nX * 2, nY * 2);
                rays[4] = newRays.get(1);
                rays[5] = myRays[3];
                /**
                 * recursive call, now the depth is updated accordingly and the height and width are doubled
                 */
                central = central.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));

                rays = new Ray[6];
                rays[1] = newRays.get(0);
                rays[2] = myRays[2];
                rays[3] = camera.constructPixelCenterRay(newRays.get(0), nX * 2, nY * 2);
                rays[4] = myRays[3];
                rays[5] = newRays.get(2);
                /**
                 * recursive call, now the depth is updated accordingly and the height and width are doubled
                 */
                central = central.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));

                rays = new Ray[6];
                rays[1] = newRays.get(1);
                rays[2] = myRays[3];
                rays[3] = camera.constructPixelCenterRay(newRays.get(1), nX * 2, nY * 2);
                rays[4] = myRays[4];
                rays[5] = newRays.get(3);
                /**
                 * recursive call, now the depth is updated accordingly and the height and width are doubled
                 */
                central = central.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));

                rays = new Ray[6];
                rays[1] = myRays[3];
                rays[2] = newRays.get(2);
                rays[3] = camera.constructPixelCenterRay(myRays[3], nX * 2, nY * 2);
                rays[4] = newRays.get(3);
                rays[5] = myRays[5];
                /**
                 * recursive call, now the depth is updated accordingly and the height and width are doubled
                 */
                central = central.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));

                central = central.reduce(5);
            }
        }
        return central;
    }

    public BufferedImage getImage(){
        return this.imageWriter.getImage();
    }
}