package geometries;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0 && alignZero(radius - maxDistance) <= 0) { // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }
            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        else if (alignZero(t1 - maxDistance) < 0)// t2 is behind the head
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        return null;
    }
}

