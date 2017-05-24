package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactSalesdayDao;
import com.tangwl.ssm.entity.YxFactSalesday;
import com.tangwl.ssm.service.YxFactSalesdayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactSalesdayImpl implements YxFactSalesdayService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactSalesdayDao YxFactSalesdayDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactSalesday> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesday|"+"|getAllList|";
		//先去缓存中取
		List<YxFactSalesday> result_cache=cache.getListCache(cache_key, YxFactSalesday.class);
		if(result_cache==null) {
			result_cache=YxFactSalesdayDao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactSalesday> getAllSum(){
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesday|"+"|getAllSum|";
		//先去缓存中取
		List<YxFactSalesday> result_cache=cache.getListCache(cache_key, YxFactSalesday.class);
		if(result_cache==null) {
			result_cache=YxFactSalesdayDao.queryAllSum();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	@Override
	public List<YxFactSalesday> getProjectbyArea(String area){
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesday|"+"|getProjectbyArea|"+area;
		//先去缓存中取
		List<YxFactSalesday> result_cache=cache.getListCache(cache_key, YxFactSalesday.class);
		if(result_cache==null) {
			result_cache=YxFactSalesdayDao.queryProjectbyArea(area);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	
	

}
