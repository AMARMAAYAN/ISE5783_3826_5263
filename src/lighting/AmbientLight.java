package lighting;
import primitives.Color;
import primitives.Double3;
public class AmbientLight {
    private Color intensity;

    /**
     * Constant static field representing no ambient light (black color and attenuation factor of (0, 0, 0)).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    //== default constructor for initialize the background to black ==//
    public AmbientLight() {
        intensity=Color.BLACK;
    }

    /**
     * Constructor for AmbientLight that computes the final intensity of the light.
     *
     * @param Ia The original color of the light (intensity of the light according to RGB)
     * @param Ka The attenuation factor of the original light
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity=(Ia.scale(Ka));
    }

    /**
     * Constructor for AmbientLight that accepts a double value for the attenuation coefficient.
     *
     * @param Ka The attenuation factor of the original light as a double value
     */

    public AmbientLight(double Ka ) {
        intensity = Color.BLACK.scale(Ka);
    }


    /**
     * Getter for the intensity of the ambient light.
     *
     * @return The intensity of the ambient light (Color)
     */
    public Color getIntensity() {
        return intensity;
    }

}
