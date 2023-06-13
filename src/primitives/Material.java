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

    /**
     * Kt - transparency component
     * 0.0 is opaque
     * 1.0 is clear
     */
    private Double3 kT = Double3.ZERO;

    /**
     * Kr - reflection component
     * 0.0 is matte
     * 1.0 is very reflexive
     */
    private Double3 kR = Double3.ZERO;
    public int shininess = 0;


    /**
     * Shininess - how shiny the material is
     */
    private int nShininess = 0;

    //*********Setters*********

    public Double3 getKs() {
        return kS;
    }

    public Material setKs(double ks) {
        kS = new Double3(ks);
        return this;
    }

    public Material setKs(Double3 ks) {
        kS = ks;
        return this;
    }

    public Double3 getKd() {
        return kD;
    }

    public Material setKd(Double3 kd) {
        this.kD = kd;
        return this;
    }

    public Material setKd(double kd) {
        this.kD = new Double3(kd);
        return this;
    }

    public int getShininess() {
        return nShininess;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Double3 getKt() {
        return kT;
    }

    public Material setKt(double kt) {
        this.kT = new Double3(kt);
        return this;
    }

    public Material setKt(Double3 kt) {
        this.kT = kt;
        return this;
    }

    //*********Getters*********

    public Double3 getKr() {
        return kR;
    }

    public Material setKr(double kr) {
        this.kR = new Double3(kr);
        return this;
    }

    public Material setKr(Double3 kr) {
        this.kR = kr;
        return this;
    }

}








