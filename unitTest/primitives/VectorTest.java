package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector v=new Vector(0,3,4);
    @Test
    void length() {
    }

    @Test
    void testLengthSquared() {
        Vector v1=new Vector(1.0,1.0,1.0);
        Vector v2=new Vector(-1.0,-1.0,-1.5);

        v1=v1.add(v2);
        assertEquals(new Vector(0.0,0.0,-0.5),v1);

        v2=v2.add(v1);
        assertEquals(new Vector(-1.0,-1.0,-2.0),v2);
    }

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

    @Test
    void testToString() {
    }

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void scale() {
    }

    @Test
    void crossProduct() {
    }

    @Test
    void dotProduct() {
    }
}