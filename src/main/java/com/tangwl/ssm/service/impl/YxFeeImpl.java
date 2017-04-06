package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFeeDao;
import com.tangwl.ssm.entity.YxFee;
import com.tangwl.ssm.service.YxFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFeeImpl implements YxFeeService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFeeDao YxFeeDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFee> getAllList() {
		System.out.println(1);
		return 	YxFeeDao.queryAll();
	}

	@Override
	public List<YxFee> getQuerySumByMonth(String mm){

		return YxFeeDao.querySumByMonth(mm);
	}



}
