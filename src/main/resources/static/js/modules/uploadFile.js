$(function(){
	var paths="";
	var fileSizeString="";
	$("#myFile").fileinput({
	    language : 'zh',
	    uploadUrl : baseURL + "apiUpload/uplodeImg",
	    autoReplace : true,
	    maxFileCount : 5,
	    allowedFileExtensions : [ "jpg", "png", "gif","txt","pdf","xlsx","xls","docx","doc","wmv","avi","swf" ],
	    browseClass : "btn btn-primary", //按钮样式 
	    uploadAsync: true, //默认异步上传
	    enctype: 'multipart/form-data',
        validateInitialCount:true,
	    previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
	    defaultPreviewContent:"<img id='showFile' width='100px' height='100px' src=''/>"
	}).on('filepreupload', function(event, data, previewId, index) {     //上传中
        var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
        //console.log('文件正在上传');
        paths='';
        fileSizeString='';
		$("#fileurl").val("");
	}).on("filecleared",function(event, data, msg){
		//console.log('清空后事件'+paths);
		paths='';
		fileSizeString='';
		$("#fileurl").val("");
	}).on("fileuploaded", function(e, data) {
	    var res = data.response;
	    alert(res.success);
	    $(".kv-file-remove").remove();
	    //$("#thumbnailurl").attr("value", res.valuePath);
	    paths += res.valuePath+";";
	    //文件大小
	    fileSizeString += res.fileSizeString;
	    console.log(fileSizeString);
	    if(fileSizeString.indexOf("M")!=-1){
	    	var resize = "";
	    	var filesize = 0.00;
	    	var arrfile = fileSizeString.split("M");
	    	for (var i = 0; i < arrfile.length-1; i++) {
	    		filesize += parseFloat(arrfile[i]);
	    		//console.log(arrfile[i]);
	    	}
	    	console.log(filesize.toFixed(4));
	    	filesize = filesize.toFixed(4);
	    	if(filesize<1){
	    		resize = (filesize*1024).toFixed(2) + "K";
	    	}
	    	else if(filesize>1024){
	    		resize = (filesize*1024).toFixed(2) + "G";
	    	}else{
	    		resize = filesize.substring(0,filesize.indexOf(".")+3) +"M";
	    	}
	    	//console.log(filesize);
	    	console.log(resize);
	    }
	    //console.log(paths);
	    $("#filesize").val(resize);
	    $("#fileurl").val(paths);
	    //repImg();
	    
	})
});


$(function(){
	$("#myImg").fileinput({
	    language : 'zh',
	    uploadUrl : baseURL + "apiUpload/uplodeImg",
	    autoReplace : true,
	    maxFileCount : 1,
	    allowedFileExtensions : [ "jpg", "png", "gif" ],
	    browseClass : "btn btn-primary", //按钮样式 
	    previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
	    //图片回显
	    defaultPreviewContent:"<img id='showImg' width='100px' height='100px' src=''/>"
	}).on("fileuploaded", function(e, data) {
	    var res = data.response;
	    alert(res.success);
	    //$("#thumbnailurl").attr("value", res.valuePath);
	    $("#thumbnailurl").val(res.valuePath);
	    //repImg();
	})
});



function repImg(){
	//处理再次添加图片上传后覆盖原图
	//文件
    if($("#fileload").find($(".file-preview-thumbnails>div")).length>2){
		//获取上传后的图片id
    	var getid = $("#fileload").find($(".file-preview-success")).attr("id");
    	//console.log(getid);
    	var array = getid.split("-");
    	var onceagain = array[2];
    	//console.log(onceagain);
    	if(onceagain==null || onceagain!=0){
    		$("#" + getid).remove();  
    		$("#zoom-" + getid).parent().remove();
        }
    }
    //图片
    else if($("#imgload").find($(".file-preview-thumbnails>div")).length>2){
		//获取上传后的图片id
    	var getid = $("#imgload").find($(".file-preview-success")).attr("id");
    	//console.log(getid);
    	var array = getid.split("-");
    	var onceagain = array[2];
    	//console.log(onceagain);
    	if(onceagain==null || onceagain!=0){
    		$("#" + getid).remove();  
    		$("#zoom-" + getid).parent().remove();
        }
    }
}


/*function repImg(){
	//处理再次添加图片上传后覆盖原图
	if($(".file-preview-thumbnails>div").length>2){
		//var files = $(".file-preview-thumbnails>div").attr("id");
		//console.log(files);
		var len = $(".file-preview-thumbnails>div").length;
    	console.log(len)
    	var temp = new Array() ;
    	$(".file-preview-thumbnails>div").each(function(){
    		 temp.push($(this));
    	});
    	console.log(temp);
    	sadiv.push(temp[1]);
    	sadiv.push(temp[2]);
    	console.log(sadiv);
		for (var i = 0; i < temp.length; i++) {
			//var id = $(sadiv)[i].attr("id");
			console.log(temp[i])
		}
		
		//获取上传后的图片id
		var getid = $(".file-preview-success").attr("id");
		//console.log(getid);
		var array = getid.split("-");
		var onceagain = array[2];
		//console.log(onceagain);
		if(onceagain==null || onceagain!=0){
			$("#" + getid).remove();  
			$("#zoom-" + getid).parent().remove();
		}
	}
}
*/