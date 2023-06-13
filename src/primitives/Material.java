package primitives;

/**
 * Represents the material properties of an object in a scene.
 */
public class Material {
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;
    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The diffuse reflection coefficient as a Double3 (RGB values).
     * @return The Material object with the updated diffuse reflection coefficient.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param doubleTokD The value of the diffuse reflection coefficient for all RGB channels.
     * @return The Material object with the updated diffuse reflection coefficient.
     */
    public Material setKd(double doubleTokD) {
        this.kD = new Double3(doubleTokD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The specular reflection coefficient as a Double3 (RGB values).
     * @return The Material object with the updated specular reflection coefficient.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param doubleTokS The value of the specular reflection coefficient for all RGB channels.
     * @return The Material object with the updated specular reflection coefficient.
     */
    public Material setKs(double doubleTokS) {
        this.kS = new Double3(doubleTokS);
        return this;
    }

    /**
     * Sets the shininess factor of the material.
     *
     * @param nShininess The shininess factor of the material.
     * @return The Material object with the updated shininess factor.
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setKt(double doubleTokT) {
        this.kT = new Double3(doubleTokT);
        return this;
    }

    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;

    }

    public Material setKr(double doubleTokR) {
        this.kR = new Double3(doubleTokR);
        return this;
    }
}
