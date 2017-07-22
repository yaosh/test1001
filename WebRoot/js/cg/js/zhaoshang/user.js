function showProject(obj) {
	$this = $(obj);
	var project = "project"+$this.val();
	//var current_act = $("#current-act").val();
	//location.href = "/zs/user/"+project+"/"+current_act+".html";
	location.href = "/zs/user/"+project+"/customer.html?default";
}

function goPage(name) {
	var path_arr = location['pathname'].split("/");
	path_arr[4]=name+".html";
	location.href = path_arr.join("/");
}

function showTeamMember(team,member, selected_value) {
	var group_id = $(team).val();
	var init_html = '<option value="0">- 请选择 -</option>';
	var proj_id = $("#proj_id").val()
	if (empty(proj_id)) {
		proj_id = $("#current-project").val();
	}
	if (group_id=="0") {
			$(member).html(init_html);
			return;
	}
	
	$.post(
		"/zs.php?app=member&act=getGroupMembers",
		{group_id:group_id,proj_id:proj_id},
		function(data) {
			$.each(data, function(key, value){
				init_html += '<option value="'+value.id+'">'+value.user_name+'</option>'
			})
			
			$(member).html(init_html);
			if (selected_value) {
				$(member).find("option[value="+selected_value+"]").attr("selected", "selected");
			}
			
		},
		'json'
	);
}

function empty(str) {
	if (str==undefined) {
		return true;
	}
	if (str=="0") {
		return true;
	}
	if (!str) {
		return true;
	}
	return false;
}