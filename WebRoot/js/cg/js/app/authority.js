$(".open-city").live("click", function(){
	var upid = $(this).parents("tr").attr("data-id");
	openCity(upid);
	//$("table tr").removeClass();
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
		aUrl+"&act=getChildren",
		{ "pid":id},
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
		alert("请输入权限名称！");
		return false;
	}
	if (!data.alias) {
		alert("请输入别名！");
		return false;
	}
	return true;
}


function finishEdit(data) {
	alert(data.msg);
	if (data.success) {
		$.dialog.dialogClose();
		if (parseInt(data.data.pid) ==0) {
			location.href =location.href;
		}else{
			openCity(data.data.pid,"closeCity("+data.data.pid+")");
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
				if (parseInt(data.data.pid) ==0) {
					location.href =location.href;
				}else{
					openCity(data.data.pid,"closeCity("+data.data.pid+")");
				}
			}
		},
		'json'
	);
}


function checkForm() {
	var data=[];
	$("input:checkbox:checked").each(function(){
		data.push(parseInt($(this).val()));
	});
	if (!confirm("确认修改权限？")) {
		return false;
	}
	$.post(
		location.href,
		{"ids": data},
		function (data) {
			alert(data.msg);
			if (data.success) {
				parent.closeDialog();
			}
		},
		'json'
	);
}

function checkAll(state) {
	if (1 == state) {
		$('.check-auth').attr('checked',"checked");
	}else{
		$('.check-auth').attr('checked',false);
	}
}


function decheckChild(id) {
	$(".auth").each(function(){
		if ($(this).attr("pid")==id) {
			$(this).find("input:checkbox").attr("checked",false);
			decheckChild($(this).attr("sid"));
		}
		
	});
}

$(function(){
	$(".check-auth").live("click",function(){
		if ($(this).attr("checked")) {
		
		}else{
			decheckChild($(this).val());
		
		}
	});
})