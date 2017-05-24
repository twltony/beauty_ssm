package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactHkyearDao;
import com.tangwl.ssm.entity.YxFactHkyear;
import com.tangwl.ssm.service.YxFactHkyearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactHkyearImpl implements YxFactHkyearService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactHkyearDao YxFactHkyearDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactHkyear> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactHkyear|"+"|getSalesRate|";
		//先去缓存中取
		List<YxFactHkyear> result_cache=cache.getListCache(cache_key, YxFactHkyear.class);
		if(result_cache==null) {
			result_cache=	YxFactHkyearDao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactHkyear> getQuerySumByYear(String yy){
		String cache_key=RedisCache.CAHCENAME+"|YxFactHkyear|"+"|getQuerySumByYear|";
		//先去缓存中取
		List<YxFactHkyear> result_cache=cache.getListCache(cache_key, YxFactHkyear.class);
		if(result_cache==null) {
			result_cache=YxFactHkyearDao.querySumByYear(yy);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactHkyear> getProjectByArea(String area){
		String cache_key=RedisCache.CAHCENAME+"|YxFactHkyear|"+"|getProjectByArea|"+area;
		//先去缓存中取
		List<YxFactHkyear> result_cache=cache.getListCache(cache_key, YxFactHkyear.class);
		if(result_cache==null) {
			result_cache=YxFactHkyearDao.queryProjectByArea(area);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	

}
