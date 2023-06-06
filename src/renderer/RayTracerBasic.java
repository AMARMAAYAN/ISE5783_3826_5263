package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The RayTracerBasic class is a basic implementation of the RayTracerBase abstract class.
 * It provides an empty implementation for the traceRay method.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final double EPS = 0.1;  //Constant variable for ray head offset size for shading rays

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
    public Color traceRay(Ray ray) { //refactoring:
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) {
            return scene.getBackground();
        }  return calcColor(closestPoint, ray);
    }

    /**
     * Calculates reflected ray and refraction ray
     *
     * @param geoPoint geometry point
     * @param ray      ray
     * @return color
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        Ray reflectedRay = constructReflectionRay(geoPoint, geoPoint.geometry.getNormal(geoPoint.point), ray.getDir());
        Ray refractedRay = constructRefractionRay(geoPoint, geoPoint.geometry.getNormal(geoPoint.point), ray.getDir());
        return calcGlobalEffects(geoPoint, level, color, material.kR, k, reflectedRay)
                .add(calcGlobalEffects(geoPoint, level, color, material.kT, k, refractedRay));
    }

    /**
     * TODO
     *
     * @param geoPoint
     * @param level
     * @param color
     * @param kx
     * @param k
     * @param ray
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, int level, Color color, Double3 kx, Double3 k, Ray ray) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint reflectedPoint = findClosestIntersection(ray);
        if (reflectedPoint != null) {
            color = color.add(calcColor(reflectedPoint, ray, level - 1, kkx).scale(kx));
        }
        return color;
    }


    /**
     * Determines if a point on a surface is unshaded by checking if it is directly visible to a light source.
     *
     * @param gp          The GeoPoint representing the point on the surface.
     * @param l           The direction Vector from the point on the surface to the light source.
     * @param n           The surface normal Vector at the given point.
     * @param lightSource The LightSource object being considered.
     * @return True if the point on the surface is unshaded, false otherwise.
     */

    private boolean unshaded(GeoPoint gp,  LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection , n);
        double maxDistance = lightSource.getDistance(gp.point);
        var intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        if (intersections != null) {
              return false;
        }

        return true;
    }


    /**
     * function calculates local effects of color on point
     *
     * @param gp  geometry point to color
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
                if (unshaded(gp, lightSource, lightVector,normal)) {
                    Color lightIntensity = lightSource.getIntensity(gp.point);
                    color = color.add(lightIntensity.scale(calcDiffusive(material, nl)), lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
                }

            }

        }
        return color;
    }

    /**
     * function calculates specular color
     *
     * @param material    material of geometry
     * @param normal      normal of geometry
     * @param lightVector light vector
     * @param nl          dot product of normal and light vector
     * @param vector      direction of ray
     * @return specular color
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double max = Math.max(0, vector.scale(-1).dotProduct(reflectedVector));
        return material.kS.scale(Math.pow(max, material.shininess));

    }

    /**
     * function calculates diffusive color
     *
     * @param material material of geometry
     * @param nl       dot product of normal and light vector
     * @return diffusive color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * function calculates color of point
     *
     * @param gp point to color
     * @return color
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        //return scene.getAmbientLight().getIntensity().add(
        // calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambient);

        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());

        //return geoPoint.geometry.getEmission().add(scene.getAmbientLight().getIntensity(), calcLocalEffects(geoPoint, ray));
    }


    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * find the closest intersection to the starting point of the ray
     *
     * @param ray the ray that intersect with the geometries of the scene
     * @return the geoPoint that is point is the closest point to the starting point of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * function will construct a reflection ray
     *
     * @param geoPoint geometry point to check
     * @param normal   normal vector
     * @param vector   direction of ray to point
     * @return reflection ray
     */
    private Ray constructReflectionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
        Vector reflectedVector = vector.subtract(normal.scale(2 * vector.dotProduct(normal)));
        return new Ray(geoPoint.point, reflectedVector, normal);
    }

    /**
     * function will construct a refraction ray
     *
     * @param geoPoint geometry point to check
     * @param normal   normal vector
     * @param vector   direction of ray to point
     * @return refraction ray
     */
    private Ray constructRefractionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
        return new Ray(geoPoint.point, vector, normal);
    }


}
