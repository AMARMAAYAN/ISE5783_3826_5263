package primitives;

/**

 Represents the material properties of an object in a scene.

 Defines the diffuse reflection coefficient (kD), specular reflection coefficient (kS),

 and the shininess factor (nShininess) of the material.
 @author AMARMAAYAN
 */
public class Material {

    public Double3 kD = Double3.ZERO; // Diffusion attenuation coefficient
    public Double3 kS = Double3.ZERO; // Specular reflection attenuation coefficient
    public int shininess = 0;
    public Double3 kT = Double3.ZERO ; // Transparency attenuation coefficient
    public Double3 kR = Double3.ZERO; // Reflection attenuation coefficient

    //sets:
    /**
     *set kT function -the Transparency attenuation coefficient of the material.
     *@param kT Transparency attenuation coefficient (Double3)
     *@return The Material object with the updated Transparency attenuation coefficient
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**

     /**
     * set kT function -the Transparency attenuation coefficient of the material.
     * @param kT Transparency attenuation coefficient (double)
     * @return The Material object with the updated Transparency attenuation coefficient
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
    /**

     /**
     Sets the diffuse reflection attenuation coefficient of the material.
     @param kR The diffuse  reflection attenuation coefficient (Double3)
     @return The Material object with the updated diffuse reflection attenuation coefficient
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }
    /**

     /**
     * set kT function - diffuse reflection attenuation coefficient
     * @param kR The diffuse reflection attenuation coefficient (double)
     * @return The Material object with the updated diffuse reflection attenuation coefficient
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
    /**


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
    public Material setShininess(int nShininess) {
        this.shininess = nShininess;
        return this;
    }
}








