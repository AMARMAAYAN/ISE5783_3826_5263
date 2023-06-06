/**
 * Tehila Ben Moshe-213385263, email:bmtehila@gmail.com
 * Maayan Amar-211763826, email:maayanamar11.01@gmail.com
 */
package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * The VectorTests class is responsible for testing the Vector class.
 * It includes unit tests for different operations that can be performed on a vector, such as addition, subtraction, scalar multiplication, cross product, and more.
 *
 * @author Maayan Amar .
 */
class VectorTests {

    Vector v = new Vector(0, 3, 4);

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length of vector (0,3,4) is proper
        assertEquals(5, new Vector(0, 3, 4).length(), 0.00001, "length() wrong value");

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);

        v1 = v1.add(v2);
        assertEquals(new Vector(0.0, 0.0, -0.5), v1);

        v2 = v2.add(v1);
        assertEquals(new Vector(-1.0, -1.0, -2.0), v2);
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector n = v.normalize();
        assertEquals(1d, n.lengthSquared(), 0.00001, "vector not normalized");
    }

    @Test
    void testNormalized2() {
        Vector n = v.normalize();
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n), "normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
    }


    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(2, 4, 6);

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(new Vector(-1, -2, -3), v1.add(v2), "Add vector does not work correctly");

        // =============== Boundary Values Tests =================
        //TC11: Zero vector as a result of the sum of opposite vectors
        assertThrows(IllegalArgumentException.class, () -> v2.add(v3), "Summing two opposite vectors must throw an exception");
    }


    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "Subtracting vectors does not work correctly");

        // =============== Boundary Values Tests =================
        //TC11:Zero vector as a result of subtracting the same vector
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "Subtracting the same vector must throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(3, 6, 9), v1.scale(3), "Scaling does not work correctly");

        // =============== Boundary Values Tests =================
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "Scaling by zero should throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, -3, 2);
        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertEquals(-28d, v1.dotProduct(v2), 0.00001, "Dot product does not work correctly");

        // =============== Boundary Values Tests =================
        //Dot product for orthogonal vectors
        assertEquals(0d, v1.dotProduct(v3), 0.00001, "Dot product for orthogonal vectors should be 0");
    }

}