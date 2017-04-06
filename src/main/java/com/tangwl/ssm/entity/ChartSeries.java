package com.tangwl.ssm.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Tonny on 2017-3-29.
 */
public class ChartSeries {
    private String name;
    private List<Double> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }


}
