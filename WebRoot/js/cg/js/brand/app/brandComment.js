function checkComment(data){
	if (!data.note) {
		alert("请输入评论内容");
		return false;
	}
	if (!data.agent_id) {
		alert("参数错误，请刷新重试！");
		return false;
	}
	return true;
}

function refreshComment(data){
	if (data.success) {
		reloadPage("#commentArea");
	}else{
		alert(data.msg);
	}
}

function deleteComment(id){
	if (!confirm("确认删除这条评论")) {
		return;
	}
	$.post(
		"?app=brandAgent&act=deleteComment",
		{"id":id},
		function(data) {
			if (data.success) {
				$(".comment-"+id).hide("normal");
			}else{
				alert(data.msg);
			}
		},
		'json'
	);
}