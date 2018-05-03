package io.renren.modules.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.renren.modules.attendance.entity.AttendanceEntity;

@Mapper
public interface AttendanceDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AttendanceEntity record);

    AttendanceEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AttendanceEntity record);

	List<AttendanceEntity> queryList(Map<String, Object> map);


}