package geometrics;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 The Cylinder class represents a cylinder in 3D space, which is a subclass of the Tube class.
 @author Maayan Amar
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    private final double height;

    /**
     * Constructs a new cylinder with the given height, axisRay, and radius.
     *
     * @param height The height of the cylinder.
     * @param axisRay   The axisRay of the cylinder.
     * @param radius The radius of the cylinder.
     */
    public Cylinder(double height, Ray axisRay, double radius) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
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