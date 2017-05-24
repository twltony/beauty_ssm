package com.tangwl.ssm.service;


import com.tangwl.ssm.entity.CbFactYfk;

import java.util.List;

public interface CbFactYfkService {

	//合同台账
	List<CbFactYfk> queryHttzByProject(String vname);
	//合同列表
	List<String> getContracttypeByProject(String vname);

}
