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
 * The TriangleTests class is responsible for testing the Triangle class.
 * It includes unit tests for getNormal method .
 * @author Maayan Amar
 */
class TriangleTests {
    /**
     * Test method for{@link geometries.Triangle#getNormal(Point)}
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

    @Test
    void findIntersections(){
        // Equivalence Partitions tests ======================================================================

        // EP01 ray passes through triangle
        Ray ray = new Ray(new Point(3, 3, 2), new Vector(-1, -1, -4));
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(1, 5, 0), new Point(6, 0, 0));
        assertEquals(1, triangle.findIntersections(ray).size());
          assertEquals(new Point(2.5, 2.5, 0), triangle.findIntersections(ray).get(0));

        // EP02 ray misses triangle on one side
        ray = new Ray(new Point(3, 3, 2), new Vector(1, 1, -4));
        assertEquals(null, triangle.findIntersections(ray));

        // EP03 ray misses triangle on two side
        ray = new Ray(new Point(3, 3, 2), new Vector(-5, 5.5, -4));
        assertEquals(null, triangle.findIntersections(ray));

        // Boundary value tests ==============================================================================
        // BV01 ray intersects vertex
        ray = new Ray(new Point(1, 0, 3), new Vector(0, 0, -1));
        assertEquals(null, triangle.findIntersections(ray));

        // BV02 ray intersects edge
        ray = new Ray(new Point(1, 0, 3), new Vector(1, 0, -6));
        assertEquals(null, triangle.findIntersections(ray));

        // BV03 ray intersects edge continuation imaginary line
        ray = new Ray(new Point(0.5, 0, 3), new Vector(0, 0, -1));
        assertEquals(null, triangle.findIntersections(ray));

    }

}