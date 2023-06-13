package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**

 The Geometries class represents a collection of intersectable geometries.
 It implements the Intersectable interface, which means that it can be intersected with a Ray object.
 */

public class Geometries extends Intersectable {

    /**
     * A list of intersectable geometries that are stored in this Geometries object.
     */
     private List<Intersectable> intersectables;


    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }


    /**
     * Constructs a Geometries object that contains the given Intersectable geometries.
     *
     * @param intersectables An array of Intersectable geometries to add to this Geometries object.
     */
    public Geometries(Intersectable... intersectables) {
        this();
        add(intersectables);
    }

    /**
     * Add interfaces to the list of the geometries
     * @param intersectables one or more interfaces to add to the geometries list
     */
    public void add(Intersectable... intersectables){
        for(var item : intersectables){
            this.intersectables.add(item);
        }
    }


    /**
     * find intersections of ray with geometry shape
     *
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {

        List<GeoPoint> list = new LinkedList<GeoPoint>();;
        for (Intersectable geometry :intersectables) {
            List<GeoPoint> lst = geometry.findGeoIntersections(ray, maxDistance);
            if (lst != null) {
                if (list == null)
                    list = new LinkedList<>();
                list.addAll(lst);
            }
        }
        return list;
    }


}
