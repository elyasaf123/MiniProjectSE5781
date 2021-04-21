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
    // view - plane's width
    private double width;
    // view - plane's height
    private double height;

    /**
     * todo
     * @param p0
     * @param vTo
     * @param vUp
     */
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
        if(isZero(Xj) && !isZero(Yi)){
            //only move on Y axis
            Pij = Pc.add(vUp.scale(Yi));
        }
        else if(isZero(Yi) && !isZero(Xj)) {
            //only move on X axis
            Pij = Pc.add(vRight.scale(Xj));
        }
        else if(!(isZero(Xj) && isZero(Yi)))
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

}