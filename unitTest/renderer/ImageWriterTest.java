package renderer;
import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * Testing ImageWriter Class
 *
 * @author tehila
 *
 */
class ImageWriterTest {
    @Test
    void testWriteToImage(){
         int nX=800;
         int nY= 500;
         Color pink=new Color(255d, 153d,204d);
         Color white=new Color(255d,255d,255d);

         ImageWriter imageWriter=new ImageWriter("pink test with grid",nX,nY);

             // Fill the image with the pink color
             for (int y = 0; y < nY; y++) {
                 for (int x = 0; x < nX; x++) {
                     imageWriter.writePixel(x, y, pink);
                 }
             }

             // Draw the grid using the white color
             int gridRows = 10;
             int gridColumns = 16;
             int squareWidth = nX / gridColumns;
             int squareHeight = nY / gridRows;

             for (int row = 0; row < gridRows; row++) {
                 for (int col = 0; col < gridColumns; col++) {
                     if (row % 2 == 0 || col % 2 == 0) {
                         int xStart = col * squareWidth;
                         int yStart = row * squareHeight;

                         for (int y = yStart; y < yStart + squareHeight; y++) {
                             for (int x = xStart; x < xStart + squareWidth; x++) {
                                 imageWriter.writePixel(x, y, white);
                             }
                         }
                     }
                 }
             }

             // Save the image
             imageWriter.writeToImage();



    //ImageWriter.fillBackground(pink);
   // ImageWriter.printGrid(50,white);
  //  ImageWriter.writeToImage();

    }

}
