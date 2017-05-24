package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactRoomstatusDao;
import com.tangwl.ssm.entity.YxFactRoomstatus;
import com.tangwl.ssm.service.YxFactRoomstatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactRoomstatusImpl implements YxFactRoomstatusService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactRoomstatusDao YxFactRoomstatusDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactRoomstatus> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactRoomstatus|"+"|getAllList|";
		//先去缓存中取
		List<YxFactRoomstatus> result_cache=cache.getListCache(cache_key, YxFactRoomstatus.class);
		if(result_cache==null) {
			result_cache=YxFactRoomstatusDao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactRoomstatus> getMainSum(String year) {
		String cache_key=RedisCache.CAHCENAME+"|YxFactRoomstatus|"+"|getMainSum|";
		//先去缓存中取
		List<YxFactRoomstatus> result_cache=cache.getListCache(cache_key, YxFactRoomstatus.class);
		if(result_cache==null) {
			result_cache=YxFactRoomstatusDao.queryMainSum(year);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}





}
