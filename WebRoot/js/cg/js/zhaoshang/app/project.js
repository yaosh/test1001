$('#myTab a').click(function (e) {
			e.preventDefault()
			$(this).tab('show')
})

//绑定删除项目事件
$("#delete-project").live("click", function(){
	deleteProject($(this).attr("data-id"));
});

function addDoc(id) {
		if (id) {
			
		}else{
			var data={ url:"http://",title:"",type:"未知"};
		}
		data.project_id = $("#project_id").val();
		$("#main_form").hide();
		var source   = $("#doc-tpl").html();
		var template = Handlebars.compile(source);
		$("#show-content").append(template(data));
		
}

function goBack() {
	$("#show-doc").remove();
	$("#main_form").show();
}

var old_url=$("#doc-url").val();
	$("#doc-url").die().live("change", function(){
		
		var url = $("#doc-url").val();
		if (url==""||url=="http://") {
			$("#doc-url").focus();
			return;
		}
		if (url.indexOf("http://")!=0) {
			$("#doc-url").focus();
			alert("无效的地址");
			$("#doc-url").val("");
			return;
		}
		$.post(
			"/zs.php?app=project&act=getDocInfo",
			{ "url":url},
			function(data){
				if (data.success) {
					$("#doc-title").val(data.data.title);
					$("#doc-type").val(data.data.filetype);
				}else{
					alert(data.msg);
				}
				
			},
			'json'
		);
	});

	function checkProject () {
		if ($("#doc-url").val()==""||$("#doc-url").val()=="http://") {
			alert("请输入资料地址");
			return false;
		}
		return true;
	}

	function finishProject(data) {
		if (data.success) {
			$("#show-doc").remove();
			$("#main_form").show();
			reloadDocList();
		}
	}
	function reloadDocList() {
		var project_id = $("#project_id").val();
		$.post(
			"/zs.php?app=project&act=getDocList",
			{ "project_id":project_id},
			function (data){
			var source   = $("#doc-list-tpl").html();
			var template = Handlebars.compile(source);
			$("#doc-list").html(template(data));
			},
			'json'
		);
	}

	function deleteDoc(id) {
		if (!confirm("确认删除这个文档？")) {
			return false
		}
		$.post(
			"/zs.php?app=project&act=deleteDoc",
			{ id:id},
			function(data){
				alert(data.msg);
				if (data.success) {
					reloadDocList();
				}
			},
			'json'
		);
	}

function deleteProject(id) {
	if (!confirm("确认删除该项目！")) {
		return;
	}
	$.post(
		"/zs.php?app=project&act=delete",
		{ id:id },
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = "/zs/"
			}
		},
		'json'
	);
}