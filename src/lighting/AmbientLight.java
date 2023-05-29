package lighting;
import primitives.Color;
import primitives.Double3;

/**
 *
 */

public class AmbientLight extends Light{


    /**
     * Constant static field representing no ambient light (black color and attenuation factor of (0, 0, 0)).
     */
  //  public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor for AmbientLight that computes the final intensity of the light.
     *
     * @param Ia The original color of the light (intensity of the light according to RGB)
     * @param Ka The attenuation factor of the original light
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }


    //== default constructor for initialize the background to black ==//
    public AmbientLight() {
        super(Color.BLACK);
    }

}
