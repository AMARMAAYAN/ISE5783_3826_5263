package primitives;
import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

/**
 * The Ray class represents a ray in 3D space, defined by a starting point and a direction.
 * @author AMARMAAYAN
 */
public class Ray {
    private static final double DELTA = 0.1;

    /**
     * The starting point of the ray.
     */
    private final Point p0;

    /**
     * The normalized direction of the ray.
     */
    private final Vector dir;

    /**
     * Retrieves the starting point of the ray.
     *
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Retrieves the direction of the ray.
     *
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Calculates and retrieves a point along the ray at a given distance t.
     *
     * @param t The distance along the ray.
     * @return The point on the ray at distance t.
     */
    public Point getPoint(double t) {
        Vector tv = dir.scale(t); // Multiply the distance with the direction vector
        Point p = p0.add(tv); // Add the vector to the starting point to get the desired point
        return p;
    }

    /**
     * Constructs a Ray object with a given starting point and direction.
     *
     * @param p   The starting point of the ray.
     * @param dir The direction of the ray.
     */
    public Ray(Point p, Vector dir) {
        this.p0 = p;
        this.dir = dir.normalize();
    }

    /**
     * Constructs a Ray object with a given starting point, direction, and normal vector.
     * The starting point is adjusted by a small delta value to avoid self-intersections with the object it originated from.
     *
     * @param p0  The starting point of the ray.
     * @param dir The direction of the ray.
     * @param n   The normal vector of the object's surface at the starting point.
     */
    public Ray(Point p0, Vector dir, Vector n) {
        double delta = dir.dotProduct(n) >= 0 ? DELTA : -DELTA;
        this.p0 = p0.add(n.scale(delta));
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * Finds the closest point from a given list of intersections.
     *
     * @param intersections The list of Point objects representing intersections.
     * @return The Point object that is closest to the reference point.
     */
    public Point findClosestPoint(List<Point> intersections) {
        return intersections == null || intersections.isEmpty() ? null
                : findClosestGeoPoint(intersections.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint from a given list of GeoPoints.
     *
     * @param geoPointList The list of GeoPoint objects representing intersections.
     * @return The GeoPoint object that is closest to the reference point.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        if (geoPointList == null) {
            return null; // If the geoPointList is null, return null
        }

        GeoPoint closestPoint = null; // Initialize the closestPoint as null
        double minDistance = Double.MAX_VALUE; // Initialize the minimum distance as the maximum value
        double geoPointDistance; // The distance between "this.p0" and each point in the list

        if (!geoPointList.isEmpty()) {
            // Iterate over the geoPointList to find the closest point
            for (var geoPoint : geoPointList) {
                geoPointDistance = this.p0.distance(geoPoint.point); // Calculate the distance between this.p0 and the current point
                if (geoPointDistance < minDistance) {
                    minDistance = geoPointDistance; // Update the minimum distance
                    closestPoint = geoPoint; // Update the closestPoint
                }
            }
        }
        return closestPoint; // Return the closest GeoPoint object
    }

}
