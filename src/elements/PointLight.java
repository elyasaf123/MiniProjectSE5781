package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    private final Point3D position;
    private double kC = 1;//constant
    private double kL = 0;//linear
    private double kQ = 0;//squared


    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }


    public Color getIntensity(Point3D p) {
        double d = position.distance(p);
        return intensity.scale(1/(kC + kL * d + kQ*d*d));
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalized();
    }
}
