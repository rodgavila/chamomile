package chamomile.test;

import chamomile.io.ImageDataLineProcessor;
import chamomile.structures.GlomerulusData;
import chamomile.structures.PodocyteData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestImageDataLineProcessor {
    private ImageDataLineProcessor mProcessor;
    private final static double DEFAULT_DELTA = 0.0001;

    @Before
    public void setup() {
        mProcessor = new ImageDataLineProcessor(42);
    }

    @Test
    public void TestSingleGlomerulus() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("3\t40.369\t8.250\t214.762\t277.457\t104.036\t7.003");
        mProcessor.processLine("4\t16.014\t5.002\t216.430\t313.139\t143.130\t4.669");

        List<GlomerulusData> glomerulusData = mProcessor.getResult();
        assertEquals(1, glomerulusData.size());
        assertEquals(8914.940, glomerulusData.get(0).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(128.412, glomerulusData.get(0).getGeometryInfo().getFeret(), DEFAULT_DELTA);
        assertEquals(1, glomerulusData.get(0).getNumber());
        assertEquals(42, glomerulusData.get(0).getImageId());


        List<PodocyteData> podocytesData = glomerulusData.get(0).getPodocytesData();
        assertEquals(3, podocytesData.size());

        assertEquals(23.243, podocytesData.get(0).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(8.696, podocytesData.get(0).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(40.369, podocytesData.get(1).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(8.250, podocytesData.get(1).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(16.014, podocytesData.get(2).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(5.002, podocytesData.get(2).getGeometryInfo().getFeret(), DEFAULT_DELTA);
    }

    @Test
    public void TestMultipleGlomerulus() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("3\t40.369\t8.250\t214.762\t277.457\t104.036\t7.003");
        mProcessor.processLine("4\t16.014\t5.002\t216.430\t313.139\t143.130\t4.669");
        mProcessor.processLine("5\t8734.557\t112.589\t274.456\t424.522\t157.537\t103.920");
        mProcessor.processLine("6\t55.939\t10.677\t304.469\t422.522\t141.340\t7.607");
        mProcessor.processLine("7\t50.378\t9.949\t276.456\t445.865\t39.560\t7.071");
        mProcessor.processLine("8\t17.682\t6.552\t288.795\t456.537\t75.256\t3.941");
        mProcessor.processLine("9\t26.913\t7.735\t279.124\t454.869\t97.431\t4.335");
        mProcessor.processLine("10\t31.361\t9.602\t281.792\t468.209\t20.323\t5.434");
        mProcessor.processLine("11\t6584.420\t115.378\t929.414\t201.423\t94.310\t76.034");
        mProcessor.processLine("12\t18.238\t5.737\t928.747\t214.095\t54.462\t4.716");

        List<GlomerulusData> glomerulusData = mProcessor.getResult();
        assertEquals(3, glomerulusData.size());

        List<PodocyteData> podocytesData;

        // Glomerulus 1
        assertEquals(8914.940, glomerulusData.get(0).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(128.412, glomerulusData.get(0).getGeometryInfo().getFeret(), DEFAULT_DELTA);
        assertEquals(1, glomerulusData.get(0).getNumber());
        assertEquals(42, glomerulusData.get(0).getImageId());


        podocytesData = glomerulusData.get(0).getPodocytesData();
        assertEquals(3, podocytesData.size());

        assertEquals(23.243, podocytesData.get(0).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(8.696, podocytesData.get(0).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(40.369, podocytesData.get(1).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(8.250, podocytesData.get(1).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(16.014, podocytesData.get(2).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(5.002, podocytesData.get(2).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        // Glomerulus 2
        assertEquals(8734.557, glomerulusData.get(1).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(112.589, glomerulusData.get(1).getGeometryInfo().getFeret(), DEFAULT_DELTA);
        assertEquals(2, glomerulusData.get(1).getNumber());
        assertEquals(42, glomerulusData.get(1).getImageId());


        podocytesData = glomerulusData.get(1).getPodocytesData();
        assertEquals(5, podocytesData.size());

        assertEquals(55.939, podocytesData.get(0).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(10.677, podocytesData.get(0).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(50.378, podocytesData.get(1).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(9.949, podocytesData.get(1).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(17.682, podocytesData.get(2).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(6.552, podocytesData.get(2).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(26.913, podocytesData.get(3).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(7.735, podocytesData.get(3).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        assertEquals(31.361, podocytesData.get(4).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(9.602, podocytesData.get(4).getGeometryInfo().getFeret(), DEFAULT_DELTA);

        // Glomerulus 3
        assertEquals(6584.420, glomerulusData.get(2).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(115.378, glomerulusData.get(2).getGeometryInfo().getFeret(), DEFAULT_DELTA);
        assertEquals(3, glomerulusData.get(2).getNumber());
        assertEquals(42, glomerulusData.get(2).getImageId());


        podocytesData = glomerulusData.get(2).getPodocytesData();
        assertEquals(1, podocytesData.size());

        assertEquals(18.238, podocytesData.get(0).getGeometryInfo().getArea(), DEFAULT_DELTA);
        assertEquals(5.737, podocytesData.get(0).getGeometryInfo().getFeret(), DEFAULT_DELTA);
    }

    @Test(expected = RuntimeException.class)
    public void TestNoHeader() {
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("3\t40.369\t8.250\t214.762\t277.457\t104.036\t7.003");
        mProcessor.processLine("4\t16.014\t5.002\t216.430\t313.139\t143.130\t4.669");

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestNoGlomerulusDataBeforePodocyteData() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("3\t40.369\t8.250\t214.762\t277.457\t104.036\t7.003");
        mProcessor.processLine("4\t16.014\t5.002\t216.430\t313.139\t143.130\t4.669");

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestGlomerulusDataWithoutPodocyteData_BeginningOfFile() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("5\t8734.557\t112.589\t274.456\t424.522\t157.537\t103.920");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("11\t6584.420\t115.378\t929.414\t201.423\t94.310\t76.034");
        mProcessor.processLine("12\t18.238\t5.737\t928.747\t214.095\t54.462\t4.716");

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestGlomerulusDataWithoutPodocyteData() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("5\t8734.557\t112.589\t274.456\t424.522\t157.537\t103.920");
        mProcessor.processLine("11\t6584.420\t115.378\t929.414\t201.423\t94.310\t76.034");
        mProcessor.processLine("12\t18.238\t5.737\t928.747\t214.095\t54.462\t4.716");

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestGlomerulusDataWithoutPodocyteData_EndOfFile() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");
        mProcessor.processLine("5\t8734.557\t112.589\t274.456\t424.522\t157.537\t103.920");

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestDoubleHeader() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestTooSmallArea() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t8914.940\t128.412\t226.768\t248.444\t117.031\t85.135");
        mProcessor.processLine("2\t0.243\t8.696\t214.429\t267.119\t57.529\t3.673"); // 0.243 is too small

        mProcessor.getResult();
    }

    @Test(expected = RuntimeException.class)
    public void TestTooBigArea() {
        mProcessor.processLine("Area\tFeret\tFeretX\tFeretY\tFeretAngle\tMinFeret");
        mProcessor.processLine("1\t18914.940\t128.412\t226.768\t248.444\t117.031\t85.135"); // 18914.940 is too big
        mProcessor.processLine("2\t23.243\t8.696\t214.429\t267.119\t57.529\t3.673");

        mProcessor.getResult();
    }
}
