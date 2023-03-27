package geometrics;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {
    }


    @Test
    public void testGetNormal(){
        Sphere sph=new Sphere(new Point(0, 0,1),1d);
        assertEquals(new Vector(0,0,1),sph.getNormal(new Point(0,0,2)),"Bad normal to sphere");

    }
}