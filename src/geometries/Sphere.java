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
     *  Gets the center point of the sphere.
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0(); // Ray's starting point
        Point O = this.center; // The sphere's center point
        Vector V = ray.getDir(); // "the v vector" from the presentation

        // If p0 is on the center, calculate with line parametric representation
        // using the normalized direction vector.
        if (O.equals(p0)) {
            Point newPoint = p0.add(ray.getDir().scale(this.radius));
            return List.of(new GeoPoint(this, newPoint));
        }

        Vector U = O.subtract(p0); // Calculate vector U as the difference between the sphere's center and the ray's starting point
        double tm = V.dotProduct(U); // Calculate tm as the dot product between V (ray's direction vector) and U
        double d = Math.sqrt(U.lengthSquared() - tm * tm); // Calculate d as the square root of the squared length of U minus tm squared
        if (d >= this.radius) { // If d is greater than or equal to the sphere's radius, there are no intersections
            return null;
        }

        double th = Math.sqrt(this.radius * this.radius - d * d); // Calculate th as the square root of the squared radius minus d squared
        double t1 = tm - th; // Calculate t1 as tm minus th
        double t2 = tm + th; // Calculate t2 as tm plus th

        if (t1 > 0 && t2 > 0) { // If both t1 and t2 are greater than zero, two intersection points are found
            Point p1 = ray.getPoint(t1); // Calculate intersection point p1 on the ray using t1
            Point p2 = ray.getPoint(t2); // Calculate intersection point p2 on the ray using t2
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2)); // Return a list of both intersection points
        }

        if (t1 > 0) { // If only t1 is greater than zero, one intersection point is found
            Point p1 = ray.getPoint(t1); // Calculate intersection point p1 on the ray using t1
            return List.of(new GeoPoint(this, p1)); // Return a list with the intersection point p1
        }

        if (t2 > 0) { // If only t2 is greater than zero, one intersection point is found
            Point p2 = ray.getPoint(t2); // Calculate intersection point p2 on the ray using t2
            return List.of(new GeoPoint(this, p2)); // Return a list with the intersection point p2
        }

        return null; // If none of the conditions above are met, there are no valid intersection points, so return null
    }


}

