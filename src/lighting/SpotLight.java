package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a spot light source in a scene.
 * <p>
 * Extends the PointLight class.
 * <p>
 * Calculates the intensity of the light at a given point based on the direction of the light.
 * <p>
 * Inherits the position and attenuation properties from the PointLight class.
 *
 * @author AMARMAAYAN
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * constructor for the intensity
     *
     * @param color of the intensity of the source of the light
     * @param dir   ->for the direction
     */
    public SpotLight(Color color, Point position, Vector dir) {
        super(color, position);
        this.direction = dir.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        double projection = this.direction.dotProduct(getL(point));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }

        double factor = Math.max(0, projection);
        Color pointLightIntensity = super.getIntensity(point);

        return (pointLightIntensity.scale(factor));
    }
}
