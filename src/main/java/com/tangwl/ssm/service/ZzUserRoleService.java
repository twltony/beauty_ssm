package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.ZzUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZzUserRoleService {

	int deleteByUidAndRid(String uId, String rId);

	int deleteByUid(String uId);

	int insertByUidAndRid(String uId, String rId);

	int updateByUidAndRid( String uId, String rId);

}
