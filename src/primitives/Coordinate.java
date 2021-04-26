package primitives;

import static primitives.Util.*;

/**
 * Class Coordinate is the basic class representing a coordinate for Cartesian
 * coordinate system. The class is based on Util controlling the accuracy.
 *
 * @version 5781B updated according to new requirements
 */
public final class Coordinate {

    /**
     * Coordinate value, intentionally "package-friendly" due to performance constraints
     */
    final double coord;

    /**
     * Coordinate constructor receiving a coordinate value
     *
     * @param coord coordinate value
     */
    public Coordinate(double coord) {

        // if it too close to zero make it zero
        this.coord = alignZero(coord);
    }

    /*************** Admin *****************/

    /**
     * override function to check if two objects are equal
     *
     * @param obj to compare
     *
     * @return true if equal and false if not
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate)obj;
        return isZero(coord - other.coord);
    }

    /**
     * string that represents the class
     *
     * @return string that represents the class
     */
    @Override
    public String toString() {
        return "" + coord;
    }
}