package geometrics;
import primitives.Vector;
import primitives.Point;
/**
 * This class represents a triangle in 3D space.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a new triangle with the given vertices.
     *
     * @param vertex1 The first vertex of the triangle.
     * @param vertex2 The second vertex of the triangle.
     * @param vertex3 The third vertex of the triangle.
     */
    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        super(vertex1, vertex2, vertex3);
    }

    @Override
    public Vector getNormal(Point point) {
        // Implementation here
        return null;
    }
}