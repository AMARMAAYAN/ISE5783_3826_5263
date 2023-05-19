package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The RayTracerBasic class is a basic implementation of the RayTracerBase abstract class.
 * It provides an empty implementation for the traceRay method.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a RayTracerBasic object with the given scene.
     *
     * @param scene the scene object to be used for ray tracing
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and returns null, indicating no intersection has been found.
     *
     * @param ray the ray to be traced
     * @return null, indicating no intersection
     */
    @Override
    public Color traceRay(Ray ray) {
        // Initialize the variable to track the closest point
        Point closestPoint = null;

        // Find the intersections of the ray with the geometries in the scene
        List<Point> intersections = scene.getGeometries().findIntersections(ray);

        // Check if any intersections are found
        if (intersections != null) {
            // Find the closest point among the intersections
            closestPoint = ray.findClosestPoint(intersections);
        }

        // Calculate the color at the closest point
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color based on the intersection point.
     *
     * @param point The Point object representing the intersection point.
     * @return The Color object representing the calculated color.
     */
    private Color calcColor(Point point){
        //if the ray does not have an intersection with object
        if(point==null){
            return this.scene.getBackground();
        }
        return this.scene.getAmbientLight().getIntensity();
    }


}
