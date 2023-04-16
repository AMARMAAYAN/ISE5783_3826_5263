package geometrics;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 2, 0);
        // Test case: create a plane with two points that converge
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(p1, p2, p1);
        }, "Failed to throw exception for two points that converge");

        // Test case: create a plane with three points that are on the same line
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(p1, p2, p3);
        }, "Failed to throw exception for three points that are on the same line");
    }

    @Test
    void testGetNormal() {
    }

    @Test
    void testTestGetNormal() {
        Sphere sph = new Sphere(new Point(0, 0, 1), 1d);
        assertEquals(new Vector(0, 0, 1), sph.getNormal(new Point(0, 0, 2)), "Bad normal to sphere");

    }


}