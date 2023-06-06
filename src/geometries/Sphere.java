package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Sphere class represents a sphere in 3D space.
 * Extends RadialGeometry.
 *
 * @author Maayan Amar
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
     * Gets the center point of the sphere.
     *
     * @return The center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }


    @Override
    public Vector getNormal(Point point) {

        return point.subtract(center).normalize();
    }

    /**
     * This method finds the intersection points between a given ray and a sphere.
     *
     * @param ray The ray to intersect with the sphere.
     * @return A list of intersection points between the ray and the sphere, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        Point p0 = ray.getP0(); // ray's starting point
        Point O = this.center; //the sphere's center point
        Vector V = ray.getDir(); // "the v vector" from the presentation

        // if p0 on center, calculate with line parametric representation
        // the direction vector normalized.
        if (O.equals(p0)) {
            Point newPoint = ray.getPoint(this.radius);
            return List.of(new GeoPoint(this, newPoint));
        }

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= this.radius) {
            return null;
        }

        double th = Math.sqrt(this.radius * this.radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0 && (t1 - maxDistance) < 0  && (t2 - maxDistance) < 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }

        if (t1 > 0  && (t1 - maxDistance) < 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }

        if (t2 > 0  && (t2 - maxDistance) < 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }
        return null;
    }

}

