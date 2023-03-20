package geometrics;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * This class represents a cylinder in 3D space.
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructs a new cylinder with the given height, axis, and radius.
     *
     * @param height The height of the cylinder.
     * @param axis   The axis of the cylinder.
     * @param radius The radius of the cylinder.
     */
    public Cylinder(double height, Ray axis, double radius) {
        super(axis, radius);
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        // Implementation here
        return null;
    }
}