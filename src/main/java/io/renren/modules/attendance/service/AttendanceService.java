package io.renren.modules.attendance.service;

import java.util.List;
import java.util.Map;

import io.renren.modules.attendance.entity.AttendanceEntity;
import io.renren.modules.sys.entity.SysDeptEntity;

public interface AttendanceService {

	int deleteByPrimaryKey(Long id);

    int insertSelective(AttendanceEntity record);

    AttendanceEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AttendanceEntity record);
    
    List<AttendanceEntity> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	List<SysDeptEntity> getDept(Long id);
}
