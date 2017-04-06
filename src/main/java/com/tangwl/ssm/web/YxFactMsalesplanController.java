package com.tangwl.ssm.web;

import com.sun.jdi.DoubleValue;
import com.tangwl.ssm.entity.*;
import com.tangwl.ssm.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

@Controller
@RequestMapping("/salesplan")
public class YxFactMsalesplanController {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YxFactMsalesplanService yxFactMsalesplanService;
	@Autowired
	private YxFactSalesplanService yxFactSalesplanService;
	@Autowired
	private YxFactSalesdayService yxFactSalesdayService;
	@Autowired
	private YxFeeService yxFeeService;
	@Autowired
	private YxFactHkyearService yxFactHkyearService;
	@Autowired
	private YxFactZyh1Service yxFactZyh1Service;
	@Autowired
	private YxFactRoomstatusService yxFactRoomstatusService;

	private String dataMonth;
	private String dataYear;

	@ResponseBody
	@RequestMapping(value = "/getAllSalesplan", method = RequestMethod.GET)
	public Map<String, Object> getAllSalesplan() {
		LOG.info("invoke----------/user/list");
		Map<String ,Object> map = new HashMap<>();
		List<YxFactMsalesplan> list = yxFactMsalesplanService.getAllList();
		map.put("status","success");
		map.put("data",list);
		return map;
	}


