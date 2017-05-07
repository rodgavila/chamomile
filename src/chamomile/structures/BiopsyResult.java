package chamomile.structures;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Represents all glomerulus and podocyte data for a given biopsy.
 */
public class BiopsyResult {
    private final String mId;
    private ImmutableList<GlomerulusData> mGlomerulusData;

    public BiopsyResult(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setGlomerulusData(List<GlomerulusData> glomerulusData) {
        mGlomerulusData = ImmutableList.copyOf(glomerulusData);
    }

    public String toString() {
        return MoreObjects.toStringHelper(GeometryInfo.class)
                .add("id", mId)
                .add("glomerulusCount", mGlomerulusData.size())
                .toString();
    }
}
