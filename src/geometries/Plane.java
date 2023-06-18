package geometries;
import java.util.LinkedList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * The Plane class represents a plane in 3D space.
 * Implements Geometry.
 *
 * @author Maayan Amar
 */
public class Plane extends Geometry {

    /**
     * A point on the plane.
     */
    private Point q0;

    /**
     * The normal vector to the plane.
     */
    private Vector normal;

    /**
     * Constructs a new plane with the given three points.
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Plane(Point p1, Point p2, Point p3) {
        Vector v1 = p1.subtract(p2);
        Vector v2 = p2.subtract(p3);
        this.normal = (v1.crossProduct(v2)).normalize();
        this.q0 = p1;
    }

    /**
     * Constructs a new plane with the given point and normal vector.
     *
     * @param q0     A point on the plane.
     * @param vector The normal vector to the plane.
     */
    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        this.normal = vector.normalize();
    }


    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Returns the normal vector to the plane.
     *
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    protected List <GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance){
        if(this.q0.equals(ray.getP0())) //ray starts at the reference point of the plane
            return null;

        Vector vecFromRayToNormal = this.q0.subtract(ray.getP0());
        double numerator = this.normal.dotProduct(vecFromRayToNormal);
        if(isZero(numerator)) // ray starts on the plane
            return null;

        double denominator = this.normal.dotProduct(ray.getDir());
        if(isZero(denominator)) // ray is parallel to the plane
            return null;

        double t = numerator / denominator;
        if(t < 0 || alignZero(t - maxDistance) > 0) // ray starts after the plane
            return null;

        List<GeoPoint> intersections = new LinkedList<>();
        intersections.add(new GeoPoint(this, ray.getPoint(t)));
        return intersections;
    }

}