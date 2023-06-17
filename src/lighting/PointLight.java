package lighting;
import primitives.Color;
import primitives.Double3;
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
    // the attenuation factors
    private Double3 kC = new Double3(1);
    private Double3 kL = new Double3(0);
    private Double3 kQ = new Double3(0);
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
        double d = point.distance(this.position);  // distance from the light source
        try {
            return this.getIntensity().reduce(kC.add(kL.scale(d)).add(kQ.scale(d * d)));
        }
        catch (Exception x){ // in case vector zero is constructed
            return this.getIntensity();
        }
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
        return point.distance(point);
    }

    /**
     Sets the constant attenuation factor for the light.
     @param kC the constant attenuation factor
     @return the updated PointLight object
     */
    public PointLight setkC(double kC) {
        this.kC = new Double3(kC);
        return this;
    }
    /**

     Sets the linear attenuation factor for the light.
     @param kL the linear attenuation factor
     @return the updated PointLight object
     */
    public PointLight setKl(double kL) {
        this.kL = new Double3(kL);
        return this;
    }
    /**

     Sets the quadratic attenuation factor for the light.
     @param kQ the quadratic attenuation factor
     @return the updated PointLight object
     */
    public PointLight setKq(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }
}
