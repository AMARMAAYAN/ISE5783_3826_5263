package primitives;

/**

 Represents the material properties of an object in a scene.

 Defines the diffuse reflection coefficient (kD), specular reflection coefficient (kS),

 and the shininess factor (nShininess) of the material.
 @author AMARMAAYAN
 */
public class Material {

    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;

    /**

     Sets the diffuse reflection coefficient of the material.
     @param kD The diffuse reflection coefficient (Double3)
     @return The Material object with the updated diffuse reflection coefficient
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }
    /**

     Sets the diffuse reflection coefficient of the material using a single value.
     The same value is applied to all three components (RGB) of the coefficient.
     @param kD The diffuse reflection coefficient (double)
     @return The Material object with the updated diffuse reflection coefficient
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**

     Sets the specular reflection coefficient of the material.
     @param kS The specular reflection coefficient (Double3)
     @return The Material object with the updated specular reflection coefficient
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**

     Sets the specular reflection coefficient of the material using a single value.
     The same value is applied to all three components (RGB) of the coefficient.
     @param kS The specular reflection coefficient (double)
     @return The Material object with the updated specular reflection coefficient
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**

     Sets the shininess factor of the material.
     @param nShininess The shininess factor (int)
     @return The Material object with the updated shininess factor
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}








