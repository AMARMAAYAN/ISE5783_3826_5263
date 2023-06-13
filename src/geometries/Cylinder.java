
package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import static primitives.Util.*;

/**
 * The Cylinder class represents a cylinder in 3D space, which is a subclass of the Tube class.
 *
 * @author Maayan Amar
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    private final double height;

    /**
     * Constructs a new cylinder with the given height, axisRay, and radius.
     *
     * @param height  The height of the cylinder.
     * @param axisRay The axisRay of the cylinder.
     * @param radius  The radius of the cylinder.
     */
    public Cylinder(double height, Ray axisRay, double radius) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }


    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        if (point.equals(p0))
            return v;

        // projection of P-p0 on the ray:
        Vector u = point.subtract(p0);

        // distance from p0 to the o who is in from of point
        double t = alignZero(u.dotProduct(v));

        // if the point is at a base
        if (t == 0 || isZero(height - t))
            return v;

        //the other point on the axis facing the given point
        Point o = p0.add(v.scale(t));

        return point.subtract(o).normalize();
    }

}
