$(function(){
	$("#province").live("change", function(){
		getCityList($(this).val());
	});
	$("#file").live("change", function(){
		uploadFloorImg();
	});
})

// 城市二级联动
function getCityList(parent_id){
	var html_options = "";
	$.post(
		"/brand.php?app=brandShop&act=getCityList",
		{"parent_id":parent_id},
		function(data){
			for(i in data){
				html_options += '<option value="'+i+'">'+data[i]+'</option>';
			}
			$("#city").html(html_options);
		},
		'json'
	);
}

// 校验 分店添加
function checkShopAdd(data){
	var error_num = 0;
	if (data.agent_id==0&&data.shop_style=="2") {
		showError("agent_id", "请选择隶属公司 ！", error_num==0);
		error_num++;
	}
	if (!data.branch_name) {
		showError("branch_name", "请输入分店名 ！", error_num==0);
		error_num++;
	}
	if (data.phone) {
		if (!isPhone(data.phone.replace(/(^\s*)|(\s*$)/g, ""))) {
			showError("phone","联系电话格式不正确 ！",error_num==0);
			error_num++;
		}
	}
	if (error_num>0) {
		return false;
	}
	return true;
}

function showResult(data){
	if (data.success) {
		var $location = "/brand.php?app=brandShop&act=addSuccess";
		if (data.data.id) {
			$location += "&id="+data.data.id;
		}
		$location += "&agent_id="+data.data.agent_id+"&brand_id="+data.data.brand_id;
		location.href = $location;
	}else{
		if (data.data.name) {
			showError(data.data.name, data.msg, 1);
		}else{
			alert(data.msg);	
		}
	}
}

function refreshBase(data){
	if (data.success) {
		alert(data.msg);
		reloadPage("#baseArea");
	}else{
		if (data.data.name) {
			showError(data.data.name, data.msg, 1);
		}else{
			alert(data.msg);	
		}
	}
}

function checkDetail(data) {
	return true;
}

function refreshDetail(data) {
	if (data.success) {
		alert(data.msg);
		reloadPage("#detailArea");
	}else{
		alert(data.msg);
	}
}

function uploadFloorImg(){
	$.ajaxFileUpload({
		url:'/brand.php?app=brandShop&act=uploadFloorImg',       //需要链接到服务器地址
		secureuri:false,
		fileElementId:"file",                            //文件选择框的id属性
		dataType: 'json',                                   //服务器返回的格式，可以是json
		success: function (data, textStatus) {               //相当于java中try语句块的用法
			if (data.success) {
				$("#floor_img_view").show();
				$("#floor_img_view img").attr("src", data.data.img_url);
				$("#floor_img_url").val(data.data.img_url);
			}else{
				alert(data.msg);
			}
		},
		error: function (data, status, e) {           //相当于java中catch语句块的用法
			alert("error");
		}
	});	
}

function checkDialogAddAgent(data) {
	var error_num = 0;
	if (!data.info.name) {
		showError("info[name]", "公司名称 ！", error_num==0);
		error_num++;
	}
	if (!data.relation.region) {
		showError("relation[region]", "请输入代理区域", error_num==0);
		error_num++;
	}
	if (error_num>0) {
		return false;
	}
	return true;
}

function finishDialogAddAgent(data) {
	var agent_name = $("#agent_name").val();
	if (data.success) {
		$("#agent_list").append('<option value="'+data.data.agent_id+'">'+agent_name+'</option>');
		$(".ui-dialog-close").click();
		alert("代理商创建成功！");
		$("#agent_list").val(data.data.agent_id);
	}else{
		alert(data.msg);
	}
}