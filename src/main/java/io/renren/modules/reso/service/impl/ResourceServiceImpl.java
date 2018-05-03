package io.renren.modules.reso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.common.utils.Query;
import io.renren.modules.reso.dao.ResourceDao;
import io.renren.modules.reso.entity.ResourceEntity;
import io.renren.modules.reso.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceDao resourceDao;
	@Override
	public int deleteByPrimaryKey(Long id) {
		return resourceDao.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(ResourceEntity record) {
		record.setCreateTime(new Date());
		resourceDao.insertSelective(record);
	}

	@Override
	public ResourceEntity selectByPrimaryKey(Long id) {
		return resourceDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ResourceEntity record) {
		record.setUpdateTime(new Date());
		return resourceDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<ResourceEntity> queryList(Map<String, Object> map) {
		return resourceDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		if(map.get("offset")!=null && map.get("limit")!=null){
			map.put("offset", null);
			map.put("limit",null);
		}
		int total = queryList(map).size();
		return total;
	}

	@Override
	public void delAll(Long[] resoIds) {
		resourceDao.delAll(resoIds);
	}

}
