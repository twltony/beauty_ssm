package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.YxFactHkyear;

import java.util.List;

public interface YxFactHkyearService {

	List<YxFactHkyear> getAllList();
	List<YxFactHkyear> getQuerySumByYear(String yy);
	List<YxFactHkyear> getProjectByArea(String area);


}
