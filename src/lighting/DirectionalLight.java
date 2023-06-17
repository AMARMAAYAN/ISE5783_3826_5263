package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**

 Represents a directional light source in a scene.

 A directional light source is located infinitely far away and emits light in a specific direction.

 The intensity of the light is constant and does not depend on the distance from the light source.

 The direction of the light is normalized to a unit vector.

 Implements the LightSource interface to provide information about the light source.

 Extends the Light class to inherit the intensity property.

 @author Maayan Amar
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**

     Constructs a directional light with the given intensity and direction.
     @param intensity the intensity of the light
     @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }
}
