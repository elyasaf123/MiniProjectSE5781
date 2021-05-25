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
     * todo
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * todo
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * todo
     */
    private static final double INITIAL_K = 1.0;

    /**
     * todo
     */
    private static final double DELTA =0.1;

    /**
     * CTOR
     *
     * @param scene The scene where we look for the intersections
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * A method to find the intersections between the ray and the 3D model of the scene.
     * First we find the point of intersection which is closest to the camera
     * (because what is not closest is "behind" and in fact is hidden and not of interest to us),
     * in which we calculate the color by an helper functions
     *
     * @param ray 3D ray
     *
     * @return the color at this point using method  - calcColor(GeoPoint, Ray) : Color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        if(points == null)
            return scene.background;
        else {
            GeoPoint point = ray.getClosestGeoPoint(points);
            return calcColor(point,ray);
        }
    }

    /**
     * calculates the Color at the given point using pong's model
     *
//     * @param geoPoint the point where we are interested in knowing what her color is in the scene,
//     *                 and in addition we send to the function the type of shape that is at this point,
//     *                 because we also need to know the color of the shape
//     *                 in order to know what final color this point gets
//     *
     * @param ray The ray that comes out of the camera and intersects the scene at this point,
     *            we will use it to calculate local effects on the color
     *            in the function calcLocalEffects(GeoPoint, Ray) : Color we call it
     *
     * @return The color at the given point
     */
    private Color calcColor (GeoPoint closestPoint, Ray ray){
//        Color emission  = geoPoint.geometry.getEmission();
//        Color basicColor = scene.ambientLight.getIntensity().add(emission);
//        return basicColor.add(calcLocalEffects(geoPoint,ray));

        return calcColor(closestPoint,ray,MAX_CALC_COLOR_LEVEL,INITIAL_K).add(scene.ambientLight.getIntensity());
    }


//    private Color calcColor(GeoPoint closestPoint, Ray ray, int maxCalcColorLevel, double initialK){}

    /**
     * todo
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     *
     * @return
     */
    private  Color calcColor(GeoPoint intersection,Ray ray, int level, double k){
//        Color color = scene.ambientLight.getIntensity().add(intersection.geometry.getEmission());
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection,ray));
        return 1== level? color:color.add(calcGlobalEffects(intersection,ray,level,k));
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
        return lightIntensity.scale(kd*ln);
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
     * 𝒌𝑺 ∙ (𝒎𝒂𝒙[𝟎, −𝒗 ∙ 𝒓]) ^ 𝒏𝒔𝒉 ∙ 𝑰𝑳
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn =Math.pow(vrMinus,nShininess);
        return lightIntensity.scale(ks*vrn);
    }

    /**
     * Function for calculating the effects of the type of material from which the shape is made
     * on the color that the dot acquires in the scene
     * (depending on the degree of scattering of the material and its degree of glossy)
     *
     * @param geoPoint the point where we are interested in knowing what her color is in the scene,
     *                 and in addition we send to the function the type of shape that is at this point,
     *                 because we need information about the material from which the shape is made
     *                 in order to know what final color this point gets
     * @param ray The ray that comes out of the camera and intersects the scene at this point
     *
     * @return The color at the given point =
     * sigma on i [all light-sources] ([𝒌𝑫 ∙ |𝒍𝒊 ∙ 𝒏| + 𝒌𝑺 ∙ (𝒎𝒂𝒙[𝟎,−𝒗∙𝒓])^𝒏𝒔𝒉] ∙ 𝑰𝑳𝒊)
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point3D);
        double nV = alignZero(n.dotProduct(v));
        if(isZero(nV)) {
            return Color.BLACK;
        }
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for(LightSource lightSource : scene.lights){
                Vector l = lightSource.getL(geoPoint.point3D);
            double nl = alignZero(n.dotProduct(l));
            // check they got the same sign (the light and the camera are in the same side of the given point)
            if(nl*nV >0 ) {
                if (unshaded(lightSource,l, n, geoPoint)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point3D);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * todo
     *
     * @param geoPoint
     * @param ray
     * @param level
     * @param k
     *
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level , double k){
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        double kr = material.kR;
        double kkr= k*kr;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point3D);
        if(kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geoPoint.point3D, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
            // .getClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
        }
        double kt = material.kT;
        double kkt = k*kt;
        if(kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geoPoint.point3D, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            // .getClosestGeoPoint(scene.geometries.findGeoIntersections(ray));

            color = color.add(calcColor(refractedPoint, refractedRay, level -1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * todo
     *
     * @param n
     * @param point
     * @param ray
     *
     * @return
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray ray) {
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * todo
     *
     * @param n
     * @param point
     * @param ray
     *
     * @return
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray ray) {
        // r = v - 2 * (v * n) * n
        // Vector n = geoPoint.geometry.getNormal(geoPoint.point3D);
        Vector v = ray.getDir();
        Vector r = null;
        // v.subtract(n.scale(2*v.dotProduct(n)));
        try {
            r = v.subtract(n.scale(v.dotProduct(n)).scale(2).normalized());
        } catch (Exception e) {
            return null;
        }
        return new Ray(point, r, n);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return ray.getClosestGeoPoint(geoPoints);
    }

    /**
     * todo
     * @param light
     * @param l
     * @param n
     * @param geoPoint
     *
     * @return
     */
    private boolean unshaded(LightSource light, Vector l, Vector n , GeoPoint geoPoint){
        Vector lightDirection = l.scale(-1);
        //ray from point toward light direction offset by delta
        Ray lightRay = new Ray(geoPoint.point3D,lightDirection,n);
        double distance = light.getDistance(geoPoint.point3D);
        List<GeoPoint>intersection = scene.geometries.findGeoIntersections(lightRay,light.getDistance(lightRay.getP0()));
        return intersection==null;
    }
}