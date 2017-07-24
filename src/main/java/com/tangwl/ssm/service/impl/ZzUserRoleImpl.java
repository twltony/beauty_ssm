package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.ZzUserDao;
import com.tangwl.ssm.dao.ZzUserRoleDao;
import com.tangwl.ssm.entity.ZzUser;
import com.tangwl.ssm.service.ZzUserRoleService;
import com.tangwl.ssm.service.ZzUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZzUserRoleImpl implements ZzUserRoleService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZzUserRoleDao zzUserRoleDao;
	@Autowired
	private RedisCache cache;


	@Override
	public int deleteByUidAndRid(String uId, String rId) {
		return zzUserRoleDao.deleteByUidAndRid(uId, rId);
	}
	@Override
	public int deleteByUid(String uId) {
		return zzUserRoleDao.deleteByUid(uId);
	}

	@Override
	public int insertByUidAndRid(String uId, String rId) {
		return zzUserRoleDao.insertByUidAndRid(uId, rId);
	}

	@Override
	public int updateByUidAndRid(String uId, String rId) {
		return zzUserRoleDao.updateByUidAndRid(uId, rId);
	}
}
