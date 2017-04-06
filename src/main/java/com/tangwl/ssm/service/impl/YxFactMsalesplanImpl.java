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
		//String cache_key=RedisCache.CAHCENAME+"|getUserList|"+offset+"|"+limit;
		//先去缓存中取
		//List<YxFactMsalesplan> result_cache=cache.getListCache(cache_key, YxFactMsalesplan.class);
		List<YxFactMsalesplan> result_cache=null;
		if(result_cache==null){
			//缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
			result_cache=yxFactMsalesplanDao.queryAll();
			//cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			//LOG.info("put cache with key:"+cache_key);
		}else{
			//LOG.info("get cache with key:"+cache_key);
		}
		return result_cache;
	}

	@Override
	public List<YxFactMsalesplan> getMonthSalesplanByYear(){
		return yxFactMsalesplanDao.queryMonthSalesplanByYear();
	}


	@Override
	public List<YxFactMsalesplan> getSalesRate(String year, String month){

		return yxFactMsalesplanDao.querySalesRate(year, month);
	}
	
	

}
