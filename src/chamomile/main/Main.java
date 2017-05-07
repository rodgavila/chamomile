package chamomile.main;


import chamomile.io.BiopsyDataProcessor;
import chamomile.structures.BiopsyResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File rootDir = new File("C:\\Users\\rodga\\Desktop\\Biopsias");

        List<BiopsyResult> allBiopsyResults = new ArrayList<>();

        for (File dir : rootDir.listFiles((File file) -> file.isDirectory())) {
            allBiopsyResults.add(BiopsyDataProcessor.parseAllDataFilesForBiopsy(dir.getName(), dir));
        }

        System.out.println(allBiopsyResults.toString());
    }
}
