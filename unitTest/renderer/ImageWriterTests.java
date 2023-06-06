package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * Testing ImageWriter Class
 *
 * @author tehila
 */
class ImageWriterTests {

    //==== the size of the view plane =====//
    int nX = 800;
    int nY = 500;

    //define the color by their RBG numbers:
    Color pink = new Color(255d, 153d, 204d);
    Color white = new Color(255d, 255d, 255d);


    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("pink test with grid", nX, nY);
        //=== running on the view plane===//
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                //=== create the net ===//
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, white);
                } else {
                    imageWriter.writePixel(i, j, pink);
                }
            }
        }
        // Save the image
        imageWriter.writeToImage();


    }


}