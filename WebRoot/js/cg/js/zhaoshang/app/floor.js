$(function(){
	$("#delete-project").live("click", function(){
		var ids = getCheckedIds("floor-check");
		if (ids=="") {
			alert("请选择要删除的楼层/区位");
			return;
		}
		if (!confirm("确认删除所选楼层/区位及其相关的所有数据？")) {
			return;
		}
		deleteFloor(ids);
	});
})

function editFloor(id) {
	var proj_id = $("#current-project").val();
	var floor_name = $.trim($("#floor-name").val());
	var floor_note = $("#floor-note").val();
	var floor_img = $("#showImg").attr("src");
	var floor_types = $("#floor-types").val();
	var shop_num = $("#shop-num").val();
	id = id ? id:0;
	if (!floor_name) {
		alert("请输入名称！");
		$("#floor-name").focus();
		return;
	}
	$.post(
		"/zs.php?app=floor&act=edit&id="+id,
	   {proj_id:proj_id, name:floor_name,types:floor_types, note:floor_note, img:floor_img,shop_num:shop_num},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}


function deleteFloor(id) {
	$.post(
		'/zs.php?app=floor&act=delete',
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


