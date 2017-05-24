package com.tangwl.ssm.service;

import com.tangwl.ssm.entity.YxFactZyh1;

import java.util.List;

public interface YxFactZyh1Service {

	List<YxFactZyh1> getAllList();

	List<YxFactZyh1> getMainSum();

	List<YxFactZyh1> getByAreaType();

	List<YxFactZyh1> getZyhByArea(String area);

	List<YxFactZyh1> getAreaSum(String year,String type);

	List<YxFactZyh1> getProjectbyArea(String area,String type);

	List<String> getAreas();

}
