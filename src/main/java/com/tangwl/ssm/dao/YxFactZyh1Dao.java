package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactZyh1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YxFactZyh1Dao {

    /**
     * 获取所有总余货数据
     *
     * @return
     */
    List<YxFactZyh1> queryAll();

    /**
     * 首页总余货
     * @return
     */
    List<YxFactZyh1> queryMainSum();



    /**
     * 总余货-区域-类型
     * @return
     */
    List<YxFactZyh1> queryByAreaType();

    /**
     * 总余货-区域-类型
     * @return
     */
    List<String> queryAreas();

    /**
     * 已取证余货-类型
     * @return
     */
    List<YxFactZyh1> queryAreaSum(@Param("year") String year,@Param("type") String type);

    /**
     * 已取证余货-类型
     * @return
     */
    List<YxFactZyh1> queryProjectbyArea(@Param("area") String area,@Param("type") String type);

    /**
     * 总余货-按区域
     * @return
     */
    List<YxFactZyh1> queryZyhByArea(@Param("area") String area);



	
}
