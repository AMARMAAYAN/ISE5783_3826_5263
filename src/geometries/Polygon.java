package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.*;



/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        if (_bvhIsOn)
            createBoundingBox();
    }

    /**
     * create boundary box for object
     */
    @Override
    public void createBoundingBox() {
        if (vertices == null)
            return;
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Point ver : vertices) {
            minX = Math.min(minX, ver.getX());
            minY = Math.min(minY, ver.getY());
            minZ = Math.min(minZ, ver.getZ());
            maxX = Math.max(maxX, ver.getX());
            maxY = Math.max(maxY, ver.getY());
            maxZ = Math.max(maxZ, ver.getZ());
        }
        _box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }




    /**
     * Finds the intersection point between a given ray and a polygon.
     * @param ray The ray to intersect with the polygon.
     * @return A list containing the intersection point, or null if there is no intersection.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Find the intersection point with the plane
        List<Point> planeIntersections = plane.findIntersections(ray);
        if (planeIntersections == null)
            return null;

        // Get the first intersection point and initialize variables for checking if it's inside the polygon
        Point p = planeIntersections.get(0);
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        double oldSign = 0;
        double sign = 0;

        // Calculate the signs of the cross products between the ray direction vector and the edges of the polygon
        Vector v1 = vertices.get(1).subtract(p0);
        Vector v2 = vertices.get(0).subtract(p0);
        try {
            oldSign = v.dotProduct(v1.crossProduct(v2));
        } catch (IllegalArgumentException e) {
            System.out.println(this.vertices);
        }
        if (isZero(oldSign))
            return null;

        // Iterate through the remaining edges of the polygon and check if the signs of the cross products change
        for (int i = vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if ((oldSign * sign) <= 0)
                return null;
        }

        // If the intersection point is inside the polygon, return it in a list
        return List.of(new GeoPoint(this,p));
    }
}




