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
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
