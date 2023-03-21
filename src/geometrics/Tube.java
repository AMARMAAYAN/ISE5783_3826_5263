package geometrics;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 This class represents a tube in 3D space, and it extends RadialGeometry.
 A tube is defined by its axisRay and its radius.
 @author Maayan Amar
 */
public class Tube extends RadialGeometry {

    /**
     * The axisRay of the Tube
     */
    private final Ray axisRay;

    /**
     * Constructs a new tube with the given axisRay and radius.
     *
     * @param axisRay   The axisRay of the tube.
     * @param radius The radius of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axisRay of the tube.
     *
     * @return The axisRay of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }


    @Override
    public Vector getNormal(Point point) {
        // Implementation here
        return null;
    }
}
