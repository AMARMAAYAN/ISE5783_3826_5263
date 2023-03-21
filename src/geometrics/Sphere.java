package geometrics;

import primitives.Point;
import primitives.Vector;

/**
 The Sphere class represents a sphere in 3D space.
 Extends RadialGeometry.
 @author Maayan Amar
 */

public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
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
     *  Gets the center point of the sphere.
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
