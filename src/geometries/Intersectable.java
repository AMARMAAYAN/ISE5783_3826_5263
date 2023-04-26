package geometries;
import java.util.List;
import primitives.Point;
import primitives.Ray;
public interface Intersectable {
    public List<Point>findIntersections(Ray ray);
}
