

function assignCheckedBrand() {
	var ids = getCheckedIds("brand-check");
	var member_id = $("#member_id").val();
	var proj_id = $("#current-project").val();
	if (!ids) {
		alert("请选择分配所选品牌！");
		return;
	}
	if (member_id=="0") {
		alert("请选择分配人员！");
		return;
	}
	if (!confirm("确认分配所选品牌？")) {
		return;
	}
	assignBrand(member_id, ids, proj_id);
}

function assignBrand(member_id, ids,proj_id) {
	$.post(
		"/zs.php?app=member&act=assignBrand",	
		{member_id:member_id,ids:ids, proj_id:proj_id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function cancelCheckedAssign() {
	var ids = getCheckedIds("brand-check");
	if (!ids) {
		alert("请选择品牌！");
		return;
	}
	if (!confirm("确认取消分配！")) {
		return;
	}
	cancelAssign(ids);
}

 function cancelUserAssign(obj) {
	if (!confirm("确认删除该跟进人？")) {
		return false;
	}
	var current_user = $(obj).parent();
	var brand_id = $(obj).attr("brand-id");
	var member_id  = $(obj).attr("data-id");
	$.post(
		"/zs.php?app=member&act=deleteUserAssign",
		{member_id:member_id,brand_id:brand_id},
		function(data){
			alert(data.msg);
			if (data.success) {
				current_user.remove();
			}
		},
		'json'
	);
}



