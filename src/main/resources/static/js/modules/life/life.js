var baseURL = "/"
$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'life/queryList',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: "id", width: 45,hidden:true },
			{ label: '日期', name: 'date', width: 35 },
			{ label: '星期', name: 'week', width: 28 },
			{ label: '用户名', name: 'username', width: 25 },
			{ label: '类型', name: 'typetext', width: 20 },
			{ label: '说明', name: 'text', width: 65 },
			{ label: '收/支', name: 'money', width: 30,cellattr: addCellAttr },
			{name: "cblb", align: 'left', width:20,hidegrid:false},  
            {name: "total", align: 'left', width:25,scrollrows:false},
			{ label: '创建时间', name: 'createtime', width: 57 },
			{ label: '修改时间', name: 'updatetime', width: 57 },
			{ label: '操作',name: '',width: 55,
				  formatter: function(cellvalue, options, rowObject){
					  var other="";
					  other+='<div class="btn-group">'+
						  '<button style="margin-right:10px" type="button" class="btn btn-success dropdown-toggle" onclick=toUpd("'+rowObject.id+'")>修改</button>'+
						  '<button type="button" class="btn btn-danger dropdown-toggle" onclick=delOne("'+rowObject.id+'")>删除</button>'+ 
						  '</div>'
						return other;
		        	}
			  }
        ],
        footerrow:true,
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
        },
        caption: "伸缩" ,
        /*loadComplete: function(){ //加载完成（初始加载），回调函数 
        	$.get(baseURL + "attendance/getuser",function(r){
        		//alert(r.user)
        		if(r.user=="admin"){
        			$("#allshow").attr("style","float: none;margin-bottom: 10px")
        			$("#adminshow").attr("style","display:show;float: left")
        			//$("#remark").attr("style","display:none")
        		}
        	})
        }*/
        //合计方法一:
        gridComplete: function () {
            var rowNum = parseInt($(this).getGridParam('records'), 10);
            if (rowNum > 0) {
                    var total = jQuery(this).getCol('money', false, 'sum');
                    $(this).footerData("set", { "cblb": "<font color='red'>合计:<font>", "total": "<font color='red'>" + total + "￥<font>"});
              } 
        },
       //合计方法二(1):
      //gridComplete: completeMethod,
    });
    //(2):
    /*function completeMethod(){
        var sum_total=$("#jqGrid").getCol('money',false,'sum');
        $("#jqGrid").footerData('set', { "cblb": '合计:', total: sum_total });
    }*/
    
    //隐藏父id一列
    //$("#jqGrid").setGridParam().hideCol("parentid").trigger("reloadGrid");
});



function addCellAttr(rowId, val, rawObject, cm, rdata) {
    if(rawObject.type == '0' ){
        return "style='color:green'";
    }else{
    	return "style='color:red'";
    }
}

//条件查询日期加载
/*$(function(){
	$('#monthly').monthpicker({
		years: [2018,2024,2023,2022,2021,2020,2019,2017],
        topOffset: 6
	})
});*/


