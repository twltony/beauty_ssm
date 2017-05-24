package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.CbFactProjectDtDao;
import com.tangwl.ssm.entity.CbFactProjectDt;
import com.tangwl.ssm.entity.CbFactYfk;
import com.tangwl.ssm.service.CbFactProjectDtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CbFactProjectDtServiceImpl implements CbFactProjectDtService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CbFactProjectDtDao cbFactProjectDtDao;
	@Autowired
	private RedisCache cache;
	

	@Override
	public List<CbFactProjectDt> getCostMain() {
		return 	cbFactProjectDtDao.queryCostMain();
	}
	@Override
	public List<String> getAreas() {
		return 	cbFactProjectDtDao.queryAreas();
	}
	@Override
	public List<String> getVnames(String unitname) {
		return 	cbFactProjectDtDao.queryVnames(unitname);
	}
	@Override
	public List<String> getUnitNames() {
		return 	cbFactProjectDtDao.queryUnitNames();
	}
	@Override
	public List<String> getFatherCodes() {
		return 	cbFactProjectDtDao.queryFatherCodes();
	}

	/**
	 * 构成分析
	 */
	@Override
	public List<CbFactProjectDt> getGcfxByVname(String vname) {
		return 	cbFactProjectDtDao.queryGcfxByVname(vname);
	}
	/**
	 * 建筑单方
	 */
	@Override
	public List<CbFactProjectDt> getJzdfByTypename2(String typename2) {
		return 	cbFactProjectDtDao.queryJzdfByTypename2(typename2);
	}

	/**
	 * 动态成本报表
	 */
	@Override
	public List<CbFactProjectDt> getDtcbByVname(String vname) {
		String cache_key=RedisCache.CAHCENAME+"|CbFactProjectDt|"+"|getDtcbByVname|"+vname;
		//先去缓存中取
		List<CbFactProjectDt> result_cache=cache.getListCache(cache_key, CbFactProjectDt.class);

		if(result_cache==null) {
			List<CbFactProjectDt> list =cbFactProjectDtDao.queryDtcbByVname(vname);
			List<String> fatherlist =cbFactProjectDtDao.queryFatherCodes();

			for (int i =0; i<fatherlist.size();i++){
				for(CbFactProjectDt cpd:list) {
					if(!fatherlist.toString().contains(cpd.getVelemcode())&& cpd.getNdyncostsum().compareTo(BigDecimal.valueOf(0))!=0){
						cpd.setLastChildDetail(true);
					}else{
						cpd.setLastChildDetail(false);
					}
				}
			}

			result_cache=list;
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;

	}

	/**
	 * 动态成本合同明细
	 */
	@Override
	public List<CbFactProjectDt> getContractDetails(String PK_CORP,String PK_PROJECT,String PK_ELEM) {
		return 	cbFactProjectDtDao.queryContractDetails(PK_CORP,PK_PROJECT,PK_ELEM);
	}


}
