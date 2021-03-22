package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    List<Intersectable> intersectables;

    public Geometries(){
        intersectables = new LinkedList<>();
    }

    public Geometries(Intersectable...intersectables1){
        this();
        intersectables.addAll(Arrays.asList(intersectables1));
    }

    public void add (Intersectable...intersectables) {
        Collections.addAll(this.intersectables, intersectables);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable item : this.intersectables){
            List<Point3D> itemPoints = item.findIntersections(ray);
            if (itemPoints != null){
                if (result == null){
                    result = new LinkedList<>();
                }
                result.addAll(itemPoints);
            }
        }
        return result;
    }
}