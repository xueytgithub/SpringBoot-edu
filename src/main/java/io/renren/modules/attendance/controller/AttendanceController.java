package io.renren.modules.attendance.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import io.renren.common.utils.ExportExcel;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.attendance.entity.AttendanceEntity;
import io.renren.modules.attendance.service.AttendanceService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;

@RestController
@RequestMapping("/attendance")
public class AttendanceController extends AbstractController implements ServletContextAware{

	private ServletContext servletContext;
	
	@Autowired
	private AttendanceService attendanceService;


	//查询考勤列表
	@RequestMapping("queryList")
	public R queryList(@RequestParam Map<String, Object> params){
		String username = getUser().getUsername();
		if(username.equals("admin")){
			params.put("userid", null);
		}else{
			params.put("userid", getUser().getUserId());
		}
		//params.put("username", getUser().getUsername());
		
		Query query = new Query(params);
		List<AttendanceEntity> list = attendanceService.queryList(query);
		int total = attendanceService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	//签到
	@RequestMapping("checkin")
	public R checkin(AttendanceEntity attendance) throws ParseException{
		SysUserEntity user = getUser();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int hours = date.getHours();
		@SuppressWarnings("deprecation")
		int minutes = date.getMinutes();
		
		//处理重复签到日期处理
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//展示日期格式处理
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map = new HashMap<String,Object>();
		String againtime = df.format(date);
		map.put("againtime", againtime);
		map.put("userid", user.getUserId());
		List<AttendanceEntity> queryList = attendanceService.queryList(map);
		if(queryList.size()>0){
			return R.ok().put("chenkin", ""+user.getUsername()+" 已经签到了!<br/>签到时间为："+df1.format(queryList.get(0).getCheckin()));
		}
		if(hours>=9 && hours<12){
			String min = hours-9+"时"+minutes+"分";
			attendance.setStatusin("迟到:"+min);
		}else if(hours>=12 && hours<14){
			attendance.setStatusin("上午迟到");
		}else if(hours>=14 && hours<18){
			String min = hours-9-2+"时"+minutes+"分";
			attendance.setStatusin("迟到:"+min);
		}else if(hours>=18){
			attendance.setStatusin("整天迟到");
		}else{
			attendance.setStatusin("正常");
		}
		attendance.setCheckin(date);
		attendance.setUserId(user.getUserId());
		attendanceService.insertSelective(attendance);
		return R.ok().put("chenkin", "签到成功!<br/>签到状态："+attendance.getStatusin());
	}
	
	//签退
	@RequestMapping("checkout")
	public R checkout(AttendanceEntity attendance) throws ParseException{
		SysUserEntity user = getUser();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int hours = date.getHours();
		@SuppressWarnings("deprecation")
		int minutes = date.getMinutes();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map = new HashMap<String,Object>();
		String againtime = df.format(date);
		map.put("againtime", againtime);
		map.put("userid", user.getUserId());
		List<AttendanceEntity> queryList = attendanceService.queryList(map);
		if(queryList.size()>0){
			for (AttendanceEntity attendanceEntity : queryList) {
				//将查出来的签到数据赋值给新的对象签退
				attendance = attendanceEntity;
				if(attendanceEntity.getCheckout()!=null){
					return R.ok().put("chenkout", ""+user.getUsername()+" 已经签退了!<br/>签退时间为："+df1.format(attendanceEntity.getCheckout()));
				}
			}
			if(hours>=14 && hours<18){
				String min = 18-hours-1+"时"+(60-minutes)+"分";
				attendance.setStatusout("早退:"+min);
			}else if(hours>=12 && hours<14){
				attendance.setStatusout("下午早退");
			}else if(hours>=9 && hours<12){
				String min = 18-hours-1-2+"时"+(60-minutes)+"分";
				attendance.setStatusout("早退:"+min);
			}else if(hours<9){
				attendance.setStatusout("整天早退");
			}else{
				attendance.setStatusout("正常");
			}
			attendance.setCheckout(date);
			attendanceService.updateByPrimaryKeySelective(attendance);
			return R.ok().put("chenkout", "签退成功!<br/>签退状态："+attendance.getStatusout());
		}else{
			return R.ok().put("chenkout", "对不起,请签到后再签退！");
		}
	}
	
	//获取部门信息
	@RequestMapping("getDept")
	public R getDept(Long deptid){
		List<SysDeptEntity> list = attendanceService.getDept(deptid);
		return R.ok().put("deptList", list);
	}

	
	//修改备注
	@RequestMapping("update")
	public R update(@RequestBody AttendanceEntity att){
		attendanceService.updateByPrimaryKeySelective(att);
		return R.ok().put("success", "修改成功");
	}
	
	//poi导出
	@RequestMapping("poiout")
	public void poiout(HttpServletRequest request,HttpServletResponse response) {
//  public void poiout(@RequestBody Map<String, Object> postData,HttpServletResponse response) {
//		String queryname = (String) postData.get("queryname");
//		String querymonth = (String) postData.get("querymonth");
//		String deptid = (String) postData.get("deptid");
//		String deptid2 =  (String) postData.get("deptid2");
		
		String queryname = request.getParameter("queryname");
		String querymonth = request.getParameter("querymonth");
		String deptid = request.getParameter("deptid");
		String deptid2 = request.getParameter("deptid2");
		String querystatusin = request.getParameter("querystatusin");
		String querystatusout = request.getParameter("querystatusout");
		String queryday = request.getParameter("queryday");
		
		Map<String,Object> map = new HashMap<String,Object>();
		String username = getUser().getUsername();
		if(username.equals("admin")){
			map.put("userid", null);
		}else{
			map.put("userid", getUser().getUserId());
		}
		map.put("queryname", queryname);
		map.put("querymonth", querymonth);
		map.put("deptid", deptid);
		map.put("deptid2", deptid2);
		map.put("querystatusin", querystatusin);
		map.put("querystatusout", querystatusout);
		map.put("queryday", queryday);

		String title = "考勤表信息";
		String[] rowsName = new String[] {"日期", "星期", "用户名", "部门名称", "签到时间","签到状态", "签退时间","签退状态","工时","备注"};
		List<Object[]> dataList = new ArrayList<Object[]>();
		//获得列表信息
		try {
			/////////////////
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			List<AttendanceEntity> list = attendanceService.queryList(map);
			//////////////////
			Object[] obj = null;
			for (AttendanceEntity att1 : list) {
				if(att1.getCheckin()!=null){
					String checkin = sdf.format(att1.getCheckin());
					att1.setPoiin(checkin);
				}if(att1.getCheckout()!=null){
					String checkout = sdf.format(att1.getCheckout());
					att1.setPoiout(checkout);
				}
				obj = new Object[rowsName.length];
				obj[0] = att1.getShowTime();
				obj[1] = att1.getWeek();
				obj[2] = att1.getUserName();
				obj[3] = att1.getDeptname();
				obj[4] = att1.getPoiin();
				obj[5] = att1.getStatusin();
				obj[6] = att1.getPoiout();
				obj[7] = att1.getStatusout();
				obj[8] = att1.getWorkTime();
				obj[9] = att1.getRemark();
				dataList.add(obj);
			}
			ExportExcel exportExcel = new ExportExcel(title, rowsName, dataList, response);
			exportExcel.export();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}
	
	//获取用户
	@RequestMapping("getuser")
	public R getuser(){
		String username = getUser().getUsername();
		return R.ok().put("user", username);
	}
}
