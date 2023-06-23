package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a tube in 3D space, and it extends RadialGeometry.
 A tube is defined by its axisRay and its radius.
 @author Maayan Amar
 */
public class Tube extends RadialGeometry {

    /**
     * The axisRay of the Tube
     */
    protected final Ray axisRay;

    /**
     * Constructs a new tube with the given axisRay and radius.
     *
     * @param axisRay   The axisRay of the tube.
     * @param radius The radius of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axisRay of the tube.
     *
     * @return The axisRay of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }


    @Override
    public Vector getNormal(Point point) {
        // get the origin point of the axis ray of the shape
        Point p0 = axisRay.getP0();
        // get the vector between the given point and the origin point of the axis ray
        Vector p0_p = point.subtract(p0);
        // get the direction vector of the axis ray
        Vector v = axisRay.getDir();
        // calculate the distance between the origin point and the closest point on the axis ray to the given point
        double t = v.dotProduct(p0_p);
        // calculate the closest point on the axis ray to the given point
        Point O = p0.add(v.scale(t));
        // get the normalized vector perpendicular to the surface of the shape at the given point
        return point.subtract(O).normalize();

    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new ArrayList<GeoPoint>(); // List to store intersection points
        Vector rayDirection = ray.getDir(); // Direction vector of the ray
        Point rayOrigin = ray.getP0(); // Origin point of the ray
        boolean condition = false; // Flag to handle intersection point ordering

        // Calculate the coefficients of the quadratic equation to find the intersection points
        Vector axisDirection = axisRay.getDir(); // Direction vector of the axis ray
        Point axisOrigin = axisRay.getP0(); // Origin point of the axis ray
        double a = rayDirection.dotProduct(rayDirection) - Math.pow(rayDirection.dotProduct(axisDirection), 2); // Coefficient 'a' calculation
        double b = 2 * (rayDirection.dotProduct(rayOrigin.subtract(axisOrigin)) - rayDirection.dotProduct(axisDirection) * rayOrigin.subtract(axisOrigin).dotProduct(axisDirection)); // Coefficient 'b' calculation
        double c = rayOrigin.subtract(axisOrigin).dotProduct(rayOrigin.subtract(axisOrigin)) - Math.pow(rayOrigin.subtract(axisOrigin).dotProduct(axisDirection), 2) - Math.pow(radius, 2); // Coefficient 'c' calculation

        double discriminant = b * b - 4 * a * c; // Discriminant calculation for quadratic equation

        if (discriminant < 0) { // No intersection if discriminant is negative
            return intersections;
        } else if (discriminant == 0) { // One intersection if discriminant is zero
            double t = -b / (2 * a); // Calculate intersection parameter 't'
            Point p = ray.getPoint(t); // Calculate intersection point
            double tAxis = axisDirection.dotProduct(p.subtract(axisOrigin)); // Calculate parameter 't' along the axis ray
            if (tAxis >= 0 && tAxis <= axisDirection.length()) { // Check if the intersection point lies within the bounds of the cylinder
                intersections.add(0, new GeoPoint(this, p)); // Add the intersection point to the list
            }
        } else { // Two intersections if discriminant is positive
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a); // Calculate intersection parameter 't' for the first point
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a); // Calculate intersection parameter 't' for the second point
            Point intersection1 = ray.getPoint(t1); // Calculate first intersection point
            Point intersection2 = ray.getPoint(t2); // Calculate second intersection point
            double tAxis1 = axisDirection.dotProduct(intersection1.subtract(axisOrigin)); // Calculate parameter 't' along the axis ray for the first point
            double tAxis2 = axisDirection.dotProduct(intersection2.subtract(axisOrigin)); // Calculate parameter 't' along the axis ray for the second point

            if (tAxis1 >= 0 && tAxis1 <= axisDirection.length()) { // Check if the first intersection point lies within the bounds of the cylinder
                intersections.add(0, new GeoPoint(this, intersection1)); // Add the first intersection point to the list
                condition = true; // Set the condition flag to true
            }

            if (tAxis2 >= 0 && tAxis2 <= axisDirection.length()) { // Check if the second intersection point lies within the bounds of the cylinder
                if (condition == true)
                    intersections.add(1, new GeoPoint(this, intersection2)); // Add the second intersection point to the list at index 1 if the condition is true
                else
                    intersections.add(0, new GeoPoint(this, intersection2)); // Add the second intersection point to the list at index 0 if the condition is false
            }
        }

        return intersections; // Return the list of intersection points
    }

}

