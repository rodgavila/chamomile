package chamomile.io;

import chamomile.structures.BiopsyResult;
import chamomile.structures.GlomerulusData;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for processing biopsy data information
 *
 * @author Gomez-Avila, Rodrigo
 */
public final class BiopsyDataProcessor {

    private BiopsyDataProcessor() {
        // Disallow instantiation
    }

    /**
     * Processes all image data files inside a given directory.
     * @param biopsyId id of the biopsy that the image files are related to.
     * @param directory an absolute path to the directory containing the image files.
     * @return
     */
    public static BiopsyResult parseAllDataFilesForBiopsy(String biopsyId, final File directory) throws IOException {
        Preconditions.checkArgument(directory.isDirectory());
        Preconditions.checkArgument(directory.isAbsolute());

        final File[] imageDataFiles = directory.listFiles(
                (File dir, String name) -> Files.getFileExtension(name).equals("xls"));

        BiopsyResult biopsyResult = new BiopsyResult(biopsyId);

        List<GlomerulusData> allGlomeruliData = new ArrayList<>();

        for (File file : imageDataFiles) {
            String filename = Files.getNameWithoutExtension(file.getName());
            Integer imageId = Ints.tryParse(filename);

            if (imageId == null) {
                throw new RuntimeException("Found illegal image data file: " + file.getName());
            }

            List<GlomerulusData> glomeruliData = parseImageDataFile(file, imageId);
            allGlomeruliData.addAll(glomeruliData);
        }

        biopsyResult.setGlomerulusData(allGlomeruliData);

        return biopsyResult;
    }

    private static List<GlomerulusData> parseImageDataFile(final File file, int imageId) throws IOException {
        Preconditions.checkNotNull(file);
        Preconditions.checkArgument(imageId > 0);
        return Files.readLines(file, Charset.defaultCharset(), new ImageDataLineProcessor(imageId));
    }
}
