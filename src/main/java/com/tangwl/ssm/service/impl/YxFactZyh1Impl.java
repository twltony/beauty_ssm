package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactZyh1Dao;
import com.tangwl.ssm.entity.YxFactZyh1;
import com.tangwl.ssm.service.YxFactZyh1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactZyh1Impl implements YxFactZyh1Service {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactZyh1Dao YxFactZyh1Dao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactZyh1> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getAllList|";
		//先去缓存中取
		List<YxFactZyh1> result_cache=cache.getListCache(cache_key, YxFactZyh1.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactZyh1> getMainSum() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getMainSum|";
		//先去缓存中取
		List<YxFactZyh1> result_cache=cache.getListCache(cache_key, YxFactZyh1.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryMainSum();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactZyh1> getByAreaType() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getByAreaType|";
		//先去缓存中取
		List<YxFactZyh1> result_cache=cache.getListCache(cache_key, YxFactZyh1.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryByAreaType();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	@Override
	public List<String> getAreas() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getAreas|";
		//先去缓存中取
		List<String> result_cache=cache.getListCache(cache_key, String.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryAreas();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	@Override
	public List<YxFactZyh1> getAreaSum(String year,String type) {
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getAreaSum|"+year+type;
		//先去缓存中取
		List<YxFactZyh1> result_cache=cache.getListCache(cache_key, YxFactZyh1.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryAreaSum(year,type);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	@Override
	public List<YxFactZyh1> getProjectbyArea(String area,String type) {
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getProjectbyArea|"+area+type;
		//先去缓存中取
		List<YxFactZyh1> result_cache=cache.getListCache(cache_key, YxFactZyh1.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryProjectbyArea(area,type);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactZyh1> getZyhByArea(String area){
		String cache_key=RedisCache.CAHCENAME+"|YxFactZyh1|"+"|getZyhByArea|"+area;
		//先去缓存中取
		List<YxFactZyh1> result_cache=cache.getListCache(cache_key, YxFactZyh1.class);
		if(result_cache==null) {
			result_cache=YxFactZyh1Dao.queryZyhByArea(area);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}



}
