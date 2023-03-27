package geometrics;
import primitives.Point;
import primitives.Vector;
/**
 The Plane class represents a plane in 3D space.
 Implements Geometry.
 @author Maayan Amar
 */
public class Plane implements Geometry{

    /**
     * A point on the plane.
     */
    final Point q0;

    /**
     * The normal vector to the plane.
     */
    final Vector normal;

    /**
     * Constructs a new plane with the given three points.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 =p1;

        Vector U=p1.subtract(p2); //AB
        Vector V=p1.subtract(p3); //AC

        Vector N=U.crossProduct(V); //AB X AC

        //right hand rule
       this.normal = N.normalize();
    }

    /**
     * Constructs a new plane with the given point and normal vector.
     * @param q0 A point on the plane.
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
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
        return normal;
    }
}
