package lighting;
import primitives.*;

/**

 Represents a light source in a scene.

 Provides methods for retrieving the intensity of the light at a given point

 and the direction vector from the light source to a given point.

 This interface should be implemented by classes representing specific types of light sources.

 The implementing classes should provide implementations for these methods.

 @author Maayan Amar
 */
public interface LightSource {

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
