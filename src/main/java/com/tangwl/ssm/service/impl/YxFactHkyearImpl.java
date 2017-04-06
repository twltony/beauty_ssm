package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactHkyearDao;
import com.tangwl.ssm.entity.YxFactHkyear;
import com.tangwl.ssm.service.YxFactHkyearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactHkyearImpl implements YxFactHkyearService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactHkyearDao YxFactHkyearDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactHkyear> getAllList() {
		System.out.println(1);
		return 	YxFactHkyearDao.queryAll();
	}

	@Override
	public List<YxFactHkyear> getQuerySumByYear(String yy){

		return YxFactHkyearDao.querySumByYear(yy);
	}

	

}
