package renderer;

import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Video {
    private static final String FOLDER_PATH = System.getProperty("user.dir") + "/images";

    public static void generateVideo(String fileName, BufferedImage[] images, int fps) throws IOException {
        AWTSequenceEncoder encoder = AWTSequenceEncoder.createSequenceEncoder(new File(FOLDER_PATH+"/"+fileName+".mp4"), fps); // 25 fps

        for (BufferedImage image : images) {
            encoder.encodeImage(image);
        }
        encoder.finish();
    }
}
