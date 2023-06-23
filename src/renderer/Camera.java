package renderer;
import primitives.*;
import primitives.Color;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;

import static primitives.Util.isZero;

/**
 A class representing a camera in a 3D space.
 The camera is defined by its location, orientation, distance to the object, and view plane size.
 @author Maayan Amar
 */
public class Camera {

    private Point position; // The position of the camera
    private Vector vTo; // The direction vector pointing towards the target
    private Vector vUp; // The up vector
    private Vector vRight; // The right vector
    private double distance; // The distance between the camera and the view plane
    private double height; // The height of the view plane
    private double width; // The width of the view plane
    private ImageWriter imageWriter; // The image writer used to write the rendered image
    private RayTracerBase rayTracer; // The ray tracer used for rendering


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
     * @param _position the position
     * @param _vTo      the vTo vector
     * @param _vUp      the vUp vector
     */
    public Camera(Point _position, Vector _vTo, Vector _vUp) {
        if (_vTo.dotProduct(_vUp) != 0)
            throw new IllegalArgumentException("vTo and vUp must be orthogonal");

        position = _position; // Set the camera position
        vTo = _vTo.normalize(); // Normalize the vTo vector
        vUp = _vUp.normalize(); // Normalize the vUp vector
        vRight = vTo.crossProduct(vUp).normalize(); // Calculate the normalized vRight vector
    }

    /**
     * function that sets the width and height
     *
     * @param width  of the view plane
     * @param height of the view plane
     * @return this
     */
    public Camera setVPSize(double width, double height) {
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
        Point pC = position.add(vTo.scale(distance)); // Calculate the center point of the image plane

        double rY = height / nY; // Calculate the height of a single pixel in the image plane
        double rX = width / nX; // Calculate the width of a single pixel in the image plane

        double yI = -(i - (nY - 1d) / 2) * rY; // Calculate the vertical offset of the pixel from the center
        double jX = (j - (nX - 1d) / 2) * rX; // Calculate the horizontal offset of the pixel from the center
        Point Pij = pC; // Initialize the point on the image plane to the center point

        if (yI != 0) Pij = Pij.add(vUp.scale(yI)); // Adjust the point vertically based on the offset
        if (jX != 0) Pij = Pij.add(vRight.scale(jX)); // Adjust the point horizontally based on the offset

        return new Ray(position, Pij.subtract(position)); // Create and return the constructed ray
    }

