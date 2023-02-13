package com.dstreltsov.testtask.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Utility class to operate with charts.
 *
 * @author dstreltsov
 * @since 12.02.2023
 */
public class ChartUtil {

    /**
     * Creates a XY chart.
     *
     * @param dataset data, returned from audit table in XYSeriesCollection format.
     * @return JFreeChart containing result set from audit table.
     */
    public static JFreeChart createXYChart(XYSeriesCollection dataset) {
        return ChartFactory.createXYLineChart(
                "Votes history",
                "",
                "Votes number",
                dataset,
                PlotOrientation.VERTICAL,
                false, false, false
        );
    }

    /**
     * Relying on histMap data from audit table generates XY dataset.
     *
     * @param histMap - result map from audit table.
     * @return dataset to be used in JFreeChart creation.
     */
    public static XYSeriesCollection createChartDataset(List<Integer> histMap) {
        XYSeries xySeries = new XYSeries("votes");
        for (int i = 0; i < histMap.size(); i++) {
            xySeries.add(i + 1, histMap.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySeries);

        return dataset;
    }

    /**
     * Converts chart to base64 string.
     *
     * @param chart XY chart
     * @return base64 string.
     * @throws IOException if unexpected I/O error happens.
     */
    public static String chartToBase64(JFreeChart chart) throws IOException {
        File file = new File(UUID.randomUUID().toString());

        ChartUtilities.saveChartAsPNG(file, chart, 640, 480);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] arr = new byte[(int) file.length()];
            fis.read(arr);

            return "data:image/png;base64," + Base64.getEncoder().encodeToString(arr);
        }
    }
}
