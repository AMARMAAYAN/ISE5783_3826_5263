package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;
//new
/**
 * This class represents a triangle in 3D space, and it is a subclass of Polygon.
 * @author Maayan Amar
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


    /**
     * Returns a list of intersection points between the current triangle and the given ray.
     * If there are no intersections, returns null.
     *
     * @param ray The ray to calculate the intersection with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

            // First, check if there is an intersection between the ray and the plane
            List<Point> planeIntersections = this.plane.findIntersections(ray);
            //if there is no intersection between the ray and the plane - return null
            if (planeIntersections == null) {
                return null;
            }

            // Get the intersection point between the ray and the plane
            Point p = planeIntersections.get(0);

            // Find the vectors from the beginning of the ray to each of the vertices of the triangle
            Vector v1 = vertices.get(0).subtract(ray.getP0());
            Vector v2 = vertices.get(1).subtract(ray.getP0());
            Vector v3 = vertices.get(2).subtract(ray.getP0());

            // Calculate the normal vectors of the triangle edges
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();

            // Calculate the dot product between the ray direction vector and each of the triangle edge normal vectors
            double s1 = alignZero(ray.getDir().dotProduct(n1));
            double s2 = alignZero(ray.getDir().dotProduct(n2));
            double s3 = alignZero(ray.getDir().dotProduct(n3));

            // If any of the dot products is zero, there is no intersection
            if (isZero(s1) || isZero(s2) || isZero(s3)) {
                return null;
            }

            // If all dot products have the same sign, there is no intersection
            if (s1 * s2 > 0 && s2 * s3 > 0) {
                return List.of(p);
            }

            return null;
        }
}

