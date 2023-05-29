package lighting;
import primitives.*;
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
