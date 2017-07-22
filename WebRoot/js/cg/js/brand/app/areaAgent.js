function checkAddAreaAgent(data){
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

function showResult(data) {
	var $location;
	$location = "/brand.php?app=areaAgent&act=showSuccess";
	if (data.success) {
		if (data.data.id) {
			$location += "&agent_brand_id="+data.data.id;
		}else{
			$location += "&brand_id="+data.data.brand_id;
			$location += "&pid="+data.data.pid;
		}
		location.href = $location;
	}else{
		if (data.data.name) {
			showError(data.data.name, data.msg, 1);
		}else{
			alert(data.msg);	
		}
	}
}

function checkBaseEdit(data){
	var error_num = 0;
	if (!data.info.name) {
		showError("info[name]", "请输入公司名称 ！", error_num==0);
		error_num++;
	}
	if (!data.agent_brand_info.region) {
		showError("agent_brand_info[region]","请输入代理区域 ！", error_num==0);
		error_num++;
	}
	if (data.info.website) {
		if (!isUrl(data.info.website)) {
			showError("info[website]", "网址格式不正确 ！", error_num==0);
			error_num++;
		}
	}
	if (data.info.phone) {
		if (!isPhone(data.info.phone.replace(/(^\s*)|(\s*$)/g, ""))) {
			showError("info[phone]", "电话号码格式不对 ！", error_num==0);
			error_num++;
		}
	}
	if (data.info.fax) {
		if (!isPhone(data.info.fax.replace(/(^\s*)|(\s*$)/g, ""))) {
			showError("info[fax]", "传真号码格式不对 ！", error_num==0);
			error_num++;
		}
	}
	if (error_num>0) {
		return false;
	}
	return true;
}

function finishBaseEdit(data){
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