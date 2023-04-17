package geometrics;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The SphereTests class is responsible for testing the Sphere class.
 * It includes unit tests for getNormal method .
 * @author Maayan Amar
 */
class SphereTests {
    /**
     * Test method for {@link geometrics.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a point on the surface of the sphere
        Sphere sphere = new Sphere(new Point(0,0,0), 1);
        Point point = new Point(0,0,1);
        Vector expectedNormal = new Vector(0,0,1);
        assertEquals(expectedNormal, sphere.getNormal(point), "the normal of a point on the surface of the sphere is incorrect normal vector.");
    }
}