package elements;

import primitives.*;
import static primitives.Util.*;

public class Camera {
    final Point3D p0;
    final Vector vTo;
    final Vector vUp;
    final Vector vRight;

    private double distance;
    private double width;
    private double height;

    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();

        if(!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vUp is not orthogonal to vTo");
        }

        vRight = vTo.crossProduct(vUp);
    }

    /**
     * borrowing from builder pattern
     * @param width #todo
     * @param height #todo
     * @return
     */
    public Camera setViewPlaneSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * #todo
     * @param distance
     * @return
     */
    public Camera setDistance(double distance){
        this.distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX,  int nY, int j , int i){
        Point3D Pc = p0.add(vTo.scale(distance));//the center of the image

        double Ry = height/nY;//height of single pixel
        double Rx = width/nX;//width of single pixel

        double Yi = ((nY - 1)/2d - i)*Ry;//amount of pixels to move in y axis from pc to i
        double Xj = (-(nX - 1)/2d + j)*Rx;//amount of pixels  to move in x axis from pc to j

        Point3D Pij = Pc;
        if(isZero(Yi) && isZero((Xj))){
            //don't change Pij
        }
        else if(isZero(Xj)){
            Pij = Pc.add(vUp.scale(Yi));//only move on Y axis
        }
        else if(isZero(Yi)) {
            Pij = Pc.add(vRight.scale(Xj));//only move on X axis
        }
        else
            Pij = Pc.add(vRight.scale(Xj).add(vUp.scale(Yi)));//move on both axes

        return new Ray(p0,Pij.subtract(p0));
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public static Vector rotateVectorCC(Vector vec, Vector axis, double theta) {

        double x, y, z;
        double u, v, w;

        x=vec.getHead().getXDouble();
        y=vec.getHead().getYDouble();
        z=vec.getHead().getZDouble();

        u=axis.getHead().getXDouble();
        v=axis.getHead().getXDouble();
        w=axis.getHead().getXDouble();

        double xPrime = u*(axis.dotProduct(vec))*(1d - Math.cos(theta))
                + x*Math.cos(theta)
                + (-w*y + v*z)*Math.sin(theta);

        double yPrime = v*(axis.dotProduct(vec))*(1d - Math.cos(theta))
                + y*Math.cos(theta)
                + (w*x - u*z)*Math.sin(theta);

        double zPrime = w*(axis.dotProduct(vec))*(1d - Math.cos(theta))
                + z*Math.cos(theta)
                + (-v*x + u*y)*Math.sin(theta);

        return new Vector(xPrime, yPrime, zPrime);
    }
}