package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.YxFactMsalesplan;

import java.util.List;
import java.util.Map;

public interface YxFactMsalesplanService {

	List<YxFactMsalesplan> getAllList();

	List<YxFactMsalesplan> getMonthSalesplanByYear();

	List<YxFactMsalesplan> getSalesRate(String year, String month);


	 
}
