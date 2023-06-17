package geometries;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 An interface for geometries that can be intersected by a Ray object.
 Implementing this interface requires defining a method to find intersections between
 the geometry and a given ray.
 @author Maayan Amar
 */
public abstract class Intersectable {




    public static class GeoPoint {

        public final Geometry geometry;

        public final Point point;

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
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return this.point.equals(geoPoint.point) && this.geometry.equals(geoPoint.geometry);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds all intersections between the implementing geometry and a given Ray.
     *
     * @param ray the ray to find intersections with.
     * @return a List of Point objects representing the intersection points, or null if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance);

}
