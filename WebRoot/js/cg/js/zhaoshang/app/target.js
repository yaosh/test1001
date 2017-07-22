function editTarget(id) {
 if (!id) {
	id = 0;
 }
 var data = {};
 data.id = id;
 data.proj_id = $("#current-project").val();
 data.period_id = $("#period-id").val();
 data.brand_type = $("#category").val();
 var empty_field;
 $("input.num").each(function(){
	 if (!$(this).val()) {
		 empty_field=$(this).attr("id");
		 return false;
	 }
	eval("data."+$(this).attr("id")+" = '"+$(this).val()+"';");
 })
if (data.period_id=="0") {
	 alert("请选择周期！");
	 return;
}
if (data.brand_type=="0") {
	 alert("请选择业态！");
	 return;
}
if (empty_field) {
	 if (isNaN(empty_field.replace("num_",""))) {
		 alert("请输入预计商家数！");
	 }else{
		alert("请输入"+empty_field.replace("num_","")+"%的商家数！");
	 }
	 return;
}

 $.post(
	"/zs.php?app=target&act=edit",
	data,
	 function(data){
		alert(data.msg);
		if (data.success) {
			location.href = location.href;
		}
	},
	'json'
 );
}

function deleteCheckedTarget() {
	var ids = getCheckedIds("target-check");
		if (!ids) {
			alert("请选择要删除的指标！");
			return;
		}
		if (!confirm("确认删除所选指标？")) {
			return;
		}
		deleteTarget(ids);
}

function deleteTarget(id) {
	$.post(
		"/zs.php?app=target&act=delete",
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

