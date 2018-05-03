$(function () {
	var flag;
    flag=location.href.split("?flag=")[1];
    if(flag=='hide'){
    	$(".hidepro").attr("style","display:none");
    }
    $("#jqGrid").jqGrid({
        url: baseURL + 'kindergarten/proqueryList',
        datatype: "json",
        colModel: [	
             //隐藏代理商id不展示
            {name: 'id', index: "id", width: 25, key: true,hidden:true },
     		{ label: '序号', name: 'proxyid',width: 25},
			{ label: '代理商名称', name: 'name', width: 70 },
			{ label: '代理商地址', name: 'kinAdderss', width: 85 },
			{ label: '代理商电话', name: 'phone', width: 55 },
			{ label: '代理幼儿园个数', name: 'scale', width: 55 },
			{ label: '创建时间', name: 'buytime', width: 55 },
			{ label: '修改时间', name: 'buytimeEnd', width: 55 },
			{ label: '操作',name: '',width: 85,
				  formatter: function(cellvalue, options, rowObject){
					  var other="";
					  if(flag!='hide'){
						  other+='<div class="hidepro" class="btn-group">'+
						  '<button style="margin-right:10px" type="button" class="btn btn-success dropdown-toggle" onclick=toUpd("'+rowObject.id+'")>修改</button>'+
						  '<button type="button" class="btn btn-danger dropdown-toggle" onclick=delOne("'+rowObject.id+'")>删除</button>'+ 
						  '</div>'
					  }
						return other;
				   }
			 }
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

//新增加载省市县
$(function () {
    //默认绑定省
    ProviceBind(1);
    //绑定事件
    $("#Province").change( function () {
        CityBind();
    });
    
    $("#City").change(function () {
        VillageBind();
    });
});

//查询加载省市县
$(function () {
    //默认绑定省
    QProviceBind(1);
    //绑定事件
    $("#QProvince").change( function () {
	    QCityBind();
    });
    
    $("#QCity").change(function () {
        QVillageBind();
    });
});



var vm = new Vue({
	el:'#rapp',
	data:{
		q:{
			proname:null,
			province:-1,
			city:-1,
			county:-1
		},
		showList: true,
		title:null,
		pro:{},
	},
	methods:{
		query:function(){
			this.reload();
		},
		reset:function(){
			vm.q.proname=null;
			vm.q.province=-1;
			vm.q.city=-1;
			vm.q.county=-1;
			$("#startTime").val('');
			$("#endTime").val('');
			vm.query();
		},
		add:function(){
			vm.showList = false;
			vm.title = "新增";
			vm.pro = {name:null,phone:null,province:-1, city:-1, county:-1};
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
					url:baseURL + "kindergarten/delAll",
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
		//校验电话
		onBlur:function(){
			var reg = /^0\d{2}-\d{8}$|^0\d{2}-\d{7}$|^0\d{3}-\d{7}$|^0\d{3}-\d{8}$/;  //座机
			var reg1 = /^1[3|4|5|8][0-9]\d{8}$/;  //手机号
			if(vm.pro.phone==null){
				$(".sp1").html("<font color='red'>电话号码或格式错误</font>");
				return false;
			}else{
				var phone = vm.pro.phone.trim().substring(0,1);
				if(phone==0){
					if(!reg.test(vm.pro.phone)){
						$(".sp1").html("<font color='red'>不能为空或座机格式错误, 格式 例：052(1)-(8)3856891  注:短杠前3或4位数字,短杠后7或8位数字</font>");
						return false;
					}else{
						$(".sp1").html("");
						return true;
					}
				}else if(phone==1){
					if(!reg1.test(vm.pro.phone)){
						$(".sp1").html("<font color='red'>手机号格式错误,以13、14、15、18开头后面9位数字</font>");
						return false;
					}else{
						$(".sp1").html("");
						return true;
					}
				}else{
					$(".sp1").html("<font color='red'>请填写正确的座机或手机号开头</font>");
					return false;
				}
			}
		},
		//新增 或 修改
		saveOrUpdate:function(){
			if(vm.pro.name==null){
				alert("请填写代理商名称");
				return;
			}else if(vm.pro.name.length<=0){
				alert("不能为空，请重新填写");
				return;
			};
			if(vm.pro.province=='-1' || vm.pro.city=='-1' || vm.pro.county=='-1'){
				alert("省市县必选");
				return;
			};
			//console.log(vm.pro.phone==undefined)
			if(vm.pro.phone==null){
				alert("请填写代理商电话");
				return;
			}else if(vm.pro.phone.length<=0){
				alert("不能为空，请重新填写");
				return;
			};
			if(!vm.onBlur()){
				return;
			}
			var url = vm.pro.id == null ? "kindergarten/prosave" : "kindergarten/proupdate";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.pro),
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
		},
		reload: function () {
			vm.showList = true;
			$(".sp1").html("");
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{
                	'proname': vm.q.proname,
                	'province': vm.q.province,
                	'city': vm.q.city,
                	'county': vm.q.county,
                	'startTime':$("#startTime").val(),
					'endTime':$("#endTime").val()
                	},
                page:page
            }).trigger("reloadGrid");
		}
	}
});

//单个删除
function delOne(id){
	confirm('确定要删除选中的记录？', function(){
		$.ajax({
			type: "POST",
		    url: baseURL + "kindergarten/delOne",
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
//修改回显
function toUpd(id){
	vm.showList = false;
	vm.title = "修改";
	/*$.get(baseURL + "kindergarten/showById/"+id,function(r){
		vm.kin = r.kin;
	})*/
	$.ajax({
		type: "GET",
	    url: baseURL + "kindergarten/showById/"+id,
        contentType: "application/json",
	    //data: JSON.stringify(id),
        async: false,
	    success: function(r){
			vm.pro = r.kin;
			//******省回显
			$("#Province option").each(function() {
              if($(this).val()==vm.pro.province){
            	 $("#Province").val(vm.pro.province)
                 $(this).attr("selected",true);
                 //console.log($(this).val()+"***"+vm.pro.province+"***"+vm.pro.city+"***"+vm.pro.county)
                //var sheng = $(this).val();
                CityBind(vm.pro.city,vm.pro.county);
              }
	        }) 
	        //******省回显
		}
	});
}

////////////////////
//省信息
function ProviceBind(id) {
    //清空下拉数据
    $("#Province").html("<option selected value='-1'>--请选择--</option>");
    $("#City").html("<option value='-1'>--请选择--</option>");
    $("#Village").html("<option value='-1'>--请选择--</option>");
    var str = "";
    $.ajax({
        type: "POST",
        url: baseURL + "kindergarten/queryPid",
        contentType: "application/json",
        data: JSON.stringify(id),
        dataType: "JSON",
        async: false,
        success: function (data) {
            //从服务器获取数据进行绑定
            $.each(data.Data, function (i, item) {
                str += "<option value=" + item.id + ">" + item.cityname + "</option>";
            })
            //将数据添加到省份这个下拉框里面
            $("#Province").append(str);
        },
        error: function () { alert("Error"); }
    });
}
//市信息
function CityBind(city,country) {
	//console.log($("#Province").val()+"******"+city)
    var provice = $("#Province").val();
    //判断省份这个下拉框选中的值是否为空
    if (provice == -1) {
    	vm.pro.city=-1;
    	vm.pro.county=-1;
    	$("#City").html("<option value='-1'>--请选择--</option>");
        $("#Village").html("<option value='-1'>--请选择--</option>");
        return;
    }
    //console.log(provice)
    //$("#City").html("<option value='-1'>--请选择--</option>");
    //$("#Village").html("<option value='-1'>--请选择--</option>");
    var str = "";
    $.ajax({
        type: "POST",
        url: baseURL + "kindergarten/queryPid",
        contentType: "application/json",
        data:JSON.stringify(provice),
        dataType: "JSON",
        async: false,
        success: function (data) {
        	vm.pro.city=-1;
        	vm.pro.county=-1;
        	$("#City").html("<option value='-1'>--请选择--</option>");
            $("#Village").html("<option value='-1'>--请选择--</option>");
            //从服务器获取数据进行绑定
            $.each(data.Data, function (i, item) {
                str += "<option value=" + item.id + ">" + item.cityname + "</option>";
            })
            //将数据添加到省份这个下拉框里面
            $("#City").append(str);  
            //**********市回显
            if(city!=-1){
                $("#City option").each(function(){
                    if($(this).val()==city){
                    	vm.pro.city = city;
                        $(this).attr("selected",true);
                        VillageBind(country)                 
                    }
                })
            }
            //**********市回显
        },
        error: function () { alert("Error"); }
    });
}
//县信息
function VillageBind(country) {
    var provice = $("#City").val();
    //判断市这个下拉框选中的值是否为空
    if (provice == -1) {
    	vm.pro.county=-1;
    	$("#Village").html("<option value='-1'>--请选择--</option>");
        return;
    }
    //$("#Village").html("<option value='-1'>--请选择--</option>");
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: baseURL + "kindergarten/queryPid",
        contentType: "application/json",
        data:JSON.stringify(provice),
        dataType: "JSON",
        async: false,
        success: function (data) {
        	vm.pro.county=-1;
        	$("#Village").html("<option value='-1'>--请选择--</option>");
            //从服务器获取数据进行绑定
            $.each(data.Data, function (i, item) {
                str += "<option value=" + item.id + ">" + item.cityname + "</option>";
            })
            //将数据添加到省份这个下拉框里面
            $("#Village").append(str);
            //**********县回显
            if(country!=-1){
                $("#Village option").each(function(){
                    if($(this).val()==country){
                    	vm.pro.county = country;
                        $(this).attr("selected",true);
                    }
                })
            }
           //**********县回显
        },
        error: function () { alert("Error"); }
    });
}

//*********************************省市县条件查询*************************
//省信息
function QProviceBind(id) {
    //清空下拉数据
    $("#QProvince").html("<option selected value='-1'>--请选择--</option>");
    $("#QCity").html("<option value='-1'>--请选择--</option>");
    $("#QVillage").html("<option value='-1'>--请选择--</option>");
    var str = "";
    $.ajax({
        type: "POST",
        url: baseURL + "kindergarten/queryPid",
        contentType: "application/json",
        data: JSON.stringify(id),
        dataType: "JSON",
        async: false,
        success: function (data) {
            //从服务器获取数据进行绑定
            $.each(data.Data, function (i, item) {
                str += "<option value=" + item.id + ">" + item.cityname + "</option>";
            })
            //将数据添加到省份这个下拉框里面
            $("#QProvince").append(str);
        },
        error: function () { alert("Error"); }
    });
}
//市信息
function QCityBind() {
    var provice = $("#QProvince").val();
    //判断省份这个下拉框选中的值是否为空
    if (provice == -1) {
    	vm.q.city=-1;
    	vm.q.county=-1;
    	$("#QCity").html("<option value='-1'>--请选择--</option>");
        $("#QVillage").html("<option value='-1'>--请选择--</option>");
        return;
    }
    var str = "";
    $.ajax({
        type: "POST",
        url: baseURL + "kindergarten/queryPid",
        contentType: "application/json",
        data:JSON.stringify(provice),
        dataType: "JSON",
        async: false,
        success: function (data) {
        	vm.q.city=-1;
        	vm.q.county=-1;
        	$("#QCity").html("<option value='-1'>--请选择--</option>");
            $("#QVillage").html("<option value='-1'>--请选择--</option>");
            //从服务器获取数据进行绑定
            $.each(data.Data, function (i, item) {
                str += "<option value=" + item.id + ">" + item.cityname + "</option>";
            })
            //将数据添加到省份这个下拉框里面
            $("#QCity").append(str);  
        },
        error: function () { alert("Error"); }
    });
}
//县信息
function QVillageBind() {
    var provice = $("#QCity").val();
    //判断市这个下拉框选中的值是否为空
    if (provice == -1) {
    	vm.q.county=-1;
    	$("#QVillage").html("<option value='-1'>--请选择--</option>");
        return;
    }
    var str = "";
    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
    $.ajax({
        type: "POST",
        url: baseURL + "kindergarten/queryPid",
        contentType: "application/json",
        data:JSON.stringify(provice),
        dataType: "JSON",
        async: false,
        success: function (data) {
        	vm.q.county=-1;
        	$("#QVillage").html("<option value='-1'>--请选择--</option>");
            //从服务器获取数据进行绑定
            $.each(data.Data, function (i, item) {
                str += "<option value=" + item.id + ">" + item.cityname + "</option>";
            })
            //将数据添加到省份这个下拉框里面
            $("#QVillage").append(str);
        },
        error: function () { alert("Error"); }
    });
}