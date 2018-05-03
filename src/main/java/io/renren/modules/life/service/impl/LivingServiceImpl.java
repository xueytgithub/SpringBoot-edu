package io.renren.modules.life.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.life.dao.LivingEntityMapper;
import io.renren.modules.life.entity.LivingEntity;
import io.renren.modules.life.service.LivingService;
@Service
public class LivingServiceImpl implements LivingService{

	@Autowired
	private LivingEntityMapper livingDao;
	
	@Override
	public List<LivingEntity> queryList(Map<String, Object> map) {
		List<LivingEntity> queryList = livingDao.queryList(map);
		//展示周几
		for (LivingEntity livingEntity : queryList) {
			if(null != livingEntity.getDate() && !"".equals(livingEntity.getDate())){
				livingEntity.setWeek(dateToWeek(livingEntity.getDate()));
			}
			livingEntity.setTypetext(livingEntity.getType()==0?"收入":"支出");
		}
		return queryList;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return livingDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(LivingEntity record) {
		return livingDao.insertSelective(record);
	}

	@Override
	public LivingEntity selectByPrimaryKey(Integer id) {
		return livingDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LivingEntity record) {
		return livingDao.updateByPrimaryKeySelective(record);
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

	/**
     * 日期转星期
     * 
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
