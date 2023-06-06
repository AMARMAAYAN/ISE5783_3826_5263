package lighting;

import primitives.Color;

/**
 * Represents a general light source in a scene.
 * <p>
 * Provides the intensity property for storing the light intensity.
 * <p>
 * This class serves as a base class for specific types of light sources.
 * <p>
 * It is an abstract class and cannot be instantiated directly.
 * <p>
 * Subclasses should provide implementations for specific types of lights.
 * <p>
 * Contains a constructor for initializing the light intensity.
 * <p>
 * Provides a getter method for accessing the light intensity.
 *
 * @author Maayan Amar
 */
abstract class Light {

    /**
     * The intensity of the light.
     */
    private Color intensity;

    /**
     * Constructs a light with the given intensity.
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;

    }

    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}