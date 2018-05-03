package io.renren.modules.life.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.life.entity.LivingEntity;

@Mapper
public interface LivingEntityMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insertSelective(LivingEntity record);

    LivingEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LivingEntity record);

	List<LivingEntity> queryList(Map<String, Object> map);

}