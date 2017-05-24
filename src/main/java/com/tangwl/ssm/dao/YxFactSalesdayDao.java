package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactSalesday;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YxFactSalesdayDao {

    /**
     * 获取所有销售计划数据
     *
     * @return
     */
    List<YxFactSalesday> queryAll();

    List<YxFactSalesday> queryAllSum();

    List<YxFactSalesday> queryProjectbyArea(@Param("area") String area);


	
}
