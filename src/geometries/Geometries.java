package geometries;

import primitives.*;
import java.util.*;

/**
 * New class of Geometries for a set of geometric bodies.
 * In order to build a scene in the future that consists of several bodies, all the components must be bound together,
 * and the collection of bodies that describe the scene must be kept in a body.
 * Also sometimes we would like to have several entities in a group of entities for practical, design and performance reasons.
 * The class will implement the Intersectable interface according to the Composite design template
 */
public class Geometries implements Intersectable {

    /**
     * List of the shapes
     */
    private List<Intersectable> intersectables;

    /**
     * CTOR witch Will contain a list of bodies
     * We chose a LinkedList according to the lecturer's recommendation and because the add action will be faster,
     * which is an action that is often used in the project
     */
    public Geometries(){
        intersectables = new LinkedList<>();
    }

    /**
     * CTOR which gets a shape (that implements the interface) and adds it to the class
     *
     * @param intersectables1 a shape (that implements the interface)
     */
    public Geometries(Intersectable...intersectables1){
        this();
        intersectables.addAll(Arrays.asList(intersectables1));
    }

    /**
     * add action to the collection
     * If we want to add to the object that already exists a few more entities
     *
     * @param intersectables shape to add
     */
    public void add (Intersectable...intersectables) {
        Collections.addAll(this.intersectables, intersectables);
    }

    /**
     * A method that receives a Ray and checks the Ray's GeoIntersection points with all the bodies of the class
     *
     * @param ray the ray received
     *
     * @return  null / list that includes all the GeoIntersection points (contains the geometry (shape) and the point in 3D)
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : this.intersectables) {
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray);
            if (itemPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemPoints);
            }
        }
        return result;
    }
}