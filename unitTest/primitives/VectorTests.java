package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Unit tests for primitives. Vector class
 */
class VectorTests {

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
    Vector v=new Vector(0,3,4);
    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
     void testLength() {

    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        Vector v1=new Vector(1.0,1.0,1.0);
        Vector v2=new Vector(-1.0,-1.0,-1.5);

        v1=v1.add(v2);
        assertEquals(new Vector(0.0,0.0,-0.5),v1);

        v2=v2.add(v1);
        assertEquals(new Vector(-1.0,-1.0,-2.0),v2);
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
    void testNormalized2(){
        Vector n = v.normalize();
        assertThrows(IllegalArgumentException.class, ()->v.crossProduct(n),"normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
    }


    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(-1, -2, -3), v1.add(v2), "Add does not work correctly");

        // =============== Boundary Values Tests =================
        Vector v3 = new Vector(0, 0, 0);
        assertEquals(v1, v1.add(v3), "Adding zero vector should not change the vector");
    }


    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "Subtract does not work correctly");

        // =============== Boundary Values Tests =================
        Vector v3 = new Vector(0, 0, 0);
        assertEquals(v1, v1.subtract(v3), "Subtracting zero vector should not change the vector");
    }

    /**
     * Test method for {@link primitives.Vector#scale(primitives.Vector)}.
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

        // ============ Equivalence Partitions Tests ==============
        assertEquals(-28, v1.dotProduct(v2), 0.00001, "Dot product does not work correctly");

        // =============== Boundary Values Tests =================
        Vector v3 = new Vector(0, 0, 0);
        assertEquals(0, v1.dotProduct(v3), 0.00001, "Dot product for orthogonal vectors should be 0");
    }

}