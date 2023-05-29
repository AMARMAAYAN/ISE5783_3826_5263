package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

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
        int i;
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        return intersections == null ? scene.getBackground() : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * function calculates local effects of color on point
     * @param gp geometry point to color
     * @param ray ray that intersects
     * @return color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = Color.BLACK;
        Vector vector = ray.getDir();
        Vector normal = gp.geometry.getNormal(gp.point);
        double nv = alignZero(normal.dotProduct(vector));
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector lightVector = lightSource.getL(gp.point);
            double nl = alignZero(normal.dotProduct(lightVector));
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(gp.point);
                color = color.add(lightIntensity.scale(calcDiffusive(material, nl)), lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
            }
        }
        return color;
    }

    /**
     * function calculates specular color
     * @param material material of geometry
     * @param normal normal of geometry
     * @param lightVector light vector
     * @param nl dot product of normal and light vector
     * @param vector direction of ray
     * @return specular color
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
        return material.kS.scale(Math.pow(max, material.nShininess));

    }

    /**
     * function calculates diffusive color
     * @param material material of geometry
     * @param nl dot product of normal and light vector
     * @return diffusive color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * function calculates color of point
     *
     * @param geoPoint point to color
     * @return color
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return geoPoint.geometry.getEmission().add(scene.getAmbientLight().getIntensity(), calcLocalEffects(geoPoint, ray));
    }


}
