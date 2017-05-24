package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactSalesplanDao;
import com.tangwl.ssm.entity.YxFactSalesplan;
import com.tangwl.ssm.service.YxFactSalesplanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactSalesplanImpl implements YxFactSalesplanService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactSalesplanDao yxFactSalesplanDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactSalesplan> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesplan|"+"|getAllList|";
		//先去缓存中取
		List<YxFactSalesplan> result_cache=cache.getListCache(cache_key, YxFactSalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactSalesplanDao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactSalesplan> getAllSum(){
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesplan|"+"|getAllSum|";
		//先去缓存中取
		List<YxFactSalesplan> result_cache=cache.getListCache(cache_key, YxFactSalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactSalesplanDao.queryAllSum();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	@Override
	public List<YxFactSalesplan> getYearAll(){
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesplan|"+"|getYearAll|";
		//先去缓存中取
		List<YxFactSalesplan> result_cache=cache.getListCache(cache_key, YxFactSalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactSalesplanDao.queryYearAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactSalesplan> getYearByArea(String area){
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesplan|"+"|getYearByArea|"+area;
		//先去缓存中取
		List<YxFactSalesplan> result_cache=cache.getListCache(cache_key, YxFactSalesplan.class);
		if(result_cache==null) {
			result_cache=yxFactSalesplanDao.queryYearByArea(area);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}


	

}
