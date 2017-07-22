function editCate(id) {
 if (!id) {
	id = 0;
 }
 var proj_id = $("#current-project").val();
 var shop_num  = $("#shop-num").val();
 var type_1,type_2,type_3;
 type_1 = $("#front_cate").val();
var color = $.trim($("#color").val());
 
 if (empty(type_1)) {
	 alert("请选择业态！");
	 return;
 }
 if (!color) {
	 alert("请选择平面图标色");
	 return;
 }
 /*
 if (empty(shop_num)) {
	 alert("请输入预设商家数！");
	 return;
 }
 */
 type_2 =$.trim($("#sub_cate").val());
type_3 = $.trim($("#sub-cate").val());

 $.post(
	"/zs.php?app=zscate&act=edit",
	{proj_id:proj_id, shop_num:shop_num, type_1:type_1, type_2:type_2, type_3:type_3, id:id,color:color},
	 function(data){
		alert(data.msg);
		if (data.success) {
			location.href = location.href;
		}
	},
	'json'
 );
}

function deleteCheckedCate() {
	var ids = getCheckedIds("zscate-check");
		if (!ids) {
			alert("请选择要删除的业态！");
			return;
		}
		if (!confirm("确认删除所选业态及其相关的所有数据？")) {
			return;
		}
		deleteCate(ids);
}

function deleteCate(id) {
	$.post(
		"/zs.php?app=zscate&act=delete",
		{"ids":id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}


