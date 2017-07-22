function editAdminCar(id) {
	if (!id) {
		id = 0;
	}
	var data = {};
	data.id=id;
	data.name = $("#name").val();
	if (empty(data.name)) {
		alert("请输入名称！");
		$("#name").focus();
		return;
	}	
	data.no = $("#no").val();
	if (empty(data.no)) {
		alert("请输入车牌号！");
		$("#no").focus();
		return;
	}	
	data.driver_id = $("#driver_id").val();
	if (empty(data.driver_id)) {
		alert("请输入驾驶员！");
		$("#driver_id").focus();
		return;
	}	$.post(
		"/?app=adminCar&act=edit",
		data,
		function(result){
			alert(result.msg);
			if (result.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function deleteAdminCar(id,msg) {
	if (!msg) {
		msg="确认删除？";
	}
	if (!confirm(msg)) {
		return;
	}
	$.post(
		"/?app=adminCar&act=delete",
		{id:id},
		function(result){
			alert(result.msg);
			if (result.success) {
				location.href = location.href;
			}
		},
		'json'
	);

}