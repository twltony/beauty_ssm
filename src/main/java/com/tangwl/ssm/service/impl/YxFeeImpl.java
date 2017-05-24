package com.tangwl.ssm.service.impl;

import com.tangwl.ssm.cache.RedisCache;
import com.tangwl.ssm.dao.YxFeeDao;
import com.tangwl.ssm.entity.YxFee;
import com.tangwl.ssm.service.YxFeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxFeeImpl implements YxFeeService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private YxFeeDao YxFeeDao;
	@Autowired
	private RedisCache cache;
	
	
	@Override
	public List<YxFee> getAllList() {
		System.out.println(1);
		return 	YxFeeDao.queryAll();
	}

	@Override
	public List<YxFee> getQuerySumByMonth(String mm){

		String cache_key=RedisCache.CAHCENAME+"|YxFee|"+"|getQuerySumByMonth|"+mm;
		//先去缓存中取
		List<YxFee> result_cache=cache.getListCache(cache_key, YxFee.class);
		if(result_cache==null) {
			result_cache=YxFeeDao.querySumByMonth(mm);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}
	@Override
	public List<YxFee> getMonthSum(String mm){
		String cache_key=RedisCache.CAHCENAME+"|YxFee|"+"|getMonthSum|"+mm;
		//先去缓存中取
		List<YxFee> result_cache=cache.getListCache(cache_key, YxFee.class);
		if(result_cache==null) {
			result_cache=YxFeeDao.queryMonthSum(mm);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFee> getListByMonth(String mm){
		String cache_key=RedisCache.CAHCENAME+"|YxFee|"+"|getListByMonth|"+mm;
		//先去缓存中取
		List<YxFee> result_cache=cache.getListCache(cache_key, YxFee.class);
		if(result_cache==null) {
			result_cache=YxFeeDao.queryListByMonth(mm);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFee> getAllMonthSum(){
		String cache_key=RedisCache.CAHCENAME+"|YxFee|"+"|getAllMonthSum|";
		//先去缓存中取
		List<YxFee> result_cache=cache.getListCache(cache_key, YxFee.class);
		if(result_cache==null) {
			result_cache=YxFeeDao.queryAllMonthSum();
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:"+cache_key);
		}else{
			LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}



}
