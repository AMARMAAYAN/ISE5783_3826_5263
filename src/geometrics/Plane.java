package geometrics;

import primitives.Point;
import primitives.Vector;

public class Plane {
    final Point q0;
    final Vector normal;
    public Plane(Point vertex, Point vertex1, Point vertex2) {
        q0 =vertex;
        normal = null;
    }

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Vector getNormal() {
        return normal;
    }
}
