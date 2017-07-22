$(".open-city").live("click", function(){
	var upid = $(this).parents("tr").attr("data-id");
	openCity(upid);
	$("table tr").removeClass();
});

$(".close-city").live("click", function(){
	var upid = $(this).parents("tr").attr("data-id");
	closeCity(upid);
	$(this).attr("src", "/res/libraries/jqtreetable/images/tv-expandable.gif");
	$(this).removeClass();
	$(this).addClass("open-city");
});


function openCity(id,func) {
	var current_tr = $("#tr-"+id);
	$.post(
		aUrl+"&act=getCityList",
		{ "upid":id},
		function(data) {
			if (func) {
				eval(func);
			}
			var source   = $("#city-list").html();
			var template = Handlebars.compile(source);
			//载入数据
			current_tr.after(template(data));
			//刷新节点
			current_tr.find("img").removeClass();
			if (data.list.length>0) {
				current_tr.find("img").addClass("close-city");
				current_tr.find("img").attr("src","/res/libraries/jqtreetable/images/tv-collapsable.gif");
			}else{
				current_tr.find("img").attr("src","/res/libraries/jqtreetable/images/tv-item.gif");
			}
		},
		'json'
	);
}

function closeCity(upid) {
	$("tr[upid="+upid+"]").each(function(){
		closeCity($(this).attr("data-id"));
		$(this).remove();
	});
}



function checkName(data) {
	if (!data.name) {
		alert("请输入地区名！");
	}
	return true;
}


function finishEdit(data) {
	alert(data.msg);
	if (data.success) {
		$.dialog.dialogClose();
		if (parseInt(data.data.upid) ==1) {
			location.href =location.href;
		}else{
			openCity(data.data.upid,"closeCity("+data.data.upid+")");
		}
	}
}


function deleteCity(id) {
	if (!confirm("确认删除！")) {
		return false;
	}
	$.post(
		aUrl+"&act=delete",
		{ "id":id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				if (parseInt(data.data.upid) ==1) {
					location.href =location.href;
				}else{
					openCity(data.data.upid,"closeCity("+data.data.upid+")");
				}
			}
		},
		'json'
	);
}

