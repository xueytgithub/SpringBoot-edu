package io.renren.modules.api.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;





@RestController
@RequestMapping("/apiUpload")
public class UploadFileController {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	//只上传图片格式
	@RequestMapping("/uplodeImg")
    public Map<String, Object> updatePhoto(HttpServletRequest request,HttpServletResponse response,@RequestParam("myFile") MultipartFile myFile){
        Map<String, Object> json = new HashMap<String, Object>();
        try {
        	//获取文件的字节大小，单位byte
        	String fileSizeString = "";
        	DecimalFormat def = new DecimalFormat("#.0000");
        	long fileLength = myFile.getSize();
        	if (fileLength < 1073741824) {
//        		if (fileLength < 1024) {
//                    fileSizeString = def.format((double) fileLength) + "B";
//                }
//                else if (fileLength < 1048576) {
//                    fileSizeString = def.format((double) fileLength / 1024) + "K";
//                }
//                else if (fileLength < 1073741824) {
                    fileSizeString = def.format((double) fileLength / 1048576) + "M";
//                }
//                else {
//                    fileSizeString = def.format((double) fileLength / 1073741824) + "G";
//                }
            }
        	//System.out.println(fileLength+"*******"+fileSizeString);
            //输出文件后缀名称
            //System.out.println(myFile.getOriginalFilename());
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            DateFormat mkdir = new SimpleDateFormat("yyyy-MM-dd");
            //图片名称
            String name = df.format(new Date());

            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            //
            String ext = FilenameUtils.getExtension(myFile.getOriginalFilename());
            //String getName = FilenameUtils.getName(myFile.getOriginalFilename());
            String getName = myFile.getOriginalFilename();
            //获取文件名
            String filename = getName.substring(0, getName.lastIndexOf("."));
            //System.out.println(filename);
            //保存图片       File位置 （全路径）   /upload/fileName.jpg
            //String url = request.getSession().getServletContext().getRealPath("/static/img/upload");
            //String url = "D://java//wokspace//working//huihai-edu//src//main//resources//static//img//upload";
            String url = "F:/uploadFile/"+mkdir.format(new Date());
            //System.out.println(url);
            //相对路径
            String path ="";
            if(!ext.equals("jpg") && !ext.equals("png") && !ext.equals("gif")){
            	 path = "/"+filename+"-"+name + "." + ext;
            }else{
            	 path = "/"+name + "." + ext;
            }
            File file = new File(url);
            if(!file.exists()){
                file.mkdirs();
            }
            myFile.transferTo(new File(url+path));
            json.put("success", "上传成功");
            json.put("valuePath", url.substring(2)+path);
            json.put("fileSizeString", fileSizeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(json);
        return json ;
	}
	
	
	//多文件上传
	@RequestMapping("/uplodeFiles")
    public Map<String, Object> uplodeFiles(HttpServletRequest request,HttpServletResponse response,@RequestParam("myFiles") MultipartFile[] myFiles){
        Map<String, Object> json = new HashMap<String, Object>();
        
        try {
        	String url = "";
            String path ="";
            String str = "";
            //输出文件后缀名称
            //System.out.println(myFile.getOriginalFilename());
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            for (int i = 0; i < myFiles.length; i++) {
	            //图片名称
	            String name = df.format(new Date());
	            Random r = new Random();
	            for(int j = 0 ;j<3 ;j++){
	                name += r.nextInt(10);
	            }
            	//
            	String ext = FilenameUtils.getExtension(myFiles[i].getOriginalFilename());
            	//保存图片       File位置 （全路径）   /upload/fileName.jpg
            	//String url = request.getSession().getServletContext().getRealPath("/static/img/upload");
            	//String url = "D://java//wokspace//working//huihai-edu//src//main//resources//static//img//upload";
            	url = "F:/uploadFile";
            	//System.out.println(url);
            	//相对路径
            	path = "/"+name + "." + ext;
            	File file = new File(url);
            	if(!file.exists()){
            		file.mkdirs();
            	}
            	myFiles[i].transferTo(new File(url+path));
            	str += url.substring(2)+path+";";
            	System.err.println(str);
			}
            json.put("success", "上传成功");
            json.put("valuePath", str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(json);
        return json ;
	}

	
}
