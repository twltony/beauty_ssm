package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.ZzUser;

import java.util.List;

public interface ZzUserService {

	List<ZzUser> getAllList(Integer pagebegin, Integer pageend);

	List<ZzUser> getListRoleByName(String username);

	int deleteByPrimaryKey(String uId);

	int insertSelective(ZzUser record);

	int insert(ZzUser record);

	ZzUser selectByPrimaryKey(String uId);

	ZzUser selectByUserName(String username);

	int updateByPrimaryKeySelective(ZzUser record);

	int updateByPrimaryKey(ZzUser record);

}
