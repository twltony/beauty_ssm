package com.tangwl.ssm.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户
 * @author  tangwl
 *
 */
public class YxFactMsalesplan {

	//排名
	private String rank;
	//区域
	private String vAreaName;

	//项目
	private String vBuildName;

	//计划
	private Double nPlan;

	//套数
	private Integer nNum;

	//面积
	private BigDecimal nArea;

	//金额
	private Double nMoney;

	//年
	private String vYyyy;

	//月
	private String vMm;

	//年月
	private String dYm;

	private Date dEtltime;

	private Double rate;

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getvAreaName() {
		return vAreaName;
	}

	public void setvAreaName(String vAreaName) {
		this.vAreaName = vAreaName;
	}

	public String getvBuildName() {
		return vBuildName;
	}

	public void setvBuildName(String vBuildName) {
		this.vBuildName = vBuildName;
	}

	public Double getnPlan() {
		return nPlan;
	}

	public void setnPlan(Double nPlan) {
		this.nPlan = nPlan;
	}

	public Integer getnNum() {
		return nNum;
	}

	public void setnNum(Integer nNum) {
		this.nNum = nNum;
	}

	public BigDecimal getnArea() {
		return nArea;
	}

	public void setnArea(BigDecimal nArea) {
		this.nArea = nArea;
	}

	public Double getnMoney() {
		return nMoney;
	}

	public void setnMoney(Double nMoney) {
		this.nMoney = nMoney;
	}

	public String getvYyyy() {
		return vYyyy;
	}

	public void setvYyyy(String vYyyy) {
		this.vYyyy = vYyyy;
	}

	public String getvMm() {
		return vMm;
	}

	public void setvMm(String vMm) {
		this.vMm = vMm;
	}

	public String getdYm() {
		return dYm;
	}

	public void setdYm(String dYm) {
		this.dYm = dYm;
	}

	public Date getdEtltime() {
		return dEtltime;
	}

	public void setdEtltime(Date dEtltime) {
		this.dEtltime = dEtltime;
	}

	@Override
	public String toString() {
		return "YxFactMsalesplanDao{" +
				"vAreaName='" + vAreaName + '\'' +
				", vBuildName='" + vBuildName + '\'' +
				", nPlan=" + nPlan +
				", nNum=" + nNum +
				", nArea=" + nArea +
				", nMoney=" + nMoney +
				", vYyyy=" + vYyyy +
				", vMm=" + vMm +
				", dYm=" + dYm +
				", dEtltime=" + dEtltime +
				'}';
	}
}
