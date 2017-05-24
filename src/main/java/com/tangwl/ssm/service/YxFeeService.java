package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.YxFee;

import java.util.List;

public interface YxFeeService {

	List<YxFee> getAllList();
	List<YxFee> getQuerySumByMonth(String mm);
	List<YxFee> getListByMonth(String mm);

	List<YxFee> getMonthSum(String mm);

	List<YxFee> getAllMonthSum();

}
