
$("#upload-btn").live("click",function(){
	$("#attachment_input").click();
});
$("#attachment_input").live("change", function(){
	uploadAttachment();
});
$(".btn-delete-attachment").live("click", function(){
	$(this).parent().remove();
	$(".show-txt").text("上传文档");
});
function uploadAttachment() {
	$.ajaxFileUpload({
		url:'?app=notice&act=upload',       //需要链接到服务器地址
		secureuri:false,
		fileElementId:"attachment_input",                            //文件选择框的id属性
		dataType: 'json',                                   //服务器返回的格式，可以是json
		success: function (data, textStatus) {               //相当于java中try语句块的用法
			setAttachment(data.data);
		},
		error: function (data, status, e) {           //相当于java中catch语句块的用法
			alert("抱歉，您的浏览器不支持该功能！\n\n您可以使用火狐，Goolge Chrome，360极速浏览器(极速模式)");
		}
	});	
}

function setAttachment(info) {
	try {
		var source   = $("#attachment-tpl").html();
		var template = Handlebars.compile(source);
		$("#upload-block").html(template(info));
		$(".show-txt").text("重新上传");
	} catch (e) {
		alert(e.message);
	}
	
}

function checkForm() {
	editor.sync();
	var id = $("#id").val();
	var title=$("#title").val();
	var content = $("#editor").val();
	//var content = editor.getContent();
	if (!title) {
		alert("请输入标题！");
		return false ;
	}
	if (!content) {
		alert("请输入内容！");
		return false ;
	}

	var data = {};
	data.id = id;
	data.title = title;
	data.content = content;
	data.company_ids = Array();
	$(".company-check:checked").each(function(){
		data.company_ids.push($(this).val());
	});
	if (data.company_ids.length==0) {
		alert("至少要发布到一个公司！");
		return false;
	}
	data.company_ids = data.company_ids.join(",");
	if ($("input[name=attachment_path]").val()!="") {
		data.attachment={};
		data.attachment.path=$("input[name=attachment_path]").val();
		data.attachment.size=$("input[name=attachment_size]").val();
		data.attachment.name=$("input[name=attachment_name]").val();
	}
	$.post(
		'/?app=adminRule&act=edit',
		data,
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = "/?app=adminRule"
			}
		},
		'json'
	);
}

function deleteRule(id) {
 if (!confirm("确认删除？")) {
	return false;
 }
 $.post(
	'/?app=adminRule&act=delete',
	{id:id},
	 function(data){
		alert(data.msg);
		if (data.success) {
			location.href = location.href;
		}
	},
	'json'
 );

}