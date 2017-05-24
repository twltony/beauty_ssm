package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactSalesplan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YxFactSalesplanDao {

    /**
     * 获取所有销售计划数据
     *
     * @return
     */
    List<YxFactSalesplan> queryAll();

    List<YxFactSalesplan> queryAllSum();
    List<YxFactSalesplan> queryYearAll();
    List<YxFactSalesplan> queryYearByArea(@Param("area") String area);

    List<YxFactSalesplan> querySalesRate();


	
}
