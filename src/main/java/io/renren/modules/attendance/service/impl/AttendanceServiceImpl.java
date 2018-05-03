package io.renren.modules.attendance.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcloud.cos.http.ResponseBodyKey.Data;

import io.renren.modules.attendance.dao.AttendanceDao;
import io.renren.modules.attendance.entity.AttendanceEntity;
import io.renren.modules.attendance.service.AttendanceService;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;

@Service("attendanceServiceImpl")
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private SysDeptDao sysDeptDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insertSelective(AttendanceEntity record) {
		return attendanceDao.insertSelective(record);
	}

	@Override
	public AttendanceEntity selectByPrimaryKey(Long id) {
		return attendanceDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AttendanceEntity record) {
		return attendanceDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<AttendanceEntity> queryList(Map<String, Object> map) {
		String deptid2 = (String) map.get("deptid2");
		if(!"-1".equals(deptid2)){
			map.put("deptid", "-1");
		}
		List<AttendanceEntity> queryList = attendanceDao.queryList(map);
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
		for (AttendanceEntity attendance : queryList) {
			//展示显示日期
			attendance.setShowTime(df.format(attendance.getCheckin()));
			//展示周几
			attendance.setWeek(dateToWeek(df.format(attendance.getCheckin())));
			//展示工时
			String workTime = getworkTime(attendance.getCheckin(),attendance.getCheckout());
			if(workTime==null){
				attendance.setWorkTime("没有签退记录<br/>无法记录工时");
			}else if("1".equals(workTime)){
				attendance.setWorkTime("中午休息期间<br/>签到、签退,无工时");
			}else if("2".equals(workTime)){
				attendance.setWorkTime("考勤无效<br/>请联系管理员");
			}else if("3".equals(workTime)){
				attendance.setWorkTime("当天考勤结束<br/>请联系管理员");
			}else{
				attendance.setWorkTime(workTime);
			}
		}
		return queryList;
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
    
	//计算工时
    @SuppressWarnings("deprecation")
	public static String getworkTime(Date startTime,Date endTime){
    	DecimalFormat df = new DecimalFormat("#.00");
    	DecimalFormat df1 = new DecimalFormat("#");
    	Date date = new Date();
    	Date date2 = new Date();
    	if(endTime!=null){
    		Long ll =null;
    		int endhours = endTime.getHours();
    		int starthours = startTime.getHours();
    		//1.表示在中午午休息期间12点至14点间 打卡签到及签退，没有工时
    		if(starthours>=12 && starthours<14 && endhours>=12 && endhours<14){
    			return "1";
    		}else if(starthours>=12 && starthours<14 && endhours>=14 && endhours<18){
    			//2.如果签到时间，在中午休息期间，默认设置为14点签到,签退时间小于18点早退
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(14);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			ll = endTime.getTime() - date.getTime();
    		}else if(starthours>=12 && starthours<14 && endhours>=18 && endhours<21){
    			//3.如果签到时间，在中午休息期间，默认设置为14点签到,签退时间大于18点小于21点之间  则签退设置为18点
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(14);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			date2.setYear(endTime.getYear());
    			date2.setMonth(endTime.getMonth());
    			date2.setDate(endTime.getDate());
    			date2.setHours(18);
    			date2.setMinutes(0);
    			date2.setSeconds(0);
    			ll = date2.getTime() - date.getTime();
    		}else if(starthours>=12 && starthours<14 && endhours>=21){
    			//4.如果签到时间，在中午休息期间，默认设置为14点签到,签退时间大于21点之间  则签退设置为18点后算加班时间
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(14);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			ll = endTime.getTime() - date.getTime();
    		}else if(starthours<9 && endhours>=9 && endhours<12){
    			//5.如果签到时间小于9点正常，设置为9点，签退时间12点前
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(9);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			ll = endTime.getTime() - date.getTime();
    		}else if(starthours<9 && endhours>=12 && endhours<14){
    			//6.如果签到时间小于9点正常，设置为9点，签退时间，在中午休息期间，默认设置为12点签退
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(9);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			date2.setYear(endTime.getYear());
    			date2.setMonth(endTime.getMonth());
    			date2.setDate(endTime.getDate());
    			date2.setHours(12);
    			date2.setMinutes(0);
    			date2.setSeconds(0);
    			ll = date2.getTime() - date.getTime();
    		}else if(starthours<9 && endhours>=14 && endhours<18){
    			//7.如果签到时间小于9点正常，设置为9点，签退时间在18点前 早退
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(9);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			date2.setYear(endTime.getYear());
    			date2.setMonth(endTime.getMonth());
    			date2.setDate(endTime.getDate());
    			date2.setHours(endhours-2);//去掉中午休息时间
    			date2.setMinutes(endTime.getMinutes());
    			date2.setSeconds(endTime.getSeconds());
    			ll = date2.getTime() - date.getTime();
    		}else if(starthours<9 && endhours>=18 && endhours<21){
    			//8.如果签到时间小于9点正常，设置为9点，签退时间在18点-21点 正常 签退设置18点
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(9);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			date2.setYear(endTime.getYear());
    			date2.setMonth(endTime.getMonth());
    			date2.setDate(endTime.getDate());
    			date2.setHours(18-2);//去掉中午休息时间
    			date2.setMinutes(0);
    			date2.setSeconds(0);
    			ll = date2.getTime() - date.getTime();
    		}else if(starthours<9 && endhours>=21 ){
    			//9.如果签到时间小于9点正常，设置为9点，签退时间大于21点 加班 签退
    			date.setYear(startTime.getYear());
    			date.setMonth(startTime.getMonth());
    			date.setDate(startTime.getDate());
    			date.setHours(9);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			date2.setYear(endTime.getYear());
    			date2.setMonth(endTime.getMonth());
    			date2.setDate(endTime.getDate());
    			date2.setHours(endhours-2);//去掉中午休息时间
    			date2.setMinutes(endTime.getMinutes());
    			date2.setSeconds(endTime.getSeconds());
    			ll = date2.getTime() - date.getTime();
    		}else if(starthours>=9 && starthours<12 && endhours>=12 && endhours<14){
    			//10.如果签到迟到,签退在中午休息时间，则设置签退为12点
    			date.setYear(endTime.getYear());
    			date.setMonth(endTime.getMonth());
    			date.setDate(endTime.getDate());
    			date.setHours(12);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			ll = date.getTime() - startTime.getTime();
    		}else if(starthours>=9 && starthours<12 && endhours>=14 && endhours<18){
    			//11.如果签到迟到,签退在14点至18点早退
    			date.setYear(endTime.getYear());
    			date.setMonth(endTime.getMonth());
    			date.setDate(endTime.getDate());
    			date.setHours(endhours-2);//去掉中午休息时间
    			date.setMinutes(endTime.getMinutes());
    			date.setSeconds(endTime.getSeconds());
    			ll = date.getTime() - startTime.getTime();
    		}else if(starthours>=9 && starthours<12 && endhours>=18 && endhours<21){
    			//12.如果签到迟到,签退在18点至21点 正常，签退设置为18点
    			date.setYear(endTime.getYear());
    			date.setMonth(endTime.getMonth());
    			date.setDate(endTime.getDate());
    			date.setHours(18-2);//去掉中午休息时间
    			date.setMinutes(0);
    			date.setSeconds(0);
    			ll = date.getTime() - startTime.getTime();
    		}else if(starthours>=9 && starthours<12 && endhours>=21){
    			//13.如果签到迟到,签退大于21点 正常，算加班
    			date.setYear(endTime.getYear());
    			date.setMonth(endTime.getMonth());
    			date.setDate(endTime.getDate());
    			date.setHours(endhours-2);//去掉中午休息时间
    			date.setMinutes(endTime.getMinutes());
    			date.setSeconds(endTime.getSeconds());
    			ll = date.getTime() - startTime.getTime();
    		}else if(starthours>=14 && starthours<18 && endhours>=18 && endhours<21){
    			//14.如果签到是下午,签退时间18点-21点，签退设置为18点
    			date.setYear(endTime.getYear());
    			date.setMonth(endTime.getMonth());
    			date.setDate(endTime.getDate());
    			date.setHours(18);
    			date.setMinutes(0);
    			date.setSeconds(0);
    			ll = date.getTime() - startTime.getTime();
    		}else if(starthours<9 && endhours<9){
    			//15.如果签到、签退都在上班前，考勤无效
    			return "2";
    		}
    		else if(starthours>=18){
    			//16.如果签到下班后，考勤无效
    			return "3";
    		}else{
    			//时间都在午休外工作时间内 签到、签退
    			ll = endTime.getTime() - startTime.getTime();
    		}
    		Double second  = (double) (ll/1000) ;//转化成秒
        	Double hour = (double) (second/3600);//转化成小时
        	//System.err.println(ll+"***********"+hour);
        	String worktime = df.format(hour); //保留一位小数
        	int indexOf = worktime.indexOf(".");
        	String substring = worktime.substring(0, indexOf);
        	String append = "";
        	if("".equals(substring)){
        		worktime = "0"+worktime;
        		append = "H(0时"+df1.format(Float.parseFloat(worktime.substring(worktime.indexOf(".")+1))*0.6)+"分)";
        	}else{
        		//根据工时算出时分
        		append = "H("+worktime.substring(0, indexOf)+"时"+df1.format(Float.parseFloat(worktime.substring(indexOf+1))*0.6)+"分)";
        	}
        	return worktime+append;
    	}else{
    		//没有签退记录，无效工时
    		return null;
    	}
    }

	@Override
	public List<SysDeptEntity> getDept(Long id) {
		return sysDeptDao.getDept(id);
	}
    
    
}
