package geometrics;
import primitives.Point;
import primitives.Vector;
/**
 The Plane class represents a plane in 3D space.
 Implements Geometry.
 @author Maayan Amar
 */
public class Plane implements Geometry{

    /**
     * A point on the plane.
     */
    final Point q0;

    /**
     * The normal vector to the plane.
     */
    final Vector normal;

    /**
     * Constructs a new plane with the given three points.
     *
     * @param vertex The first point.
     * @param vertex1 The second point.
     * @param vertex2 The third point.
     */
    public Plane(Point vertex, Point vertex1, Point vertex2) {
        q0 =vertex;
        normal = null;
    }

    /**
     * Constructs a new plane with the given point and normal vector.
     * @param q0 A point on the plane.
     * @param normal The normal vector to the plane.
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }



    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Returns the normal vector to the plane.
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }
}
