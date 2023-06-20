package primitives;
import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

/**
 The Ray class represents a ray in 3D space, defined by a starting point and a direction dir.
 */
public class Ray {
    private static final double DELTA = 0.1;

    /**
     * The starting point of the ray.
     */
    private final Point p0;

    /**
     * The normalized direction dir of the ray.
     */
    private final Vector dir;

    /**
     * returns the point po
     * @return po
     */
    public Point getP0() {
        return p0;
    }

    /**
     * returns thr vector dir
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    public Point getPoint(double t){
        Vector tv = dir.scale(t); //multiply the double with the vector
        Point p= p0.add(tv); //adds the vector to the point and return a point
        return p;
    }

    /**
     * Constructs a Ray object with a given starting point and direction dir.
     * @param p The starting point of the ray.
     * @param dir The direction dir of the ray.
     */
    public Ray(Point p, Vector dir) {
        this.p0 = p;
        this.dir = dir.normalize();
    }

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
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {

        if (geoPointList == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double minDistance = Double.MAX_VALUE;
        double geoPointDistance; // the distance between the "this.p0" to each point in the list

        if (!geoPointList.isEmpty()) {
            for (var geoPoint : geoPointList) {
                geoPointDistance = this.p0.distance(geoPoint.point);
                if (geoPointDistance < minDistance) {
                    minDistance = geoPointDistance;
                    closestPoint = geoPoint;
                }
            }
        }
        return closestPoint;
    }
    }



