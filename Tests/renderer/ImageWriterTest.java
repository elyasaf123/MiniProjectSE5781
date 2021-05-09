package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * Unit tests for renderer.imageWrite class
 */
public class ImageWriterTest {

    /**
     * Test method for {@link renderer.imageWrite.writeToImage()}.
     */
    @Test
    public void writeToImage(){
        int nX = 800;
        int nY = 600;
        int gapX = nX/16;
        int gapY = nY/10;
        ImageWriter imageWriter = new ImageWriter("blue screen", nX, nY);
        for(int i = 0; i< nY; i++)
        {
            for(int j = 0; j< nX; j++){
                if(i%gapY == 0 || j%gapX == 0) {
                    imageWriter.writePixel(j, i, Color.BLACK);
                }
                else if(i > (nY - gapY) && (j < gapX)) {
                    imageWriter.writePixel(j, i, new Color(255, 0, 0));
                }
                else{
                    imageWriter.writePixel(j,i,new Color(0,0,1000));
                }
            }
            imageWriter.writeToImage();
        }
    }

    /**
     *  Optional test method for {@link renderer.imageWrite.writeToImage()}.
     *
     *  Creates smiley :-)
     */
    @Test
    public void writeToImage2(){
        int nX = 1100;
        int nY = 1000;
        int gapX = nX/11;
        int gapY = nY/10;
        ImageWriter imageWriter = new ImageWriter("smiley face", nX, nY);
        for(int i = 0; i< nY; i++) {
            for(int j = 0; j< nX; j++){

                // eyes = blue
                if(i >=100 && i <= 200 && ((j >= 300  && j <= 400)|| (j >= 700  && j <= 800))) {
                    imageWriter.writePixel(j, i, new Color(0, 0, 255));
                }

                // nose = green
                else if(i >=300 && i <= 400 && j >= 500  && j <= 600){
                    imageWriter.writePixel(j, i, new Color(0, 255, 0));
                }

                // mouth = red
                else if((i >= 500 && i <= 600 && (j >= 200  && j <= 300 || j  >= 800 && j <= 900) )
                        ||(i >= 600 && i <= 700 && (j >= 300  && j <= 400 || j  >= 700 && j <= 800) )
                        || (i >= 700 && i <= 800 && j >= 400  && j <= 700  ))
                {
                    imageWriter.writePixel(j, i, new Color(255, 0, 0));
                }

                // background = yellow
                else{
                    imageWriter.writePixel(j,i,new Color(255,255,0));
                }
            }
            imageWriter.writeToImage();
        }
    }
}