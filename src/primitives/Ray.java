package primitives;
import java.util.Objects;

/**
 The Ray class represents a ray in 3D space, defined by a starting point and a direction vector.
 */
public class Ray {

    /**
     * The starting point of the ray.
     */
    final Point p;

    /**
     * The normalized direction vector of the ray.
     */
    final Vector vector;

    /**
     * Constructs a Ray object with a given starting point and direction vector.
     * @param p The starting point of the ray.
     * @param vector The direction vector of the ray.
     */
    public Ray(Point p, Vector vector) {
        this.p = p;
        this.vector = vector.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p, ray.p) && Objects.equals(vector, ray.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, vector);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p=" + p +
                ", vector=" + vector +
                '}';
    }
}
