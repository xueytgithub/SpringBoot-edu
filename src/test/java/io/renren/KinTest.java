package io.renren;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.kindergarten.controller.KindergartenController;
import io.renren.modules.kindergarten.entity.KindergartenEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KinTest {

	 @Autowired
	 private KindergartenController kinController;
	 
	//测试查询方法
	@Test
	 public void proqueryList(){
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("page", 1);
		 map.put("limit", 5);
		 //测试幼儿园查询方法
		 R queryList = kinController.kinqueryList(map);
		 //测试代理商查询方法
		 //R queryList = kinController.proqueryList(map);
		 PageUtils pageUtils = (PageUtils) queryList.get("page");
		 System.out.println("总条数"+pageUtils.getTotalCount()+"***"+pageUtils.getList());
	 }
	
	//@Test
	public void onloadProList(){
		R queryList = kinController.onloadProList();
		List<KindergartenEntity> loadList = (List<KindergartenEntity>) queryList.get("loadList");
		for (KindergartenEntity kindergartenEntity : loadList) {
			System.out.println(kindergartenEntity);
		}
	}
	 
	 //测试幼儿园添加
	 //@Test
	 public void save(){
		 KindergartenEntity kin = new KindergartenEntity();
		 kin.setName("kintest");
		 kin.setProxyid(1L);
		 kinController.save(kin);
	 }
	 
	 //测试代理商添加
	 //@Test
	 public void prosave(){
		 KindergartenEntity pro = new KindergartenEntity();
		 pro.setName("代理商测试");
		 kinController.prosave(pro);
	 }
	 
	 //测试幼儿园删除
	 //@Test
	 public void del(){
		 Long[] ids = {11L,12L};
		 Long[] ids1 = new Long[]{11L,12L};
		 Long[] ids2 = new Long[2];
		 ids2=new Long[]{11L,12L};
		 kinController.delAll(ids);
	 }
	 
}
