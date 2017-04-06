package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.YxFactRoomstatus;

import java.util.List;

public interface YxFactRoomstatusService {

	List<YxFactRoomstatus> getAllList();

	List<YxFactRoomstatus> getMainSum(String year);


}
