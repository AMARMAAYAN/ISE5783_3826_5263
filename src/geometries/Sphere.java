package geometries;

import primitives.*;
import static primitives.Util.*;
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
        Point p0=ray.getP0(); //get point p0
        Vector v=ray.getDir(); //get direction of the vector

        //if p0=center
        if(p0.equals(center)){
            return List.of(center.add(v.scale(radius)));
        }

        Vector u = center.subtract(p0);
        double tm=alignZero(v.dotProduct(u)); //tm=v*u
        double d= alignZero(Math.sqrt(v.lengthSquared()-tm*tm));

        //if the ray direction above the sphere
        if(d >= this.getRadius()){
            return null;
        }

        //double dSquared=isZero(tm) ? v.lengthSquared() : v.lengthSquared() - tm*tm;
       // if(thSquared<=0) return null;

        double th =alignZero(Math.sqrt(this.radius * this.radius - d*d));
        double t1= alignZero(tm-th);
        double t2= alignZero(tm+th);

        // if both of them >0 : there is 2 Intersection points
        if(t1>0 && t2>0){
           Point p1=ray.getPoint(t1);
           Point p2 = ray.getPoint(t2);
           return List.of(p1,p2);
        }

        //if only one of them>0 : there is 1 intersection point
        if(t1>0){
            Point p1=ray.getPoint(t1);
            return List.of(p1);
        }

        if(t2>0){
            Point p2=ray.getPoint(t2);
            return List.of(p2);
        }

        return null;
    }
}

