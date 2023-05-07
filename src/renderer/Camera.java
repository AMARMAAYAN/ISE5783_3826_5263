package renderer;
import primitives.*;
import static primitives.Util.isZero;

/**
 A class representing a camera in a 3D space.
 The camera is defined by its location, orientation, distance to the object, and view plane size.
 @author Maayan Amar
 */
public class Camera {

    //p0=the location of the camera
    private Point p0;

    //x axis vector
    private Vector vTo;

    //y axis vector
    private Vector vUp;

    //z axis vector
    private Vector vRight;

    //the distance from the object to the camera
    private double distance;

    //object actual width
    private double width;

    //object actual height
    private double height;

    /**
     * Constructs a new camera with the given location, orientation and distance to the object.
     *
     * @param p0  the location of the camera
     * @param vTo the direction of the camera
     * @param vUp the up direction of the camera
     * @throws IllegalArgumentException if the vUp and vTo vectors are not orthogonal
     */

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vTo.dotProduct(vUp))){
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        }
        this.p0=p0;

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        //receiving vRight by doing a cross product between vTp and vUp
        vRight=this.vTo.crossProduct(this.vUp);
    }

    /**
     * setter - chaining method
     * @param width - the with of the vew plane
     * @param height - the height of the view plane
     * @return thge camera with the configured view plane
     */
    public Camera setVPSize(double width, double height){
        this.width=width;
        this.height=height;
        return this;
    }

    /**
     * setter - chaining method
     * @param distance - the object distance from the center of the camera
     * @return the camera with the configured distance
     */
    public Camera setVPDistance(double distance){
    this.distance=distance;
    return this;
    }



    /**
     * Constructs a ray from the camera to the specified pixel on the view plane.
     *
     * @param nX the number of pixels in the x axis
     * @param nY the number of pixels in the y axis
     * @param j  the x coordinate of the pixel on the view plane
     * @param i  the y coordinate of the pixel on the view plane
     * @return the ray constructed from the camera to the specified pixel on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i){

        //center point of the view plane
        Point Pc=p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx=width / nX;
        double Ry=width / nY;

        //Pij point[i,j] in view plane coordinate
        Point Pij=Pc;

        //delta values for moving on the view plane
        double Xj= (j - (nX - 1) / 2d) * Rx;
        double Yi= -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) {
            Pij=Pij.add(vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            Pij=Pij.add(vUp.scale(Yi));
        }

        //vector from the camera in the direction of point(i,j) in the view plane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }


}
