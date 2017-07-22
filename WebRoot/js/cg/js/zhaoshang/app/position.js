$(function(){
	$("#delete-position").live("click", function(){
		var ids = getCheckedIds("position-check");
		if (ids=="") {
			alert("请选择要删除的铺位");
			return;
		}
		if (!confirm("确认删除所选铺位")) {
			return;
		}
		deletePosition(ids);
	});
})

function deleteCheckedPos() {
	var ids = getCheckedIds("position-check");
	if (ids=="") {
			alert("请选择要删除的铺位");
			return;
		}
		if (!confirm("确认删除所选铺位")) {
			return;
		}
		deletePosition(ids);
}

function editPosition(id) {
	var proj_id = $("#current-project").val();
	var floor_id = $("#floor-id").val();
	var position_note = $("#position-note").val();
	var position_name = $("#position-name").val();
	var position_area = $("#position-area").val();
	var build_area = $("#build-area").val();
	id = id ? id:0;
	if (floor_id=="0") {
		alert("请选择楼层/区位！");
		return;
	}
	if (!position_name) {
		alert("请输入铺位号！");
		$("#position-name").focus();
		return;
	}
	if (build_area) {
		$("#build-area").focus();
	}
	$.post(
		"/zs.php?app=position&act=edit&id="+id,
	   {proj_id:proj_id, name:position_name, note:position_note,group_id:floor_id,area:position_area,area_build:build_area},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}


function deletePosition(id) {
	$.post(
		'/zs.php?app=position&act=delete',
		{"ids":id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}


function showAddForm(obj) {
$(obj).hide("fast");
$("#add-form").show("fast");

}

function resetAreaRate(id) {
		$.post(
			"/zs.php?app=position&act=getInfo",
			{id:id},
			function(data){
				$("#rate-"+id).text(data.area_rate);
			},
			'json'
		);
}