function checkForm(data){
	if (!data.name) {
		return false;
	}
	return true;
}

function refreshPage(data){
	if (data.success) {
		location.href = location.href;
	}else{
		alert(data.msg);
	}

}

$(".delete-btn").live("click", function(){
	var setting_size = $(".setting-line").length;
	var edit_size = $(".edit-line").length;
	var is_edit = $(this).parents("tr").hasClass("edit-line");
	var edit_all = 0;
	//只有一个编辑框
	if (is_edit&&(setting_size==0)) {
		edit_all = 1;
		if (!confirm("确定删除，删除后台将会删除整个选项")) {
			
			return false;
		}
	}
	//一个编辑框和一固定框
	if ((!is_edit)&&setting_size==1) {
		if (!confirm("确定删除，删除后台将会删除整个选项")) {
			return false;
		}
	}
	if ($(this).attr('data-id')) {
		$(".edit-line").remove();
		$(this).parents("tr").remove();
		$("#save-btn").click();
		return;
	}else{
		$(this).parents("tr").remove();
	}
	
	if (edit_all==1) {
		$("#save-btn").click();	
	}
});

function checkData(){
	var flag = 0;
	var num = 0;
	$("input:text").each(function(){
		if ($(this).val()=="") {
			flag++;
		}
		num++;
	});
	if (flag>0) {
		alert("每项值都不能为空，不需要的请删除！");
		return false;
	}
	return true;
}

function showResult(data){
	alert(data.msg);
	if (data.success) {
		location.href = location.href;
	}
}

function continueAdd(){
	if ($(".edit-line").length>0) {
		alert("请先保存 再添加！");
		return false;
	}
	$(".setting-line").last().after($("#continue-add").html());
	var max_id = $(".setting-line").last().attr("data-id");
	$(".edit-line td").first().text(parseInt(max_id)+1);
}