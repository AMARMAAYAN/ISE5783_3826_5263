package renderer;
import primitives.*;
import scene.Scene;
import java.awt.Color;
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
        Point closetPoint=null;
        List<Point> intersections = scene.getGeometries().findIntersections(ray);
        if(intersections != null){
            closetPoint = ray.findClosestPoint(intersections);
        }
        return calcColor(closetPoint);
    }

    private Color calcColor(Point point){
        //if the ray does not have an intersection with object
        if(point==null){
            return scene.getBackground();
        }
        return scene.getAmbientLight().getIntensity();
    }


}
