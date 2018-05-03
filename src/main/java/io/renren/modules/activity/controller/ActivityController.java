package io.renren.modules.activity.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.common.utils.ResourceConstant;
import io.renren.modules.activity.entity.Activity;
import io.renren.modules.activity.service.ActivityService;
import io.renren.modules.reso.entity.ResourceEntity;

@RestController
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	//根据不同的资源获取不同信息(初始化)
	@RequestMapping("getResource")
	public R getResource(){
		Map<String,Object> map = new HashMap<String,Object>();
		//领域
		map.put("parentid", ResourceConstant.TERRITORY_PARENTID);
		map.put("flag", null);
		List<ResourceEntity> territoryList = activityService.queryActivity(map);
		//年龄(班级)
		map.put("parentid", ResourceConstant.AGE_PARENTID);
			//所有班级
		List<ResourceEntity> allageList = activityService.queryActivity(map);
			//根据条件进行筛选
		map.put("flag", ResourceConstant.AGE_FLAG1);
			//获取 小、中、大 班
		List<ResourceEntity> ageList = activityService.queryActivity(map);
			//获取学前班
		map.put("flag", ResourceConstant.AGE_FLAG2);
		List<ResourceEntity> ageList2 = activityService.queryActivity(map);
		for (ResourceEntity resourceEntity : ageList2) {
			//将学前班加入到小、中、大 班的集合中 ageList
			 ageList.add(resourceEntity);
		}
		//获取 小、中、大 班
		map.put("parentid", ResourceConstant.AGE_PARENTID);
		map.put("flag", ResourceConstant.AGE_FLAG1);
		List<ResourceEntity> ageList3 = activityService.queryActivity(map);
		//资源类行
		map.put("parentid", ResourceConstant.TYPE_PARENTID);
		map.put("flag", null);
		List<ResourceEntity> typeList = activityService.queryActivity(map);
		//特色课程(入学准备)
		map.put("parentid", ResourceConstant.COURSE_PARENTID);
		map.put("flag", null);
		List<ResourceEntity> courseList = activityService.queryActivity(map);
		//学期
		map.put("parentid", ResourceConstant.TERM_PARENTID);
		map.put("flag", null);
		List<ResourceEntity> termList = activityService.queryActivity(map);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("territoryList", territoryList);
		returnMap.put("allageList", allageList);
		returnMap.put("ageList", ageList);
		returnMap.put("ageList3", ageList3);
		returnMap.put("typeList", typeList);
		returnMap.put("courseList", courseList);
		returnMap.put("termList", termList);
		return R.ok().put("getResource", returnMap);
	}
	
	//活动列表查询
	@RequestMapping("queryList")
	public R queryList(@RequestParam Map<String, Object> params){
		String str = (String) params.get("keyword");
		if(str!=null && !"".equals(str)){
			String[] keyword = str.split(",");
			params.put("keyword", keyword);
		}
		//查询列表数据
		Query query = new Query(params);
		List<Activity> list = activityService.queryList(query);
		int total = activityService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	//活动添加
	@RequestMapping("territorySave")
	public R save(@RequestBody Activity activity){
		activity.setCreateTime(new Date());
		//System.out.println(activity);
		activityService.insertSelective(activity);
		return R.ok();
	}
	
	//删除活动
	@RequestMapping("delOne")
	public R delOne(@RequestBody Long id){
		activityService.deleteByPrimaryKey(id);
		return R.ok();
	}
	
	//批量删除活动
	@RequestMapping("delAll")
	public R delOne(@RequestBody Long[] ids){
		for (int i = 0; i < ids.length; i++) {
			activityService.deleteByPrimaryKey(ids[i]);
		}
		return R.ok();
	}

	//活动回显
	@RequestMapping("showById")
	public R showById(@RequestBody Long id){
		Activity activity = activityService.selectByPrimaryKey(id);
		return R.ok().put("activity", activity);
	}
	
	//活动修改
	@RequestMapping("update")
	public R update(@RequestBody Activity activity){
		activity.setUpdateTime(new Date());
		activityService.updateByPrimaryKeySelective(activity);
		return R.ok();
	}
	
	//清除缓存文件
	@RequestMapping("clearCache")
	public R clearCache(){
		Map<String,Object> map =null;
		activityService.clearCache(map);
		return R.ok().put("success", "清除成功");
	}
	
	//查看活动文件
	@RequestMapping("showFiles")
	public R showFiles(@RequestBody Long id) throws IOException{
		StringBuffer html= new StringBuffer();
		Activity active = activityService.selectByPrimaryKey(id);
//		URL url = ClassLoader.getSystemResource("static/img/pdf.jpg");
//	    File file = new File(url.getPath());
//	    String icon = file.getCanonicalPath().replace("\\", "/");
//	    System.out.println(file.getCanonicalPath());
//	    System.out.println(file.exists());
		String[] split = active.getFileurl().split(";");
		html.append("<html><head></head><body><div id='acContent'>");
		int count = 0;
		for (int i = 0; i < split.length; i++) {
			html.append("<div style='float:left;margin:10px;'>"
							+ "<img style='display: block;'id='img"+count+++"'  width='170px' height='200px' src='http://localhost:8086"+split[i]+"'></img>"
							+ "<span style='display: block;float:left;'>"+split[i].split("/")[split[i].split("/").length-1]+"</span>"
							+ "<a href='http://localhost:8086/activity/download?fileName="+split[i]+"' javascript:0 style='display: block;text-align:center'>下载</a>"
					 + "</div>");
		}
		//html.append("<img id='img"+count+++"' style='margin:10px;' width='170px' height='200px' src='"+icon+"'></img>");
		html.append("</div></body></html>");
		//System.err.println(html);
		return R.ok().put("html", html);
	}
	
	//下载文件
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	  public void download(HttpServletResponse res,String fileName) {
	    //String fileName = "upload.jpg";
	    res.setHeader("content-type", "application/octet-stream");
	    res.setContentType("application/octet-stream");
	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	    byte[] buff = new byte[1024];
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    try {
	      os = res.getOutputStream();
	      bis = new BufferedInputStream(new FileInputStream(new File("f://"
	          + fileName)));
	      int i = bis.read(buff);
	      while (i != -1) {
	        os.write(buff, 0, buff.length);
	        os.flush();
	        i = bis.read(buff);
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      if (bis != null) {
	        try {
	          bis.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    //System.out.println("success");
	  }
	
}
