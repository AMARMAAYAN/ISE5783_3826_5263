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
        Point point=ray.getP0(); //get point p0
        Vector v=ray.getDir(); //get direction of the vector
        Vector normal = p0.crossProduct(p1).normalize();
        double det = v.dotProduct(normal);
        // Vector n = getNormal(point); // Get the normal vector of the plane
        //double t = alignZero(n.dotProduct(p0.subtract(point)) / n.dotProduct(v));

        Plane plane = new Plane(p0,p1,p2); // Create a plane object with the triangle's plane parameters
        //if there is no intersection between the ray and the plane - return null
        if(plane.findIntersections(ray)==null){
            return null;
        }

        //3 checks:
        //#1-Check if ray is parallel to the triangle





        /**
         Point intersectionPoint = point.add(v.scale(t));

         **/



        if(Plane.findIntersections(ray)==null) return null;

        // Calculate the triangle edges and normal vector
        Vector edge1 = p1.subtract(p0);
        Vector edge2 = p2.subtract(p0);
        Vector normal = edge1.crossProduct(edge2).normalize();

        // Calculate the determinant of the ray direction vector and triangle normal
        double det = v.dotProduct(normal);

    }
}