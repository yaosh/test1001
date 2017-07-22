function checkBaseEdit(data){
	var error_num = 0;
	if (!data.name) {
		showError("name","请输入公司名称 ！", error_num==0);
		error_num++;
	}

	if (data.website) {
		if (!isUrl(data.website)) {
			showError("website", "网地格式不正确 ！", error_num==0);
			error_num++;
		}
	}

	if (data.phone) {
		if (!isPhone(data.phone.replace(/(^\s*)|(\s*$)/g, ""))) {
			showError("phone", "电话号码格式不正确 ！", error_num==0);
			error_num++;
		}
	}
	if (data.fax) {
		if (!isPhone(data.fax.replace(/(^\s*)|(\s*$)/g, ""))) {
			showError("fax", "传真号码格式不正确 ！", error_num==0);
			error_num++;
		}
	}
	if (error_num>0) {
		return false;
	}
	return true;
}

function  finishBaseEdit(data) {
	alert(data.msg);
	if (data.success) {
		reloadPage("#baseArea");
	}
}

function addAgentBrand() {
	var brand_arr = new Array();
	var brand_ids = ""; 
	var agent_id = $("#agent_id").val();
	$("input:checkbox:checked").each(function(){
		brand_arr.push($(this).val());
	});
	brand_ids = brand_arr.join();
	if (!brand_ids) {
		alert("请选中要添加的品牌！");
		return;
	}
	var brand_tr;
	$.post(
		"/brand.php?app=brandAgent&act=addAgentBrand&agent_id="+agent_id,
		{"ids":brand_ids},
		function(data) {
			alert(data.msg);
			if (data.success) {
				reloadPage("#brandArea", window.parent.window);
			}
		},
		'json'
	);
}


//删除区域代理
function deleteAreaAgent(id){
	if (!confirm("确认删除该区域代理")) {
		return false;
	}
	$.post(
		'/brand.php?app=areaAgent&act=delete',
		{"id": id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".agent-"+id).hide("normal");
			}
		},
		'json'
	);
}


function checkAddContact(data) {
	var error_num = 0;
	if (!data.info.name) {
		showError("info[name]", "请输入公司名称 ！", error_num==0);
		error_num++;
	}
	if (!data.relation.region) {
		showError("relation[region]", "请输入代理区域 ！", error_num==0);
		error_num++;
	}
	if (error_num>0) {
		return false;
	}
	return true;
}

function finishAddContact(data) {
	alert(data.msg);
	if (data.success) {
		reloadPage("#contactArea");
	}
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