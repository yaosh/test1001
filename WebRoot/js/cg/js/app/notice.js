function checkNoticeValid(data) {
	data.company_ids = Array();
	$(".company-check:checked").each(function(){
		data.company_ids.push($(this).val());
	});
	
	if (!data.title) {
		alert("请输入公告的标题 ！");
		return false
	}
	if (!data.content) {
		alert("请输入公告内容 ！");
		return false;
	}
	if (data.company_ids.length==0) {
		alert("通知范围至少要涉及到一个公司！");
		return false;
	}
	data.company_ids = data.company_ids.join(",");
	return true;
}

function finishPublishNotice(data) {
	alert(data.msg);
	if (data.success) {
		location.href = "/?app=notice";
	}
}

function turnNotice(id,state) {
	if (!confirm("确认操作")) {
		return false;
	}
	if (!state) {
		state = 0;
	}
	$.post(
		"?app=notice&act=changeState&id="+id,
		{"state":state},
		function(rs){
			alert(rs.msg);
			if (rs.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

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
			alert("抱歉，您的浏览器不支持该功能！\n\n您可以使用火狐，Goolge Chrome，360极速浏览器(极速模式)");
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
			$("#attachment-start").after(template(info));
	} catch (e) {
		alert(e.message);
	}

}

function editorAction() {
	/*$.swfFileUpload({
			url : "/?app=notice&act=upload", //服务器端地址
			fileId : "attachment_input", // 选择框id
			fileType : "image", //文件类型限制
			text : "添加附件",
			onSuccess : function(file, data){ //操作成功响应事件
				alert(data);
				//data = jQuery.parseJSON(data);
				//$("body").append('<img src="'+data.img_url+'">');
				//addAttachment(data.data);
			}
		});
		*/
	KindEditor.ready(function(K) {
				  window.editor = K.create('#editor', {
					width:"680px",
					height:"300px",
					resizeType : 0,	//高度不可伸缩
					uploadJson : '/?app=notice&act=uploadImg', //上传图片入口
					/*items : [					//菜单
						'source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'table', 'emoticons', 'image', 'link']
					*/
				});
				
        });
		
}