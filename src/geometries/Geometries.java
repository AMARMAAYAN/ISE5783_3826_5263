package geometries;

import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * The Geometries class represents a collection of intersectable geometries.
 * It implements the Intersectable interface, which means that it can be intersected with a Ray object.
 */

public class Geometries extends Intersectable {

    /**
     * A list of intersectable geometries that are stored in this Geometries object.
     */
    List<Intersectable> intersectables;

    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {
        intersectables = new LinkedList<Intersectable>();
    }


    /**
     * Constructs a Geometries object that contains the given Intersectable geometries.
     *
     * @param intersectables An array of Intersectable geometries to add to this Geometries object.
     */

    public Geometries(Intersectable... intersectables) {
        this.intersectables = new LinkedList<Intersectable>();
        Collections.addAll(this.intersectables, intersectables);
    }

    /**
     * Adds the given Intersectable geometries to this Geometries object.
     *
     * @param intersectables An array of Intersectable geometries to add to this Geometries object.
     */
    public void add(Intersectable... intersectables) {
        Collections.addAll(this.intersectables, intersectables);
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersection = null;

        for (Intersectable geometry : this.intersectables) { // loop on all the geometry that implement "the Intersectables"
            // list of crossing point between the ray ana the geometry//
            var geoIntersections = geometry.findGeoIntersections(ray,maxDistance);
            if (geoIntersections != null) { // if there is a crossing
                if (intersection == null) {
                    intersection = new LinkedList<>();
                }
                intersection.addAll(geoIntersections);
            }
        }

        return intersection;
    }


    /**
     * Finds all the intersection points of the given Ray object with the geometries stored in this Geometries object.
     *
     * @param ray The Ray object to intersect with the geometries in this Geometries object.
     * @return A list of Point objects that represent the intersection points of the given Ray object with the geometries in this Geometries object,
     *         or null if no intersection points were found.
     */
    //@Override
    /**
     public List<Point> findIntersections(Ray ray) {
     List<Point> listOfAllThePoint = new ArrayList<>();

     for (Intersectable geometry : intersectables) {
     List<Point> pointList = geometry.findIntersections(ray);
     if (pointList == null) continue;
     for (Point point : pointList) {
     listOfAllThePoint.add(point);
     }
     }

     if (listOfAllThePoint.size() == 0)
     return null;
     return listOfAllThePoint;
     }
     */


}
