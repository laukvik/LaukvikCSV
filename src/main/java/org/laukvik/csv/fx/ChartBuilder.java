package org.laukvik.csv.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder that produces Charts.
 */
public final class ChartBuilder {

    /**
     * The maximum number of items in pie chart.
     */
    private static final int PIE_CHART_MAX = 50;

    /**
     * Hide default constructor.
     */
    private ChartBuilder() {
    }

    /**
     * Builds a pie chart.
     *
     * @param frequencyDistributionTableView the frequencyDistributionTableView
     * @return piechart
     */
    public static PieChart buildPieChart(final FrequencyDistributionTableView frequencyDistributionTableView) {
        List<PieChart.Data> dataset = new ArrayList<>();
        int x = 0;
        for (ObservableFrequencyDistribution fd : frequencyDistributionTableView.getItems()) {
            if (fd.isSelected()) {
                dataset.add(new PieChart.Data(fd.getValue(), fd.getCount()));
            }
        }
        if (dataset.isEmpty()) {
            for (ObservableFrequencyDistribution fd : frequencyDistributionTableView.getItems()) {
                if (x < PIE_CHART_MAX) {
                    dataset.add(new PieChart.Data(fd.getValue(), fd.getCount()));
                }
                x++;
            }
        }
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(dataset);
        return new PieChart(data);
    }

}