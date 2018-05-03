package io.renren.modules.reso.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.common.utils.Query;
import io.renren.modules.activity.entity.Activity;
import io.renren.modules.reso.entity.ResourceEntity;

@Mapper
public interface ResourceDao {
	
    int deleteByPrimaryKey(Long id);

    int insertSelective(ResourceEntity record);

    ResourceEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResourceEntity record);

	List<ResourceEntity> queryList(Map<String, Object> map);

	void delAll(Long[] resoIds);
	
	List<ResourceEntity> queryActivity(Map<String,Object> map);
}