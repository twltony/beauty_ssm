package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactSalesplan;

import java.util.List;

public interface YxFactSalesplanDao {

    /**
     * 获取所有销售计划数据
     *
     * @return
     */
    List<YxFactSalesplan> queryAll();

    List<YxFactSalesplan> queryAllSum();

    List<YxFactSalesplan> querySalesRate();


	
}
