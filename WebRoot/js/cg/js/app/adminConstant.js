function edit(id){
	id = id ? id :"";
	var dataUrl = "?app=adminConstant&act=edit&id="+id+"&height=300&width=500" ;
	var title = id=="" ?  "添加常量" : "编辑常量" ;
	initDialog(dataUrl , title) ;
}

function checkForm() {
 var id = $("#id").val();
 var name = $("#name").val();
 var value = $("#value").val();
 var comment = $("#comment").val();
 if (!name) {
	 alert("请输入常量名");
	 $("#name").focus();
	 return false;
 }
 if (!value) {
	 alert("请输入常量值");
	 $("#value").focus();
	 return false;
 }
 if (!comment) {
	 alert("请备注");
	 $("#comment").focus();
	 return false;
 }

id = id ? id :"";
 $.post(
	location.href+"&act=edit&id="+id,
	{"name":name, "value":value, "comment":comment},
	function(data) {
		alert(data.msg);
		if (data.success) {
			location.href=location.href;
		}
	},'json'
 );
 return false;
}

function del(id) {
	if (!confirm("确认删除")) {
		return false;
	}
	$.post(
		location.href+"&act=delete",
		{"id":id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	)
}