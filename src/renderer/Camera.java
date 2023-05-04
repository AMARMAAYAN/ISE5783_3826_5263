package renderer;
import primitives.*;

import static primitives.Util.isZero;

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

    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vTo.dotProduct(vUp))){
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        }
        this.p0=p0;

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        //reciving vRight by doing a cross product between vTp and vUp
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
