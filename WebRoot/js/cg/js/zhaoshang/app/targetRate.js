function editTargetRate(id) {
	var data = {};
	if (id) {
		data.id = id;
	}else{
		data.id = 0;
	}
	data.proj_id = $("#proj_id").val();
	data.period_id = $("#period_id").val();
	data.type_ids = getCheckedIds("zscate-check");
	if (data.type_ids==""){
		alert("请选择业态！");
		return;
	}
	if (!data.period_id) {
		alert("周期数据错误！");
		return;
	}
	if (!data.proj_id) {
		alert("项目数据错误！");
		return;
	}
	data.percent = $("#target-percent").val();
	data.rate = $("#target-rate").val();
	
	if (data.percent=="0") {
		alert("请选择合作意向！");
		return;
	}
	if (!data.rate) {
		alert("请输入自定义的百分值！")
		return;
	}
	if (parseInt(data.rate)<101) {
		alert("百分值必须大于100！");
		return;
	}
	$.post(
		"/zs.php?app=targetRate&act=edit",
		data,
		function(rs){
			alert(rs.msg);
			if (rs.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function deleteCheckedTargetRate() {
	var ids = getCheckedIds("target-check");
	if (!ids) {
		alert("请选择要删除的进度的指标！");
		return;
	}
	if (!confirm("确认删除所选进度的指标？")) {
		return;
	}
	deleteTargetRate(ids);
}

function deleteTargetRate(id) {
	$.post(
		"/zs.php?app=targetRate&act=delete",
		{id:id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	
	);

}