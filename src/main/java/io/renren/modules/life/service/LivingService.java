package io.renren.modules.life.service;

import java.util.List;
import java.util.Map;

import io.renren.common.utils.Query;
import io.renren.modules.life.entity.LivingEntity;

public interface LivingService {

	List<LivingEntity> queryList(Map<String, Object> map);
	
	int deleteByPrimaryKey(Integer id);

    int insertSelective(LivingEntity record);

    LivingEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LivingEntity record);

	int queryTotal(Map<String, Object> map);


}
