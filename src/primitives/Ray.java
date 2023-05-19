package primitives;
import java.util.List;
import java.util.Objects;
import static primitives.Util.*;

/**
 The Ray class represents a ray in 3D space, defined by a starting point and a direction dir.
 */
public class Ray {

    /**
     * The starting point of the ray.
     */
    final Point p0;

    /**
     * The normalized direction dir of the ray.
     */
    final Vector dir;

    /**
     * returns the point po
     * @return po
     */
    public Point getP0() {
        return p0;
    }

    /**
     * returns thr vector dir
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    public Point getPoint(double t){
        Vector tv = dir.scale(t); //multiply the double with the vector
        Point p= p0.add(tv); //adds the vector to the point and return a point
        return p;
    }

    /**
     * Constructs a Ray object with a given starting point and direction dir.
     * @param p The starting point of the ray.
     * @param dir The direction dir of the ray.
     */
    public Ray(Point p, Vector dir) {
        this.p0 = p;
        this.dir = dir.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * Finds the closest point from a given list of intersections.
     *
     * @param intersections The list of Point objects representing intersections.
     * @return The Point object that is closest to the reference point.
     */
    public Point findClosestPoint(List<Point> intersections) {
        Point closestPoint = null;
        double miniDistance = Double.MAX_VALUE;
        double ptDistance;

        // Iterate through each Point object in the intersections list
        for (Point pt : intersections) {
            // Calculate the distance between the current point (pt) and a reference point (p0)
            ptDistance = pt.distanceSquared(p0);

            // Check if the calculated distance is smaller than the current minimum distance
            if (ptDistance < miniDistance) {
                // If so, update the minimum distance and set the closest point to the current point
                miniDistance = ptDistance;
                closestPoint = pt;
            }
        }

        // Return the closest point found
        return closestPoint;
    }
}
