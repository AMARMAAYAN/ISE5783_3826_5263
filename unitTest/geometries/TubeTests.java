/**
 * Tehila Ben Moshe-213385263, email:bmtehila@gmail.com
 * Maayan Amar-211763826, email:maayanamar11.01@gmail.com
 */
package geometries;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
/**
 * The TubeTests class is responsible for testing the Tube class.
 * It includes unit tests for getNormal method .
 * @author Maayan Amar
 */
class TubeTests {

    /**
     * Test method for{@link geometries.Tube#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a point on the side of the tube
        Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1);
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 1, 1)), "ERROR - TC01: Wrong normal to tube");

        // =============== Boundary Values Tests =================
        //TC11: Test for a point at the head of the tube
        assertEquals(new Vector(1,0,0), tube.getNormal(new Point(1,0,2)), "ERROR - TC02: Wrong normal to tube at head");


    }
}