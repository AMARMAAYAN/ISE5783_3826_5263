package primitives;

/**
 This class represents a three-dimensional Vector, which extends Point.
 A Vector has a magnitude and direction, represented by its X, Y, and Z components.
 It provides methods for performing vector operations, such as adding, scaling, normalizing, computing cross and dot product.
 @author Maayan Amar
 */
public class Vector extends Point {

    /**
     * Constructs a new Vector object with given X, Y, and Z coordinates.
     * @param x The X coordinate of the vector.
     * @param y The Y coordinate of the vector.
     * @param z The Z coordinate of the vector.
     * @throws IllegalArgumentException if the coordinates are (0,0,0), i.e. the zero vector.
     */
    public Vector(double x, double y,double z) {
        super(x,y,z);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    /**
     * Constructs a new Vector object from the given Double3 object.
     * @param double3 The Double3 object containing the X, Y, and Z coordinates of the vector.
     */
    Vector(Double3 double3){
        this(double3.d1,double3.d2,double3.d3);
    }


    /**
     * Returns the length of the vector, computed using the Pythagorean theorem.
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }


    /**
     * Returns the square of the length of the vector, computed without using the square root operation.
     * @return The square of the length of the vector.
     */
    public double lengthSquared() {
        double dx=xyz.d1;
        double dy=xyz.d2;
        double dz=xyz.d3;
        return dx*dx+dy*dy+dz*dz;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    /**
     * Returns a new vector with the same direction as this vector but with a magnitude of 1 (unit vector).
     * @return The normalized vector.
     */
    public Vector normalize(){
        double len=length();
        return new Vector(xyz.reduce(length()));

        //in some point we wiil need to change to:
        /** double len=length();
         double x= xyz.d1/len;
         double y= xyz.d2 /len;
         double z= xyz.d3/len;

         return new Vector(x,y,z); **/
    }

    @Override
    public String toString() {
        return "Vector:" + xyz;
    }

    /**
     * Returns a new vector that is the result of adding the specified vector to this vector.
     * @param vector The vector to add to this vector.
     * @return The sum of the two vectors.
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }


    public Vector subtract(Vector other){
        return new Vector(xyz.subtract(other.xyz));

        //in some point we change to:
        /**
         double x=xyz.d1 - other.xyz.d1;
         double y=xyz.d2 - other.xyz.d2;
         double z=xyz.d3 - other.xyz.d3;
         **/

    }
    /**
     * Returns a new vector that is the result of scaling this vector by the specified scalar value.
     * @param x The scalar value to scale this vector by.
     * @return The scaled vector.
     */
    public Vector scale(double x) {
        return new Vector(xyz.scale(x));
    }

    /**
     * Returns a new vector that is the cross product of this vector and the specified vector.
     * @param other The vector to compute the cross product with.
     * @return The cross product of the two vectors.
     */
    public Vector crossProduct(Vector other){
        double x=this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2;
        double y=this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3;
        double z=this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1;
        return new Vector(x,y,z);
    }


    /**
     Calculates the dot product between this vector and another given vector.
     @param vector- the vector to calculate the dot product with
     @return the dot product between this vector and the given vector
     */
    public double dotProduct(Vector vector){
        double dotProduct = this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3;
        return dotProduct;
    }


}