package lighting;
import primitives.Color;
import primitives.Double3;

/**

 Represents ambient light in a scene.

 Ambient light contributes a constant level of illumination to all objects in the scene, regardless of their position or orientation.

 @author Maayan Amar
 */
public class AmbientLight extends Light {



    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
    /**

     Default constructor for AmbientLight that initializes the background to black.
     */
    public AmbientLight() {
        super(new Color(0,0,0));
    }
}


