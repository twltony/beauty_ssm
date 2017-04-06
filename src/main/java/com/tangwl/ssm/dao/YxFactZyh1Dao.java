package com.tangwl.ssm.dao;

import com.tangwl.ssm.entity.YxFactZyh1;

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



	
}
