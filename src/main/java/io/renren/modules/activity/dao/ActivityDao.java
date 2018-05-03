package io.renren.modules.activity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.activity.entity.Activity;
import io.renren.modules.reso.entity.ResourceEntity;

@Mapper
public interface ActivityDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Activity record);

	List<Activity> queryList(Map<String, Object> map);

}