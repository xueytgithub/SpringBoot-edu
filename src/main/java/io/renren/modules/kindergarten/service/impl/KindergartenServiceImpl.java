package io.renren.modules.kindergarten.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.modules.kindergarten.dao.AreaDao;
import io.renren.modules.kindergarten.dao.KindergartenDao;
import io.renren.modules.kindergarten.entity.Area;
import io.renren.modules.kindergarten.entity.KindergartenEntity;
import io.renren.modules.kindergarten.service.KindergartenService;

@Service
public class KindergartenServiceImpl implements KindergartenService{

	@Autowired
	private KindergartenDao KindergartenDao;
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public int deleteByPrimaryKey(KindergartenEntity kin) {
		return KindergartenDao.deleteByPrimaryKey(kin);
	}

	@Override
	public int insertSelective(KindergartenEntity record) {
		return KindergartenDao.insertSelective(record);
	}

	@Override
	public KindergartenEntity selectByPrimaryKey(Long id) {
		return KindergartenDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(KindergartenEntity record) {
		return KindergartenDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<KindergartenEntity> queryList() {
		return KindergartenDao.queryList();
	}

	//返回总条数，幼儿园和代理商公用一个方法
	/**
	 * 方法一：根据控制层传递的方法，判断方法名来查询不同业务的总条数
	 */
	@Override
	public int queryTotal(Map<String, Object> map,String method) {
		if(map.get("offset")!=null && map.get("limit")!=null){
			map.put("offset", null);
			map.put("limit",null);
		}
		int total=0;
		if(method.equals("kinqueryList")){
			total = kinqueryList(map).size();
		}else if(method.equals("proqueryList")){
			total = proqueryList(map).size();
		}
		return total;
	}
	/**
	 * 方法二：同时查询幼儿园和代理商的总条数放进Map集合，到控制层根据键值取出相应总条数
	 * 		 如果只查其中一个业务数据，但改方法每次都会查询两个业务的总条数，影响程序性能
	 */
	
	/*@Override
	public Map queryTotal(Map<String, Object> map) {
		if(map.get("offset")!=null && map.get("limit")!=null){
			map.put("offset", null);
			map.put("limit",null);
		}
		int kintotal = kinqueryList(map).size();
		int prototal = proqueryList(map).size();
		Map<String, Object> remap = new HashMap<String, Object>();
		remap.put("kintotal", kintotal);
		remap.put("prototal", prototal);
		return remap;
	}*/

	@Override
	public List<KindergartenEntity> kinqueryList(Map<String, Object> map) {
		 List<KindergartenEntity> kinqueryList = KindergartenDao.kinqueryList(map);
		 for (KindergartenEntity kin : kinqueryList) {
			 kin.setKinAdderss(kin.getProvince()+"-"+kin.getCity()+"-"+kin.getCounty()+"-"+kin.getAddress());
		 }
		 return kinqueryList;
	}

	/////////////////////////////////////////代理商信息/////////////////////
	@Override
	public List<KindergartenEntity> proqueryList(Map<String, Object> map) {
		List<KindergartenEntity> proqueryList = KindergartenDao.proqueryList(map);
		for (KindergartenEntity kin : proqueryList) {
			 kin.setKinAdderss(kin.getProvince()+"-"+kin.getCity()+"-"+kin.getCounty()+"-"+kin.getAddress());
		 }
		return proqueryList;
	}

	@Override
	public Long maxproxyid() {
		return KindergartenDao.maxproxyid();
	}
	
	@Override
	public List<KindergartenEntity> onloadProList() {
		Map<String,Object> map = null;
		return KindergartenDao.proqueryList(map);
	}

	@Override
	public List<Area> queryPid(Long id) {
		return areaDao.queryPid(id);
	}
}
