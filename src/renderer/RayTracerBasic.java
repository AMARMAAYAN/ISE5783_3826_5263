package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import primitives.*;
import lighting.*;
import java.lang.Math.*;
import geometries.Intersectable.GeoPoint;
import scene.Scene;
import static primitives.Util.*;

import java.util.List;

/**
 * Rey tracer
 * calculate the color ray-geometry intersection
 * @author rivki and efrat
 */
public class RayTracerBasic extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);

    /**
     * ctor - initializing the scene parameter
     * uses super ctor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Double3 transparency(GeoPoint geopoint,  Vector n, LightSource lightSource){
        Vector lightDirection = lightSource.getL(geopoint.point).scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);

        List<GeoPoint> intersections = scene.getGeometries().
                findGeoIntersections(lightRay,
                        lightSource.getDistance(geopoint.point));
        if(intersections == null) return Double3.ONE;

        Double3 transparent = Double3.ONE; // full transparency
        for (GeoPoint gp : intersections) {
            transparent = transparent.product(gp.geometry.getMaterial().getKt());
            if(transparent.subtract(new Double3(MIN_CALC_COLOR_K)).lowerThan(0))
                return Double3.ZERO; // no transparency
        }
        return transparent;
    }
    /**
     * According to the pong model
     * This model is additive in which we connect all the components that will eventually
     * make up an image with background colors, self-colors and texture colors.
     *
     * @param geoPoint the geometry and the lighted point at him
     * @param ray      the ray that goes out of the camera
     * @return the color at the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.getAmbientLight().getIntensity()               // the intensity of the ambient light
                .add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));  // recursive call
    }
    /**
     * overload methode of {@link renderer.RayTracerBasic#calcColor(GeoPoint, Ray)}
     *
     * @param geoPoint the geometry and the lighted point at him
     * @param ray      the ray that goes out of the camera
     * @param level    the level of the recursion
     * @param k        Represents influencing factors of transparency and reflection
     * @return the color at the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k)
                .add(geoPoint.geometry.getEmission());
        return 1 == level ? color :
                color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v = ray.getDir ();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;                                      // the camera doesn't see the light

        Material mat = geoPoint.geometry.getMaterial();
        Color color = Color.BLACK;

        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(geoPoint.point);            // from the lightSource to the geometry
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0 ) { // sign(nl) == sing(nv) ->
                // the camera and the light source are on the same side of the surface

                Double3 ktr = transparency(geoPoint, n, lightSource);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {  // the light is not shaded by other geometries
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(mat, l, n, lightIntensity),
                            calcSpecular(mat, l, n, v, lightIntensity));
                }
            }
        }
        return color;
    }


    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = k.product(material.getKr());
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) // the color is effected by the reflection
            color = calcGlobalEffects(constructReflectedRay(gp.point, ray, n), level, material.getKr(), kkr);

        Double3 kkt = k.product(material.getKt());
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) // the color is effected due to the transparency
            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point, ray, n), level, material.getKt(), kkt));

        return color;
    }


//    private Ray constructReflected(GeoPoint geoPoint, Ray ray) {
//        Vector v = ray.getDir();
//        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
//        double nv = alignZero(v.dotProduct(n));
//        // r = v - 2*(v * n) * n
//        Vector r = v.subtract(n.scale(2d * nv)).normalize();
//
//        return new Ray(geoPoint.point, r, n); //use the constructor with the normal for moving the head a little
//    }

//    private Ray constructRefracted(GeoPoint geoPoint, Ray inRay) {
//        return new Ray(geoPoint.point, inRay.getDir(), geoPoint.geometry.getNormal(geoPoint.point));
//    }

    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, reflectionVector(ray.getDir(), n), n);
    }

    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDir(), n);
    }



    private Color calcSpecular(Material mat, Vector l, Vector n, Vector v, Color lightIntensity) {
        Vector r = reflectionVector(l, n);    // the specular ray
        // the phong model formula for the specular effect: ks ∙ ( 𝒎𝒂𝒙 (𝟎, −𝒗 ∙ 𝒓) ^ 𝒏𝒔𝒉 ) ∙ 𝑰
        return lightIntensity
                .scale(mat.getKs().scale( alignZero( Math.pow( Math.max(0, v.scale(-1).dotProduct(r)),
                        mat.getShininess()))));
    }

    private Color calcDiffusive(Material mat, Vector l, Vector n, Color lightIntensity) {
        // the phong model formula for the diffusive effect: 𝒌𝑫 ∙| 𝒍 ∙ 𝒏 |∙ 𝑰
        return lightIntensity.scale(mat.getKd().scale(Math.abs(n.dotProduct(l))));
    }

    private Vector reflectionVector(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }

    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     * @param ray the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    public Color traceRay(Ray ray) {
        GeoPoint clossestGeoPoint = findClosestIntersection(ray);
        if (clossestGeoPoint == null)
            return this.scene.getBackground();
        return calcColor(clossestGeoPoint, ray);
    }
    /**
     * find closest intersection to the starting point of the ray
     * @param ray the ray that intersect with the geometries of the scene
     * @return the GeoPoint that is point is the closest point to the starting point of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersectionPoints);
    }

}