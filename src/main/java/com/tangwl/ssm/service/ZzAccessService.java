package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.ZzAccess;

import java.util.List;

/**
 * Created by Administrator on 2017-6-5.
 */
public interface ZzAccessService {

    int insert(ZzAccess zzAccess);

    List<ZzAccess> selectAll();
}
