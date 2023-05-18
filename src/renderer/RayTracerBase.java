package renderer;
import primitives.*;
import scene.Scene;

import java.awt.Color;

/**
 * The RayTracerBase class is an abstract class that serves as a base for ray tracing algorithms.
 * It contains a field for the scene and provides a method for tracing rays.
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * Constructs a RayTracerBase object with the given scene.
     *
     * @param scene the scene object to be used for ray tracing
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color at the intersection point.
     *
     * @param ray the ray to be traced
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
