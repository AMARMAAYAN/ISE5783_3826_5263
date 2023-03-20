package geometrics;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * This class represents a tube in 3D space.
 */
public class Tube extends RadialGeometry {
    private final Ray axis;

    /**
     * Constructs a new tube with the given axis and radius.
     *
     * @param axis   The axis of the tube.
     * @param radius The radius of the tube.
     */
    public Tube(Ray axis, double radius) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Returns the axis of the tube.
     *
     * @return The axis of the tube.
     */
    public Ray getAxis() {
        return axis;
    }

    @Override
    public Vector getNormal(Point point) {
        // Implementation here
        return null;
    }
}
