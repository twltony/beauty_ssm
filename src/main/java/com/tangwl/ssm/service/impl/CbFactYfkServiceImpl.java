package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.CbFactProjectDtDao;
import com.tangwl.ssm.dao.CbFactYfkDao;
import com.tangwl.ssm.entity.CbFactProjectDt;
import com.tangwl.ssm.entity.CbFactYfk;
import com.tangwl.ssm.service.CbFactProjectDtService;
import com.tangwl.ssm.service.CbFactYfkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CbFactYfkServiceImpl implements CbFactYfkService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CbFactYfkDao cbFactYfkDao;
	@Autowired
	private RedisCache cache;
	


	/**
	 * 合同台账
	 */
	@Override
	public List<CbFactYfk> queryHttzByProject(String vname) {
		return 	cbFactYfkDao.queryHttzByProject(vname);
	}
	/**
	 * 合同台账
	 */
	@Override
	public List<String> getContracttypeByProject(String vname) {
		return 	cbFactYfkDao.queryContracttypeByProject(vname);
	}

}
