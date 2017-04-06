package com.tangwl.ssm.entity;

/**
 * Created by Tonny on 2017-3-29.
 */
public class ColumnChart {
    private String chartTitle;
    private ChartSeries chartSeries;

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public ChartSeries getChartSeries() {
        return chartSeries;
    }

    public void setChartSeries(ChartSeries chartSeries) {
        this.chartSeries = chartSeries;
    }
}
