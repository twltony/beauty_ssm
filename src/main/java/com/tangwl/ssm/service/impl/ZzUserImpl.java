package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.ZzUserDao;
import com.tangwl.ssm.entity.ZzUser;
import com.tangwl.ssm.service.ZzUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZzUserImpl implements ZzUserService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZzUserDao zzUserDao;
	@Autowired
	private RedisCache cache;
	
	
//	@Override
//	public int insert(ZzRole zzRole) {
//		System.out.println(1);
//		return 	zzUserDao.insert(zzRole);
//	}

	@Override
	public List<ZzUser> getAllList(Integer pagebegin, Integer pageend) {
		return 	zzUserDao.getAllList(pagebegin,pageend);
	}

	@Override
	public int deleteByPrimaryKey(String uId) {
		return  zzUserDao.deleteByPrimaryKey(uId);
	}

	@Override
	public int insertSelective(ZzUser record){
		return zzUserDao.insertSelective(record);
	}
	@Override
	public int insert(ZzUser record){
		return zzUserDao.insert(record);
	}

	@Override
	public ZzUser selectByPrimaryKey(String uId){
		return zzUserDao.selectByPrimaryKey(uId);
	}

	@Override
	public int updateByPrimaryKeySelective(ZzUser record){
		return zzUserDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ZzUser record){
		return zzUserDao.updateByPrimaryKey(record);
	}
}
