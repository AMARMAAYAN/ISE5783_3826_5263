package geometries;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 An interface for geometries that can be intersected by a Ray object.
 Implementing this interface requires defining a method to find intersections between
 the geometry and a given ray.
 @author Maayan Amar
 */
public interface Intersectable {
    /**
     * Finds all intersections between the implementing geometry and a given Ray.
     *
     * @param ray the ray to find intersections with.
     * @return a List of Point objects representing the intersection points, or null if there are no intersections.
     */
    public List<Point>findIntersections(Ray ray);
}
