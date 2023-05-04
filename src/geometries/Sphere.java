package geometries;

import primitives.*;
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
    public List<Point> findIntersections(Ray ray){
        if (ray.getP0().equals(center)) { // if the ray's starting point is equal to the sphere's center
            List<Point> points = new ArrayList<>(1); // create a list with a capacity of one
            Point p = center.add(ray.getDir().scale(radius)); // calculate the intersection point using the ray's direction and the sphere's radius
            points.add(p); // add the intersection point to the list
            return points; // return the list of intersection points
        }

        Vector u = center.subtract(ray.getP0()); // calculate the vector from the ray's starting point to the sphere's center
        double Tm = ray.getDir().dotProduct(u); // calculate the projection of u onto the ray's direction
        double d = Math.sqrt(u.lengthSquared()-Tm*Tm); // calculate the distance between the ray's starting point and the intersection point
        if (d >= radius) // if the distance is greater than or equal to the sphere's radius, there are no intersections
            return null;
        double Th = Math.sqrt(radius*radius-d*d); // calculate the distance between the intersection points
        double t1 = alignZero(Tm + Th); // calculate the first intersection point
        double t2 = alignZero(Tm - Th); // calculate the second intersection point
        if (t1 <= 0 && t2 <= 0) // if both intersection points are behind the ray's starting point, there are no intersections
            return null;
        int size = 0; // initialize the size of the list of intersection points to zero
        if(t1 > 0)
            size += 1; // increment the size of the list if the first intersection point is in front of the ray's starting point
        if(t2 > 0)
            size += 1; // increment the size of the list if the second intersection point is in front of the ray's starting point
        List<Point> points = new ArrayList<>(size); // create a new list of intersection points with the correct size
        if (t1 > 0) { // if the first intersection point is in front of the ray's starting point
            Point p = ray.getPoint(t1); // calculate the first intersection point
            points.add(p); // add the first intersection point to the list of intersection points
        }
        if (t2 > 0) { // if the second intersection point is in front of the ray's starting point
            Point p = ray.getPoint(t2); // calculate the second intersection point
            points.add(p); // add the second intersection point to the list of intersection points
        }
        return points; // return the list of intersection points
    }
}

