package elements;

import primitives.*;
import static primitives.Util.*;

/**
 * class to represent the camera.
 * We treat the camera as having a single point of view
 */
public class Camera {
     // the camera location
    final Point3D p0;
    // vector toward
    final Vector vTo;
    final Vector vUp;
    final Vector vRight;

    // distance from camera to view plane
    private double distance;
    // view - plan's width
    private double width;
    // view - plan's height
    private double height;

    // CTOR
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();

        // check that vUp is orthogonal to vTo
        if(!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vUp is not orthogonal to vTo");
        }

        // Right hand rule
        vRight = vTo.crossProduct(vUp);
    }

    /**
     * borrowing from builder pattern
     * @param width view - plan's width
     * @param height view - plan's height
     * @return new entity from type of camera
     */
    public Camera setViewPlaneSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * distance setter
     * @param distance distance between camera to view plane
     * @return camera
     */
    public Camera setDistance(double distance){
        this.distance = distance;
        return this;
    }

    /**
     * method that calculate the exact location of the ray in the view plane
     * @param nX number of columns in the view plane
     * @param nY number of rows in the view plane
     * @param j The column in the matrix where the desired position is
     * @param i The row in the matrix where the desired position is
     * @return the ray from the camera to view plane
     */
    public Ray constructRayThroughPixel(int nX,  int nY, int j , int i){
        // the  image's center
        Point3D Pc = p0.add(vTo.scale(distance));

        //height of single pixel
        double Ry = height/nY;

        //width of single pixel
        double Rx = width/nX;

        //amount of pixels to move in y axis from pc to i
        double Yi = ((nY - 1)/2d - i)*Ry;

        //amount of pixels  to move in x axis from pc to j
        double Xj = (-(nX - 1)/2d + j)*Rx;

        Point3D Pij = Pc;
        if(isZero(Yi) && isZero((Xj))){
            //don't change Pij
        }
        else if(isZero(Xj)){
            //only move on Y axis
            Pij = Pc.add(vUp.scale(Yi));
        }
        else if(isZero(Yi)) {
            //only move on X axis
            Pij = Pc.add(vRight.scale(Xj));
        }
        else
            //move on both axes
            Pij = Pc.add(vRight.scale(Xj).add(vUp.scale(Yi)));

        return new Ray(p0,Pij.subtract(p0));
    }

    /**
     * P0 getter
     * @return P0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * VTo getter
     * @return VTo
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * VUp getter
     * @return VUp
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * VRight getter (using cross product)
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * Sliding and rotating method
     * @param vec vector to rotate
     * @param axis The axis on which the rotating is based
     * @param theta the angle of the rotate
     * @return
     */
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