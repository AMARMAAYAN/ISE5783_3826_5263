package geometries;
import primitives.Point;
import primitives.Vector;
/**
 The Geometry interface represents a geometric object in 3D space.
 It provides a method to get the normal vector of the object at a specific point.
 This interface is implemented by various geometric shapes such as planes, spheres, etc.
 @author Maayan Amar
 */
public interface Geometry {
    /**
     * Returns the normal vector to the Geometry object at a given point.
     *
     * @param point - the point on the geometry object to get the normal vector at.
     * @return the normal vector to the geometry object at the given point.
     */
    Vector getNormal(Point point);
}
