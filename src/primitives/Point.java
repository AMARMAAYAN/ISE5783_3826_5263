package primitives;

import java.util.Objects;

/**
 Represents a point in three-dimensional space using Cartesian coordinates.
 The class provides methods for computing the distance between two points and
 for adding a vector to a point and subtracting two points to obtain a vector.
 The class overrides the equals, hashCode, and toString methods for object comparison
 and string representation.
 @author Maayan Amar
 */
public class Point {

    public static final Point ZERO = new Point(0,0,0);
    /**
     * The coordinates of the point in three-dimensional space */
    final Double3 xyz;

    /**
     * Constructs a point with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) {
       xyz=new Double3(x,y,z);
    }

    /**
     * Constructs a point from a Double3 object containing the coordinates.
     *
     * @param double3 the Double3 object containing the coordinates
     */
     Point(Double3 double3 ) {
        this(double3.d1,double3.d2,double3.d3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return this.xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.xyz);
    }

    @Override
    public String toString() {
        return "Point:" + this.xyz;
    }

    /**
     * Computes the square of the distance between this point and another point.
     *
     * @param other the other point to compute the distance to
     * @return the square of the distance between this point and the other point
     */
    public double distanceSquared(Point other){
        return(other.xyz.d1- xyz.d1)*(other.xyz.d1- xyz.d1)+
                (other.xyz.d2- xyz.d2)*(other.xyz.d2- xyz.d2)+
                (other.xyz.d3- xyz.d3)*(other.xyz.d3- xyz.d3);

    }

    /**
     * Computes the distance between this point and another point.
     *
     * @param other the other point to compute the distance to
     * @return the distance between this point and the other point
     */
    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Adds a vector to this point.
     *
     * @param vector the vector to add to this point
     * @return a new point that is the result of adding the vector to this point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }


    /**
     * Subtracts another point from this point to obtain a vector.
     *
     * @param point the point to subtract from this point
     * @return a new vector that is the result of subtracting the other point from this point
     */
    public Vector subtract(Point point) {

        Double3 result = this.xyz.subtract(point.xyz);

        if (result.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("ZERO vector not allowed");
        }
        return new Vector(result);
    }

    /**
     * Getter of X coordinate value
     * @return x coordinate value
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Getter of Y coordinate value
     * @return y coordinate value
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Getter of Z coordinate value
     * @return z coordinate value
     */
    public double getZ() {
        return xyz.d3;
    }
}
