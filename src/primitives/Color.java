package primitives;

/**
 * Wrapper class for java.jwt.Color The constructors operate with any
 * non-negative RGB values. The colors are maintained without upper limit of
 * 255. Some additional operations are added that are useful for manipulating
 * light's colors
 */
public class Color {

    /**
     * The internal fields tx`o maintain RGB components as double numbers from 0 to whatever...
     */
    private double r = 0.0;
    /**
     * default number for green
     */
    private double g = 0.0;
    /**
     *default number for blue
     */
    private double b = 0.0;

    /**
     * constant color for black
     */
    public static final Color BLACK = new Color();

    /**
     * Default constructor - to generate Black Color (privately)
     */
    private Color() {
    }

    /**
     * Constructor to generate a color according to RGB components Each component in
     * range 0..255 (for printed white color) or more [for lights]
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     *
     * @throws IllegalArgumentException if (r OR g or b) smaller than 0
     */
    public Color(double r, double g, double b) {
        if (r < 0 || g < 0 || b < 0)
            throw new IllegalArgumentException("Negative color component is illegal");

        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Copy constructor for Color
     *
     * @param other the source color
     */
    public Color(Color other) {
        r = other.r;
        g = other.g;
        b = other.b;
    }

    /**
     * Constructor on base of java.awt.Color object
     *
     * @param other java.awt.Color's source object
     */
    public Color(java.awt.Color other) {
        r = other.getRed();
        g = other.getGreen();
        b = other.getBlue();
    }

    /**
     * Color setter to reset the color to BLACK
     *
     * @return the Color object itself for chaining calls
     */
    public Color setColor() {
        r = 0.0;
        g = 0.0;
        b = 0.0;
        return this;
    }

    /**
     * Color setter to generate a color according to RGB components Each component
     * in range 0..255 (for printed white color) or more [for lights]
     *
     * @param r Red component
     * @param g Green component
     * @param b Blue component
     *
     * @return the Color object itself for chaining calls
     *
     * @throws IllegalArgumentException if (r OR g or b) smaller than 0
     */
    public Color setColor(double r, double g, double b) {
        if (r < 0 || g < 0 || b < 0)
            throw new IllegalArgumentException("Negative color component is illegal");
        this.r = r;
        this.g = g;
        this.b = b;
        return this;
    }

    /**
     * Color setter to copy RGB components from another color
     *
     * @param other source Color object
     *
     * @return the Color object itself for chaining calls
     */
    public Color setColor(Color other) {
        r = other.r;
        g = other.g;
        b = other.b;
        return this;
    }

    /**
     * Color setter to take components from an base of java.awt.Color object
     *
     * @param other java.awt.Color's source object
     *
     * @return the Color object itself for chaining calls
     */
    public Color setColor(java.awt.Color other) {
        r = other.getRed();
        g = other.getGreen();
        b = other.getBlue();
        return this;
    }

    /**
     * Color getter - returns the color after converting it into java.awt.Color
     * object During the conversion any component bigger than 255 is set to 255
     *
     * @return java.awt.Color object based on this Color RGB components
     */
    public java.awt.Color getColor() {
        int ir = (int) r;
        int ig = (int) g;
        int ib = (int) b;
        return new java.awt.Color(ir > 255 ? 255 : ir, ig > 255 ? 255 : ig, ib > 255 ? 255 : ib);
    }

    /**
     * Operation of adding this and one or more other colors (by component)
     *
     * @param colors one or more other colors to add
     *
     * @return new Color object which is a result of the operation
     */
    public Color add(Color... colors) {
        double rr = r;
        double rg = g;
        double rb = b;
        for (Color c : colors) {
            rr += c.r;
            rg += c.g;
            rb += c.b;
        }
        return new Color(rr, rg, rb);
    }

    /**
     * Scale the color by a scalar
     *
     * @param k scale factor
     *
     * @return new Color object which is the result of the operation
     *
     * @throws IllegalArgumentException if k smaller 0
     */
    public Color scale(double k) {
        if (k < 0)
            throw new IllegalArgumentException("Can't scale a color by a negative number");
        return new Color(r * k, g * k, b * k);
    }

    /**
     * Scale the color by (1 / reduction factor)
     *
     * @param k reduction factor
     *
     * @return new Color object which is the result of the operation
     *
     * @throws IllegalArgumentException if k smaller than 1
     */
    public Color reduce(double k) {
        if (k < 1)
            throw new IllegalArgumentException("Can't scale a color by a number lower than 1");
        return new Color(r / k, g / k, b / k);
    }
}