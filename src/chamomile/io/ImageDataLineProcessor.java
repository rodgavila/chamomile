package chamomile.io;

import chamomile.structures.GeometryInfo;
import chamomile.structures.GlomerulusData;
import chamomile.structures.PodocyteData;
import com.google.common.base.Preconditions;
import com.google.common.io.LineProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Line processor for image data file.
 *
 * Image data file follow this convention below:
 *
 * Header
 * Glomerulus Data
 * Podocyte Data
 * ...
 *
 * Example:
 *
 * Area	Feret	FeretX	FeretY	FeretAngle	MinFeret
 * 1	8914.940	128.412	226.768	248.444	117.031	85.135
 * 2	23.243	8.696	214.429	267.119	57.529	3.673
 * 3	15.236	5.834	249.444	354.158	149.036	3.773
 * 4	24.689	6.939	259.782	270.454	35.218	5.182
 * 5	23.132	6.670	262.450	255.781	126.870	4.669
 * 6	8734.557	112.589	274.456	424.522	157.537	103.920
 * 7	55.939	10.677	304.469	422.522	141.340	7.607
 * 8	50.378	9.949	276.456	445.865	39.560	7.071
 * 9	17.682	6.552	288.795	456.537	75.256	3.941
 *
 * Glomerulus data is tell apart from podocyte data by the size of the area. If the area size falls between
 * {@link PodocyteData#MIN_PODOCYTE_AREA} and {@link PodocyteData#MAX_PODOCYTE_AREA} it's assumed that the data is of a
 * podocyte. If the area size falls between {@link GlomerulusData#MIN_GLOMERULOUS_AREA} and
 * {@link GlomerulusData#MAX_GLOMERULOUS_AREA} it's assumed that the data is of a glomerulus. If the data doesn't fall
 * between any of those the data is considered invalid.
 *
 * @author Gomez-Avila, Rodrigo
 */
public final class ImageDataLineProcessor implements LineProcessor<List<GlomerulusData>> {
    private final int mImageId;
    private boolean mValidHeader;
    private GlomerulusData mCurrentGlomerulus;
    private List<PodocyteData> mPodocytesFound;
    private List<GlomerulusData> mGlomeruliFound;
    private final boolean mRequireOnePodocytePerGlomerulus = true;

    public ImageDataLineProcessor(int imageId) {
        Preconditions.checkArgument(imageId > 0);
        mImageId = imageId;
        mValidHeader = false;
        mCurrentGlomerulus = null;
        mPodocytesFound = null;
        mGlomeruliFound = new ArrayList<>();
    }

    /**
     * Returns the processed glomerulus data.
     * @return glomerulus data
     *
     * @throws RuntimeException if a valid header wasn't found in line 1
     * @throws RuntimeException if no glomerulus information was found
     */
    public List<GlomerulusData> getResult() {
        if (!mValidHeader) {
            throw new RuntimeException("Header wasn't found");
        }

        if (mCurrentGlomerulus == null) {
            throw new RuntimeException("No glomerulus found");
        }

        // Commit the very last glomerulus
        if (mRequireOnePodocytePerGlomerulus && mPodocytesFound.isEmpty()) {
            throw new RuntimeException("Found glomerulus without any podocyte data");
        }
        mCurrentGlomerulus.setPodocytesData(mPodocytesFound);
        mGlomeruliFound.add(mCurrentGlomerulus);

        return mGlomeruliFound;
    }

    /**
     * Processes the line.
     * @param line must be either a header line or a line with valid measurement info.
     * @return {@code true}
     */
    public boolean processLine(String line) {
        if (isHeaderLine(line)) {
            if (!mValidHeader) {
                mValidHeader = true;
            } else {
                throw new RuntimeException("Double header found!");
            }
        } else {
            ParseMeasurementLineResult parseResult = processMeasurementLine(line);

            final double area = parseResult.geometryInfo.getArea();
            if (area >= PodocyteData.MIN_PODOCYTE_AREA && area <= PodocyteData.MAX_PODOCYTE_AREA) {
                if (mPodocytesFound == null) {
                    throw new RuntimeException("Podocyte information found before glomerulus information");
                }
                mPodocytesFound.add(new PodocyteData(parseResult.geometryInfo));
            } else if (area >= GlomerulusData.MIN_GLOMERULOUS_AREA && area <= GlomerulusData.MAX_GLOMERULOUS_AREA) {
                if (mCurrentGlomerulus != null) {
                    if (mRequireOnePodocytePerGlomerulus && mPodocytesFound.isEmpty()) {
                        throw new RuntimeException("Found glomerulus without any podocyte data");
                    }
                    mCurrentGlomerulus.setPodocytesData(mPodocytesFound);
                    mGlomeruliFound.add(mCurrentGlomerulus);
                }
                mCurrentGlomerulus = new GlomerulusData(parseResult.geometryInfo, mImageId, mGlomeruliFound.size() + 1);
                mPodocytesFound = new ArrayList<>();
            } else {
                throw new RuntimeException(
                        String.format("Illegal measurement found: area = %f, imageId = %d", area, mImageId));
            }
        }

        return true;
    }

    private static class ParseMeasurementLineResult {
        public final int number;
        public final GeometryInfo geometryInfo;

        private ParseMeasurementLineResult(int number, GeometryInfo geometryInfo) {
            this.number = number;
            this.geometryInfo = Preconditions.checkNotNull(geometryInfo);
        }
    }

    private static ParseMeasurementLineResult processMeasurementLine(String line) {
        Scanner scanner = new Scanner(line);

        int number = scanner.nextInt();
        double area = scanner.nextDouble();
        double feret = scanner.nextDouble();

        // Verify that the last four number are present even if don't use them to make
        // sure the format was valid
        for (int i=0; i < 4; i++) {
            scanner.nextDouble();
        }

        return new ParseMeasurementLineResult(number, new GeometryInfo(area, feret));
    }

    private static boolean isHeaderLine(String line) {
        return line.contains("Area");
    }
}