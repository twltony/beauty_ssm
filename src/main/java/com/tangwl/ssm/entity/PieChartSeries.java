package com.tangwl.ssm.entity;

import java.util.List;

/**
 * Created by Tonny on 2017-3-29.
 */
public class PieChartSeries {
    private String name;
    private Double y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Double getValue() {
        return y;
    }

    public void setValue(Double value) {
        this.y = value;
    }
}
