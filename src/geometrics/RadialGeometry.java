package geometrics;

public abstract class RadialGeometry implements Geometry  {
    protected double radius;

    /**
     * Constructor that accepts the radius of the body as a parameter
     * @param radius the radius of the body
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * Returns the radius of the body
     * @return the radius of the body
     */
    public double getRadius() {
        return radius;
    }
}
