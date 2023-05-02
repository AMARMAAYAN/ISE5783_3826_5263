package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

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
        return null;
    }
}

