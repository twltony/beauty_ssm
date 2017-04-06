package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFee;

import java.util.List;

public interface YxFeeDao {

    /**
     * 获取所有销售计划数据
     *
     * @return
     */
    List<YxFee> queryAll();

    /**
     * 通过月份查营销汇总
     * @param mm
     * @return
     */
    List<YxFee> querySumByMonth(String mm);

    /**
     * 营销首页汇总
     * @return
     */
    List<YxFee> queryLatestSum();


	
}
