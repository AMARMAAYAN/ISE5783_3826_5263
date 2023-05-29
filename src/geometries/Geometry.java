package geometries;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
/**
 The Geometry interface represents a geometric object in 3D space.
 It provides a method to get the normal vector of the object at a specific point.
 This interface is implemented by various geometric shapes such as planes, spheres, etc.
 @author Maayan Amar
 */
public abstract class Geometry extends Intersectable {

    protected Color emission=Color.BLACK;

    /**
     * Returns the color of the geometry
     *
     * @return the color of the geometry
     */
    public Color getEmission() {
        return emission;
    }


    private Material material=new Material();



    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Sets the color of the geometry
     * @param emission the color of the emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


    /**
     * Returns the normal vector to the Geometry object at a given point.
     *
     * @param point - the point on the geometry object to get the normal vector at.
     * @return the normal vector to the geometry object at the given point.
     */
    public abstract Vector getNormal(Point point);
}
