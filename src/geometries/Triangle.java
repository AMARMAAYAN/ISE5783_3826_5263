package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 This class represents a triangle in 3D space, and it is a subclass of Polygon.
 @author Maayan Amar
 */
public class Triangle extends Polygon {

    final Point p0;
    final Point p1;
    final Point p2;

    /**
     * Constructs a new triangle with the given vertices.
     *
     * @param pA The first vertex of the triangle.
     * @param pB The second vertex of the triangle.
     * @param pC The third vertex of the triangle.
     */
    public Triangle(Point pA, Point pB, Point pC) {

        super(pA,pB,pC);
        this.p0=pA;
        this.p1=pB;
        this.p2=pC;

    }

    @Override
    public String toString() {
        return "Triangle{" + "p0=" + p0 + ", p1=" + p1 + ", p2=" + p2 + '}';
    }

    public List<Point> findIntersections(Ray ray){

/**  Point point=ray.getP0(); //get point p0
 *         Vector v=ray.getDir(); //get direction of the vector
 *         Vector normal = p0.crossProduct(p1).normalize();
 *         double det = v.dotProduct(normal);
 *         // Vector n = getNormal(point); // Get the normal vector of the plane
 *         //double t = alignZero(n.dotProduct(p0.subtract(point)) / n.dotProduct(v))
 **/

        Point point=ray.getP0(); //get the head of the ray

        // First, check if there is a intersection between the ray and the plane
        List<Point> planeIntersections = this.plane.findIntersections(ray);

        //if there is no intersection between the ray and the plane - return null
        if (planeIntersections == null) {
            return null;
        }

        //the Intersections with the triangle/ plane
        Point p = planeIntersections.get(0);

        //find the vector from the p0- the beginning of the ray to the triangle edge
        Vector v1 = p0.subtract(point);
        Vector v2 = p1.subtract(point);
        Vector v3 = p2.subtract(point);

        //calculate the normal
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        //v*ni
        double s1 = alignZero(ray.getDir().dotProduct(n1));
        double s2 = alignZero(ray.getDir().dotProduct(n2));
        double s3 = alignZero(ray.getDir().dotProduct(n3));


        //BVA
        //1- if two of them=0: the ray is on the vertex - there is no intersection - return null
        if ((isZero(s1) && isZero(s2)) ||
                (isZero(s1) && isZero(s3)) ||
                (isZero(s2) && isZero(s3))) {
            return null;
        }

        //2- if only 1 of them=0: the ray is on the side - there is no intersection - return null
        if (isZero(s1) || isZero(s2) || isZero(s3)) {
            return null;
        }

        //Point traingle point = ray.getPoint(point);

        //EP
        //The point is inside if all v*ni have the same sign:
        boolean isPositive1 = s1 > 0;
        boolean isPositive2 = s2 > 0;
        boolean isPositive3 = s3 > 0;
        if ((isPositive1 == isPositive2) && (isPositive2 == isPositive3)) {
            return List.of(point); // Ray intersects triangle inside
        } else {
            return null; // Ray intersects triangle outside
        }





    }
}