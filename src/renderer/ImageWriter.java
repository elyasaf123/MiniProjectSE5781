package renderer;

import primitives.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;

/**
 *CLASS FOR CREATING AN IMAGE INCLUDES WRITE PIXEL AND WRITE TO IMAGE
 * Image writer class combines accumulation of pixel color matrix and finally
 * producing a non-optimized jpeg image from this matrix. The class although is
 * responsible of holding image related parameters of View Plane - pixel matrix
 * size and resolution
 */
public class ImageWriter {

    /**
     *     // amount of pixels by Width
     */
    private int nX;

    /**
     * amount of pixels by Height
     */
    private int nY;

    /**
     * path to save the picture
     */
    private static final String FOLDER_PATH = System.getProperty("user.dir") + "/images";

    /**
     * image
     */
    private BufferedImage image;

    /**
     * name of the image
     */
    private String imageName;

    /**
     * logger
     */
    private Logger logger = Logger.getLogger("ImageWriter");

    // ***************** Constructors ********************** //

    /**
     * Image Writer constructor accepting image name and View Plane parameters,
     *
     * @param imageName the name of jpeg file
     * @param nX        amount of pixels by Width
     * @param nY        amount of pixels by height
     */
    public ImageWriter(String imageName, int nX, int nY) {
        this.imageName = imageName;
        this.nX = nX;
        this.nY = nY;

        image = new BufferedImage(nX, nY, BufferedImage.TYPE_INT_RGB);
    }

    // ***************** Getters/Setters ********************** //

    /**
     * View Plane Y axis resolution
     *
     * @return the amount of vertical pixels
     */
    public int getNy() {
        return nY;
    }

    /**
     * View Plane X axis resolution
     *
     * @return the amount of horizontal pixels
     */
    public int getNx() {
        return nX;
    }

    // ***************** Operations ******************** //

    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     */
    public void writeToImage() {
        try {
            File file = new File(FOLDER_PATH + '/' + imageName + ".png");
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error", e);
        }
    }

    /**
     * getter
     * @return image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * The function writePixel writes a color of a specific pixel into pixel color matrix
     *
     * @param xIndex X axis index of the pixel
     * @param yIndex Y axis index of the pixel
     * @param color  final color of the pixel
     */
    public void writePixel(int xIndex, int yIndex, Color color) {
        image.setRGB(xIndex, yIndex, color.getColor().getRGB());
    }
}