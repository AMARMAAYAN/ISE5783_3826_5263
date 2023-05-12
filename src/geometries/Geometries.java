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

public class Geometries implements Intersectable {

    /**
     * A list of intersectable geometries that are stored in this Geometries object.
     */
     private List<Intersectable> list;


    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {
        list = new LinkedList<>();
    }


    /**
     * Constructs a Geometries object that contains the given Intersectable geometries.
     *
     * @param geometries An array of Intersectable geometries to add to this Geometries object.
     */
    public Geometries(Intersectable... geometries){
        list = new LinkedList<>();
        for (Intersectable geometry : geometries) {
            list.add(geometry);
        }
    }

    /**
     * Finds all the intersection points of the given Ray object with the geometries stored in this Geometries object.
     *
     * @param ray The Ray object to intersect with the geometries in this Geometries object.
     * @return A list of Point objects that represent the intersection points of the given Ray object with the geometries in this Geometries object,
     *         or null if no intersection points were found.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> listOfAllThePoint = new ArrayList<>();

        for (Intersectable geometry : list) {
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

    /**
     * Adds the given Intersectable geometries to this Geometries object.
     *
     * @param geometries An array of Intersectable geometries to add to this Geometries object.
     */
    public void add(Intersectable... geometries){
        for (Intersectable geometry : geometries) {
            list.add(geometry);
        }
    }
}
