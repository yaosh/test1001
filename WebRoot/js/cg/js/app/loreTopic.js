//检测复选框的值
function checkBox() {
	var chk_value = [];
	$("input[name='checkbox']:checked").each(function(){ 
		chk_value.push($(this).val());
	});
	if (chk_value.length == 0) {
		alert('请选择需要批量操作的列');
		return false;
	}else{
		if (confirm('确认要执行此批量操作？')) {
			return chk_value;
		};
	};
}

//批量审核专题
function setState(is_show) {
	var chk_value = checkBox();
	if (chk_value) {
		$.post("?app=loreTopic&act=setState&is_show="+is_show, { "ids":chk_value } , function(data) {
			alert(data['msg']);
			if(data['success']) {
				closeDialog();
				window.location.reload();
			}
		} , 'json');
	};
}

//批量推荐或取消推荐
function setDigg(is_show) {
	var chk_value = checkBox();
	if (chk_value) {
		$.post("?app=loreTopic&act=setDigg&is_show="+is_show , { "ids":chk_value } , function(data) {
			alert(data['msg']);
			if(data['success']) {
				closeDialog();
				window.location.reload();
			}
		} , 'json');
	};
}