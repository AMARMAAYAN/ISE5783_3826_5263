package geometrics;
import primitives.Vector;
import primitives.Point;

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
    public Vector getNormal(Point point) {
        // Implementation here
        return null;
    }

    @Override
    public String toString() {
        return "Triangle{" + "p0=" + p0 + ", p1=" + p1 + ", p2=" + p2 + '}';
    }
}