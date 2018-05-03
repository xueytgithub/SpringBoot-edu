package io.renren.modules.kindergarten.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.kindergarten.entity.Area;
import io.renren.modules.kindergarten.entity.KindergartenEntity;
import io.renren.modules.kindergarten.service.KindergartenService;

@RestController
@RequestMapping("/kindergarten")
public class KindergartenController {

	@Autowired
	private KindergartenService kindergartenService;
	
	//添加用户时加载幼儿园信息
	@RequestMapping("queryList")
	public R queryList(){
		List<KindergartenEntity> list = kindergartenService.queryList();
		return R.ok().put("kinList", list);
	}
	
	//幼儿园信息查询
	@RequestMapping("kinqueryList")
	public R kinqueryList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<KindergartenEntity> list = kindergartenService.kinqueryList(query);
		//获取当前方法
		String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
		System.err.println(method);
		int total = kindergartenService.queryTotal(query,method);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	//幼儿园信息新增
	@RequestMapping("kinsave")
	public R save(@RequestBody KindergartenEntity kin){
		kin.setType(1L);
		kindergartenService.insertSelective(kin);
		return R.ok();
	}
	
	//删除幼儿园/代理商信息(公用)
	@RequestMapping("delOne")
	public R delOne(@RequestBody Long id){
		KindergartenEntity kin = kindergartenService.selectByPrimaryKey(id);
		kindergartenService.deleteByPrimaryKey(kin);
		return R.ok();
	}
	
	//批量删除幼儿园/代理商信息(公用)
	@RequestMapping("delAll")
	public R delAll(@RequestBody Long[] ids){
		if(ids!=null){
			for (int i = 0; i < ids.length;i++) {
				KindergartenEntity kin = kindergartenService.selectByPrimaryKey(ids[i]);
				kindergartenService.deleteByPrimaryKey(kin);
			}
		}else{
			return R.error(1, "不允许空值");
		}
		return R.ok();
	}
	
	//回显幼儿园/代理商信息(公用)
	@GetMapping("showById/{id}")
	public R showById(@PathVariable("id") Long id){
		KindergartenEntity kin = kindergartenService.selectByPrimaryKey(id);
		return R.ok().put("kin", kin);
	}
	
	//修改幼儿园信息
	@RequestMapping("kinupdate")
	public R update(@RequestBody KindergartenEntity kin){
		kindergartenService.updateByPrimaryKeySelective(kin);
		return R.ok();
	}
	
	//////////////////
	//代理商信息查询
	@RequestMapping("proqueryList")
	public R proqueryList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<KindergartenEntity> list = kindergartenService.proqueryList(query);
		//获取当前方法
		String method = Thread.currentThread() .getStackTrace()[1].getMethodName();
		System.err.println(method);
		int total = kindergartenService.queryTotal(query,method);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	//新增代理商信息
	@RequestMapping("prosave")
	public R prosave(@RequestBody KindergartenEntity pro){
		Long maxproxyid = kindergartenService.maxproxyid();
		pro.setBuytime(new Date());
		pro.setType(2L);
		pro.setProxyid(maxproxyid+1);
		kindergartenService.insertSelective(pro);
		return R.ok();
	}
	
	//修改代理商信息
	@RequestMapping("proupdate")
	public R proupdate(@RequestBody KindergartenEntity pro){
		pro.setBuytimeEnd(new Date());
		kindergartenService.updateByPrimaryKeySelective(pro);
		return R.ok();
	}
	
	//添加幼儿园时加载代理商信息
	@RequestMapping("onloadProList")
	public R onloadProList(){
		List<KindergartenEntity> onloadProList = kindergartenService.onloadProList();
		return R.ok().put("loadList", onloadProList);
	}
	
	
	//根据获取省、市、县信息
	@RequestMapping("queryPid")
	public R queryPid(@RequestBody Long id){
		List<Area> pidList = kindergartenService.queryPid(id);
		return R.ok().put("Data", pidList);
	}
}
