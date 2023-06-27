package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 *  implementation of the abstract class RayTracerBase
 *  adding the local and global effects of the objects presented in the scene
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * Maximum level of color calculation recursion.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Minimum value of color calculation factor.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Initial color factor value.
     */
    private static final Double3 INITIAL_K = new Double3(1.0);

    /**
     * sqrt of maximum number of rays in a beam
     */
    private int _nRays = 4;

    /**
     * Creates a RayTracerBasic object with the specified scene.
     * @param scene The scene to be rendered.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * setter for nrays
     *
     * @param nRays sqrt of maximum number of rays in a beam
     * @return this
     */
    public RayTracerBasic setNRays(int nRays) {
        _nRays = nRays;
        return this;
    }


    @Override
    public Color traceRay(Ray ray) {
        GeoPoint gp = findClosestIntersection(ray); // intersect the ray with the geometries
        if (gp == null)     // no intersection was found
            return this.scene.getBackground();   // the background color
        return calcColor(gp, ray);   // return the calculated color of this point
    }



    /**
     *  calculate the amount of shade covering the point
     * @param geopoint a point to check the shadow on
     * @param n normal to the geometry
     * @param lightSource the shading light
     * @return the amount of the shade
     */
    private Double3 transparency(GeoPoint geopoint,  Vector n, LightSource lightSource){
        Vector lightDirection = lightSource.getL(geopoint.point).scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        double lightDistance = lightSource.getDistance(geopoint.point);
        List<GeoPoint> intersections = scene.getGeometries().
                findGeoIntersections(lightRay);
        if(intersections == null) return Double3.ONE;

        Double3 transparent = Double3.ONE; // full transparency
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                transparent = transparent.product(gp.geometry.getMaterial().getKt()); //the more transparency the less shadow
                if (transparent.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }
        return transparent;
    }

    /**
     * calculate the color of a point infected by a light ray
     * cover function for the recursive function that calculates the color
     * @param geoPoint calculate the color of this point
     * @param ray the ray of intersection that 'hit' the point
     * @return  the color including all the effects
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.getAmbientLight().getIntensity()               // the intensity of the ambient light
                .add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));  // recursive call
    }

    /**
     * calculating the local (diffuse + specular) and the global (reflection + refraction) effects
     * @param geoPoint  to calculate its color
     * @param ray that intersected the point
     * @param level the depth of the recursion for the global effects
     * @param k the volume of the color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k)
                .add(geoPoint.geometry.getEmission());
        return 1 == level ? color :
                color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }

    /**
     * calculate the next level of the global effects if there are more intersections to check
     * @param ray the is used to intersect the geometries
     * @param level the current level
     * @param kx a color factor to reduce the color (according to the current level of recursion)
     * @param kkx the color factor for the next level of recursion
     * @return the new calculated color
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }



    /**
     * before the improvement for mini project1
     * calculate the color according to the k factor for the reflection and refraction effects
     * @param gp calculate the color of this point
     * @param ray the ray of intersection that 'hit' the point
     * @param level of the recursion
     * @param k the volume of the color
     * @return the calculated color
     */

