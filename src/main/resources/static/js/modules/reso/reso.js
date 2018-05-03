var flag;
$(function () {
	flag = location.href.split("?flag=")[1];
    $("#jqGrid").jqGrid({
        url: baseURL + 'reso/queryList?flag='+flag,
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: "id", width: 45, key: true },
			{ label: '分类名称', name: 'name', width: 70 },
			{ label: '顺序号', name: 'sn', width: 65 },
			{ label: '创建时间', name: 'createTime', width: 55 },
			{ label: '修改时间', name: 'updateTime', width: 55 },
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
			  //隐藏父id不展示
			  { name: 'parentid',hidden:true}
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
    //隐藏父id一列
    //$("#jqGrid").setGridParam().hideCol("parentid").trigger("reloadGrid");
});

var vm = new Vue({
	el:'#rapp',
	data:{
		showList: true,
		title:null,
		navTitle:"www",
		reso: {},
		count:0
	},
	methods:{
		add:function(){
			vm.showList = false;
			vm.title = "新增";
			var jq = $("#jqGrid");
			var array = jq.jqGrid('getRowData'); //获取当前显示的记录(总条数 -1)，因为最后一行不显示
			//如果获取列表为空，则将截取的flag作为父id
			if(array.length==0){
				var parentid = flag;
			}
			//遍历数组，主要是获取父id值
			for (var i = 0; i < array.length; i++) {
				//因为列表展示的父id是一样的，所以只获取第一条记录的父id
				var parentid = array[0].parentid;
			}
			//获取查询得到的总记录数量
			var rows = jq.jqGrid('getGridParam', 'records'); 
			//console.log(rows);
			//console.log(array);
			//console.log(parentid);
			//在添加时 主要展示顺序号是根据总条数递增的
			vm.count = rows+1;
			
			//遍历div 相同的class标签，根据点击不同的展示页面
			/*$("div[class='idnames']").each(function(){
				//遍历id的值
				var id = $(this).attr("id");
				//console.log(id);
				//判断id值是否有父id对应，条件符合则显示相应的添加页面
				if(id==parentid){
					$(this).attr("style","display: show")
				}
			});*/
			
			if(flag==0){
				vm.navTitle = "主分类"
			}else if(flag==1){
				vm.navTitle = "领域分类"
			}else if(flag==2){
				vm.navTitle = "年龄分类"
			}else if(flag==3){
				vm.navTitle = "资源类行分类"
			}else if(flag==4){
				vm.navTitle = "特色课程分类"
			}else{
				vm.navTitle = "暂无分类"
			}
			//console.log("###"+vm.navTitle);
				
			vm.reso = {};
		},
		//批删
		del:function(){
			var resoIds = getSelectedRows();
			if(resoIds==null){
				alert("请选择删除项");
				return;
			}
			confirm("确定要删除选中的记录？",function(){
				$.ajax({
					url:baseURL + "reso/delAll",
					type:"POST",
					contentType: "application/json",
					data:JSON.stringify(resoIds),
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
		//修改校验序号格式是否正确
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
		},
		//新增 或 修改
		saveOrUpdate:function(){
			vm.reso.sn = vm.count;
			vm.reso.parentid = flag;
			if(!vm.reso.name){
				alert("不要有空值！");
				return;
			}else{
				if(vm.onBlur() && vm.onBlur1()){
					var url = vm.reso.id == null ? "reso/save" : "reso/update";
					//alert(url);
					$.ajax({
						type: "POST",
					    url: baseURL + url,
		                contentType: "application/json",
					    data: JSON.stringify(vm.reso),
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
					alert("格式错误！");
					//console.log(vm.reso.name)
					return;
				}
			}
		},
		reload: function () {
			vm.showList = true;
			$(".sp1").html("");
			$(".sp2").html("");
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{
                	//'username': vm.q.username
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
		    url: baseURL + "reso/delOne",
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
	    url: baseURL + "reso/showById",
        contentType: "application/json",
	    data: JSON.stringify(id),
	    success: function(r){
			vm.reso = r.reso;
			vm.count = r.reso.sn;
			
			$("#orderby").attr("disabled",false);
			if(flag==0){
				vm.navTitle = "主分类"
			}else if(flag==1){
				vm.navTitle = "领域分类"
			}else if(flag==2){
				vm.navTitle = "年龄分类"
			}else if(flag==3){
				vm.navTitle = "资源类行分类"
			}else if(flag==4){
				vm.navTitle = "特色课程分类"
			}else{
				vm.navTitle = "暂无分类"
			}
			
			//遍历div 相同的class标签，根据点击不同的展示页面
			$("div[class='idnames']").each(function(){
				//遍历id的值
				var id = $(this).attr("id");
				//console.log(id);
				//判断id值是否有父id对应，条件符合则显示相应的添加页面
				if(id==vm.reso.parentid){
					$(this).attr("style","display: show");
					//将顺序号释放可修改
					$(this).find("input").attr("disabled",false);
				}
			});
		}
	});
}

