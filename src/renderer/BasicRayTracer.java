package renderer;

import elements.*;
import geometries.Intersectable.*;
import primitives.*;
import scene.*;
import java.util.List;
import static primitives.Util.*;

/**
 * finds intersections between the ray and the scene,
 * and calculates the color of the closest intersection
 */
public class BasicRayTracer extends RayTraceBase {

    /**
     * Stop condition in the recursion of transparencies / reflections
     * (If the effect on the pixel's color is lower than the above constant (Coefficient of attenuation)
     * then it is really marginal and we will not consider it)
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Stop condition in the recursion of transparencies / reflections
     * (If the number of recursive calls we made is higher than the above constant
     * then we will no longer make any more readings)
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * initialize the effect on the pixel's color (Coefficient of attenuation), at first its value is 1
     */
    private static final double INITIAL_K = 1.0;

    /**
     * CTOR
     *
     * @param scene The scene where we look for the intersections
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * A method to find the closest geoIntersection between the ray and the 3D model of the scene
     * (containing the shape and the point in 3D) in which we calculate the color by an helper functions
     *
     * @param ray 3D ray
     *
     * @return the backGround's color at default (no intersections found)
     *         else: the color at this point using method  - calcColor(GeoPoint, Ray) : Color
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint geoPoint = findClosestIntersection(ray);
        return geoPoint == null ? scene.background : calcColor(geoPoint,ray);
    }

    /**
     * calculates the Color at the given point using pong's model
     *
     * @param closestPoint the point where we are interested in knowing what her color is in the scene,
     *                     and in addition we send to the function the type of shape that is at this point,
     *                     because we also need to know the type of material from which the shape is made
     *                     in order to know what final color this point gets
     * @param ray The ray that comes out of the camera and intersects the scene at this point,
     *            we will use it to calculate local effects and global effects on the color
     *            in the appropriate function we call it
     *
     * @return The color at the given point
     */
    private Color calcColor (GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * calculates the color at the given point considering all the local and general effects that have on that point
     * using the appropriate functions
     *
     * @param intersection the point where we are interested in knowing what her color is in the scene,
     *                     and in addition we send to the function the type of shape that is at this point,
     *                     because we also need to know the type of material from which the shape is made
     *                     in order to know what final color this point gets
     * @param ray The ray that comes out of the camera and intersects the scene at this point,
     *            we will use it to calculate local effects & global effects on the color
     * @param level Stop condition in the recursion of transparencies / reflections
     *              (If the number of recursive calls we made is higher than the above constant
     *              then we will no longer make any more readings)
     * @param k Stop condition in the recursion of transparencies / reflections
     *          (If the effect on the pixel's color is lower than k (Coefficient of attenuation)
     *          then it is really marginal and we will not consider it)
     *
     * @return The color at the given point considering all the local and general effects
     */
    private  Color calcColor(GeoPoint intersection,Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the effect of the degree of scattering of the material from which the shape is made
     * on the color that this point will receive
     *
     * @param kd Coefficient of attenuation of the degree of diffusion
     * @param l Vector coming out of the light to the point (which we calculate its color)
     * @param n A normal vector that coming out of the point on the geometric shape that we calculate its color
     * @param lightIntensity The intensity of the light source
     *
     * @return The color that the point receive according to the calculation of the diffusion
     * (Kd * |l * n| * Il) (pong's model)
     */
    private Color calcDiffusive(double kd,Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }

    /**
     * Calculates the effect of the degree of glossy of the material from which the shape is made
     * on the color that this point will receive
     *
     * @param ks Coefficient of attenuation of the degree of glossy
     * @param l Vector coming out of the light to the point (which we calculate its color)
     * @param n A normal vector that coming out of the point on the geometric shape that we calculate its color
     * @param v Vector coming out of the camera to the point
     * @param nShininess How gloss the shape is
     * @param lightIntensity The intensity of the light source
     *
     * @return The color that the point receive according to the calculation of the glossy
     * ks * (max[0, -v * r]) ^ nSh * Il
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn = Math.pow(vrMinus, nShininess);
        return lightIntensity.scale(ks * vrn);
    }

    /**
     * Function for calculating the local effects of the type of material from which the shape is made
     * on the color that the dot acquires in the scene
     * (depending on the degree of scattering of the material, degree of glossy
     * and its degree of transparency (The level at which it is transparent, through which can be seen))
     *
     * @param geoPoint the point where we are interested in knowing what her color is in the scene,
     *                 and in addition we send to the function the type of shape that is at this point,
     *                 because we need information about the material from which the shape is made
     *                 in order to know what final color this point gets
     * @param ray The ray that intersects geoPoint
     *            (comes from the camera or
     *            from some shape that is in the scene that has an element of reflection or transparency)
     *
     * @return The color at the given point =
     * sigma on i [all light-sources] ([kd * |li * n| + ks * (max[0, -v * r]) ^ nSh] * Ili * Si)
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point3D);
        double nv = alignZero(n.dotProduct(v));
        if(isZero(nv)) {
            return Color.BLACK;
        }
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for(LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point3D);
            double nl = alignZero(n.dotProduct(l));
            // check they got the same sign (the light and the camera are in the same side of the given point)
            if (checkSign(nl, nv)) {
                double ktr = transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point3D).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * Function for calculating the global effects of the type of material from which the shape is made
     * on the color that the dot acquires in the scene,
     * depending on the degree of reflection of the material and degree of refraction
     * (To what extent does the material cause light to refract)
     *
     * @param geoPoint the point where we are interested in knowing what her color is in the scene,
     *                 and in addition we send to the function the type of shape that is at this point,
     *                 because we need information about the material from which the shape is made
     *                 in order to know what final color this point gets
     * @param ray The ray that intersects geoPoint
     *            (comes from the camera or
     *            from some shape that is in the scene that has an element of reflection or transparency)
     *
     * @param level Stop condition in the recursion of transparencies / reflections
     *        (If the number of recursive calls we made is higher than the above constant
     *        then we will no longer make any more readings)
     * @param k Stop condition in the recursion of transparencies / reflections
     *          (If the effect on the pixel's color is lower than k (Coefficient of attenuation)
     *          then it is really marginal and we will not consider it)
     *
     * @return The color at the given point =
     * sigma on i [all light-sources] ([kd * |li * n| + ks * (max[0, -v * r]) ^ nSh] * Ili * Si) + kr * IR + kt * It)
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level , double k){
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        double kr = material.getKr();
        double kkr= k*kr;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point3D);
        if(kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geoPoint.point3D, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }
        double kt = material.kT;
        double kkt = k*kt;
        if(kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geoPoint.point3D, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return color;
    }

    /**
     * Builds a ray that comes out of the shape with the material that refracts the light
     *
     * @param n the normal vector at this point (using for offset the ray's head by DELTA in this direction)
     * @param point The point from which the ray emerges
     * @param ray the original ray
     *
     * @return new ray after offset the ray's head by DELTA in the normal vector's direction
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray ray) {
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * Builds a ray that comes out of the shape with the material that has reflection
     *
     * @param n the normal vector from the shape's surface at this point (using for offset the ray's head by DELTA in this direction)
     * @param point The point from which the ray emerges
     * @param ray the original ray
     *
     * @return new ray after offset the ray's head by DELTA in the normal vector's direction
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray ray) {
        // r = v - 2 * (v * n) * n
        Vector v = ray.getDir();
        Vector r = null;
        try {
            r = v.subtract(n.scale(v.dotProduct(n)).scale(2)).normalized();
        }
        catch (Exception e) {
            return null;
        }
        return new Ray(point, r, n);
    }

    /**
     * finds the closest intersection of this ray with some shape
     *
     * @param ray the ray we want to find its closest intersection
     *
     * @return GeoPoint (includes the point of intersection and the shape we have at this point)
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return ray.getClosestGeoPoint(geoPoints);
    }

    /**
     * A function which calculates the effect of material transparency on how much can be seen through it
     *
     * @param ls The light source for which we want to calculate the transparency
     * @param l the direction vector from the light source to the geoPoint
     * @param n the normal vector from the shape's surface at this point (using for offset the ray's head by DELTA in this direction)
     * @param geoPoint the point we want to calculate its transparency
     *
     * @return Transparency's intensity (measured in doubles)
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Point3D point = geoPoint.point3D;
        Vector lightDirection = l.scale(-1); // from point to light source
        // ray from point toward light direction offset by delta
        Ray lightRay = new Ray(point, lightDirection, n);
        double lightDistance = ls.getDistance(point);
        var intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(lightRay.getP0()));
        if (intersections == null) {
            return 1.0;
        }
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point3D.distance(point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }
}