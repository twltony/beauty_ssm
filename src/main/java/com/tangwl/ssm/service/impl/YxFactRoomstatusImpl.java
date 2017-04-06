package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactRoomstatusDao;
import com.tangwl.ssm.entity.YxFactRoomstatus;
import com.tangwl.ssm.service.YxFactRoomstatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactRoomstatusImpl implements YxFactRoomstatusService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactRoomstatusDao YxFactRoomstatusDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactRoomstatus> getAllList() {
		return 	YxFactRoomstatusDao.queryAll();
	}

	@Override
	public List<YxFactRoomstatus> getMainSum(String year) {
		return 	YxFactRoomstatusDao.queryMainSum(year);
	}





}
