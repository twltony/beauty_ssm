package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactSalesplanDao;
import com.tangwl.ssm.entity.YxFactSalesplan;
import com.tangwl.ssm.service.YxFactSalesplanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactSalesplanImpl implements YxFactSalesplanService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactSalesplanDao yxFactSalesplanDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactSalesplan> getAllList() {
		System.out.println(1);
		return 	yxFactSalesplanDao.queryAll();
	}

	@Override
	public List<YxFactSalesplan> getAllSum(){

		return yxFactSalesplanDao.queryAllSum();
	}
	@Override
	public List<YxFactSalesplan> getYearAll(){

		return yxFactSalesplanDao.queryYearAll();
	}


	

}
