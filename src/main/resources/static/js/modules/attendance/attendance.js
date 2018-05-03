$(function () {
	
    $("#jqGrid").jqGrid({
        url: baseURL + 'attendance/queryList',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', index: "id", width: 45,hidden:true },
			{ label: '日期', name: 'showTime', width: 40 },
			{ label: '星期', name: 'week', width: 30 },
			{ label: '用户名', name: 'userName', width: 30 },
			{ label: '部门名称', name: 'deptname', width: 33 },
			{ label: '签到时间', name: 'checkin', width: 70 },
			{ label: '签到状态', name: 'statusin', width: 30 },
			{ label: '签退时间', name: 'checkout', width: 65 },
			{ label: '签退状态', name: 'statusout', width: 30 },
			{ label: '工时', name: 'workTime', width: 55 },
			{ label: '备注', name: 'remark', width: 55 },
			/*{ label: '备注', name: '', width: 55,
				formatter: function(cellvalue, options, rowObject){
					  var other="";
					  other+='<div class="btn-group">'+
						  '<input class="editremark" type="text" value="'+rowObject.remark+'" readonly onclick=edit()></input>'+
						  '</div>'
						return other;
		        	}
			},*/
			/*{ label: '操作',name: '',width: 55,
				  formatter: function(cellvalue, options, rowObject){
					  var other="";
					  other+='<div class="btn-group">'+
						  '<button style="margin-right:10px" type="button" class="btn btn-success dropdown-toggle" onclick=toUpd("'+rowObject.id+'")>修改</button>'+
						  '<button type="button" class="btn btn-danger dropdown-toggle" onclick=delOne("'+rowObject.id+'")>删除</button>'+ 
						  '</div>'
						return other;
		        	}
			  },*/
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
        },
        loadComplete: function(){ //加载完成（初始加载），回调函数 
        	$.get(baseURL + "attendance/getuser",function(r){
        		//alert(r.user)
        		if(r.user=="admin"){
        			$("#allshow").attr("style","float: none;margin-bottom: 10px")
        			$("#adminshow").attr("style","display:show;float: left")
        			//$("#remark").attr("style","display:none")
        		}
        	})
        }
    });
    //隐藏父id一列
    //$("#jqGrid").setGridParam().hideCol("parentid").trigger("reloadGrid");
});

//条件查询日期加载
$(function(){
	$('#monthly').monthpicker({
		years: [2018,2024,2023,2022,2021,2020,2019,2017],
        topOffset: 6
	})
});

//条件查询部门加载
$(function(){
	$.get(baseURL + "attendance/getDept?deptid=1",function(r){
		vm.deptList = r.deptList;
		$("#deptid").bind("change",function () {  
            if (this.value == "9"){
            	var deptid2 = this.value;
            	$.get(baseURL + "attendance/getDept?deptid="+deptid2+"",function(r){
            		vm.deptList2 = r.deptList;
            		//$("#deptid").val('-1');
            		$("#deptid2").attr("disabled",false)
            	})
            }else{
            	$("#deptid2").val("-1")
            	$("#deptid2").attr("disabled",true)
            }  
        }) 
	})
})

var vm = new Vue({
	el:'#rapp',
	data:{
		showList: true,
		title:null,
		q:{
			queryname:null,
			querystatusin:null,
			querystatusout:null
		},
		deptList:{},
		deptList2:{}
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
			vm.q.queryname=null;
			vm.q.querystatusin=null;
			vm.q.querystatusout=null;
			$("#monthly").attr("value","");
			$("#deptid").val('-1'),
			$("#deptid2").val('-1'),
			$("#deptid2").attr("disabled",true),
			$("#queryday").val('')
			vm.query();
		},
		//签到
		checkin:function(){
			$.get(baseURL + "attendance/checkin/",function(r){
				alert(r.chenkin,function(){
                    vm.reload();
				});
			})
		},
		//签退
		checkout:function(){
			$.get(baseURL + "attendance/checkout/",function(r){
				alert(r.chenkout,function(){
                    vm.reload();
				});
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
                	 'queryname': vm.q.queryname,
                	 'querystatusin': vm.q.querystatusin,
                	 'querystatusout': vm.q.querystatusout,
                	 'querymonth': $("#monthly").val(),
                	 'deptid': $("#deptid").val(),
                	 'deptid2': $("#deptid2").val(),
                	 'queryday': $("#queryday").val()
                	},
                page:page
            }).trigger("reloadGrid");
		}
	}
})


//编辑备注
function edit(){
	$("td").each(function(){
		var remark = $(this).attr("aria-describedby");
		if(remark=='jqGrid_remark'){
			$(this).click(function(event){ 
			  //td中已经有了input,则不需要响应点击事件
			  if($(this).children("input").length > 0) 
			   return false; 
			  var tdObj = $(this); 
			  var preText = tdObj.html();
			  //得到当前文本内容 
			  var inputObj = $("<input type='text' />");
			  //创建一个文本框元素 
			  tdObj.html(""); //清空td中的所有元素 
			  inputObj 
			   .width(tdObj.width())
			   //设置文本框宽度与td相同 
			   .height(tdObj.height()) 
			   .css({border:"0px",fontSize:"17px",font:"宋体"})
			   .val(preText) 
			   .appendTo(tdObj)
			   //把创建的文本框插入到tdObj子节点的最后
			   .trigger("focus")
			   //用trigger方法触发事件 
			   .trigger("select"); 
			  inputObj.keyup(function(event){ 
			   if(13 == event.which)
			   //用户按下回车 
			   { 
			    var text = $(this).val(); 
			    tdObj.html(text); 
			    //*****发送事件
			    var attid = $("#jqGrid").jqGrid('getGridParam','selrow'); //获取当前行ID
			    //console.log("*****"+attid)
			    var data = {"id":attid,"remark":text}
			    //console.log(data)
			    $.ajax({
					type: "POST",
				    url: baseURL + "attendance/update",
			        contentType: "application/json",
				    data: JSON.stringify(data),
				    success: function(r){
				    	alert(r.success);
					}
				});
			    //**************
			   } 
			   else if(27 == event.which)
			   //ESC键 
			   { 
			    tdObj.html(preText); 
			   } 
			  }); 
			  //已进入编辑状态后，不再处理click事件 
			  inputObj.click(function(){ 
				  //alert("111")
			   return false; 
			  }); 
			 }); 
		}
	})
	 
}



function edit1(){
	var count=1;
	var count2=1;
	var count3=1;
	var count4=1;
	$("td").each(function(){
		var remark = $(this).attr("aria-describedby");
		if(remark=='jqGrid_remark'){
			$(this).attr("class","jqremark"+count++);
			//获取class为caname的元素 
			var td = $(".jqremark"+count2++); 
			//var txt = td.text(); 
			var txt = $(this).attr("title"); 
			//console.log("******"+txt)
			var input = $("<input class='inputc' type='text' value='" + txt + "'/>"); 
			td.html(input); 
			input.click(function() { return false; }); 
			//获取焦点 
			input.trigger("focus"); 
			//文本框失去焦点后提交内容，重新变为文本 
			input.blur(function() { 
			var newtxt = $(this).val(); 
				//判断文本有没有修改 
				if (newtxt != txt) { 
					td.html(newtxt);
				}
			})
		}
	})
}
