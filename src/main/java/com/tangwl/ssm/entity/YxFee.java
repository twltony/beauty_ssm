package com.tangwl.ssm.entity;

import java.util.Date;

/**
 * Created by Tonny on 2017-3-31.
 */
public class YxFee {
    private String costcode;
    private String costname;
    private String yyyy;
    private String mm;
    private Date yyyyMm;
    private Double paylocal;
    private String unitname;


    public String getCostcode() {
        return costcode;
    }

    public void setCostcode(String costcode) {
        this.costcode = costcode;
    }

    public String getCostname() {
        return costname;
    }

    public void setCostname(String costname) {
        this.costname = costname;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public Date getYyyyMm() {
        return yyyyMm;
    }

    public void setYyyyMm(Date yyyyMm) {
        this.yyyyMm = yyyyMm;
    }

    public Double getPaylocal() {
        return paylocal;
    }

    public void setPaylocal(Double paylocal) {
        this.paylocal = paylocal;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }
}
