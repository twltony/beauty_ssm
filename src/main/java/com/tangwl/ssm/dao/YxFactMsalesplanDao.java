package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactMsalesplan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YxFactMsalesplanDao {

    /**
     * 获取所有销售计划数据
     *
     * @return
     */
    List<YxFactMsalesplan> queryAll();

    List<YxFactMsalesplan> queryMonthSalesplanByYear();

    List<YxFactMsalesplan> querySalesRate(String year,String month);


	
}
