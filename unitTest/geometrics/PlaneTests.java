/**
 * Tehila Ben Moshe-213385263, email:bmtehila@gmail.com
 * Maayan Amar-211763826, email:maayanamar11.01@gmail.com
 */
package geometrics;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * The PlaneTests class is responsible for testing the Plane class.
 * It includes unit tests for getNormal method and for the Plane constructor .
 * @author Maayan Amar
 */
class PlaneTests {

    /**
     * Test method for {@link geometrics.Plane#Plane}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 2, 0);
        // TC11: create a plane with two points that converge
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(p1, p2, p1);
        }, "Failed to throw exception for two points that converge");

        // TC12: create a plane with three points that are on the same line
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(p1, p2, p3);
        }, "Failed to throw exception for three points that are on the same line");
    }



    /**
     * Test method for {@link geometrics.Plane#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        Point p=new Point(0,0,1);
        Plane plane = new Plane(new Point(0,0,1), new Point(0,1,0), new Point(1,0,0));
        Vector result=plane.getNormal(p);
        assertEquals(1,result.length(),0.00000001,"Planes normal is not a unit vector");
        assertTrue(isZero(result.dotProduct(p.subtract(new Point(0,1,0)))),"Planes normal isnt orthogonal to plane");
        // =============== Boundary Values Tests =============
        // There are no boundary values tests according to instructions
    }




}