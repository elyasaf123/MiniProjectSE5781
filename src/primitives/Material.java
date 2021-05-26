package primitives;

/**
 * class for the representation of the material from which the shapes are made
 */
public class Material {

    // Fixed for the physical equation according to the pong model (kS + kD <= 1)/
    //
    // Coefficient of attenuation of the degree to which the material emits the light it illuminates
    // (ranging from 0 to 1)
    public double kD;

    // Coefficient of attenuation of the degree to which the material glossy (Expressed in the reflection of light)
    // (ranging from 0 to 1)
    public double kS;

    // Coefficient of attenuation of the degree to which the material reflected (ranging from 0 to 1)
    public double kR = 0.0;

    // Coefficient of attenuation of the degree to which the material causes light to refract (ranging from 0 to 1)
    public double kT = 0.0;

    // How gloss the shape is
    public int nShininess = 0;

    /**
     * setter for the Coefficient of attenuation of the degree to which the material emits the light it illuminates
     *
     * @param kD the kD given
     *
     * @return this (for chaining)
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter for the Coefficient of attenuation of the degree to which the material glossy
     *
     * @param kS the kS given
     *
     * @return this (for chaining)
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter for how gloss the shape is
     *
     * @param nShininess the nShininess given
     *
     * @return this (for chaining)
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter for the Coefficient of attenuation of the degree to which the material reflected
     *
     * @param kR the kR given
     *
     * @return this (for chaining)
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter for the Coefficient of attenuation of the degree to which the material causes light to refract
     *
     * @param kT the kT given
     *
     * @return this (for chaining)
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     * getter for kR
     *
     * @return kR
     */
    public double getKr() {
        return kR;
    }

    /**
     * getter for kT
     *
     * @return kT
     */
    public double getKt() {
        return kT;
    }
}