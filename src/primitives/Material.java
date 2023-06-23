package primitives;

/**
 * Represents the material properties of an object in a scene.
 * Defines the diffuse reflection coefficient (kD), specular reflection coefficient (kS),
 * and the shininess factor (nShininess) of the material.
 *
 * author: AMARMAAYAN
 */
public class Material {

    /**
     * Kd - diffuse component, represents the scattering of light rays to all directions from the surface
     */
    private Double3 Kd = Double3.ZERO;

    /**
     * Ks - specular component, represents the reflectance of the light source over the surface
     */
    private Double3 Ks = Double3.ZERO;

    /**
     * Kt - transparency component
     * 0.0 is opaque
     * 1.0 is clear
     */
    private Double3 Kt = Double3.ZERO;

    /**
     * Kr - reflection component
     * 0.0 is matte
     * 1.0 is very reflective
     */
    private Double3 Kr = Double3.ZERO;

    /**
     * Shininess - how shiny the material is
     */
    private int nShininess = 0;

    // **** Setters ****

    /**
     * Sets the shininess factor of the material.
     *
     * @param nShininess The shininess factor to set
     * @return The Material object itself for method chaining
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material using a single value.
     *
     * @param ks The specular reflection coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKs(double ks) {
        Ks = new Double3(ks);
        return this;
    }

    /**
     * Sets the specular reflection coefficient (kS) of the material using a Double3 object.
     *
     * @param ks The specular reflection coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKs(Double3 ks) {
        Ks = ks;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material using a single value.
     *
     * @param kd The diffuse reflection coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKd(double kd) {
        this.Kd = new Double3(kd);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) of the material using a Double3 object.
     *
     * @param kd The diffuse reflection coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKd(Double3 kd) {
        this.Kd = kd;
        return this;
    }

    /**
     * Sets the transparency coefficient (kT) of the material using a single value.
     *
     * @param kt The transparency coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKt(double kt) {
        this.Kt = new Double3(kt);
        return this;
    }

    /**
     * Sets the transparency coefficient (kT) of the material using a Double3 object.
     *
     * @param kt The transparency coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKt(Double3 kt) {
        this.Kt = kt;
        return this;
    }

    /**
     * Sets the reflection coefficient (kR) of the material using a single value.
     *
     * @param kr The reflection coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKr(double kr) {
        this.Kr = new Double3(kr);
        return this;
    }

    /**
     * Sets the reflection coefficient (kR) of the material using a Double3 object.
     *
     * @param kr The reflection coefficient to set
     * @return The Material object itself for method chaining
     */
    public Material setKr(Double3 kr) {
        this.Kr = kr;
        return this;
    }

    // **** Getters ****

    /**
     * Retrieves the reflection coefficient (kR) of the material.
     *
     * @return The reflection coefficient of the material
     */
    public Double3 getKr() {
        return Kr;
    }

    /**
     * Retrieves the specular reflection coefficient (kS) of the material.
     *
     * @return The specular reflection coefficient of the material
     */
    public Double3 getKs() {
        return Ks;
    }

    /**
     * Retrieves the transparency coefficient (kT) of the material.
     *
     * @return The transparency coefficient of the material
     */
    public Double3 getKt() {
        return Kt;
    }

    /**
     * Retrieves the diffuse reflection coefficient (kD) of the material.
     *
     * @return The diffuse reflection coefficient of the material
     */
    public Double3 getKd() {
        return Kd;
    }

    /**
     * Retrieves the shininess factor of the material.
     *
     * @return The shininess factor of the material
     */
    public int getShininess() {
        return nShininess;
    }
}



