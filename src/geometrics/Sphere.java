package geometrics;

import primitives.Point;
import primitives.Vector;

/**
 * This class represents a sphere in 3D space.
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructs a new sphere with the given center point and radius.
     *
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return The center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        // Implementation here
        return null;
    }
}
