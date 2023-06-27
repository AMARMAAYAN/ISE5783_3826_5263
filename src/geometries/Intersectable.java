package geometries;
import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 An interface for geometries that can be intersected by a Ray object.
 Implementing this interface requires defining a method to find intersections between
 the geometry and a given ray.
 @author Maayan Amar
 */
public abstract class Intersectable {

    /**
     * a field to turn on and off the bvh
     */
    protected boolean _bvhIsOn = true;
    /**
     * actual boundary box
     */
    public BoundingBox _box;

    /**
     * setter, builder pattern
     *
     * @param bvhIsOn true of false
     * @return this
     */
    public Intersectable setBvhIsOn(boolean bvhIsOn) {
        if (!_bvhIsOn && bvhIsOn) {
            createBoundingBox();
        }
        _bvhIsOn = bvhIsOn;
        return this;
    }

    /**
     * class representing boundary box
     */
    public class BoundingBox {
        /**
         * extreme node of box, containing minimal values for each axis
         */
        public Point _minimums;
        /**
         * extreme node of box, containing maximal values for each axis
         */
        public Point _maximums;

        /**
         * constructor for bounding box
         *
         * @param minimums minimal bounding values
         * @param maximums maximal bounding values
         */
        public BoundingBox(Point minimums, Point maximums) {
            _minimums = minimums;
            _maximums = maximums;
        }
    }

    /**
     * create boundary box for object
     */
    public abstract void createBoundingBox();

    /**
     * return true if ray intersects object
     *
     * @param ray ray to check
     * @return whether ray intersects box
     * code taken from scratchapixel.com
     * https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-acceleration-structure/bounding-volume-hierarchy-BVH-part1
     */
    public boolean isIntersectingBoundingBox(Ray ray) {
        if (!_bvhIsOn || _box == null)
            return true;
        Vector dir = ray.getDir();
        Point p0 = ray.getP0();
        double tmin = (_box._minimums.getX() - p0.getX()) / dir.getX();
        double tmax = (_box._maximums.getX() - p0.getX()) / dir.getX();

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }


        double tymin = (_box._minimums.getY() - p0.getY()) / dir.getY();
        double tymax = (_box._maximums.getY() - p0.getY()) / dir.getY();

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (_box._minimums.getZ() - p0.getZ()) / dir.getZ();
        double tzmax = (_box._maximums.getZ() - p0.getZ()) / dir.getZ();

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;
    }


    /**
     * Finds all intersections between the implementing geometry and a given Ray.
     *
     * @param ray the ray to find intersections with.
     * @return a List of Point objects representing the intersection points, or null if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


    public static class GeoPoint {

        public Geometry geometry;

        public Point point;

        /**
         * constructor of the helper class
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return geometry == geoPoint.geometry && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (_bvhIsOn && !isIntersectingBoundingBox(ray))
            return null;

        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
