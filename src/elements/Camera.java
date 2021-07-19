package elements;

import primitives.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

/**
 * class to represent the camera.
 * We treat the camera as having a single point of view
 */
public class Camera {

    /**
     * Private CTOR of the class built by Builder Pattern only
     *
     * @param cameraBuilder A virtual entity of the class we are currently implementing
     */
    private Camera(CameraBuilder cameraBuilder) {

        this.distance = cameraBuilder.distance;
        this.width = cameraBuilder.width;
        this.height = cameraBuilder.height;
        this.p0 = cameraBuilder.p0;
        this.vTo = cameraBuilder.vTo;
        this.vUp = cameraBuilder.vUp;
        this.vRight = cameraBuilder.vRight;
    }

    /**
     * the camera location in 3D
     */
    private final Point3D p0;

    /**
     * vector toward - the direction vector in which the camera is aimed
     */
    private final Vector vTo;

    /**
     * Direction vector that defines what is the "top" of the camera
     */
    private final Vector vUp;

    /**
     * Direction vector that defines what is the "right" direction of the camera
     */
    private final Vector vRight;

    /**
     * distance from camera to view plane
     */
    private double distance;

    /**
     * view - plane's width
     */
    private double width;

    /**
     * view - plane's height
     */
    private double height;

    /**
     * Builder Pattern, by this class we are updating the parent class (Camera),
     * instance that we are interested in creating even before we create it
     */
    public static class CameraBuilder{

        /**
         * the camera location in 3D
         */
        private Point3D p0;

        /**
         * Direction vector that defines what is the "top" of the camera
         */
        private Vector vUp;

        /**
         * vector toward - the direction vector in which the camera is aimed
         */
        private Vector vTo;

        /**
         * Direction vector that defines what is the "right" direction of the camera
         */
        private Vector vRight;

        /**
         * distance from camera to view plane
         */
        private double distance;

        /**
         * view - plane's width
         */
        private double width;

        /**
         * view - plane's height
         */
        private double height;

        /**
         * CameraBuilder's CTOR,
         * gets three components he needs to create a show
         *
         * @param p0 Location of the camera in 3D
         * @param vTo vector toward - the direction vector in which the camera is aimed
         * @param vUp Direction vector that defines what is the "top" of the camera
         */
        public CameraBuilder(Point3D p0, Vector vTo, Vector vUp) {
            this.p0 = p0;
            this.vTo = vTo.normalized();
            this.vUp = vUp.normalized();

            // check that vUp is orthogonal to vTo
            if(!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vUp is not orthogonal to vTo");
            }
            // Right hand rule
            vRight = vTo.crossProduct(vUp).normalized();
        }

        /**
         * borrowing from builder pattern
         *
         * @param width view - plan's width
         * @param height view - plan's height
         *
         * @return new entity from type of cameraBuilder
         */
        public CameraBuilder setViewPlaneSize(double width, double height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * distance setter
         *
         * @param distance distance between camera to view plane
         *
         * @return cameraBuilder
         */
        public CameraBuilder setDistance(double distance){
            this.distance = distance;
            return this;
        }

        /**
         * We call this function when we have finished giving values to all the fields of the class,
         * and we are interested in creating an entity
         *
         * @return Camera
         */
        public Camera build() {
            return new Camera(this);
        }
    }

    /**
     * method that calculate the exact location of the ray in the view plane
     *
     * @param nX number of columns in the view plane
     * @param nY number of rows in the view plane
     * @param j The column in the matrix where the desired position is
     * @param i The row in the matrix where the desired position is
     *
     * @return the ray from the camera to view plane
     */
    public Ray constructRayThroughPixel(int nX,  int nY, int j , int i) {

        // the image's center
        Point3D Pc = getP0().add(vTo.scale(distance));

        //height of single pixel
        double Ry = alignZero(height/nY);

        //width of single pixel
        double Rx = alignZero(width/nX);

        //amount of pixels to move in y axis from pc to i
        double Yi = alignZero(-(i - ((nY - 1) / 2d)) * Ry);

        //amount of pixels  to move in x axis from pc to j
        double Xj = alignZero((j - ((nX - 1) / 2d)) * Rx);

        Point3D Pij = Pc;
        if(!isZero(Xj)) {
            //only move on X axis
            Pij = Pij.add(vRight.scale(Xj));
        }
        if(!isZero(Yi)) {
            //only move on Y axis
            Pij = Pij.add(vUp.scale(Yi));
        }

        return new Ray(getP0(),Pij.subtract(getP0()));
    }

    /**
     * consruct a beam of rays around one ray, inside the pixel, according to the division it received,
     * and inside each sub-square takes out a random point for checking the color
     *
     * @param nX amount of pixels in width
     * @param nY amount of pixels in height
     * @param j the pixel's column
     * @param i the pixel's row
     *
     * @return list of internal rays
     */
    public LinkedList<Ray> constructBeam(int nX,  int nY, int j , int i, double divide) {

        /**
         * the image's center
         */
        Point3D Pc = getP0().add(vTo.scale(distance));

        /**
         * height of single pixel
         */
        double Ry = alignZero(height/nY);

        /**
         * width of single pixel
         */
        double Rx = alignZero(width/nX);

        /**
         * amount of pixels to move in y axis from pc to i
         */
        double Yi = alignZero(-(i - ((nY - 1) / 2d)) * Ry);

        /**
         * amount of pixels  to move in x axis from pc to j
         */
        double Xj = alignZero((j - ((nX - 1) / 2d)) * Rx);

        Point3D Pij = Pc;

        if(!isZero(Xj)) {
            //only move on X axis
            Pij = Pij.add(vRight.scale(Xj));
        }


        if(!isZero(Yi)) {
            //only move on Y axis
            Pij = Pij.add(vUp.scale(Yi));
        }

        var rayList = new LinkedList<Ray>();
        rayList.add(constructRayThroughPixel(nX, nY, j, i));
        /**
         * up left corner of pixel
         */
        Point3D pixStart = Pij.add(vRight.scale(-Rx / 2)).add(vUp.scale(Ry / 2));
        // The formation of the rays within the division of the pixel,
        // in each square a point of intersection is selected at random
        for (double row = 0; row < divide; row++) {
            for (double col = 0; col < divide; col++) {
                rayList.add(randomPointRay(pixStart, col/divide, -row/divide));
            }
        }
        return rayList;
    }

