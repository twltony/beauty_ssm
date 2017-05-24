package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFactSalesmonthDao;
import com.tangwl.ssm.entity.YxFactSalesmonth;
import com.tangwl.ssm.service.YxFactSalesmonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFactSalesmonthImpl implements YxFactSalesmonthService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFactSalesmonthDao YxFactSalesmonthDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFactSalesmonth> getAllList() {
		String cache_key=RedisCache.CAHCENAME+"|YxFactSalesmonth|"+"|getAllList|";
		//先去缓存中取
		List<YxFactSalesmonth> result_cache=cache.getListCache(cache_key, YxFactSalesmonth.class);
		if(result_cache==null) {
			result_cache= YxFactSalesmonthDao.queryAll();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}


}
