package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactSalesdayDao;
import com.tangwl.ssm.entity.YxFactSalesday;
import com.tangwl.ssm.service.YxFactSalesdayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactSalesdayImpl implements YxFactSalesdayService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactSalesdayDao YxFactSalesdayDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactSalesday> getAllList() {
		System.out.println(1);
		return 	YxFactSalesdayDao.queryAll();
	}

	@Override
	public List<YxFactSalesday> getAllSum(){

		return YxFactSalesdayDao.queryAllSum();
	}
	
	

}
