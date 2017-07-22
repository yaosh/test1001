function edit(id){
	id = id ? id :"";
	var dataUrl = "?app=adminImage&act=edit&id="+id+"&height=300&width=500" ;
	var title = id=="" ?  "添加尺寸" : "编辑尺寸" ;
	initDialog(dataUrl , title) ;
}

function checkForm(data) {
 if (!data.sortno) {
	 alert("请输入数据");
	 return false;
 }
 if (isNaN(data.sortno)) {
	alert("请输入有效的数字");
	return false;
}
return true;
}

$("#sortno").live("keyup",function(){
	$(this).val($(this).val().replace(/[^0123456789]/g,''));
})

function showResult(data){
	alert(data.msg);
	if (data.success) {
		location.href = location.href;
	}
}

function del(id) {
if (!confirm("确认删除")) {
	return false;
}
$.post(
	location.href+"&act=delete",
	{"id":id},
	function(data){
		alert(data.msg);
		if (data.msg) {
			location.href = location.href;
		}
	},
	'json'
);
}