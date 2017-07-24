package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.dao.ZzAccessDao;
import com.tangwl.ssm.entity.ZzAccess;
import com.tangwl.ssm.service.ZzAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-6-5.
 */
@Service
public class ZzAccessImpl implements ZzAccessService{
    @Autowired
    private ZzAccessDao zzAccessDao;

    @Override
    public int insert(ZzAccess record){
        return zzAccessDao.insert(record);
    }
    @Override
    public List<ZzAccess> selectAll(){
        return zzAccessDao.selectAll();
    }
}
