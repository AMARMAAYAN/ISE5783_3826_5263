/**
 * Tehila Ben Moshe-213385263, email:bmtehila@gmail.com
 * Maayan Amar-211763826, email:maayanamar11.01@gmail.com
 */package geometrics;


import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
/**
 * The TriangleTests class is responsible for testing the Triangle class.
 * It includes unit tests for getNormal method .
 * @author Maayan Amar
 */
class TriangleTests {

    /**
     * Test method for {@link geometrics.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test a regular triangle
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector expectedNormal = new Vector(0, 0, 1);
        assertEquals(expectedNormal, triangle.getNormal(new Point(0, 0, 0)),
                " Triangles normal is not correct");

        // =============== Boundary Values Tests =================
        // No boundary values tests are required for this class
    }

}