package renderer;
import primitives.*;
import primitives.Color;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 A class representing a camera in a 3D space.
 The camera is defined by its location, orientation, distance to the object, and view plane size.
 @author Maayan Amar
 */
public class Camera {

    private Point position;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;
    private double height;
    private double width;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    /**
     * function that gets the position of the camera
     *
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * function that gets the vTo vector
     *
     * @return the vTo vector
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * function that gets the vUp vector
     *
     * @return the vUp vector
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * function that gets the vRight vector
     *
     * @return the vRight vector
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * function that gets the distance
     *
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * function that gets the height
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * function that gets the width
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * function that constructs the camera
     *
     * @param position the position
     * @param vTo      the vTo vector
     * @param vUp      the vUp vector
     */
    public Camera(Point position, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vto  and vup are not orthogonal");
        }
        this.position = position;

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        vRight = this.vTo.crossProduct(this.vUp);
    }

    /**
     * function that sets the width and height
     *
     * @param width  of the view plane
     * @param height of the view plane
     * @return this
     */
    public Camera setVPSize(double width, double height) {
        if (isZero(width) || isZero(height)) {
            throw new IllegalArgumentException("width or height cannot be zero");
        }

        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * function that sets the distance
     *
     * @param distance value to set
     * @return this
     */
    public Camera setVPDistance(double distance) {
        if (isZero(distance)) {
            throw new IllegalArgumentException("distance cannot be zero");
        }

        this.distance = distance;
        return this;
    }

    /**
     * function that sets imageWriter
     *
     * @param imageWriter object to set
     * @return camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * function that sets the rayTracer
     *
     * @param rayTracer object to set
     * @return camera itself
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * function that gets the ray from the camera to the point
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param i  the x coordinate
     * @param j  the y coordinate
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pC = position.add(vTo.scale(distance));

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1d) / 2) * rY;
        double jX = (j - (nX - 1d) / 2) * rX;
        Point Pij = pC;

        if (yI != 0) Pij = Pij.add(vUp.scale(yI));
        if (jX != 0) Pij = Pij.add(vRight.scale(jX));

        return new Ray(position, Pij.subtract(position));
    }


    public Camera renderImage() {
        if (imageWriter == null || rayTracer == null || width == 0 || height == 0 || distance == 0) {
            throw new MissingResourceException("Camera is missing some fields", "Camera", "field");
        }

        for (int i = 0; i < imageWriter.getNx(); i++){
            for (int j = 0; j<imageWriter.getNy(); j++){
                imageWriter.writePixel(j, i,                                                // for each pixel (j,i)
                        rayTracer.traceRay(                                              // find the color of the pixel using
                                constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i)));  // construction of a ray through the pixel
                // and intersecting with the geometries
            }
        }
        return this;
    }




    /**
     * function that gets the color of the pixel and renders in to image
     */
//    public Camera renderImage() {
//        if (position== null || vRight == null
//                || vUp == null || vTo == null || distance == 0
//                || width == 0 || height == 0
//                || imageWriter == null || rayTracer == null) {
//            throw new MissingResourceException("Missing camera data", Camera.class.getName(), null);
//        }
//        for (int i = 0; i < imageWriter.getNy(); i++) {
//            for (int j = 0; j < imageWriter.getNx(); j++) {
//                // Pixel coloring by ray
//                Ray ray = constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i);
//                imageWriter.writePixel(j,i, rayTracer.traceRay(ray));
//
//            }
//        }
//        return this;
//    }





    /**
     * function that prints grid on top of image
     *
     * @param interval of grid
     * @param color    of grid
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null)
            throw new MissingResourceException("imageWriter is null", ImageWriter.class.getName(), null);
        imageWriter.printGrid(interval,color);
    }
//    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
//        Point pIJ = getCenterOfPixel(nX, nY, j, i); // center point of the pixel
//
//        //Vi,j = Pi,j - P0, the direction of the ray to the pixel(j, i)
//        Vector vIJ = pIJ.subtract(p0);
//        return new Ray(p0, vIJ);
//    }




    /**
     * function that calls write to image function
     *
     * @return
     */
    public void writeToImage() {
        if (imageWriter == null){
            throw new MissingResourceException("", "", "Camera is not initialized");}

        imageWriter.writeToImage();

    }

    /**
     * function that casts ray and returns color
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param i  the x coordinate
     * @param j  the y coordinate
     * @return the color
     */
    private void castRay(int nX, int nY, int i, int j) {
        Ray ray = constructRay(nX, nY, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, pixelColor);

    }

}