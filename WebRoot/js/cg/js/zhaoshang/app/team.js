function addTeam() {
	var $current_select = $(".group a.current");	
	var proj_id = $("#current-project").val();
	var name = $.trim($("#team-name").val());
	var parent_id = $current_select.attr("data-id");
	 if (!name) {
		alert("请输入团队分组名称！");
		 return;
	 }
	 $.post(
		"/zs.php?app=team&act=edit",
		{proj_id:proj_id, name:name, parent_id:parent_id},
		 function(result){
			alert(result.msg);
			if (result.success) {
				$.dialog.dialogClose();
				var data ={};
				data.name = name;
				data.parent_id = parent_id;
				data.deep = parseInt($current_select.parents(".group").attr("deep"))+1;
				data.id = result.data;
				data.deep_width = data.deep*18;
				var template = Handlebars.compile($("#team-list-tpl").html());
				var html = template(data);
				if ($('.group-of-'+parent_id).last().attr("data-id")){
					$('.group-of-'+parent_id).last().after($(html));
				}else{
					$("#group-"+parent_id).after($(html));
				}
				openNode($("#group-"+parent_id));
			}
		},
		'json'
	);
}

function editTeam(id) {
 if (!id) {
	id = 0;
 }
 var $current_select = $(".group a.current");
 var proj_id = $("#current-project").val();
 var name = $.trim($("#team-name").val());
 var note = $.trim($("#team-note").val());
 if (!name) {
	 alert("请输入团队分组名称！");
	 return;
 }
 $.post(
	"/zs.php?app=team&act=edit",
	{proj_id:proj_id, name:name, note:note,id:id},
	 function(data){
		alert(data.msg);
		if (data.success) {
			$.dialog.dialogClose();
			$current_select.text(name);
		}
	},
	'json'
 );
}

function deleteCheckedTeam() {
	var ids = getCheckedIds("team-check");
		if (!ids) {
			alert("请选择要删除的团队分组！");
			return;
		}
		if (!confirm("确认删除所选团队分组？")) {
			return;
		}
		deleteTeam(ids);
}

function deleteCurrentTeam() {
	var id = $(".group a.current").attr("data-id");
	if (!confirm("确认删除该分组？"))
	{
		return false;
	}
	if (!id)
	{
		alert("操作失败");
		return;
	}
	deleteTeam(id,removeCurrent);
}

function removeCurrent() {
	var $prev_group = $(".group a.current").parents(".group").prev(".group");
	var parent_id = $(".group a.current").parents(".group").attr("parent_id");
	$(".group a.current").parents(".group").remove();
	if ($(".group .group-of-"+parent_id).length<1) {
		$("#group-"+parent_id).removeClass("opened");
		$("#group-"+parent_id).removeClass("closed");
		$("#group-"+parent_id).addClass("normal");
	}
	$prev_group.find("a").click();
}

function deleteTeam(id,act) {
	$.post(
		"/zs.php?app=team&act=delete",
		{"ids":id},
		function(data){
			alert(data.msg);
			if (data.success) {
				if (act)
				{
					act();
				}else{
					location.href = location.href;
				}
				
			}
		},
		'json'
	);
}

function deleteTeamEx(id) {
	if (!confirm("确认删除该团队分组？")) {
		return;
	}
	deleteTeam(id);
}

function addTeamMember() {
	var proj_id = $("#current-project").val();
	var group_id = $(".group a.current").attr("data-id");
	var group_name = $(".group a.current").text();
	var title = "添加成员("+group_name+")";
	var url="/zs.php?app=member&proj_id="+proj_id+"&act=add&group_id="+group_id+"&callback=addMemberEx&height=500&width=300";
	$.dialog.dialogShow(url, title);
}

function finishedAddMember(group_id) {
	reloadMemberEx(group_id)
}