//    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
//        Color color = Color.BLACK;
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//
//        Double3 kkr = k.product(material.getKr());
//        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) // the color is effected by the reflection
//            color = calcGlobalEffects(constructReflectedRay(gp.point, ray, n), level, material.getKr(), kkr);
//
//        Double3 kkt = k.product(material.getKt());
//        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) // the color is effected due to the transparency
//            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point, ray, n), level, material.getKt(), kkt));
//
//        return color;
//    }

    /**
     * calculates global lighting effects recursively
     *
     * @param gp    point to calculate color for
     * @param v     direction vector of ray intersection the point
     * @param level depth left for recursive calls
     * @param k     current k
     * @return color calculated for the given point
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.getKr());
        // reflectance
        if (kkr.greaterThan(MIN_CALC_COLOR_K)) {
            List<Ray> reflectedBeam = constructBeamAroundRay(constructReflectedRay(gp.point, v, n), n,
                    material._kg);
            Color temp = Color.BLACK;
            for (Ray reflected : reflectedBeam) {
                temp = temp.add(calcGlobalEffects(reflected, level, material.getKr(), kkr));
            }
            color = color.add(temp.reduce(reflectedBeam.size()));
        }
        Double3 kkt = k.product(material.getKt());
        // refraction
        if (kkt.greaterThan(MIN_CALC_COLOR_K)) {
            List<Ray> refractedBeam = constructBeamAroundRay(constructRefractedRay(gp.point, v, n), n,
                    1 - material._kb);
            Color temp = Color.BLACK;
            for (Ray refracted : refractedBeam) {
                temp = temp.add(calcGlobalEffects(refracted, level, material.getKt(), kkt));
            }
            color = color.add(temp.reduce(refractedBeam.size()));
        }
        return color;
    }

    /**
     * constructs a beam around a ray according to coefficient
     *
     * @param ray         ray to construct beam around
     * @param n           normal to geometry
     * @param coefficient precision factor
     * @return list of rays in beam
     */
    private List<Ray> constructBeamAroundRay(Ray ray, Vector n, double coefficient) {

        List<Ray> beam = new LinkedList<>();
        beam.add(ray);
        double widthFactor = 1 - coefficient;
        Vector ortho;
        // ortho = dir - (dir * n) * n
        if (isZero(ray.getDir().dotProduct(n)))
            ortho = n;
        else
            ortho = ray.getDir().subtract(n.scale(ray.getDir().dotProduct(n))).normalize();

        for (int i = 0; i < _nRays && !isZero(widthFactor); i++, widthFactor *= 0.9d) {
            ortho = ortho.scale(widthFactor);
            Vector dir = ray.getDir().add(ortho).normalize();
            for (int j = 0; j < _nRays;
                 j++, dir = dir.rotate(ray.getDir(), 360d / _nRays)) {
                // if the rays are on the same side of surface
                if (dir.dotProduct(n) * ray.getDir().dotProduct(n) > 0) {
                    beam.add(new Ray(ray.getP0(), dir));
                }
            }
        }
        return beam;
    }

    /**
     * calculate the effects of the lighting on the intensity of the point
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray the ray of the camera pointing to the GeoPoint's geometry
     * @return  the calculated color on the point in the geoPoint
     */
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


    /**
     * the specular effect on the object according to the phong reflection model
     * @param mat the geometry's material
     * @param l vec from the light source to a point on the geometry
     * @param n normal vec to the point on the geometry
     * @param v vec from the camera to the geometry = the camera's eye
     * @param lightIntensity intensity of the light
     * @return calculated intensity with the specular effect
     */
    private Color calcSpecular(Material mat, Vector l, Vector n, Vector v, Color lightIntensity) {
        Vector r = reflectionVector(l, n);    // the specular ray
        // the phong model formula for the specular effect: ks ∙ ( 𝒎𝒂𝒙 (𝟎, −𝒗 ∙ 𝒓) ^ 𝒏𝒔𝒉 ) ∙ 𝑰
        return lightIntensity
                .scale(mat.getKs().scale( alignZero( Math.pow( Math.max(0, v.scale(-1).dotProduct(r)),
                        mat.getShininess()))));
    }


    /**
     * the diffusion effect on the object according to the phong reflection model
     * @param mat the geometry's material
     * @param l vec from the light source to a point on the geometry
     * @param n vec from the light source to a point on the geometry
     * @param lightIntensity intensity of the light
     * @return calculated intensity with the diffusive effect
     */
    private Color calcDiffusive(Material mat, Vector l, Vector n, Color lightIntensity) {
        // the phong model formula for the diffusive effect: 𝒌𝑫 ∙| 𝒍 ∙ 𝒏 |∙ 𝑰
        return lightIntensity.scale(mat.getKd().scale(Math.abs(n.dotProduct(l))));
    }


    /**
     * intersects the ray with the scene and finds the closest point the ray intersects
     * @param ray to intersect the scene with
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersectionPoints);
    }


    /**
     * construct the refraction ray according to the physics law of refraction
     * @param point reference point of the new ray
     * @param ray the ray to refract
     * @param n the normal to the geometry the point belongs to
     * @return the refraction ray
     */
    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDir(), n);
    }

    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * construct the reflection ray according to the physics law of reflection
     * @param point reference point of the new ray
     * @param ray the ray to reflect
     * @param n the normal to the geometry the point belongs to
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, reflectionVector(ray.getDir(), n), n);
    }

    /**
     * constructs a reflected ray for a given point and vector
     *
     * @param point point
     * @param v     vector of ray that intersects
     * @param n     normal to point
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        if (isZero(v.dotProduct(n)))
            return new Ray(point, v);
        Vector r = v.subtract(n.scale(v.dotProduct(n)).scale(2)); // r = v - 2 * (v * n) * n
        return new Ray(point, r, n);
    }

    /**
     * calculate the reflected vector using linear algebra and projection on a normal vector
     * @param l the vector to reflect
     * @param n the normal vector to reflect by
     * @return the reflected vector
     */
    private Vector reflectionVector(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }






}
