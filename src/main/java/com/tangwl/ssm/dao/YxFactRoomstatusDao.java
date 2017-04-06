package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactRoomstatus;

import java.util.List;

public interface YxFactRoomstatusDao {

    /**
     * 获取所有取证余货数据
     *
     * @return
     */
    List<YxFactRoomstatus> queryAll();

    /**
     * 通过年份获取余货汇总
     *
     * @return
     */
    List<YxFactRoomstatus> queryMainSum(String year);


	
}
