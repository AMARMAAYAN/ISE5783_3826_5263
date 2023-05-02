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
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = new ArrayList<Point>();
        Vector rayDirection = ray.getDir();
        Point rayOrigin = ray.getP0();

        // Calculate the coefficients of the quadratic equation to find the intersection points
        Vector axisDirection = axisRay.getDir();
        Point axisOrigin = axisRay.getP0();
        double a = rayDirection.dotProduct(rayDirection) - Math.pow(rayDirection.dotProduct(axisDirection), 2);
        double b = 2 * (rayDirection.dotProduct(rayOrigin.subtract(axisOrigin)) - rayDirection.dotProduct(axisDirection) * rayOrigin.subtract(axisOrigin).dotProduct(axisDirection));
        double c = rayOrigin.subtract(axisOrigin).dotProduct(rayOrigin.subtract(axisOrigin)) - Math.pow(rayOrigin.subtract(axisOrigin).dotProduct(axisDirection), 2) - Math.pow(radius, 2);

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return intersections; // no intersection
        }
        else if (discriminant == 0) {
            double t = -b / (2 * a);
            Point intersection = ray.getPoint(t);
            double tAxis = axisDirection.dotProduct(intersection.subtract(axisOrigin));
            if (tAxis >= 0 && tAxis <= axisDirection.length()) {
                intersections.add(intersection);
            }
        }
        else {
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            Point intersection1 = ray.getPoint(t1);
            Point intersection2 = ray.getPoint(t2);
            double tAxis1 = axisDirection.dotProduct(intersection1.subtract(axisOrigin));
            double tAxis2 = axisDirection.dotProduct(intersection2.subtract(axisOrigin));
            if (tAxis1 >= 0 && tAxis1 <= axisDirection.length()) {
                intersections.add(intersection1);
            }
            if (tAxis2 >= 0 && tAxis2 <= axisDirection.length()) {
                intersections.add(intersection2);
            }
        }
        return intersections;
    }



}

