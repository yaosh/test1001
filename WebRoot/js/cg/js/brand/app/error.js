$(function(){
	var error_link,error_area,error_title;
	if ($("#user_id").val() != $("#add_user").val()) {
		$(".edit-btn").each(function(){
			if ($(this).attr("noerror")) {
				return true;
			}
			error_link = location.href.split("#");
			error_link = error_link[0];
			error_area = $(this).parents(".detail-box").attr("id");
			error_link = error_area ? error_link+"#"+error_area : error_link;
			error_title = $(this).attr("data-title").replace("编辑", "").replace("添加", "");
			$(this).parents(".btns").prepend('<a href="javascript:void(0)" class="edit-btn error-btn"  data-url="'+error_link+'">纠错</a>');
		});
	}
	$(".error-btn").live("click", function(){
		window.error_link = $(this).attr("data-url");
		window.error_title = $(this).attr("data-title");
		$.dialog.dialogShow("/brand.php?app=user&act=sendError&width=600&height=400","纠错");
	});
})

function checkErrorSubmit(data) {
	if (!data.content) {
		showError("content","请输入描述！",1);
		return false;
	}
	return true;
}

function finishErrorResult(data) {
	if (data.success) {
		$(".ui-dialog-close").click();
		alert("提交成功");
	}else{
		alert(data.msg);
	}
}