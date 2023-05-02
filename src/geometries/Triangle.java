package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 This class represents a triangle in 3D space, and it is a subclass of Polygon.
 @author Maayan Amar
 */
public class Triangle extends Polygon {


    /**
     * Constructs a new triangle with the given vertices.
     *
     * @param pA The first vertex of the triangle.
     * @param pB The second vertex of the triangle.
     * @param pC The third vertex of the triangle.
     */
    public Triangle(Point pA, Point pB, Point pC) {

        super(pA, pB, pC);
    }


    @Override
    public String toString() {
        return "Triangle{" + "p0=" + this.vertices.get(0) + ", p1=" + this.vertices.get(1) + ", p2=" + this.vertices.get(2) + '}';
    }

    public List<Point> findIntersections(Ray ray) {

/**  Point point=ray.getP0(); //get point p0
 *         Vector v=ray.getDir(); //get direction of the vector
 *         Vector normal = p0.crossProduct(p1).normalize();
 *         double det = v.dotProduct(normal);
 *         // Vector n = getNormal(point); // Get the normal vector of the plane
 *         //double t = alignZero(n.dotProduct(p0.subtract(point)) / n.dotProduct(v))
 **/

        Point point = ray.getP0(); //get the head of the ray

        // First, check if there is a intersection between the ray and the plane
        List<Point> planeIntersections = this.plane.findIntersections(ray);

        //if there is no intersection between the ray and the plane - return null
        if (planeIntersections == null) {
            return null;
        }


        Point p0 = this.vertices.get(0);
        Point p1 = this.vertices.get(1);
        Point p2 = this.vertices.get(2);
        //the Intersections with the triangle/ plane
        Point p = planeIntersections.get(0);


        try {

            //find the vector from the p0- the beginning of the ray to the triangle edge
            /*Vector v1 = p1.subtract(p0);
            Vector v2 = p2.subtract(p1);
            Vector v3 = p0.subtract(p2);


            //calculate the normal
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();
*/
            Vector n1 = p1.subtract(p0).crossProduct(p0.subtract(p));
            Vector n2 = p2.subtract(p1).crossProduct(p1.subtract(p));
            Vector n3 = p0.subtract(p2).crossProduct(p2.subtract(p));
            //v*ni
            double s1 = alignZero(ray.getDir().dotProduct(n1));
            double s2 = alignZero(ray.getDir().dotProduct(n2));
            double s3 = alignZero(ray.getDir().dotProduct(n3));



            if (s1 == 0) return null;
            double n2n3 = alignZero(n2.dotProduct(n3));
            if (s1 * s2 <= 0) return null; // must have same sign
            double n3n1 = alignZero(n3.dotProduct(n1));
            if (s2 * n3n1 <= 0) return null; // must have same sign
/*
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
            }*/



        } catch (IllegalArgumentException ignore) {
            return null;


        }
    }
}