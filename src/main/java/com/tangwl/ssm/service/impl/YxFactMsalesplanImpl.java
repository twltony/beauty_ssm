package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactMsalesplanDao;
import com.tangwl.ssm.entity.YxFactMsalesplan;
import com.tangwl.ssm.service.YxFactMsalesplanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YxFactMsalesplanImpl implements YxFactMsalesplanService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactMsalesplanDao yxFactMsalesplanDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactMsalesplan> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactMsalesplan|"+"|getAllList|";
		//先去缓存中取
		List<YxFactMsalesplan> result_cache=cache.getListCache(cache_key, YxFactMsalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactMsalesplanDao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactMsalesplan> getMonthSalesplanByYear(){
		String cache_key=RedisCache.CAHCENAME+"|YxFactMsalesplan|"+"|getMonthSalesplanByYear|";
		//先去缓存中取
		List<YxFactMsalesplan> result_cache=cache.getListCache(cache_key, YxFactMsalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactMsalesplanDao.queryMonthSalesplanByYear();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}


	@Override
	public List<YxFactMsalesplan> getSalesRate(String year, String month){
		String cache_key=RedisCache.CAHCENAME+"|YxFactMsalesplan|"+"|getSalesRate|";
		//先去缓存中取
		List<YxFactMsalesplan> result_cache=cache.getListCache(cache_key, YxFactMsalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactMsalesplanDao.querySalesRate(year, month);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	//获取区域月销售数据
	@Override
	public List<YxFactMsalesplan> getAreaMonthSales(String year, String month){
		String cache_key=RedisCache.CAHCENAME+"|YxFactMsalesplan|"+"|getAreaMonthSales|"+year+month;
		//先去缓存中取
		List<YxFactMsalesplan> result_cache=cache.getListCache(cache_key, YxFactMsalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactMsalesplanDao.queryAreaMonthSales(year, month);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	//通过区域月份获取项目销售情况
	@Override
	public List<YxFactMsalesplan> getProjectbyAreaMonth(String area, String month){
		String cache_key=RedisCache.CAHCENAME+"|YxFactMsalesplan|"+"|getProjectbyAreaMonth|"+area+month;
		//先去缓存中取
		List<YxFactMsalesplan> result_cache=cache.getListCache(cache_key, YxFactMsalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactMsalesplanDao.queryProjectbyAreaMonth(area, month);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	
	

}
