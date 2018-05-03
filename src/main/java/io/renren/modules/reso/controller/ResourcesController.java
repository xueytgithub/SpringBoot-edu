package io.renren.modules.reso.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.reso.entity.ResourceEntity;
import io.renren.modules.reso.service.ResourceService;
import io.renren.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/reso")
public class ResourcesController extends AbstractController{

	@Autowired
	private ResourceService resourceService;
	
	//查询列表
	@RequestMapping("/queryList")
	public R queryList(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ResourceEntity> list = resourceService.queryList(query);
		int total = resourceService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	//添加
	@RequestMapping("save")
	public R save(@RequestBody ResourceEntity reso){
		resourceService.insertSelective(reso);
		return R.ok();
	}
	
	//单个删除
	@RequestMapping("delOne")
	public R delOne(@RequestBody Long id){
		resourceService.deleteByPrimaryKey(id);
		return R.ok();
	}
	
	//批量删除
	@RequestMapping("delAll")
	public R delAll(@RequestBody Long[] resoIds){
		resourceService.delAll(resoIds);
		return R.ok();
	}
	
	//回显
	@RequestMapping("showById")
	public R showById(@RequestBody Long id){
		ResourceEntity reso = resourceService.selectByPrimaryKey(id);
		return R.ok().put("reso", reso);
	}
	
	//修改
	@RequestMapping("update")
	public R update(@RequestBody ResourceEntity reso){
		resourceService.updateByPrimaryKeySelective(reso);
		return R.ok();
	}
	
}
