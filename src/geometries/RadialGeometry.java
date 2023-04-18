package geometries;
/**
 The RadialGeometry class is an abstract class that represents geometric objects with a radial attribute, which implements the Geometry interface.
 @author Maayan Amar
 */
public abstract class RadialGeometry implements Geometry  {

    /**
     * The radius of the radial geometry object.
     */
    protected final double radius;

    /**
     * Constructor that receives the radius of the body as a parameter
     * @param radius - the radius of the radial geometry object.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * @return the radius of the body
     */
    public double getRadius() {
        return radius;
    }
}
