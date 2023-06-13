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
import static primitives.Util.isZero;

/**
 * The PlaneTests class is responsible for testing the Plane class.
 * It includes unit tests for getNormal method and for the Plane constructor .
 * @author Maayan Amar
 */
class PlaneTests {

    /**
     * Test method for{@link geometries.Plane#getNormal(Point)}
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
     * Test method for {@link Plane#getNormal(primitives.Point)}.
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
    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        //  ==================================Equivalence Partitions tests====================================
        // EP01 test if neither orthogonal nor parallel points intersects with plane
        Plane plane = new Plane(new Point(1, 0, 0), new Vector(0, 1, 0));
        Ray ray = new Ray(new Point(0, -2, 0), new Vector(-3, 6, 0));
        assertEquals(1, plane.findIntersections(ray).size(), "ray intersects plane");
        assertEquals(new Point(-1, 0, 0), plane.findIntersections(ray).get(0), "ray intersects plane");

        // EP02 test if ray after plane and goes in opposite direction
        ray = new Ray(new Point(0, -2, 0), new Vector(3, -4, 0));
        assertEquals(null, plane.findIntersections(ray), "ray does not intersect plane");


        //=================================== Boundary Value Analysis tests ===============================
        // BV01  ray is parallel to plane (0 points)
        ray = new Ray(new Point(0, -2, 0), new Vector(0, 0, 1));
        assertEquals(null, plane.findIntersections(ray), "ray is parallel to plane");

        // BV02  ray is parallel to plane and is included in plane (0 points)
        ray = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
        assertEquals(null, plane.findIntersections(ray), "ray is parallel to plane and is included in plane");

        // BV03  ray is orthogonal to plane and starts before plane (1 point)
        ray = new Ray(new Point(0, -2, 0), new Vector(0, 1, 0));
        assertEquals(1, plane.findIntersections(ray).size(), "ray is orthogonal to plane and starts before plane");
        assertEquals(new Point(0, 0, 0), plane.findIntersections(ray).get(0), "ray is orthogonal to plane and starts before plane");

        // BV04  ray is orthogonal to plane and starts in plane (0 points)
        ray = new Ray(new Point(2, 0, 0), new Vector(0, 1, 0));
        assertEquals(null, plane.findIntersections(ray), "ray is orthogonal to plane and starts in plane");

        // BV05  ray is orthogonal to plane and starts after plane (0 points)
        ray = new Ray(new Point(-1, 1, 0), new Vector(0, 1, 0));
        assertEquals(null, plane.findIntersections(ray), "ray is orthogonal to plane and starts after plane");

        // BV06  ray is neither orthogonal nor parallel to plane and starts in plane (0 points)
        ray = new Ray(new Point(2, 0, 0), new Vector(1, 1, 0));
        assertEquals(null, plane.findIntersections(ray), "ray is neither orthogonal nor parallel to plane and starts in plane");

        // BV07  ray is neither orthogonal nor parallel to plane and starts in plane and starts at reference point (0 points)
        ray = new Ray(new Point(1, 0, 0), new Vector(1, 1, 0));
        assertEquals(null, plane.findIntersections(ray), "ray is neither orthogonal nor parallel to plane and starts in plane and starts at reference point");

    }


}