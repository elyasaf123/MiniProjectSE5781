package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    List<Intersectable> intersectables = null;
    public Geometries(){
        intersectables = new LinkedList<>();
    }

    public Geometries(Intersectable...intersectables){
        for (Intersectable item : intersectables){
            add(intersectables); // // TODO: 21/03/2021  
        }
    }

    public void add (Intersectable...intersectables){
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
