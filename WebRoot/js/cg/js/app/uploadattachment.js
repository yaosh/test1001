function uploadAttachment() {
	$.ajaxFileUpload({
		url:'?app=notice&act=upload',       //需要链接到服务器地址
		secureuri:false,
		fileElementId:"attachment_input",                            //文件选择框的id属性
		dataType: 'json',                                   //服务器返回的格式，可以是json
		success: function (data, textStatus) {               //相当于java中try语句块的用法
				addAttachment(data.data);
		
		},
		error: function (data, status, e) {           //相当于java中catch语句块的用法
			alert("error");
		}
	});	
}
$(function(){
	$("#attachment_input").live("change", function(){
		uploadAttachment();
	})
	$(".btn-delete-attachment").live("click", function(){
		$(this).parents(".attachemnt-tpl").remove();
	});
})
	
function addAttachment(info) {
	try {
			var source   = $("#attachment-tpl").html();
			var template = Handlebars.compile(source);
			$("#attachment-start").before(template(info));
	} catch (e) {
		alert(e.message);
	}

}