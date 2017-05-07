package chamomile.structures;

/**
 * Represents data for a single glomerulus' podocyte.
 *
 * @author Gomez-Avila, Rodrigo
 */

public final class PodocyteData extends AnatomicStructure {

    /**
     * Minimum valid area of a podocyte in µm (@value).
     */
    public static final double MIN_PODOCYTE_AREA = 5.0;

    /**
     * Maximum valid area of a podocyte in µm (@value).
     */
    public static final double MAX_PODOCYTE_AREA = 150.0;

    public PodocyteData(GeometryInfo geometryInfo) {
        super(geometryInfo);
    }
}
