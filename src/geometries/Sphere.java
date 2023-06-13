package geometries;

import primitives.*;

import static java.lang.Math.sqrt;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        if (P0.equals(center)) {
            if (alignZero(radius - maxDistance) > 0) {
                return null;
            }
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        Vector U = center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 <= 0 && t2 <= 0) {
            return null;
        }

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
//            Point P1 = P0.add(v.scale(t1));
//            Point P2 = P0.add(v.scale(t2));
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2));
        }
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
//            Point P1 = P0.add(v.scale(t1));
            Point P1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
//            Point P2 = P0.add(v.scale(t2));
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }
        return null;

    }
}
