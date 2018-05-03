package io.renren.modules.kindergarten.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.kindergarten.entity.Area;

@Mapper
public interface AreaDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);
    
    List<Area> queryPid(Long id);

}