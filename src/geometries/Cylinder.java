package geometries;
import primitives.Ray;

/**
 * class cylinder which adds height to  tube
 */
public class Cylinder extends Tube  {

    /**
     * height of cylinder
     */
    private double height;

    /**
     * ctor of cylinder  which has to call super(Tube)
     * @param axisRay axis ray for super(Tube)
     * @param radius radius for super(Tube)
     */
    public Cylinder(Ray axisRay, double radius) {
        super(axisRay, radius);
    }

    /**
     * ctor of cylinder  which has to call super(Tube)
     * @param axisRay axis which the cylinder will be there
     * @param radius radius of cylinder
     * @param height height of cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * getter for height
     * @return height of cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * to string for cylinder
     * @return string that repersents a cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
