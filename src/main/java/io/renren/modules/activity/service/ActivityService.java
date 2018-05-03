package io.renren.modules.activity.service;

import java.util.List;
import java.util.Map;

import io.renren.common.utils.Query;
import io.renren.modules.activity.entity.Activity;
import io.renren.modules.reso.entity.ResourceEntity;

public interface ActivityService {

	int deleteByPrimaryKey(Long id);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Activity record);
    
    List<ResourceEntity> queryActivity(Map<String,Object> map);

	List<Activity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void clearCache(Map<String, Object> map);
}
