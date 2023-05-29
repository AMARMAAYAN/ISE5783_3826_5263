package lighting;

import primitives.Color;

 abstract class Light {

    /**
     * The intensity of the light.
     */
    protected Color intensity;

    /**
     * Constructs a light with the given intensity.
     *
     * @param color the intensity of the light
     */
    protected Light(Color color) {
        this.intensity = color;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
