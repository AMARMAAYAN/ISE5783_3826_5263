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
        List<GeoPoint> intersections = new ArrayList<>();

        if(this.center.equals(ray.getP0())){           //ray starts at the center of the sphere
            intersections.add(new GeoPoint(this, ray.getPoint(radius)));
            return intersections;
        }

        Vector u = this.center.subtract(ray.getP0());  // vector from ray to the sphere's center

        double tm = u.dotProduct(ray.getDir());        // projection of u on ray
        double dSquared =u.lengthSquared() - tm * tm;  // Pythagoras - distance from sphere's center to the ray

        if (alignZero(dSquared - this.radius * this.radius) >= 0)         // ray crosses outside the sphere
            return null;

        double th = alignZero(Math.sqrt(this.radius * this.radius - dSquared));
        // t1, t2 are the units to extend dir vec inorder to get the intersections
        double t1 = tm + th;
        double t2 = tm - th;

        if (alignZero(t1) <= 0 && alignZero(t2) <= 0)   // intersects on the opposite direction of ray
            return null;

        if(alignZero(t1) > 0 && alignZero(t1 - maxDistance) <= 0)
            intersections.add(new GeoPoint(this, ray.getPoint(t1)));
        if(alignZero(t2) > 0 && alignZero(t2 - maxDistance) <= 0)
            intersections.add(new GeoPoint(this, ray.getPoint(t2)));

        return intersections;
    }
}
