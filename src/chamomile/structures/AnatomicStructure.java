package chamomile.structures;

import com.google.common.base.Preconditions;

/**
 * Represents data for an anatomic structure
 *
 * @author Gomez-Avila, Rodrigo
 */
public abstract class AnatomicStructure {
    private final GeometryInfo mGeometryInfo;

    public AnatomicStructure(GeometryInfo geometryInfo) {
        mGeometryInfo = Preconditions.checkNotNull(geometryInfo);
    }

    public GeometryInfo getGeometryInfo() {
        return mGeometryInfo;
    }
}