var vm = new Vue({
	el:'#rapp',
	data:{
		showList: true,
		title:null,
		q:{
			type:null,
		},
		life:{}
	},
	methods:{
		//查询
		query:function(){
			//console.log("*******"+vm.q.querystatusin)
			//console.log("*******"+$("#queryday").val())
			vm.reload();
		},
		//重置
		reset:function(){
			$("#startTime").val('');
			$("#endTime").val('');
			vm.q.type=null;
			vm.query();
		},
		//批删
		del:function(){
			var ids = getSelectedRows();
			if(ids==null){
				alert("请选择删除项");
				return;
			}
			//console.log(ids)
			confirm("确定要删除选中的记录？",function(){
				$.ajax({
					url:baseURL + "life/deleteAll",
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
		poiout:function(){
			/*var postData = {
				           	 "queryname": vm.q.queryname,
				           	 "querymonth": $("#monthly").val(),
				           	 "deptid": $("#deptid").val(),
				           	 "deptid2": $("#deptid2").val()
				           	}
			$.ajax({
				//url:baseURL + "attendance/poiout",
				type:"POST",
				contentType: "application/json",
				data:JSON.stringify(postData),
				dataType:"json",
				success:function(){
					alert("Yes")
					location.href = location;
				}
			})*/
			//var params = "queryname="+vm.q.queryname+"&querymonth="+$("#monthly").val()+"&deptid="+$("#deptid").val()+"&deptid2="+$("#deptid2").val()+"&querystatusin="+vm.q.querystatusin+"&querystatusout="+vm.q.querystatusout+"";
			var params = "queryname="+vm.q.queryname+
					     "&querymonth="+$("#monthly").val()+
					     "&deptid="+$("#deptid").val()+
					     "&deptid2="+$("#deptid2").val()+
					     "&querystatusin="+vm.q.querystatusin+
					     "&querystatusout="+vm.q.querystatusout+
					     "&queryday="+$("#queryday").val();
			location.href= baseURL + "attendance/poiout?"+params;
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{
                	'startTime': $("#startTime").val(),
                	'endTime': $("#endTime").val(),
                	 'type': vm.q.type,
                	},
                page:page
            }).trigger("reloadGrid");
		}
	}
})


//新增
function add(){
	var index = layer.open({  
		type: 2,  
		title:'添加费用记录',
		btn: ['提交', '取消'],
		area: ['60%', '60%'],  
		fixed: false, //不固定  
		maxmin: true,  
		content: baseURL + '../modules/life/add.html',  
		//cancel: function(index){ return true; } 
		/*yes: function(index, layero){
		    //do something
		    //layer.close(index); //如果设定了yes回调，需进行手工关闭
		}*/
		yes: function(index, layero){
			vm.life.date = layer.getChildFrame('#date', index).val();
			vm.life.text = layer.getChildFrame('#text', index).val();
			vm.life.money = layer.getChildFrame("#money", index).val();
			vm.life.type = layer.getChildFrame("input[name='type']:checked", index).val();
			//console.log(vm.life.date+"*************")
			$.ajax({
				type: "POST",
			    url: baseURL + "life/insert",
		        contentType: "application/json",
		        data: JSON.stringify(vm.life),
			    success: function(r){
			    	layer.close(index)
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
			
		}
	});
}

//单个删除
function delOne(id){
	confirm("确定要删除选中的记录？",function(){
		$.ajax({
			url:baseURL + "life/deleteOne",
			type:"POST",
			contentType: "application/json",
			data:JSON.stringify(id),
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
 }

//修改回显
function toUpd(id){
	$.get(baseURL + "life/showById?id="+id,function(r){
		var index = layer.open({  
			type: 2,  
			title:'添加费用记录',
			btn: ['提交', '取消'],
			area: ['60%', '60%'],  
			fixed: false, //不固定  
			maxmin: true,  
			content: baseURL + '../modules/life/add.html',  
			success: function(layero, index){
				layer.getChildFrame('#date', index).val(r.Living.date);
				layer.getChildFrame('#text', index).val(r.Living.text);
				layer.getChildFrame("#money", index).val(r.Living.money);
				layer.getChildFrame("input[name='type'][value="+r.Living.type+"]", index).attr("checked",true);
			},
			yes: function(index, layero){
				vm.life.id = r.Living.id;
				vm.life.date = layer.getChildFrame('#date', index).val();
				vm.life.text = layer.getChildFrame('#text', index).val();
				vm.life.money = layer.getChildFrame('#money', index).val();
				vm.life.type = layer.getChildFrame("input[name='type']:checked", index).val();
				//console.log(vm.life.date+"*************")
				$.ajax({
					type: "POST",
				    url: baseURL + "life/update",
			        contentType: "application/json",
			        data: JSON.stringify(vm.life),
				    success: function(r){
				    	layer.close(index)
				    	if(r.code === 0){
							alert('操作成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			}
		});
	})
}

/*function saveOrUpdate(){
	//alert("1111")
	//alert($("#startTime").val())
	vm.life.money = $("#text").val();
	alert(vm.life.money)
	//console.log($("#startTime").val())
	$.ajax({
		type: "POST",
	    url: baseURL + "life/insert",
        contentType: "application/json",
        //data:$("#saveOrupdate").serialize(),
        data:{"text":text},
	    //data: JSON.stringify($("#saveOrupdate").serialize()),
	    success: function(r){
	    	//layer.close(index)
	    	if(r.code === 0){
				alert('操作成功', function(){
					//vm.reload();
				});
			}else{
				alert(r.msg);
			}
		}
	});
}*/

