package primitives;

public class Vector extends Point {
    public Vector(double x, double y,double z) {
        super(x,y,z);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    Vector(Double3 double3){
        this(double3.d1,double3.d2,double3.d3);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }
    public double lengthSquared() {
        double dx=xyz.d1;
        double dy=xyz.d2;
        double dz=xyz.d3;
        return dx*dx+dy*dy+dz*dz;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }
    public Vector normalize(){
        double len=length();
        return new Vector(xyz.reduce(length()));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }


    public Vector scale(double x) {
        return new Vector(xyz.scale(x));
    }

    public Vector crossProduct(Vector other){
        double x=this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2;
        double y=this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3;
        double z=this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1;
        return new Vector(x,y,z);
    }



    public double dotProduct(Vector vector){
        double dotProduct = this.xyz.d1 * vector.xyz.d1 +
                this.xyz.d2 * vector.xyz.d2 +
                this.xyz.d3 * vector.xyz.d3;
        return dotProduct;
    }


}