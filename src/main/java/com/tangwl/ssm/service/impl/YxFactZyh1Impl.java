package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactZyh1Dao;
import com.tangwl.ssm.entity.YxFactZyh1;
import com.tangwl.ssm.service.YxFactZyh1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactZyh1Impl implements YxFactZyh1Service {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactZyh1Dao YxFactZyh1Dao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactZyh1> getAllList() {
		return 	YxFactZyh1Dao.queryAll();
	}

	@Override
	public List<YxFactZyh1> getMainSum() {
		return YxFactZyh1Dao.queryMainSum();
	}



}