	@ResponseBody
	@RequestMapping(value = "/getMonthSalesplanByYear", method = RequestMethod.GET)
	public Map<String, Object> list(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin","*");
		LOG.info("invoke----------/user/list");
		Map<String ,Object> map = new HashMap<>();

		//数据年份/月份
		Map<String,Date> salesDateMap = new HashMap<>();
		List<String> yearList = new ArrayList<>();
		this.dataYear = "2017";
		this.dataMonth = "4";
		yearList.add("2016");
		yearList.add("2017");

		//营销情况
		List<YxFactSalesplan> salePlanList = yxFactSalesplanService.getAllSum();
		List<String> salesPlanstatusList = new ArrayList<>();
		Map<String,Double> salesYearMap = new HashMap<>();
		//年度、月度销售情况
		for(YxFactSalesplan yfs : salePlanList){
			if(yfs.getvBuname().contains("合计")){
				Double fmYearPlan =Double.parseDouble(formatUpperDouble(yfs.getnYearPlan()/10000));
				Double fmYearMoney =Double.parseDouble(formatUpperDouble(yfs.getnYearMoney()/100000000));
				salesYearMap.put("nYearPlan",fmYearPlan);
				salesYearMap.put("nYearMoney",fmYearMoney);
				salesYearMap.put("nYearpercent",Double.parseDouble(formatUpperDouble(fmYearMoney/fmYearPlan*100)));
				salesYearMap=filterMonthData(salesYearMap,dataMonth,yfs);
			}
		}
		//日销售情况
		List<YxFactSalesday> salesDayList = yxFactSalesdayService.getAllSum();
		for(YxFactSalesday yfsd: salesDayList){
			if(yfsd.getvAreaname().contains("合计")){
				salesYearMap.put("nDaySales",Double.parseDouble(formatUpperDouble(yfsd.getnXsamount())));
				salesDateMap.put("nDaySalesTime",(yfsd.getEtlTime()));
			}
		}
		//营销费用
		List<YxFee> yxFeesSum =yxFeeService.getQuerySumByMonth("%"+dataMonth+"%");
		//System.out.println(formatUpperDouble(yxFeesSum.get(0).getPaylocal()/10000));
		map.put("yxFee", formatUpperDouble(yxFeesSum.get(0).getPaylocal()/10000));

		//回款未回款
		List<YxFactHkyear> yxFactHkyearSum = yxFactHkyearService.getQuerySumByYear("%"+dataYear+"%");
		for(YxFactHkyear yfhs: yxFactHkyearSum){
			if(yfhs.getvBuname().contains("合计")){
					System.out.println(formatUpperDouble(yfhs.getnMhkmoney()/10000));
				map.put("hkMonth", formatDouble(yfhs.getnMhkmoney()/10000));
				map.put("hkYear", formatDouble(yfhs.getnHkmoney()/10000));
			}
		}

		//余货
		List<YxFactZyh1> yxFactZyh1Sum = yxFactZyh1Service.getMainSum();
		map.put("yhZts",formatUpperDouble(yxFactZyh1Sum.get(0).getnZnum()));
		map.put("yhZmj",formatUpperDouble(yxFactZyh1Sum.get(0).getnZarea()/10000));
		map.put("yhZyghz",formatDouble(yxFactZyh1Sum.get(0).getnZmoney()/10000));

		//已取证余货
		List<YxFactRoomstatus> yxFactRoomstatusSum = yxFactRoomstatusService.getMainSum("%"+dataYear+"%");
		//饼图
		PieChart pieChart = new PieChart();
		map.put("qzYhSumList",yxFactRoomstatusSum);
		Map<String ,Object> mapPie = new HashMap<>();
		List<Object> pieSeries = new ArrayList<>();
		for(YxFactRoomstatus yfrs: yxFactRoomstatusSum){
			Double sumArea = 1.0;
			if(yfrs.getvType().contains("合计")){
				map.put("qzYhts", formatDouble(yfrs.getnNum()));
				sumArea = yfrs.getnArea()/10000;
				map.put("qzYhmj", formatDouble(yfrs.getnArea()/10000));
				map.put("qzYhyghz", formatDouble(yfrs.getnMoney()/100000000));
			}else{
				PieChartSeries chartSeriesPie = new PieChartSeries();
				chartSeriesPie.setName(yfrs.getvType());
				chartSeriesPie.setValue(yfrs.getnArea()/sumArea);
				pieSeries.add(chartSeriesPie);
				//chartSeriesPie.setData();
			}
		}
		mapPie.put("chartTitle","");
		mapPie.put("chartSeriesName","面积");
		mapPie.put("chartSeries",pieSeries);

		//柱状图
		ColumnChart columnChart = new ColumnChart();
		ChartSeries chartSeriesPlan = new ChartSeries();
		ChartSeries chartSeriesSale = new ChartSeries();
		List<YxFactMsalesplan> mSalesPlanlist = yxFactMsalesplanService.getMonthSalesplanByYear();
		Map<String ,Object> mapColumn = new HashMap<>();
		List<Double> nPlanList = new ArrayList<>();
		List<Double> nMoneyList = new ArrayList<>();
		for(int i=0; i<mSalesPlanlist.size();i++){
			Double nPlan=mSalesPlanlist.get(i).getnPlan()/10000;
			nPlanList.add(i,Double.parseDouble(formatDouble(nPlan)));
			Double nMoney=mSalesPlanlist.get(i).getnMoney()/100000000;
			nMoneyList.add(i,Double.parseDouble(formatDouble(nMoney)));
		}
		chartSeriesPlan.setName("计划金额");
		chartSeriesPlan.setData(nPlanList);
		chartSeriesSale.setName("销售金额");
		chartSeriesSale.setData(nMoneyList);
		columnChart.setChartTitle("点击该颜色数字可下钻到报表\t数据截止日期：");

		List<Object> series = new ArrayList<>();
		series.add(chartSeriesPlan);
		series.add(chartSeriesSale);

		mapColumn.put("chartTitle",columnChart.getChartTitle());
		mapColumn.put("chartData",mSalesPlanlist);
		mapColumn.put("chartSeries",series);

		//销售排名
		List<YxFactMsalesplan> salePlanListRate = yxFactMsalesplanService.getSalesRate("%"+dataYear+"%",dataMonth);
		List<Map> rankList = new ArrayList<>();
		for(int i=0; i<10;i++){
			Map<String,Object> rmap = new HashedMap();
			rmap.put("rank",salePlanListRate.get(i).getRank());
			rmap.put("name",salePlanListRate.get(i).getvBuildName());
			rmap.put("rate",salePlanListRate.get(i).getRate());
			rankList.add(rmap);
		}

		map.put("status","success");
		map.put("salesStatus",salesYearMap);
		map.put("salesDates",salesDateMap);
		map.put("yearList",yearList);
		map.put("currentDataYear",dataYear);
		map.put("currentDataMonth",dataMonth);
		map.put("rankTopTen",rankList);
		map.put("columnChart",mapColumn);
		map.put("pieChart",mapPie);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/getSalesStatusMonth", method = RequestMethod.POST)
	public Map<String, Object> getSalesStatusMonth(HttpServletResponse response){
		Map<String,Object> salesStatusMap = new HashedMap();
		List<YxFactSalesplan> salePlanList = yxFactSalesplanService.getAllSum();
		for(YxFactSalesplan yfs : salePlanList){
			Map<String,Object> salesStatu = new HashedMap();
			filterSalesStatusMonthData(dataMonth,yfs);
		}

		return salesStatusMap;
	}

	private static String formatDouble(double d) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		// 保留两位小数
		nf.setMaximumFractionDigits(1);
		// 如果不需要四舍五入，可以使用RoundingMode.DOWN
		nf.setRoundingMode(RoundingMode.HALF_UP);
		return nf.format(d);
	}
	private static String formatUpperDouble(double d) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		// 保留两位小数
		nf.setMaximumFractionDigits(0);
		// 如果不需要四舍五入，可以使用RoundingMode.DOWN
		nf.setRoundingMode(RoundingMode.HALF_UP);
		String result = nf.format(d);
		if (result.contains(",")){
			result = result.replace(",","");
		}
		return result;
	}

	private Map<String,Double> filterMonthData(Map<String,Double> salesYearMap, String dataMonth, YxFactSalesplan yfs){
		Double fmMonthPlan=null;
		Double fmMonthMoney= null;
		switch (dataMonth){
			case "1":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0){
						salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "2":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnFebPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnFebMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
						salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "3":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnMarPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnMarMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "4":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnAprPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnAprMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "5":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnMayPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnMayMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "6":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnJunePlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnJuneMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "7":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnJulyPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnJulyMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "8":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnAugPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnAugMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "9":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnSeptPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnSeptMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "10":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnOctPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnOctMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "11":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnNovPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnNovMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
			case "12":
					fmMonthPlan= Double.parseDouble(formatUpperDouble(yfs.getnDecPlan()/10000));
					fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnDecMoney()/100000000));
					salesYearMap.put("nMonthPlan",fmMonthPlan);
					salesYearMap.put("nMonthMoney",fmMonthMoney);
					if(fmMonthPlan!=0.0) {
					salesYearMap.put("nMonthPrecent",Double.parseDouble(formatUpperDouble(fmMonthMoney/fmMonthPlan*100)));
					}else{salesYearMap.put("nMonthPrecent",0.0);}
				break;
		}
		return salesYearMap;
	}

	private Map<String,Object> filterSalesStatusMonthData(String dataMonth, YxFactSalesplan yfs){
		Map<String,Object> salesStatu = new HashedMap();
		switch (dataMonth){
			case "1":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "2":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "3":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "4":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "5":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "6":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "7":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "8":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "9":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "10":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "11":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
			case "12":
				salesStatu.put("project",yfs.getvBuname());
				salesStatu.put("salesPlan",Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
				salesStatu.put("salesNum",Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
				salesStatu.put("realArea",Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
				salesStatu.put("realMoney",Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
				salesStatu.put("percent",Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney()))/Double.parseDouble(formatUpperDouble(yfs.getnJanPlan()))*100)));

				break;
		}
		return salesStatu;
	}
}
