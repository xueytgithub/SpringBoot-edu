package io.renren.modules.activity.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.renren.common.utils.Query;
import io.renren.modules.activity.dao.ActivityDao;
import io.renren.modules.activity.entity.Activity;
import io.renren.modules.activity.service.ActivityService;
import io.renren.modules.reso.dao.ResourceDao;
import io.renren.modules.reso.entity.ResourceEntity;

@Service
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		String url = "";
		Activity activity = activityDao.selectByPrimaryKey(id);
		if(activity.getFileurl()!=null){
			String[] split = activity.getFileurl().substring(1).split("/");
			url = split[0] +"/"+ split[1];
			//获取删除文件夹路径
			File folder = new File("F:/"+url);
			//获取文件夹下文件数据
			File[] files = folder.listFiles();
			//获取数据库字段值的数据
			String[] urls = activity.getFileurl().replace("/", "\\").split(";");
			for (int i = 0; i < urls.length; i++) {
				for(File file:files){
					//文件夹与数据库分割的字段比较，匹配删除
					if(urls[i].equals(file.toString().substring(2))){
						//System.out.println("good");
						file.delete();
					}
				}
			}
		}
		return activityDao.deleteByPrimaryKey(id);
		
	}
	@Override
	public int insertSelective(Activity record) {
		return activityDao.insertSelective(record);
	}
	@Override
	public Activity selectByPrimaryKey(Long id) {
		return activityDao.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(Activity record) {
		return activityDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<ResourceEntity> queryActivity(Map<String,Object> map) {
		return resourceDao.queryActivity(map);
	}
	@Override
	public List<Activity> queryList(Map<String, Object> map) {
		List<Activity> queryList = activityDao.queryList(map);
		if(map!=null){
			Integer flag = Integer.parseInt((String) map.get("flag"));
			if(flag!=null){
				if(flag==1){
					for (Activity activity : queryList) {
						activity.setShowType(activity.getOneclassify()+","+activity.getClasses()+","+activity.getTerm());
					}
				}else if(flag==21){
					for (Activity activity : queryList) {
						activity.setShowType(activity.getTerm());
					}
				}else if(flag==22){
					for (Activity activity : queryList) {
						activity.setShowType(activity.getClasses()+","+activity.getTerm()+","+activity.getTerritory());
					}
				}else if(flag==42){
					for (Activity activity : queryList) {
						activity.setShowType(activity.getClasses()+","+activity.getTerm());
					}
				}else if(flag==43){
					for (Activity activity : queryList) {
						activity.setShowType(activity.getTerritory()+","+activity.getTerm());
					}
				}
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
	
	@Override
	public void clearCache(Map<String, Object> map) {
		 int aa=0;
		 List<Activity> queryList = queryList(map);
		 //获取删除文件夹路径
		 File folder = new File("F:/uploadFile");
		 //获取路径文件夹下文件数据
		 File[] files = folder.listFiles();
		 String allurl = "";
		 if(queryList!=null && queryList.size()>0 && files.length>0){
			 for (Activity activity : queryList) {
				//获取数据库字段值的数据
				allurl += activity.getFileurl() + activity.getThumbnailurl()+";";
			 }
			 String[] urls = allurl.replace("/", "\\").split(";");
			 //遍历文件
			 for(File file:files){
				//System.out.println(file);
				if(file.isDirectory()){
					File[] list = file.listFiles(); //获取文件夹下文件绝对路径
					//String[] list = file.list(); //获取文件夹下文件名称
					//判断文件夹下是否有文件
					if(list.length>0){
						//遍历文件夹下所有文件
						for (int j = 0; j < list.length; j++) {
							//System.err.println(list[j]);
							int count = 0;
							//遍历数据库所有实际存在的上传文件(路径)
							for (int i = 0; i < urls.length; i++) {
								//文件夹数据与数据库分割的字段比较，不匹配删除
								if(urls[i].equals(list[j].toString().substring(2))){
									//System.out.println("good--"+list[j].toString().replace("\\", "/").split("/")[list[j].toString().replace("\\", "/").split("/").length-1]);
									count++;
								}
							}
							//判断没有匹配的文件删除掉
							if(count==0){
								list[j].delete();
								//****递归调用重新查询****
								clearCache(null);
							}
						}
					}else{
						//文件夹下没有文件删除文件夹
						//System.out.println("空文件夹");
						file.delete();
					}
				}else{
					for (int i = 0; i < urls.length; i++) {
						//文件夹与数据库分割的字段比较，不匹配删除(保留需要固定的图片)
						if(!urls[i].equals(file.toString().substring(2)) && !file.toString().substring(2).equals("\\uploadFile\\pdf.jpg") && !file.toString().substring(2).equals("\\uploadFile\\excel.jpg") && !file.toString().substring(2).equals("\\uploadFile\\word.jpg") && !file.toString().substring(2).equals("\\uploadFile\\void.jpg")){
							System.out.println(file.toString().substring(2));
							file.delete();
						}
					}
				}
			}
		}
	}
	
	
}