    /**
     * function that gets the color of the pixel and renders in to image
     */
    public Camera renderImage() {
        if (position == null || vTo == null || vUp == null || vRight == null || distance == 0 || height == 0 || width == 0 || imageWriter == null || rayTracer == null)
            throw new MissingResourceException("", "", "Camera is not initialized");

        int nX = imageWriter.getNx(); // Get the width of the image
        int nY = imageWriter.getNy(); // Get the height of the image

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                Ray ray = constructRay(nX, nY, j, i); // Construct the ray for the current pixel
                imageWriter.writePixel(j, i, this.castRay(nX, nY, i, j)); // Cast the ray and write the color to the image writer
            }
        }

        return this;
    }

    /**
     * function that prints grid on top of image
     *
     * @param interval of grid
     * @param color    of grid
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("", "", "Camera is not initialized");
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if ((i % interval == 0) || (j % interval == 0))
                    imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * function that calls write to image function
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("", "", "Camera is not initialized");
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
    private Color castRay(int nX, int nY, int i, int j) {
        Ray tempRay = constructRay(nX, nY, j, i);
        return rayTracer.traceRay(tempRay);

    }


    //additional classes for stage 7:
    /**
     * spin the camera up and down around vRight vector
     * @param angle in degrees (how many we need move)
     * @return
     */
    public Camera spinAroundVRight(double angle){
        if(angle >= 360){
            angle = angle % 360;
        }
        if(angle > 180){
            angle = angle - 180; //now all the angles between -180 and 180
        }
        //we are move only the vUp and the vTo vectors
        double angleRad = Math.toRadians(angle);
        double sinA = Math.sin(angleRad);
        double cosA = Math.cos(angleRad);
        if(isZero(cosA)){ //if angle is 90 or -90 degrees, we move the camera 90 degrees upper
            vTo = vUp.scale(sinA); //sin (90) = 1, sin (-90) = -1, vTo = vUp or vTo = -vUp
        }
        else if(isZero(sinA)){ //if angle is 0 or 180 degrees
            vTo = vTo.scale(cosA); //cos(0) = 1, cos(180) = -1, vTo = vTo or vTo = -vTo
        }
        else{ //spin around the vTo vector according to the Rodrigues' rotation formula (rotation V = v*cosA + (K x v)*sinA + K*(K*V)(1-cosA), k is vRight in our situation
            vTo = vTo.scale(cosA).add(vRight.crossProduct(vTo).scale(sinA));
        }
        vUp = vRight.crossProduct(vTo).normalize(); //change the vUp vector according to the new vTo (vUp is perpendicular to vTo and vRight)

        return this;
    }

    /**
     * spin the camera left and right around vUp vector
     * @param angle in degrees (how many we need move)
     * @return
     */
    public Camera spinAroundVUp(double angle){
        if(angle >= 360){
            angle = angle % 360;
        }
        if(angle > 180){
            angle = angle - 180; //now all the angles between -180 and 180
        }
        //we are move only the vRight and the vTo vectors
        double angleRad = Math.toRadians(angle);
        double sinA = Math.sin(angleRad);
        double cosA = Math.cos(angleRad);
        if(isZero(cosA)){ //if angle is 90 or -90 degrees, we move the camera 90 degrees
            vRight = vTo.scale(sinA); //sin (90) = 1, sin (-90) = -1, vRight = vTo or vRight = -vTo
        }
        else if(isZero(sinA)){ //if angle is 0 or 180 degrees
            vRight = vRight.scale(cosA); //cos(0) = 1, cos(180) = -1, vRight = vRight or vRight = -vRight
        }
        else{ //spin around the vTo vector according to the Rodrigues' rotation formula (rotation V = v*cosA + (K x v)*sinA + K*(K*V)(1-cosA), k is vUp in our situation
            vRight = vRight.scale(cosA).add(vUp.crossProduct(vRight).scale(sinA));
        }
        vTo = vUp.crossProduct(vRight).normalize(); //change the vTo vector according to the new vRight (vTo is perpendicular to vUp and vRight)

        return this;
    }

    /**
     * spin the camera around vTo vector
     * @param angle in degrees (how many we need move)
     * @return
     */
    public Camera spinAroundVTo(double angle){
        if(angle >= 360){
            angle = angle % 360;
        }
        if(angle > 180){
            angle = angle - 180; //now all the angles between -180 and 180
        }
        //we are moving only the vUp and the vRight vectors
        double angleRad = Math.toRadians(angle);
        double sinA = Math.sin(angleRad);
        double cosA = Math.cos(angleRad);
        if(isZero(cosA)){ //if angle is 90 or -90 degrees, we move the camera 90 degrees
            vUp = vRight.scale(sinA); //sin (90) = 1, sin (-90) = -1, vUp = vRight or vUp = -vRight
        }
        else if(isZero(sinA)){ //if angle is 0 or 180 degrees
            vUp = vUp.scale(cosA); //cos(0) = 1, cos(180) = -1, vUp = vUp or vUp = -vUp
        }
        else{ //spin around the vTo vector according to the Rodrigues' rotation formula (rotation V = v*cosA + (K x v)*sinA + K*(K*V)(1-cosA), k is vTo in our situation
            vUp = vUp.scale(cosA).add(vTo.crossProduct(vUp).scale(sinA));
        }
        vRight = vTo.crossProduct(vUp).normalize(); //change the vTo vector according to the new vRight (vTo is perpendicular to vUp and vRight)

        return this;
    }


    /**
     * move the camera up or down
     * @param distance how many the camera goes up (it can be minus and go down)
     * @return
     */
    public Camera moveUpDown(double distance){
        if(!isZero(distance)) {
            position = position.add(vUp.scale(distance));
        }
        return this;
    }

    /**
     * move the camera right or left
     * @param distance how many the camera goes right (can be minus and go left)
     * @return
     */
    public Camera moveRightLeft(double distance){
        if(!isZero(distance)) {
            position = position.add(vRight.scale(distance));
        }
        return this;
    }


    /**
     * move the camera near or away (from the objects)
     * @param distance how many the camera goes forward (can be minus and go backward)
     * @return
     */
    public Camera moveNearAway(double distance){
        if(!isZero(distance)) {
            position = position.add(vTo.scale(distance));
        }
        return this;
    }


}
