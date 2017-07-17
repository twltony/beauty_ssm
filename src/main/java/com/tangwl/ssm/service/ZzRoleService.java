package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.ZzRole;

import java.util.List;

public interface ZzRoleService {

	int insert(ZzRole zzRole);

	List<ZzRole> getAllList();

	int deleteByPrimaryKey(String uId);

	int updateByPrimaryKey(ZzRole record);

}
