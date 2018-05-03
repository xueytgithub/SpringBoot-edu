package io.renren.modules.kindergarten.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.kindergarten.entity.Area;
import io.renren.modules.kindergarten.entity.KindergartenEntity;

public interface KindergartenService {

	int deleteByPrimaryKey(KindergartenEntity kin);

    int insertSelective(KindergartenEntity record);

    KindergartenEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KindergartenEntity record);

	List<KindergartenEntity> queryList();

	//获取幼儿园或代理商总条数
	int queryTotal(Map<String, Object> map,String method);

	List<KindergartenEntity> kinqueryList(Map<String, Object> map);

	List<KindergartenEntity> proqueryList(Map<String, Object> map);
	//幼儿园添加时加载代理商信息
	List<KindergartenEntity> onloadProList();
	//获取代理商proxyid的最大值
	Long maxproxyid();

	List<Area> queryPid(Long id);
}
