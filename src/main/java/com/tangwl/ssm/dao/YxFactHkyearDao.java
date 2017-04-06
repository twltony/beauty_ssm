package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactHkyear;

import java.util.List;

public interface YxFactHkyearDao {

    /**
     * 获取所有回款汇总
     *
     * @return
     */
    List<YxFactHkyear> queryAll();

    /**
     * 通过年份查回款汇总
     * @param yy
     * @return
     */
    List<YxFactHkyear> querySumByYear(String yy);




	
}
