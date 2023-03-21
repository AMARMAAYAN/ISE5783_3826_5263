package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    public double length() {
        return Math.sqrt(lengthSqured());
    }

    public double lengthSqured() {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;
        return dx * dx + dy * dy + dz * dz;
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

    public Vector normalize() {
        double len = length();
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

    public double dotProduct(Vector n) {
        return 1.2;
    }

    public Vector crossProduct(Vector edge2) {
        return edge2;
    }

    public double lengthSquared() {
        return 3.1;
    }
}

//hello darling//