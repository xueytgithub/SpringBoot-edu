var flag;
$(function () {
	flag = location.href.split("?flag=")[1];
    $("#jqGrid").jqGrid({
        //url: baseURL + 'reso/queryList?flag='+flag,
        url: baseURL + 'activity/queryList?flag='+flag,
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: "id", key: true,hidden:true },
			{ label: '活动编号', name: 'number',width:30 },
			{ label: '活动名称', name: '', width: 60 ,
				formatter: function(cellvalue, options, rowObject){
					return "<div><a href='javascript:showFiles("+rowObject.id+")' style='display: block;'>" +
							"<img style='display: block;' width='100px' height='100px' src='"+rowObject.thumbnailurl+"'/>" +
							rowObject.activityname+"</a></div>";
		        }
			},
			{ label: '文件路径', name: 'fileurl', width: 65 },
			{ label: '缩略图路径', name: 'thumbnailurl', width: 55 },
			{ label: '搜索关键字', name: 'keyword', width: 40 },
			{ label: '类别', name: 'showType', width: 55 },
			{ label: '文件大小', name: 'filesize', width: 35 },
			{ label: '创建时间', name: 'createTime', width: 55 },
			{ label: '操作',name: '',width: 55,
				  formatter: function(cellvalue, options, rowObject){
					  var other="";
					  other+='<div class="btn-group">'+
						  '<button style="margin-right:10px" type="button" class="btn btn-success dropdown-toggle" onclick=toUpd("'+rowObject.id+'")>修改</button>'+
						  '<button type="button" class="btn btn-danger dropdown-toggle" onclick=delOne("'+rowObject.id+'")>删除</button>'+ 
						  '</div>'
						return other;
		          }
			 },
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order:"order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

$(function(){
	$.get(baseURL + "activity/getResource/",function(r){
		vm.territoryList = r.getResource.territoryList;
		vm.ageList = r.getResource.ageList;
		vm.termList = r.getResource.termList;
	});
});


var vm = new Vue({
	el:'#rapp',
	data:{
		q:{
			keyword:null,
		},
		showList: true,
		title:null,
		activity: {},
		territoryList:[],
		ageList:[],
		termList:[]
	},
	methods:{
		query:function(){
			this.reload();
		},
		reset:function(){
			$("#territory").val('-1');
			$("#age").val('-1');
			$("#term").val('-1');
			vm.q.keyword=null;
			vm.query();
		},
		add:function(){
			vm.showList = false;
			vm.title = "新增";
			vm.activity = {};
			$("#oneclassify").val("-1");
			$("#twoclassify").val("-1");
			$("#threeclassify").val("-1");
			$("#fileurl").val("");
			$("#thumbnailurl").val("");
			
			vm.getResource();
		},
		//批删
		del:function(){
			var ids = getSelectedRows();
			if(ids==null){
				alert("请选择删除项");
				return;
			}
			confirm("确定要删除选中的记录？",function(){
				$.ajax({
					url:baseURL + "activity/delAll",
					type:"POST",
					contentType: "application/json",
					data:JSON.stringify(ids),
					dataType:"json",
					success:function(r){
						if(r.code == 0){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				})
			})
		},
		//获取资源类行
		getResource:function(){
			$.get(baseURL + "activity/getResource/",function(r){
				vm.territoryList = r.getResource.territoryList;
				vm.ageList = r.getResource.ageList;
				vm.termList = r.getResource.termList;
			});
		},
		//清除缓存图片
		clearCache:function(){
			$.get(baseURL + "activity/clearCache/",function(r){
				alert(r.success)
			})
		},
		/*//修改校验序号格式是否正确
		onBlur:function(){
			vm.reso.sn = vm.count;
			var reg = /\d/;
			if(!reg.test(vm.reso.sn)){
				$(".sp1").html("<font color='red'>请填写数字</font>");
				return false;
			}else{
				$(".sp1").html("");
				return true;
			}
		},
		//校验名称格式是否正确
		onBlur1:function(){
			var reg = /^[\w\u4e00-\u9fa5]+$/;  //数字、字母、汉字
			//console.log(vm.reso.name);
			if(!vm.reso.name){
				$(".sp2").html("<font color='red'>不能为空</font>");
				return false;
			}else{
				if(!vm.reso.name | vm.reso.name.length<=0){
					$(".sp2").html("<font color='red'>不能为空</font>");
					return false;
				}else{
					$(".sp2").html("");
					if(!reg.test(vm.reso.name)){
						$(".sp2").html("<font color='red'>只能填写数字、字母和汉字</font>");
						return false;
					}else{
						$(".sp2").html("");
						return true;
					}
				}
			}
		},*/
		//新增 或 修改
		saveOrUpdate:function(){
			vm.activity.flag = flag;
			vm.activity.oneclassify = $("#oneclassify").val();
			vm.activity.classes = $("#twoclassify").val();
			vm.activity.term = $("#threeclassify").val();
			vm.activity.filesize = $("#filesize").val();
			var fileurl = $("#fileurl").val();
			var thumbnailurl = $("#thumbnailurl").val();
			var sf = $("#showFile").attr("src");
			var sm = $("#showImg").attr("src");
			//console.log("******",sf)
			if(sf==null && fileurl.length<=0){
				alert("请上传文件");
				$("#fileurl").val('')
				return;
			}
			if(sf==''){
				alert("请上传文件");
				$("#fileurl").val('')
				return;
			}
			if(sm==null && thumbnailurl.length<=0){
				alert("请上传图片");
				$("#thumbnailurl").val('')
				return;
			}
			if(sm==''){
				alert("请上传图片");
				$("#thumbnailurl").val('')
				return;
			}
			vm.activity.fileurl = fileurl;
			vm.activity.thumbnailurl = thumbnailurl;
			
			if(vm.activity.oneclassify!=null && vm.activity.oneclassify!='-1'){
				if(vm.activity.classes!=null && vm.activity.classes!='-1'){
					if(vm.activity.term!=null && vm.activity.term!='-1'){
						var url = vm.activity.id == null ? "activity/territorySave" : "activity/update";
						//alert(url);
						$.ajax({
							type: "POST",
						    url: baseURL + url,
			                contentType: "application/json",
						    data: JSON.stringify(vm.activity),
						    success: function(r){
						    	if(r.code === 0){
									alert('操作成功', function(){
										vm.reload();
									});
								}else{
									alert(r.msg);
								}
							}
						});
					}else{
						alert("请选择学期分类");
						return;
					}
				}else{
					alert("请选择年龄分类");
					return;
				}
			}else{
				alert("请选择一级分类");
				return;
			}
		},
		reload: function () {
			vm.showList = true;
			//$(".sp1").html("");
			//$(".sp2").html("");
			/*if($(".file-preview-thumbnails>div").length>0){
				window.location.reload();
			}*/
			$("#myFile").fileinput("clear");
			$("#myImg").fileinput("clear");
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{
                	'territory': $("#territory").val(),
                	'age': $("#age").val(),
                	'term':$("#term").val(),
                	'keyword': vm.q.keyword
                	},
                page:page
            }).trigger("reloadGrid");
		}
	}
})

//单个删除
function delOne(id){
	confirm('确定要删除选中的记录？', function(){
		$.ajax({
			type: "POST",
		    url: baseURL + "activity/delOne",
            contentType: "application/json",
		    data: JSON.stringify(id),
		    success: function(r){
				if(r.code == 0){
					alert('操作成功', function(){
                        vm.reload();
					});
				}else{
					alert(r.msg);
				}
			}
		});
	});
}
//修改
function toUpd(id){
	vm.showList = false;
	vm.title = "修改";
	$.ajax({
		type: "POST",
	    url: baseURL + "activity/showById",
        contentType: "application/json",
	    data: JSON.stringify(id),
	    success: function(r){
			vm.activity = r.activity;
			$("#oneclassify").val(vm.activity.oneclassify);
			$("#twoclassify").val(vm.activity.classes);
			$("#threeclassify").val(vm.activity.term);
			$("#filesize").val(vm.activity.filesize);
			$("#fileurl").val(vm.activity.fileurl);
			//console.log(vm.activity.fileurl)
			var fileurl = vm.activity.fileurl.split(";");
			//console.log(fileurl)
			$("#showFile").attr("src",fileurl[0]);
			//console.log(fileurl)
			for (var i = 1; i < fileurl.length-1; i++) {
				//console.log(fileurl[i])
				$("#showFile").parent().append("<img width='100px' height='100px' src='"+fileurl[i]+"'/>");
			}
			$("#thumbnailurl").val(vm.activity.thumbnailurl);
			$("#showImg").attr("src",vm.activity.thumbnailurl);
		}
	});
}

function showFiles(id){
	$.ajax({  
        type: 'post',  
        contentType: "application/json",
        url: baseURL + "activity/showFiles",//发送请求  
        //data: {"id":id},  
        data: JSON.stringify(id),  
        //dataType : "json",  
        success: function(result) {  
        	//console.log(result.html)
            var htmlCont = result.html;//返回的结果页面
        	var index = layer.open({  
                type: 1,//弹出框类型  
                title: '活动详情',  
                shadeClose: false, //点击遮罩关闭层  
                area : ['60%' , '90%'],  
                shift:1,  
                content: htmlCont,//将结果页面放入layer弹出层中  
                success: function(layero, index){ //弹框之前调用  
                    console.log(layero+"&&&&&&&&&&"+index);
                }
                
            });
            setTimeout(function () {
            	var other="";
            	$("#acContent>div>img").each(function(){
            		if($(this).attr("src").split(".")[$(this).attr("src").split(".").length-1]=="pdf"){
            			$(this).attr("src","http://localhost:8086/uploadFile/pdf.jpg");
            			//$(this).parent().append("<span style='display: block'>111</span>");
            		};
            		if($(this).attr("src").split(".")[$(this).attr("src").split(".").length-1]=="doc" || $(this).attr("src").split(".")[1]=="docx"){
            			$(this).attr("src","http://localhost:8086/uploadFile/word.jpg");
            		};
            		if($(this).attr("src").split(".")[$(this).attr("src").split(".").length-1]=="xls" || $(this).attr("src").split(".")[1]=="xlsx"){
            			$(this).attr("src","http://localhost:8086/uploadFile/excel.jpg");
            		};
            		if($(this).attr("src").split(".")[$(this).attr("src").split(".").length-1]=="avi" || $(this).attr("src").split(".")[1]=="wmv" || $(this).attr("src").split(".")[1]=="swf"){
            			$(this).attr("src","http://localhost:8086/uploadFile/void.jpg");
            		};
            		console.log("******"+$(this).attr("src"))
            	});
            	//alert("弹窗关闭")
            	//做完后关闭弹层
            	//layer.close(index);
            }, 1000);
        }  
      }); 
	
	/*$.post( baseURL + "activity/showFiles?id="+id+"", {}, function(str){
	  layer.open({
	    type: 1,
	    skin: 'layui-layer-rim', //加上边框
		area: ['420px', '240px'], //宽高
	    content: str //注意，如果str是object，那么需要字符拼接。
	  });
	});*/
}


