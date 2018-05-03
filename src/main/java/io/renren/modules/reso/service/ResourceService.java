package io.renren.modules.reso.service;

import java.util.List;
import java.util.Map;

import io.renren.common.utils.Query;
import io.renren.modules.reso.entity.ResourceEntity;

public interface ResourceService {

	int deleteByPrimaryKey(Long id);

    void insertSelective(ResourceEntity record);

    ResourceEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResourceEntity record);

	List<ResourceEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	void delAll(Long[] resoIds);
}
