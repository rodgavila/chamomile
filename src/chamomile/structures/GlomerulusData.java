package chamomile.structures;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Represents data for a single glomerulus.
 *
 * @author Gomez-Avila, Rodrigo
 */
public final class GlomerulusData extends AnatomicStructure {
    private ImmutableList<PodocyteData> mPodocytesData;
    private final int mImageId;
    private final int mNumber;

    /**
     * Minimum valid area of a glomerulus in µm (@value).
     */
    public static final double MIN_GLOMERULOUS_AREA = 4000.0;

    /**
     * Maximum valid area of a glomerulus in µm (@value).
     */
    public static final double MAX_GLOMERULOUS_AREA = 15000.0;

    /**
     * Create a data object for a renal glomerulus.
     * @param geometryInfo
     * @param imageId
     * @param number
     */
    public GlomerulusData(GeometryInfo geometryInfo, int imageId, int number) {
        super(geometryInfo);
        mImageId = imageId;
        mNumber = number;
    }

    /**
     * Returns the podocytes data for this glomerulus.
     * @return a {@code non-null} list of podocytes information.
     */
    public ImmutableList<PodocyteData> getPodocytesData() {
        return mPodocytesData;
    }

    /**
     * Sets the podoctyes data for this glomerulus.
     * @param podocytesData
     */
    public void setPodocytesData(List<PodocyteData> podocytesData) {
        Preconditions.checkNotNull(podocytesData);
        mPodocytesData = ImmutableList.copyOf(podocytesData);
    }

    /**
     * Returns the id of the image where this glomerulus was found.
     * @return int image id
     */
    public int getImageId() {
        return mImageId;
    }

    /**
     * Returns the arbitrary number that was assigned to the glomerulus for counting purposes.
     * @return int glomerulus number.
     */
    public int getNumber() {
        return mNumber;
    }

    public String toString() {
        return MoreObjects.toStringHelper(GlomerulusData.class)
                .add("area", getGeometryInfo().getArea())
                .add("feret", getGeometryInfo().getFeret())
                .add("podocytesCount", mPodocytesData.size())
                .toString();
    }
}
