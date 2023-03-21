package primitives;
import java.util.Objects;

public class Ray {
    final Point p;
    final Vector vector;


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
