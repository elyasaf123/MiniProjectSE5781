package renderer;

import elements.LightSource;
import geometries.Intersectable.*;
import primitives.*;
import scene.*;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * finds intersections between the ray and the scene,
 * and calculates the color of the closest intersection
 */
public class BasicRayTracer extends RayTraceBase {

    /**
     * CTOR
     *
     * @param scene The scene where we look for the cuts
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * A method to find the intersections between the ray and the 3D model of the scene.
     * First we find the point of intersection which is closest to the camera
     * (because what is not closest is "behind" and in fact is hidden and not of interest to us),
     * in which we calculate the color by an helper function
     *
     * @param ray 3D ray
     *
     * @return the color at this point using method  - calcColor(point) : Color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        GeoPoint point;
        if(points == null)
            return scene.background;
        else {
            point = ray.getClosestGeoPoint(points);
            return calcColor(point,ray);
        }
    }

    /**
     * todo
     * @param geoPoint
     * @param ray
     * @return
     */
    private Color calcColor (GeoPoint geoPoint, Ray ray){
        Color emmision  = geoPoint.geometry.getEmission();
        Color basicColor = scene.ambientLight.getIntensity().add(emmision);
        return basicColor.add(calcLocalEffects(geoPoint,ray));
    }

    private Color calcDiffusive(double kd,Vector l, Vector n, Color lightIntensity)
    {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd*ln);
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity){
        Vector r = l.subtract(n.scale(l.dotProduct(n)*2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn =Math.pow(vrMinus,nShininess);
        return lightIntensity.scale(ks*vrn);
    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point3D);
        double nV = alignZero(n.dotProduct(v));
        if(nV ==0){
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
            if(nl*nV >0){
                Color lightIntensity  = lightSource.getIntensity(geoPoint.point3D);
                color = color.add(calcDiffusive(kd,l,n,lightIntensity),calcSpecular(ks,l,n,v,nShininess,lightIntensity));
            }
        }
        return color;
    }
}