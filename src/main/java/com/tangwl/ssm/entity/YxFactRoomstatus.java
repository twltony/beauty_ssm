package com.tangwl.ssm.entity;

import java.util.Date;

/**
 * Created by Tonny on 2017-3-31.
 */
public class YxFactRoomstatus {
    private String projname;
    private String nump;
    private String vBuildname;
    private String vSalestatus;
    private String nYsznum;
    private Integer nNum;
    private Double nArea;
    private Double nMoney;
    private String vType;
    private String vAreaname;
    private Date nEtltime;


    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }

    public String getNump() {
        return nump;
    }

    public void setNump(String nump) {
        this.nump = nump;
    }

    public String getvBuildname() {
        return vBuildname;
    }

    public void setvBuildname(String vBuildname) {
        this.vBuildname = vBuildname;
    }

    public String getvSalestatus() {
        return vSalestatus;
    }

    public void setvSalestatus(String vSalestatus) {
        this.vSalestatus = vSalestatus;
    }

    public String getnYsznum() {
        return nYsznum;
    }

    public void setnYsznum(String nYsznum) {
        this.nYsznum = nYsznum;
    }

    public Integer getnNum() {
        return nNum;
    }

    public void setnNum(Integer nNum) {
        this.nNum = nNum;
    }

    public Double getnArea() {
        return nArea;
    }

    public void setnArea(Double nArea) {
        this.nArea = nArea;
    }

    public Double getnMoney() {
        return nMoney;
    }

    public void setnMoney(Double nMoney) {
        this.nMoney = nMoney;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getvAreaname() {
        return vAreaname;
    }

    public void setvAreaname(String vAreaname) {
        this.vAreaname = vAreaname;
    }

    public Date getnEtltime() {
        return nEtltime;
    }

    public void setnEtltime(Date nEtltime) {
        this.nEtltime = nEtltime;
    }
}
