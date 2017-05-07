package chamomile.structures;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Represents geometric info of an anatomic structure.
 *
 * @author Gomez-Avila, Rodrigo
 */
public final class GeometryInfo {
    private final double mArea;
    private final double mFeret;

    /**
     * Creates a new geometric info.
     * @param area area > 0
     * @param feret feret > 0
     */
    public GeometryInfo(double area, double feret) {
        Preconditions.checkArgument(area > 0, "Area must be greater than zero");
        Preconditions.checkArgument(feret > 0, "Feret must be greater than zero");
        mArea = area;
        mFeret = feret;
    }

    /**
     * Returns the area of the anatomic structure.
     * @return int area of the anatomic structure.
     */
    public double getArea() {
        return mArea;
    }

    /**
     * REturns the feret diameter of the anatomic structure.
     * @return int feret diameter of the anatomic structure.
     */
    public double getFeret() {
        return mFeret;
    }

    public String toString() {
        return MoreObjects.toStringHelper(GeometryInfo.class)
                .add("area", mArea)
                .add("feret", mFeret)
                .toString();
    }
}
