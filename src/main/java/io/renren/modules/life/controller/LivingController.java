package io.renren.modules.life.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import io.renren.modules.life.entity.LivingEntity;
import io.renren.modules.life.service.LivingService;
import io.renren.modules.sys.controller.AbstractController;

@RestController
@RequestMapping("/life")
public class LivingController extends AbstractController{

	@Autowired
	private LivingService livingService;
	
	//查询列表
	@RequestMapping("queryList")
	public R queryList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<LivingEntity> list = livingService.queryList(query);
		int total = livingService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
		
	}
	
	//新增
	@RequestMapping("insert")
	public R insert(@RequestBody LivingEntity life) throws ParseException{
		String username = getUser().getUsername();
		life.setUsername(username);
		life.setCreatetime(new Date());
		if(life.getType()!=0L){
			life.setMoney(-1*life.getMoney());
		}
		int insertSelective = livingService.insertSelective(life);
		if(insertSelective!=1){
			System.out.println("错误");
			return R.error();
		}else{
			return R.ok();
		}
	}
	
	//删除多个
	@RequestMapping("deleteAll")
	public R deleteAll(@RequestBody String[] ids){
		//String[] array = ids.split(",");
		String[] array = ids;
		for (int i = 0; i < array.length; i++) {
			int insertSelective = livingService.deleteByPrimaryKey(Integer.valueOf(array[i]));
			if(insertSelective!=1){
				System.out.println("错误");
				return R.error();
			}
		}
		return R.ok();
	}
	
	//单个删除
	@RequestMapping("deleteOne")
	public R deleteOne(@RequestBody Integer id){
		int insertSelective = livingService.deleteByPrimaryKey(id);
		if(insertSelective!=1){
			System.out.println("错误");
			return R.error();
		}
		return R.ok();
	}
	
	//修改(回显)
	@RequestMapping("showById")
	public R showById( Integer id){
		LivingEntity LivingEntity = livingService.selectByPrimaryKey(id);
		return R.ok().put("Living", LivingEntity);
	}
	
	//修改
	@RequestMapping("update")
	public R update(@RequestBody LivingEntity life){
		life.setUpdatetime(new Date());
		if(life.getType()!=0L){
			life.setMoney(-1*life.getMoney());
		}
		int update = livingService.updateByPrimaryKeySelective(life);
		if(update!=1){
			System.out.println("错误");
			return R.error();
		}
		return R.ok();
	}
}
