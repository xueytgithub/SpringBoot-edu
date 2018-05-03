package io.renren.modules.kindergarten.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.kindergarten.entity.KindergartenEntity;
@Mapper
public interface KindergartenDao {
	
    int deleteByPrimaryKey(KindergartenEntity kin);

    int insertSelective(KindergartenEntity record);

    KindergartenEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KindergartenEntity record);

	List<KindergartenEntity> queryList();

	List<KindergartenEntity> kinqueryList(Map<String, Object> map);

	List<KindergartenEntity> proqueryList(Map<String, Object> map);

	Long maxproxyid();
}