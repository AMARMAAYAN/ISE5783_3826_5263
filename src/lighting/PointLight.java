package lighting;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
/**

 Represents a point light source in a scene.

 Extends the Light class and implements the LightSource interface.

 Provides methods for retrieving the intensity of the light at a given point

 and the direction vector from the light source to a given point.

 Additionally, it allows setting the light attenuation coefficients for distance-based attenuation.

 The intensity of the light decreases with distance from the light source according to the attenuation factors.

 The attenuation factors include a constant factor (kC), a linear factor (kL), and a quadratic factor (kQ).

 These factors control how the light intensity attenuates as the distance increases.

 The intensity of the light on a specific point is calculated based on the squared distance from the light source.

 The direction vector from the light source to a point is normalized.

 This class provides setter methods for setting the light attenuation coefficients (kC, kL, kQ).

 @author AMARMAAYAN
 */
public class PointLight extends Light implements LightSource {

    private final Point position; // The position point of the light source in the space
    private double kC = 1, kL = 0, kQ = 0; // Light attenuation factors -> constant, linear, and quadratic

    /**

     Constructs a point light with the given intensity and position.
     @param intensity the intensity of the light
     @param position the position of the light source in the space (Point)
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
    /**

     Calculates and returns the intensity of the light at a specific point.
     The intensity is proportional to the squared distance between the light source and the point.
     The light attenuation factors (kC, kL, kQ) are used to attenuate the intensity based on the distance.
     @param point the point to calculate the light intensity for (Point)
     @return the intensity of the light at the given point (Color)
     */
    @Override
    public Color getIntensity(Point point) {
        double distance = this.position.distance(point);
        double distanceSquared = distance * distance;
        double factor = this.kC + this.kL * distance + this.kQ * distanceSquared;
        return getIntensity().reduce(factor);
    }
    /**

     Returns the direction vector from the light source to a given point.
     The vector is normalized to have a length of 1.
     @param point the point to calculate the direction vector for (Point)
     @return the normalized direction vector from the light source to the given point (Vector)
     */
    @Override
    public Vector getL(Point point) {
        return point.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

    /**
     Sets the constant attenuation factor for the light.
     @param kC the constant attenuation factor
     @return the updated PointLight object
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }
    /**

     Sets the linear attenuation factor for the light.
     @param kL the linear attenuation factor
     @return the updated PointLight object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    /**

     Sets the quadratic attenuation factor for the light.
     @param kQ the quadratic attenuation factor
     @return the updated PointLight object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
