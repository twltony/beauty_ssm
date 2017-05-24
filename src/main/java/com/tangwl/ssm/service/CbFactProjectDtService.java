package com.tangwl.ssm.service;


import com.tangwl.ssm.entity.CbFactProjectDt;
import com.tangwl.ssm.entity.CbFactYfk;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CbFactProjectDtService {

	List<CbFactProjectDt> getCostMain();

	/**
	 * 构成分析
	 * @return
	 */
	List<CbFactProjectDt> getGcfxByVname(String vnames);

	List<String> getAreas();
	//板块列表
	List<String> getUnitNames();
	//项目列表
	List<String> getVnames(String unitname);

	//建筑单方
	List<CbFactProjectDt> getJzdfByTypename2(String typename2);

	//项目动态成本
	List<CbFactProjectDt> getDtcbByVname(String vname);

	//项目动态成本合同明细
	List<CbFactProjectDt> getContractDetails(String pkCorp,String pkProject1, String pkElem);

	//父节点列表
	List<String> getFatherCodes();
}
