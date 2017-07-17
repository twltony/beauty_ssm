package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.ZzRoleDao;
import com.tangwl.ssm.entity.ZzRole;
import com.tangwl.ssm.service.ZzRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZzRoleImpl implements ZzRoleService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZzRoleDao ZzRoleDao;
	@Autowired
	private RedisCache cache;

	@Override
	public List<ZzRole> getAllList(){
		return ZzRoleDao.getAllList();
	}
	@Override
	public int updateByPrimaryKey(ZzRole record){
		return ZzRoleDao.updateByPrimaryKey(record);
	}

	@Override
	public int insert(ZzRole zzRole) {
		return 	ZzRoleDao.insert(zzRole);
	}

	@Override
	public int deleteByPrimaryKey(String uId) {
		return  ZzRoleDao.deleteByPrimaryKey(uId);
	}




}
