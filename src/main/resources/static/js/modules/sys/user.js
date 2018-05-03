$(function(){
	addressInit('cmbProvince', 'cmbCity', 'cmbArea'); 
	addressInit('cmbProvince1', 'cmbCity1', 'cmbArea1'); 
})

$(function(){
	$.get(baseURL + "sys/role/select", function(r){
		vm.roleList = r.list;
		/*var array = r.list;
		for (var i = 0; i < array.length; i++) {
			$("#roleflag").attr("id",array[i]);
			//console.log($("#roleflag").val());
		}*/
		//console.log("list",array)
		//alert(list);
	});
});

$(function(){
	$('#datetimepickerStart').datetimepicker({
	    //format: 'yyyy-mm-dd HH:ii:ss',
		format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayBtn: true
	});
    //结束时间
	$('#datetimepickerEnd').datetimepicker({
//	    format: 'yyyy-mm-dd HH:ii:ss',
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayBtn: true
	});
});

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true },
			{ label: '手机号', name: 'mobile', width: 70 },
			{ label: '用户名', name: 'username', width: 65 },
			{ label: '角色', name: 'rolename', width: 55 },
			{ label: '幼儿园名称', name: 'name', width: 105 },
            { label: '注册时间', name: 'showstartTime', index: "create_time", width: 55},
            { label: '会员类型', name: 'vitype', width: 45 },
            { label: '到期时间', name: 'showendTime', width: 55},
            { label: '所属区域', name: 'userAddress', width: 85},
           /* { label: '所属部门', name: 'deptName', width: 75 },*/
			{ label: '邮箱', name: 'email', width: 70 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">启用</span>';
			}}
			
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
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;


var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			username: null,
			userId:null,
			kinname:null,
			buytype:null,
			viptype:null,
			status:null,
			createTime:null,
			createTimeEnd:null,
			roleIdList:[]
		},
		showList: true,
		title:null,
		roleList:{},
		kinList:{},
		user:{
			status:1,
			deptId:null,
            deptName:null,
			roleIdList:[]
		}
	},
	methods: {
		query: function () {
			if($("#backing").attr("id")=="backing"){
				$("#backing").attr("id","nobacking");
			}
			  vm.reload();
		},
		reset: function () {
			/*vm.q.username=null;
			vm.q.userId=null;
			vm.q.kinname=null;
			vm.q.buytype=null;
			vm.q.viptype=null;
			vm.q.status=null;
			$("#datetimepickerStart").val('');
			$("#datetimepickerEnd").val('');
			vm.q.roleIdList=[];
			vm.query();*/
			window.location.reload()
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.roleList = {};
			vm.kinList = {};
			vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};
			
			//获取角色信息
			this.getRoleList();
			//获取幼儿园信息
			this.getKinList();
			//获取区域信息
			 $("#addArea").attr("style","display:show");
			vm.getDept();
			
		},
        getDept: function(){
            //加载部门树
            $.get(baseURL + "sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
				}
            })
        },
		update: function () {
			var userId = getSelectedRow();
			//alert(userId);
			if(userId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
            $("#addArea").attr("style","display:show");
			//获取用户信息
			vm.getUser(userId);
			//获取角色信息
			this.getRoleList();
			//获取幼儿园信息
			this.getKinList();
			
		},
		del: function () {
			var userIds = getSelectedRows();
			if(userIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
				    data: JSON.stringify(userIds),
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
		},
		saveOrUpdate: function () {
			//获取区域信息 判断是否选择
			var province = $("#cmbProvince1").val();
			var city = $("#cmbCity1").val();
			var county = $("#cmbArea1").val();
			if(province=="--请选择--" || city=="--请选择--" || county=="--请选择--"){
				alert("请完善区域选择");
				return;
			}
			//校验购买形式
			var buy = $("input[name='buy']:checked").val();
			if(!buy){
				alert("请选择购买形式");
				return;
			}
			//校验角色
			var names = $("input[name='names']:checked").val();
			if(!names){
				alert("请选择角色");
				return;
			}
			//校验vip类型
			var vip = $("input[name='vip']:checked").val();
			if(!vip){
				alert("请选择会员类型");
				return;
			}
			//获取区域信息存入user
			vm.user.province = province;
			vm.user.city = city;
			vm.user.county = county;
			//获取幼儿园名称的值
			var kinname = $("#kinname").val();
			vm.user.kindergartenId = kinname;
			if(kinname!=-1){
				var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
				$.ajax({
					type: "POST",
				    url: baseURL + url,
	                contentType: "application/json",
				    data: JSON.stringify(vm.user),
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
				alert("请选择幼儿园");
				return;
			}
			
		},
		getUser: function(userId){
			$.get(baseURL + "sys/user/info/"+userId, function(r){
				vm.user = r.user;
				vm.user.password = null;
				$("#kinname").val(vm.user.kindergartenId);
				$("#cmbProvince1").val(vm.user.province);
				//$("#cmbCity2").val(vm.user.city);
				//$("#cmbArea2").val(vm.user.county);
				$("#cmbCity1").html("<option value='"+vm.user.city+"'>"+vm.user.city+"</option>")
				$("#cmbArea1").html("<option value='"+vm.user.county+"'>"+vm.user.county+"</option>")
				//console.log("省",vm.user.province);
				//console.log("市",vm.user.city);
				//console.log("县",vm.user.county);
                vm.getDept();
			});
		},
		getRoleList: function(){
			$.get(baseURL + "sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		//获取幼儿园信息
		getKinList: function(){
			$.get(baseURL + "kindergarten/queryList", function(r){
				//console.log("r",r,r.kinList[0]);
				vm.kinList = r.kinList;
			});
		},
		//
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择单位",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
		reload: function () {
			vm.showList = true;
			//alert($("#datetimepickerStart").val())
			//时间区间查询值获取
			vm.q.createTime = $("#datetimepickerStart").val();
			vm.q.createTimeEnd = $("#datetimepickerEnd").val();
			//角色id集合
			var rolenames = vm.q.roleIdList;
			var ids="";
			for (var i = 0; i < rolenames.length; i++) {
				ids += ","+rolenames[i];
			}
			var roleids = ids.substring(1);
			//console.log("rolename",roleids);
			/*var array = vm.roleList;
			for (var i = 0; i < array.length; i++) {
				$("#roleflag").attr("id","role"+array[i].roleId);
			}*/
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{
                	'username': vm.q.username,
                	'userId':vm.q.userId,
                	'kinname':vm.q.kinname,
                	'buytype':vm.q.buytype,
                	'viptype':vm.q.viptype,
                	'status':vm.q.status,
                	'createTime':vm.q.createTime,
                	'createTimeEnd':vm.q.createTimeEnd,
                	'roleids':roleids,
                	"aprovince":$("#cmbProvince").val(),
                	"acity":$("#cmbCity").val(),
                	"acounty":$("#cmbArea").val(),
                	},
                page:page
            }).trigger("reloadGrid");
			if($("#backing").attr("id")=="nobacking"){
				$("#backing").attr("id","backing");
			}
			if($("#backing").attr("id")=="backing"){
				window.location.reload();
			}
			//console.log("",$("#adding").attr("id"))
		}
	}
});