    /**
     * In the first iteration we want to check the colors in all the corners of the pixel and in the center
     * and of course keep them in order to avoid re-checking later
     *
     * @param myRays array of the rays, at first it contains only the central ray in the third index
     * @param nX number of pixels in row
     * @param nY  number of pixels in column
     *
     * @return array of the rays
     */
    public Ray[] constructFiveRays(Ray myRays[], double nX, double nY) {

        /**
         * pixel height
         */
        double rY = alignZero(height / nY);
        /**
         * pixel width
         */
        double rX = alignZero(width / nX);

        Ray myRay = myRays[3];

        double dis = distance;
        /**
         * distance from camera to our pixel
         */
        double t = dis / vTo.dotProduct(myRay.getDir());
        /**
         * the middle of the pixel
         */
        Point3D center = myRay.getTargetPoint(t);

        // up left
        myRays[1] =  new Ray(p0, center.add(vRight.scale(-rX / 2)).add(vUp.scale(rY / 2)).subtract(p0));
        // up right
        myRays[2] = new Ray(p0, center.add(vRight.scale(rX / 2)).add(vUp.scale(rY / 2)).subtract(p0));
        // down left
        myRays[4] = new Ray(p0, center.add(vRight.scale(-rX / 2)).add(vUp.scale(-rY / 2)).subtract(p0));
        // down right
        myRays[5] = new Ray(p0, center.add(vRight.scale(rX / 2)).add(vUp.scale(-rY / 2)).subtract(p0));
        return myRays;
    }

    /**
     *
     *
     * @param ray
     * @param nX
     * @param nY
     *
     * @return
     */
    public Ray constructPixelCenterRay(Ray ray, double nX, double nY){

        // pixel height
        double height = alignZero(this.height / nY);
        // pixel width
        double width = alignZero(this.width / nX);

        double dis = distance;
        /**
         * distance from camera to our pixel
         */
        double t = dis/(vTo.dotProduct(ray.getDir())); //cosinus on the angle
        Point3D point = ray.getTargetPoint(t);
        point = point.add(vRight.scale(width/2)).add(vUp.scale(-height/2));
        return new Ray(p0, point.subtract(p0));
    }

    /**
     * constructing 4 rays from one ray, using if we got a different color in the previous test of the colors
     * in the 'square' of the pixel
     *
     * @param ray the ray to separate
     * @param nX
     * @param nY
     * @return
     */
    public List<Ray> constructFourRays(Ray ray, double nX, double nY) {

        // pixel height
        double height = alignZero(this.height / nY);
        // pixel width
        double width = alignZero(this.width / nX);

        List<Ray> myRays = new ArrayList<>();
        Point3D center = getPointOnViewPlane(ray);

        Point3D point1 = center.add(vUp.scale(height / 2));
        Point3D point2 = center.add(vRight.scale(-width / 2));
        Point3D point3 = center.add(vRight.scale(width / 2));
        Point3D point4 = center.add(vUp.scale(-height / 2));
        myRays.add(new Ray(p0, point1.subtract(p0)));
        myRays.add(new Ray(p0, point2.subtract(p0)));
        myRays.add(new Ray(p0, point3.subtract(p0)));
        myRays.add(new Ray(p0, point4.subtract(p0)));
        return myRays;
    }

    /**
     * Function to find a specific point on the plane
     * we need to calculate the distance from the point on the camera to the plane
     * for that we use the cos of the angle of the direction ray with vTo vector
     * @param ray ray to the specific point
     * @return the distance to the point
     */
    private Point3D getPointOnViewPlane(Ray ray) {
        double dis = distance;
        /**
         * distance from camera to our pixel
         */
        double t = dis / (vTo.dotProduct(ray.getDir())); //cosinus of the angle
        return ray.getTargetPoint(t);
    }

    /**
     * using in simple anti alysing for calculate color in a point in a sub-square
     *
     * @param pixStart up left corner of the sub-square
     * @param col the column
     * @param row the row
     *
     * @return random ray inside the range
     */
    private Ray randomPointRay(Point3D pixStart, double col, double row) {
        Point3D point = pixStart;
        if(!isZero(col)) {
            //only move on X axis
            point = point.add(vRight.scale(random(0, col)));
        }
        if(!isZero(row)) {
            //only move on Y axis
            point = point.add(vUp.scale(random(row, 0)));
        }
        return new Ray(getP0(), point.subtract(getP0()));
    }

    /**
     * P0 getter
     *
     * @return P0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * VTo getter
     *
     * @return VTo
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * VUp getter
     *
     * @return VUp
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * VRight getter (using cross product)
     *
     * @return vRight
     */
    public Vector getVRight() {
        return vRight;
    }
}