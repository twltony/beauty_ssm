package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactHkyear;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 通过区域查
     * @param area
     * @return
     */
    List<YxFactHkyear> queryProjectByArea(@Param("area") String area);




	
}
