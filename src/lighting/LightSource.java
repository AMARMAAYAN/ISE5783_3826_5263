package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a light source in a scene.
 * <p>
 * Provides methods for retrieving the intensity of the light at a given point
 * <p>
 * and the direction vector from the light source to a given point.
 * <p>
 * This interface should be implemented by classes representing specific types of light sources.
 * <p>
 * The implementing classes should provide implementations for these methods.
 *
 * @author Maayan Amar
 */
public interface LightSource {

    /**
     * get the distance to the Light source
     *
     * @param point given point to calculate the distance
     * @return distance from the given point to the light source
     */
    double getDistance(Point point);


    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p the point to calculate the light intensity for
     * @return the intensity of the light at the given point
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction vector from the light source to the given point.
     *
     * @param p the point to calculate the direction vector for
     * @return the direction vector from the light source to the given point
     */
    public Vector getL(Point p);
}
