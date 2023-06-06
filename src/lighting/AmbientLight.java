package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in a scene.
 * <p>
 * Ambient light contributes a constant level of illumination to all objects in the scene, regardless of their position or orientation.
 *
 * @author Maayan Amar
 */
public class AmbientLight extends Light {

    /**
     * Constant static field representing no ambient light (black color and attenuation factor of (0, 0, 0)).
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs an AmbientLight object with the specified original color and attenuation factor.
     *
     * @param Ia The original color of the light (intensity of the light according to RGB)
     * @param Ka The attenuation factor of the original light
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Default constructor for AmbientLight that initializes the background to black.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}


