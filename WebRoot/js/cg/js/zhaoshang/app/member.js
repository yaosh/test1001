$(function(){
	$(".multi-btn").die().live("click", function(){
//		if (empty(getCheckedIds("member-check"))) {
//			alert("请选择要编辑的成员！");
//			return;
//		}
		$(".multi-editor").hide("normal");
		$(".multi-btn").show("normal");
		showEditor($(this));
	});
})
function addMember(callback) {
	var user_id = getCheckedIds("user-check");
	var proj_id = $("#current-project").val();
	var group_id = $("#member-group").val();
	if (empty(user_id)) {
		alert("请选择成员！");
		return;
	}
	if (empty(proj_id)) {
		alert("项目编号错误！");
		return;
	}
	$.post(
		"/zs.php?app=member&act=add",
		{proj_id:proj_id,user_id:user_id, group_id:group_id,role:0},
		function(data){
			alert(data.msg);
			if (callback) {
				eval(callback+"("+group_id+")");
				$.dialog.dialogClose();
				return;
			}
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	
	);
}

function editMember(id) {
	if (!id) {
		id=0;
	}
	var user_id = $("#user_id").val();
	var group_id = $("#group_id").val();
	var role = $("#role").val();
	var proj_id = $("#current-project").val();
	if (empty(group_id)) {
		alert("请选择分组！");
		return;
	}
	if (empty(user_id)) {
		alert("请选择成员！");
		return
	}
	if (empty(role)) {
		alert("请选择角色！");
		return;
	}
	$.post(
		'/zs.php?app=member&act=edit',
		{id:id,group_id:group_id,user_id:user_id,role:role, proj_id:proj_id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function selectMember(id) {
	if (id) {
		selectUser("user_id", "choseMember");
	}else{
		selectMultiUser("user_id", "choseMultiMember");
	}
}

function choseMember() {
	var obj = $("input:radio[name=select_user]:checked");
	if (!obj.attr("data-id")) {
		alert("您还没有选择！");
		return false;
	}
	$("#user_name").val(obj.attr("data-user"));
	$("#user_id").val(obj.attr("data-id"));
	$.dialog.dialogClose();
}


function deleteCheckedMember() {
	var ids = getCheckedIds("member-check");
		if (!ids) {
			alert("请选择要删除的成员！");
			return;
		}
		if (!confirm("确认删除所选成员？")) {
			return;
		}
		deleteMember(ids);
}

function deleteMember(ids,callback) {
	$.post(
		"/zs.php?app=member&act=delete",
		{"ids":ids},
		function(data){
			alert(data.msg);
			if (callback) {
				eval(callback);
				return;
			}
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function deleteMemberEx(id,group_id) {
	if (!confirm("确认删除该成员！")) {
		return;
	}
	deleteMember(id,"reloadMember("+group_id+")");
}

function choseMultiMember() {
	var ids = getCheckedIds("user-check");
	var names = getCheckedIds("user-check", "data-name");
	names = names.split(",");
	short_names = new Array();
	if (names[0]) {
		short_names.push(names[0]);
	}
	if (names[1]) {
		short_names.push(names[1]);
	}
	if (names[2]) {
		short_names.push(names[2]);
	}
	short_names = short_names.join(",");
	if (names[3]) {
		short_names += "等"+names.length+"人";
	}
	$("#user_name").val(short_names);
	$("#user_id").val(ids);
	$.dialog.dialogClose();
	
}

function showEditor(obj) {
	$(obj).hide("normal");
	$($(obj).attr("data-target")).show("normal");
}

function submitEditor(obj) {
	var ids = getCheckedIds("member-check");
	var sub_form = $(obj).parents(".multi-editor");
	var field = sub_form.attr("data-field");
	var value = sub_form.find("select").val();
	var name = sub_form.attr("data-name");
	if (!ids) {
		alert("请选择要编辑的成员！");
		return;
	}
	if (empty(value)) {
		alert("请选择"+name+"！");
		return;
	}
	if (!confirm("确认修改所选成员的"+name+"?")) {
		return;
	}
	data = {};
	data.ids = ids;
	eval("data."+field+"="+value+";");
	$.post(
		"/zs.php?app=member&act=edit",
		data,
		function(rs) {
			alert(rs.msg);
			if (rs.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

//获取部门成员
function reloadMember(group_id) {
	var template = Handlebars.compile($("#member-list-tpl").html());
	var proj_id = $("#current-project").val();
	$.post(
		"/zs.php?app=member&act=getGroupMember",
		{group_id:group_id,proj_id:proj_id},
		function(data){
			var html = template(data.data);
			$(".parent-"+group_id).remove();
			$(".team-"+group_id).after(html);
			$(".team-"+group_id).attr("childs",data.data.childs);
			showTeamMember($(".team-"+group_id));
			resetState($(".team-"+group_id));
		
		},
		'json'
	);
}

function addMemberEx() {
	addMember("reloadMember");
}