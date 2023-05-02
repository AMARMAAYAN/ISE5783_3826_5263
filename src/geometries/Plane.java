package geometries;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * The Plane class represents a plane in 3D space.
 * Implements Geometry.
 *
 * @author Maayan Amar
 */
public class Plane implements Geometry {

    /**
     * A point on the plane.
     */
    final protected Point q0;

    /**
     * The normal vector to the plane.
     */
    final protected Vector normal;

    /**
     * Constructs a new plane with the given three points.
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;

        Vector U = p1.subtract(p2); //AB
        Vector V = p1.subtract(p3); //AC
        Vector N = U.crossProduct(V); //AB X AC

        //right hand rule
        this.normal = N.normalize();
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
    public List <Point> findIntersections(Ray ray){
         Point p0=ray.getP0(); //get point p0
         Vector v=ray.getDir(); //get direction of the vector

        Vector n= normal;

        if(q0.equals(p0)){ //if they equal-there is no Intersections between them
            return null;
        }

        Vector p0_q0=q0.subtract(p0);

        //numerator
        double n_p0q0=alignZero(n.dotProduct(p0_q0));

        //because then t will be equal to 0
        if(isZero(n_p0q0)){
            return null;
        }

        //denominator
        double nv=alignZero(n.dotProduct(v));

        //denominator can't be zero -ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double t = alignZero(n_p0q0/nv);

        if(t<=0){
            return null;
        }

        Point point = ray.getPoint(t);

        return List.of(point);
    }

}
