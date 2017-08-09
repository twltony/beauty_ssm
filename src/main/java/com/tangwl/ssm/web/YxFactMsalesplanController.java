package com.tangwl.ssm.web;


import com.tangwl.ssm.entity.*;
import com.tangwl.ssm.service.*;
import com.tangwl.ssm.util.AccessUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
    @Autowired
    private YxFactSalesmonthService yxFactSalesmonthService;
    @Autowired
    private ZzAccessService zzAccessService;


    private String dataMonth;
    private String dataLastMonth;
    private String dataYear;


    public int setAccessStatus(String username, String imei, String oldDate) throws Exception {

        DateFormat format = new SimpleDateFormat();
        Date dt = null;
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(oldDate);
            dt = new Date(lt);
            //dt = format.parse(oldDate);
        } catch (Exception pe) {
            pe.printStackTrace();
        }

        ZzAccess za = new ZzAccess();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().split("-")[0];
        za.setId(id);
        za.setUsername(username);
        za.setImei(imei);
        za.setAccesstime1(new Date());
        za.setAccesstime2(dt);
        za.setDevicename("device");
        return zzAccessService.insert(za);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllSalesplan", method = RequestMethod.GET)
    public Map<String, Object> getAllSalesplan(HttpServletRequest request) {
        LOG.info("invoke----------/user/list");
        String time = request.getParameter("time");
        String username = request.getParameter("username");
        Map<String, Object> map = new HashMap<>();
        List<YxFactMsalesplan> list = yxFactMsalesplanService.getAllList();
        map.put("status", "success");
        map.put("data", list);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/getMonthSalesplanByYear", method = RequestMethod.GET)
    public Map<String, Object> list(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        LOG.info("invoke----------/user/list");
        String time = request.getParameter("time");
        String username = request.getParameter("username");

        Map<String, Object> map = new HashMap<>();

        //数据年份/月份
        Map<String, Date> salesDateMap = new HashMap<>();
        List<String> yearList = new ArrayList<>();
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        SimpleDateFormat sdfyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfmm = new SimpleDateFormat("MM");
        this.dataYear = sdfyy.format(d);
        this.dataMonth = sdfmm.format(d);
        this.dataLastMonth = sdfmm.format(cal.getTime());
        yearList.add("2016");
        yearList.add("2017");

        //营销情况
        List<YxFactSalesplan> salePlanList = yxFactSalesplanService.getAllSum();
        List<String> salesPlanstatusList = new ArrayList<>();
        Map<String, Double> salesYearMap = new HashMap<>();
        //年度、月度销售情况
        Double fmYearPlan = Double.parseDouble(formatUpperDouble(salePlanList.get(0).getnYearPlan() / 10000));
        Double fmYearMoney = Double.parseDouble(formatUpperDouble(salePlanList.get(0).getnYearMoney() / 10000));
        salesYearMap.put("nYearPlan", fmYearPlan);
        salesYearMap.put("nYearMoney", fmYearMoney);
        salesYearMap.put("nYearpercent", Double.parseDouble(formatUpperDouble(fmYearMoney / fmYearPlan * 100)));
        salesYearMap = filterMonthData(salesYearMap, dataMonth, salePlanList.get(0));

        //日销售情况
        List<YxFactSalesday> salesDayList = yxFactSalesdayService.getAllSum();
        for (YxFactSalesday yfsd : salesDayList) {
            if (yfsd.getvAreaname().contains("合计")) {
                salesYearMap.put("nDaySales", Double.parseDouble(formatUpperDouble(yfsd.getnXsamount())));
                salesDateMap.put("nDaySalesTime", (yfsd.getEtlTime()));
            }
        }
        //营销费用
        List<YxFee> yxFeesSum = null;
        try {
            yxFeesSum = yxFeeService.getMonthSum("%" + dataMonth + "%");
        } catch (Exception e) {
            YxFee yf = new YxFee();
            List<YxFee> tempList = new ArrayList<>();
            tempList.add(yf);
            yxFeesSum = tempList;
        }
        //System.out.println(formatUpperDouble(yxFeesSum.get(0).getPaylocal()/10000));
        map.put("yxFee", formatUpperDouble(yxFeesSum.get(0).getFCPAYLOCAL() / 10000));

        //回款未回款
        List<YxFactHkyear> yxFactHkyearSum = yxFactHkyearService.getQuerySumByYear("%" + dataYear + "%");
        for (YxFactHkyear yfhs : yxFactHkyearSum) {
            if (yfhs.getvBuname().contains("合计")) {
                if (null != yfhs.getnMhkmoney()) {
                map.put("hkMonth", formatDouble(yfhs.getnMhkmoney() / 10000));
                }
                if(null!=yfhs.getnHkmoney()  ){

                map.put("hkYear", formatDouble(yfhs.getnHkmoney() / 10000));
                }
            }
        }


//		List<YxFactZyh1> yxFactZyh1Sum = yxFactZyh1Service.getMainSum();


        //已取证余货 和 余货
        List<YxFactZyh1> yxFactZyh1ListSum = yxFactZyh1Service.getMainSum();
        //List<YxFactRoomstatus> yxFactRoomstatusSum = yxFactRoomstatusService.getMainSum("%"+dataYear+"%");
        //饼图
        PieChart pieChart = new PieChart();
        map.put("qzYhSumList", yxFactZyh1ListSum);
        Map<String, Object> mapPie = new HashMap<>();
        List<Object> pieSeries = new ArrayList<>();
        for (YxFactZyh1 yfrs : yxFactZyh1ListSum) {
            Double sumArea = 1.0;
            if (yfrs.getvType().contains("合计")) {
                sumArea = yfrs.getnYczarea();
                map.put("yhZts", formatUpperDouble(yfrs.getnZnum()));
                map.put("yhZmj", formatUpperDouble(yfrs.getnZarea() / 10000));
                map.put("yhZyghz", formatDouble(yfrs.getnZmoney() / 10000));
                map.put("qzYhts", formatDouble(yfrs.getnYcznum()));
                map.put("qzYhmj", formatDouble(yfrs.getnYczarea() / 10000));
                map.put("qzYhyghz", formatDouble(yfrs.getnYczmoney() / 10000));
            }
            if (!yfrs.getvType().contains("合计")) {
                PieChartSeries chartSeriesPie = new PieChartSeries();
                chartSeriesPie.setName(yfrs.getvType());
                chartSeriesPie.setValue(yfrs.getnYczarea() / sumArea);
                pieSeries.add(chartSeriesPie);
            }
        }
        mapPie.put("chartTitle", "");
        mapPie.put("chartSeriesName", "面积");
        mapPie.put("chartSeries", pieSeries);

        //柱状图
        ColumnChart columnChart = new ColumnChart();
        ChartSeries chartSeriesPlan = new ChartSeries();
        ChartSeries chartSeriesSale = new ChartSeries();
        List<YxFactMsalesplan> mSalesPlanlist = yxFactMsalesplanService.getMonthSalesplanByYear();
        Map<String, Object> mapColumn = new HashMap<>();
        List<Double> nPlanList = new ArrayList<>();
        List<Double> nMoneyList = new ArrayList<>();
        for (int i = 0; i < mSalesPlanlist.size(); i++) {
            Double nPlan = mSalesPlanlist.get(i).getnPlan() / 10000;
            nPlanList.add(i, Double.parseDouble(formatDouble(nPlan)));
            Double nMoney = mSalesPlanlist.get(i).getnMoney() / 100000000;
            nMoneyList.add(i, Double.parseDouble(formatDouble(nMoney)));
        }
        chartSeriesPlan.setName("计划金额");
        chartSeriesPlan.setData(nPlanList);
        chartSeriesSale.setName("销售金额");
        chartSeriesSale.setData(nMoneyList);
        columnChart.setChartTitle("点击该颜色数字可下钻到报表\t数据截止日期：");

        List<Object> series = new ArrayList<>();
        series.add(chartSeriesPlan);
        series.add(chartSeriesSale);

        mapColumn.put("chartTitle", columnChart.getChartTitle());
        mapColumn.put("chartData", mSalesPlanlist);
        mapColumn.put("chartSeries", series);

        //销售排名
        List<YxFactMsalesplan> salePlanListRate = yxFactMsalesplanService.getSalesRate("%" + dataYear + "%", dataMonth);
        List<Map> rankList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> rmap = new HashedMap();
            rmap.put("rank", salePlanListRate.get(i).getRank());
            rmap.put("name", salePlanListRate.get(i).getvBuildName());
            rmap.put("rate", salePlanListRate.get(i).getRate());
            rankList.add(rmap);
        }

        map.put("status", "success");
        map.put("salesStatus", salesYearMap);
        map.put("salesDates", salesDateMap);
        map.put("yearList", yearList);
        map.put("currentDataYear", dataYear);
        map.put("currentDataMonth", dataMonth);
        map.put("rankTopTen", rankList);
        map.put("columnChart", mapColumn);
        map.put("pieChart", mapPie);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getMarketMonth", method = RequestMethod.POST)
    public Map<String, Object> getMarketMonth(HttpServletResponse response) {
        Map<String, Object> salesStatusMap = new HashedMap();
        List<YxFactSalesplan> salePlanList = yxFactSalesplanService.getAllSum();
        for (YxFactSalesplan yfs : salePlanList) {
            Map<String, Object> salesStatu = new HashedMap();
            filterSalesStatusMonthData(dataMonth, yfs);
        }

        return salesStatusMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getMarketYear", method = RequestMethod.GET)
    public Map<String, Object> getMarketYear(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> salesStatusMap = new HashedMap();
        List<YxFactSalesplan> salePlanList = yxFactSalesplanService.getYearAll();
        List<YxFactSalesplan> salePlanListSum = yxFactSalesplanService.getAllSum();
        if (salePlanList.size() > 0 && salePlanListSum.size() > 0) {
            salePlanList.add(salePlanListSum.get(0));
        }
        salesStatusMap.put("yearList", salePlanList);

        return salesStatusMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getMarketYearByArea", method = RequestMethod.GET)
    public List<YxFactSalesplan> getMarketYearByArea(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String area = request.getParameter("area");
        if (area.equals("") || area.equals("全部")) {
            area = "%";
        } else {
            area = "%" + area + "%";
        }
        List<YxFactSalesplan> salePlanList = yxFactSalesplanService.getYearByArea(area);

        return salePlanList;
    }


    @ResponseBody
    @RequestMapping(value = "/getareamonthstatus")
    public List<YxFactMsalesplan> getMonthStatus(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        List<YxFactMsalesplan> salesMonthList = yxFactMsalesplanService.getAreaMonthSales("%" + year + "%", "%" + month + "%");
        return salesMonthList;
    }

    //获取日销情况
    @ResponseBody
    @RequestMapping(value = "/getareadaystatus")
    public List<YxFactSalesday> getDayStatus(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<YxFactSalesday> salesMonthList = yxFactSalesdayService.getAllSum();
        return salesMonthList;
    }

    //销售费用-每月汇总
    @ResponseBody
    @RequestMapping(value = "/getAllMonthFeeSum")
    public List<YxFee> getAllMonthFeeSum(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<YxFee> salesMonthFeeSum = yxFeeService.getAllMonthSum();
        return salesMonthFeeSum;
    }

    //销售费用
    @ResponseBody
    @RequestMapping(value = "/getMonthFee")
    public List<YxFee> getMonthFee(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String mm = request.getParameter("month");
        mm = "%" + mm + "%";
        List<YxFee> salesMonthFee = yxFeeService.getListByMonth(mm);
        List<YxFee> salesMonthFeeSum = yxFeeService.getMonthSum(mm);
        salesMonthFeeSum.get(0).setCostname("合计");
        salesMonthFee.add(salesMonthFeeSum.get(0));

        return salesMonthFee;
    }

    //集团回款情况表
    @ResponseBody
    @RequestMapping(value = "/getYearBack")
    public List<YxFactHkyear> getYearBack(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String year = request.getParameter("year");
        year = "%2017%";
        //year = "%"+year+"%";
        List<YxFactHkyear> monthBackList = yxFactHkyearService.getQuerySumByYear(year);
        return monthBackList;
    }

    //区域回款情况表
    @ResponseBody
    @RequestMapping(value = "/getAreaBack")
    public List<YxFactHkyear> getAreaBack(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String area = request.getParameter("area");
        List<YxFactHkyear> monthBackList = yxFactHkyearService.getProjectByArea(area);
        return monthBackList;
    }

    //集团总余货
    @ResponseBody
    @RequestMapping(value = "/getZyh")
    public List<YxFactZyh1> getZyh(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Object> resultList = new ArrayList<>();

        List<YxFactZyh1> zyhList = yxFactZyh1Service.getByAreaType();
//		List<String> areaList = yxFactZyh1Service.getAreas();
//		areaList.add("总合计");
//
//		for(int i=0;i<areaList.size();i++){
//			Map<String,Object> map = new HashedMap();
//			List<YxFactZyh1> zyhListTemp = new ArrayList();
//			String vBuname = "";
//			for(YxFactZyh1 zyh: zyhList){
//				if(zyh.getvBuname().equals(areaList.get(i))){
//					vBuname = zyh.getvBuname();
//					zyhListTemp.add(zyh);
//				}
//			}
//			map.put("vBuname",vBuname);
//			map.put("zyhListTemp",zyhListTemp);
//			resultList.add(map);
//		}
        return zyhList;
    }


    //区域总余货
    @ResponseBody
    @RequestMapping(value = "/getZyhByArea")
    public List<YxFactZyh1> getZyhByArea(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String area = request.getParameter("area");

        List<YxFactZyh1> zyhList = yxFactZyh1Service.getZyhByArea(area);
        return zyhList;
    }


    //集团余货区域分类汇总
    @ResponseBody
    @RequestMapping(value = "/getZyhAreaSum")
    public List<YxFactZyh1> getZyhAreaSum(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String type = request.getParameter("type");
        if (type == "" || type.equals("全部")) {
            type = "%";
        } else {
            type = "%" + type + "%";
        }
        List<YxFactZyh1> getZyhAreaSumList = yxFactZyh1Service.getAreaSum("", type);

        return getZyhAreaSumList;
    }

    //区域取证余货
    @ResponseBody
    @RequestMapping(value = "/getQzyhByArea")
    public List<YxFactZyh1> getQzyhByArea(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String area = request.getParameter("area");
        String type = request.getParameter("type");
        if (type.equals("") || type.equals("全部")) {
            type = "%";
        } else {
            type = "%" + type + "%";
        }
        List<YxFactZyh1> getZyhAreaSumList = yxFactZyh1Service.getProjectbyArea(area, type);

        return getZyhAreaSumList;
    }

    //销售排名
    @ResponseBody
    @RequestMapping(value = "/getSalesRankDetail")
    public List<YxFactMsalesplan> getSalesRankDetail(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        year = "%" + year + "%";
        month = "%" + month + "%";
        List<YxFactMsalesplan> getSalesRankList = yxFactMsalesplanService.getSalesRate(year, month);

        return getSalesRankList;
    }

    //项目月销售情况
    @ResponseBody
    @RequestMapping(value = "/getProjectMonthSales")
    public List<YxFactMsalesplan> getProjectMonthSales(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String area = request.getParameter("area");
        String month = request.getParameter("month");
        List<YxFactMsalesplan> projectSalesList = yxFactMsalesplanService.getProjectbyAreaMonth(area, month);

        return projectSalesList;
    }

    //项目日销售情况
    @ResponseBody
    @RequestMapping(value = "/getProjectDaySales")
    public List<YxFactSalesday> getProjectDaySales(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String area = request.getParameter("area");
        List<YxFactSalesday> projectSalesList = yxFactSalesdayService.getProjectbyArea(area);

        return projectSalesList;
    }

    //获取区域列表
    @ResponseBody
    @RequestMapping(value = "/getAreaList")
    public List<String> getAreaList(HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<String> areaList = yxFactZyh1Service.getAreas();

        return areaList;
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
        if (result.contains(",")) {
            result = result.replace(",", "");
        }
        return result;
    }

    private Map<String, Double> filterMonthData(Map<String, Double> salesYearMap, String dataMonth, YxFactSalesplan yfs) {
        Double fmMonthPlan = null;
        Double fmMonthMoney = null;
        switch (dataMonth) {
            case "01":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnJanPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnJanMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "02":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnFebPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnFebMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "03":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnMarPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnMarMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "04":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnAprPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnAprMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "05":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnMayPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnMayMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "06":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnJunePlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatUpperDouble(yfs.getnJuneMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "07":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnJulyPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnJulyMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "08":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnAugPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnAugMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "09":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnSeptPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnSeptMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "10":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnOctPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnOctMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "11":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnNovPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnNovMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
            case "12":
                fmMonthPlan = Double.parseDouble(formatUpperDouble(yfs.getnDecPlan() / 10000));
                fmMonthMoney = Double.parseDouble(formatDouble(yfs.getnDecMoney() / 10000));
                salesYearMap.put("nMonthPlan", fmMonthPlan);
                salesYearMap.put("nMonthMoney", fmMonthMoney);
                if (fmMonthPlan != 0.0) {
                    salesYearMap.put("nMonthPrecent", Double.parseDouble(formatUpperDouble(fmMonthMoney / fmMonthPlan * 100)));
                } else {
                    salesYearMap.put("nMonthPrecent", 0.0);
                }
                break;
        }
        return salesYearMap;
    }

    private Map<String, Object> filterSalesStatusMonthData(String dataMonth, YxFactSalesplan yfs) {
        Map<String, Object> salesStatu = new HashedMap();
        switch (dataMonth) {
            case "1":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "2":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "3":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "4":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "5":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "6":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "7":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "8":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "9":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "10":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "11":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
            case "12":
                salesStatu.put("project", yfs.getvBuname());
                salesStatu.put("salesPlan", Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())));
                salesStatu.put("salesNum", Double.parseDouble(formatUpperDouble(yfs.getnJanNum())));
                salesStatu.put("realArea", Double.parseDouble(formatUpperDouble(yfs.getnJanArea())));
                salesStatu.put("realMoney", Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())));
                salesStatu.put("percent", Double.parseDouble(formatUpperDouble(Double.parseDouble(formatUpperDouble(yfs.getnJanMoney())) / Double.parseDouble(formatUpperDouble(yfs.getnJanPlan())) * 100)));

                break;
        }
        return salesStatu;
    }
}
