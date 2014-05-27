package org.springframework.democache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.democache.model.Parametres;
import org.springframework.democache.model.Reponse;
import org.springframework.stereotype.Service;

@Service
public class ServiceCacher {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCacher.class);

	@Cacheable("dataCache")
	public Reponse getDatas(Parametres params){
		logger.info("execution getDatas()");
		Reponse Reponse=null;
		if(params!=null){
			Reponse=new Reponse(params.getParam1(), params.getParam2());
		}
		return Reponse;
	}

	
	@Cacheable(value="dataCache",condition="#params.param1>100")
	public Reponse getDatasWithCondition(Parametres params){
		logger.info("execution getDatasWithCondition()");
		Reponse Reponse=null;
		if(params!=null){
			Reponse=new Reponse(params.getParam1(), params.getParam2());
		}
		return Reponse;
	}
	
	
	@Cacheable(value="dataCache",key="#params.param1")
	public Reponse getDatasWithKey(Parametres params){
		logger.info("execution getDatasWithKey()");
		Reponse Reponse=null;
		if(params!=null){
			Reponse=new Reponse(params.getParam1(), params.getParam2());
		}
		return Reponse;
	}
	
	@CacheEvict(value="dataCache",allEntries=true)
	public void viderCacheData(){
		
	}
	
	@CachePut("dataCache")
	public Reponse getDatasWithPut(Parametres params){
		logger.info("execution getDatasWithPut()");
		Reponse Reponse=null;
		if(params!=null){
			Reponse=new Reponse(params.getParam1(), params.getParam2());
		}
		return Reponse;
	}

}
