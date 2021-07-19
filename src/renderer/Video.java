package renderer;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * class for generating a video
 */
public class Video {
    /**
     * folder path of video
     */
    private static final String FOLDER_PATH = System.getProperty("user.dir") + "/images";

    /**
     * a function that generates a video
     * @param fileName file name where the video will be generated
     * @param images list of images for video
     * @param fps frames per second in video
     * @throws IOException in case exception is thrown
     */
    public static void generateVideo(String fileName, BufferedImage[] images, int fps) throws IOException {
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(new File(FOLDER_PATH+"/"+fileName+".mp4"), fps); // 25 fps

        //go over all the images and make a video out of it
        for (BufferedImage image : images) {
            encoder.encodeImage(image);
        }
        encoder.finish();
    }
}
