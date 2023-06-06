package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represents a tube in 3D space, and it extends RadialGeometry.
 * A tube is defined by its axisRay and its radius.
 *
 * @author Maayan Amar
 */
public class Tube extends RadialGeometry {

    /**
     * The axisRay of the Tube
     */
    protected final Ray axisRay;

    /**
     * Constructs a new tube with the given axisRay and radius.
     *
     * @param axisRay The axisRay of the tube.
     * @param radius  The radius of the tube.
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new LinkedList<>();
        Vector rayDirection = ray.getDir();
        Point rayOrigin = ray.getP0();
        boolean condition = false;

        // Calculate the coefficients of the quadratic equation to find the intersection points
        Vector axisDirection = axisRay.getDir();
        Point axisOrigin = axisRay.getP0();

        double a = rayDirection.dotProduct(rayDirection) - Math.pow(rayDirection.dotProduct(axisDirection), 2);
        double b = 2 * (rayDirection.dotProduct(rayOrigin.subtract(axisOrigin)) - rayDirection.dotProduct(axisDirection) * rayOrigin.subtract(axisOrigin).dotProduct(axisDirection));
        double c = rayOrigin.subtract(axisOrigin).dotProduct(rayOrigin.subtract(axisOrigin)) - Math.pow(rayOrigin.subtract(axisOrigin).dotProduct(axisDirection), 2) - Math.pow(radius, 2);

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return null; // no intersection
        } else if (discriminant == 0) {
            double t = -b / (2 * a);
            if(alignZero(t - maxDistance) >=0){
                return null;
            }
            Point p = ray.getPoint(t);
            double tAxis = axisDirection.dotProduct(p.subtract(axisOrigin));
            if (tAxis >= 0 && tAxis <= axisDirection.length()) {
                intersections.add(0, new GeoPoint(this, p));
            }
        } else {

            // todo use maxDistance
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            Point intersection1 = ray.getPoint(t1);
            Point intersection2 = ray.getPoint(t2);
            double tAxis1 = axisDirection.dotProduct(intersection1.subtract(axisOrigin));
            double tAxis2 = axisDirection.dotProduct(intersection2.subtract(axisOrigin));
            if (tAxis1 >= 0 && tAxis1 <= axisDirection.length()) {
                intersections.add(0, new GeoPoint(this, intersection1));
                condition = true;
            }
            if (tAxis2 >= 0 && tAxis2 <= axisDirection.length()) {
                if (condition == true)
                    intersections.add(1, new GeoPoint(this, intersection2));
                else
                    intersections.add(0, new GeoPoint(this, intersection2));
            }
        }
        return intersections;
    }
}

