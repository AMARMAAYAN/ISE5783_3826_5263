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

    public List<Point> findIntersections(Ray ray){
        if (ray.getP0().equals(center)) {
            List<Point> points = new ArrayList<>(1);
            Point p = center.add(ray.getDir().scale(radius));
            points.add(p);
            return points;
        }

        Vector u = center.subtract(ray.getP0());
        double Tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-Tm*Tm);
        if (d >= radius)
            return null;
        double Th = Math.sqrt(radius*radius-d*d);
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);
        if (t1 <= 0 && t2 <= 0)
            return null;
        int size = 0;
        if(t1 > 0)
            size += 1;
        if(t2 > 0)
            size += 1;
        List<Point> points = new ArrayList<>(size);
        if (t1 > 0) {
            Point p = ray.getPoint(t1);
            points.add(p);
        }
        if (t2 > 0) {
            Point p = ray.getPoint(t2);
            points.add(p);
        }
        return points;
    }
}

