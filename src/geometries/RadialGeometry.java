package geometries;

public abstract class RadialGeometry {
    /**
     *radius of sphere in 3D
     */
    final protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     *getter for spheres radius
     * @return radius of sphere in 3D
     */
    public double getRadius() {
        return radius;
    }
}