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
			{ label: '活动名称', name: '', width: 50 ,
				formatter: function(cellvalue, options, rowObject){
					return "<img width='100px' height='100px' src='"+rowObject.thumbnailurl+"'/>" +
							"<span  style='display: block;'>"+rowObject.activityname+"</span>";
		        }
			},
			{ label: '文件路径', name: 'fileurl', width: 65 },
			{ label: '缩略图路径', name: 'thumbnailurl', width: 55 },
			{ label: '搜索关键字', name: 'keyword', width: 55 },
			{ label: '类别', name: 'showType', width: 55 },
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
		vm.courseList = r.getResource.courseList;
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
		courseList:[],
		termList:[]
	},
	methods:{
		query:function(){
			this.reload();
		},
		reset:function(){
			$("#course").val('-1');
			$("#term").val('-1');
			vm.q.keyword=null;
			vm.query();
		},
		add:function(){
			vm.showList = false;
			vm.title = "新增";
			vm.activity = {};
			$("#addcourse").val('-1');
			$("#addterm").val("-1");
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
				vm.courseList = r.getResource.courseList;
				vm.termList = r.getResource.termList;
			});
		},
		//新增 或 修改
		saveOrUpdate:function(){
			vm.activity.flag = flag;
			vm.activity.territory = $("#addcourse").val();
			vm.activity.term = $("#addterm").val();
			
			var fileurl = $("#fileurl").val();
			var thumbnailurl = $("#thumbnailurl").val();
			var sf = $("#showFile").attr("src");
			var sm = $("#showImg").attr("src");
			//console.log(sf)
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
			
			if(vm.activity.territory!=null && vm.activity.territory!='-1'){
				if(vm.activity.term!=null && vm.activity.term!='-1'){
					var url = vm.activity.id == null ? "activity/territorySave" : "activity/update";
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
				alert("请选择入学准备分类");
				return;
			}
		},
		reload: function () {
			vm.showList = true;
			$("#myFile").fileinput("clear");
			$("#myImg").fileinput("clear");
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{
                	'course': $("#course").val(),
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
			$("#addcourse").val(vm.activity.territory);
			$("#addterm").val(vm.activity.term);
			
			$("#fileurl").val(vm.activity.fileurl);
			var fileurl = vm.activity.fileurl.split(";");
			$("#showFile").attr("src",fileurl[0]);
			for (var i = 1; i < fileurl.length-1; i++) {
				$("#showFile").parent().append("<img width='100px' height='100px' src='"+fileurl[i]+"'/>");
			}
			$("#thumbnailurl").val(vm.activity.thumbnailurl);
			$("#showImg").attr("src",vm.activity.thumbnailurl);
		}
	});
}

