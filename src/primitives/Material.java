package primitives;

/**
 * todo
 */
public class Material {
    //ks + ks <= 1
    //קבועים בשביל משוואה פיזיקלית
    public double kD;//כמה אני מפזר
    public double kS;//כמה אור חוזק
    public int nShininess;//כמה הגוף מבריק



    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
