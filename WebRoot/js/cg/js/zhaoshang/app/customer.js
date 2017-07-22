function addPlanPos(data) {
	for (i in data ) {
		if (posExits(data[i].id,"plan-pos-list")) {
			alert("该铺位已存在！");
			return false;
		}
	}
	var source   = $("#pos-list-tpl").html();
	var template = Handlebars.compile(source);
	$("#plan-pos-list").append(template({"data":data}));
	$(".hidden-pos").slideDown("normal", function(){
		$(this).removeClass("hidden-pos");
	});
	$.dialog.dialogClose();
	
}

function setFixPos(data) {
	for ( i in data) {
		if (posExits(data[i].id,"fix-pos-list")) {
			alert("铺位"+data[i].name+"已存在！");
			return false;
		}
	}
	
	var source   = $("#pos-list-tpl").html();
	var template = Handlebars.compile(source);
	$("#fix-pos-list").append(template({"data":data}));
	$(".hidden-pos").slideDown("normal", function(){
		$(this).removeClass("hidden-pos");
	});
	$.dialog.dialogClose();
}

$(function(){
$(".document-item-remove").live("click", function(){
	$(this).parent().slideUp("normal",function(){
		$(this).remove();
	});
});
})

function posExits(id,parent_id) {
	var ids = new Array();
	$("#"+parent_id+" .document-item").each(function(){
		ids[$(this).attr("data-id")] = 1;
	});
	if (ids[id]) {
		return true;
	}else{
		return false;
	}
}

function editTalk(id) {
	if (!id) {
		id = 0;
	}
	var data={};
	data.id = id;
	data.proj_id = $("#current-project").val();
	data.brand_id = $("#brand_id").val();
	data.time = $.trim($("#time").val());
	data.next_time = $.trim($("#next_time").val());
	data.type = $("#talk-type").val();
	data.address = $("#address").val();
	data.people_a = $("#people_a").val();
	data.position_a = $("#position_a").val();
	data.contact = $("#contact").val();
	data.people_b = $("#people_b").val();
	data.subject = $("#subject").val();
	data.content = $("#content").val();
	data.strategy = $.trim($("#strategy").val());
	data.percent = $("#percent").val();
	data.pos_plan = new Array();
	data.pos_fix = new Array();
	$("#plan-pos-list .document-item").each(function(){
		data.pos_plan.push($(this).attr("data-id"));
	});
	$("#fix-pos-list .document-item").each(function(){
		data.pos_fix.push($(this).attr("data-id"));
	});
	data.pos_plan = data.pos_plan.join(",");
	data.pos_fix = data.pos_fix.join(",");
	if (!data.time) {
		alert("请输入洽谈时间！");
		return false;
	}
	if (empty(data.type)) {
		alert("请选择跟进方式！");
		return false;
	}
	if (data.percent=="-1") {
		alert("请输入合作意向！");
		return false;
	}
	$.post(
		'/zs.php?app=talk&act=edit',
		data,
		function(rs) {
			alert(rs.msg);
			if (rs.success) {
				if (!empty($("#no-parent").val())) {
					location.href = location.href;
				}else{
					resetBrandInfo(data.brand_id);
					$.dialog.dialogClose();
					$.dialog.dialogClose();
					$.dialog.dialogShow(window.dialog_url,window.dialog_title);
				}
			}
		},
		'json'
	);
}



function deleteCheckedTalk() {
	var ids = getCheckedIds("talk-check");
	if (!ids) {
		alert("请选择要删除的记录！");
		return false;
	}
	if (!confirm("确认删除所选记录")) {
		return false;
	}
	deleteTalk(ids);
}

function deleteTalk(ids) {
	$.post(
		'/zs.php?app=talk&act=delete',
		{ids:ids},
		function(data){
			alert(data.msg);
			$(".talk-check:checked").each(function(){
				$(this).parents("tr").hide("normal", function(){
					$(this).remove();
					autoShowTable($("#talk-list"));
				});
				resetBrandInfo(window.dialog_brand_id);
			});
		},
		'json'
	);
}


function resetBrandInfo(id) {
	$.post(
		"/zs.php?app=talk&act=getLastTalkInfo",
		{id:id},
		function(data){
			if (data.this_week) {
				$("#percent-"+id).css("color","red");
				$("#content-"+id).css("color", "red");
				$("#strategy-"+id).css("color", "red");
			}
			$("#percent-"+id).text(data.percent+"%");
			$("#content-"+id).text(data.content);
			$("#strategy-"+id).text(data.strategy);
			$("#type-name-"+id).text(data.type_name);
			$("#date-"+id).text(data.date);
			$("#day-"+id).text(data.day);
			$("#week-"+id).text(data.week);
		},
		'json'
	);
}

function showTarget(obj) {
	var target_id = $(obj).attr("data-target");
	$(".target").hide();
	$("#"+target_id).show();
	$("#floor-tab li a").removeClass("active");
	$(obj).addClass("active");
}

function editContact(id) {
	var brand_id = $("#brand_id").val();
	var agent_id = $("#agent_list").val();
	var name = $("#contact-name").val();
	var region = $("#contact-region").val();
	var job = $("#contact-job").val();
	var sex = $("input[name=sex]:checked").val();
	var phone = $("#contact-phone").val();
	var mobile = $("#contact-mobile").val();
	var email = $("#contact-email").val();
	if (empty(agent_id)) {
		alert("请选择隶属公司！");
		return;
	}
	if (empty(name)) {
		alert("请输入拓展联系人姓名！");
		return;
	}
	if (empty(phone)&&empty(mobile)) {
		alert("联系电话和手机号码至少要填写一项！");
		return;
	}
	var data = {};
	data.name = name;
	data.brand_id = brand_id;
	data.agent_id = agent_id;
	data.region = region;
	data.job = job;
	data.sex = sex;
	data.phone = phone;
	data.mobile = mobile;
	data.email = email;
	if (id) {
		data.id= id;
	}
	$.post(
		'/zs.php?app=contact&act=edit',
		data,
		function(rs) {
			alert(rs.msg);
			if (rs.success) {
					/*$.dialog.dialogClose();
					$.dialog.dialogClose();
					$.dialog.dialogShow(window.parent.dialog_url,window.parent.dialog_title); */
					dialogDoModel("#list-page",2);
			}
		},
		'json'
	);
	
}

function deleteContact(id) {
	$.post(
		"/zs.php?app=contact&act=delete",
		{id:id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".contact-check:checked").each(function(){
					$(this).parents("tr").remove();
				});
				autoShowTable($("#contact-table"));
			}
		},
		'json'
	);
}


function checkDialogAddAgent(data) {
	var error_num = 0;
	if (empty(data.info.name)) {
		alert("请输入公司名称！");
		return false;
	}
	if (empty(data.relation.region)) {
		alert("请输入代理区域!");
		return false;
	}
	return true;
}

function finishDialogAddAgent(data) {
	var agent_name = $("#agent_name").val();
	if (data.success) {
		$("#agent_list").append('<option value="'+data.data.agent_id+'">'+agent_name+'</option>');
		$.dialog.dialogClose();
		alert("代理商创建成功！");
		$("#agent_list").val(data.data.agent_id);
	}else{
		alert(data.msg);
	}
}

function deleteCheckedContact() {
	var ids = getCheckedIds("contact-check");
	if (!ids) {
		alert("请选择要删除的拓展联系人！");
		return false;
	}
	if (!confirm("确认删除所选拓展联系人！")) {
		return false;
	}
	deleteContact(ids);
}

function selectAction() {
$(".submit-line ul li a").live("click", function(){
		var field = $(this).parents(".submit-line").attr("data-field");
		var new_val = $(this).attr("data-id");
		var old_val = $("#"+field).val();
		if (field=="team_id") {
			if (new_val==old_val) {
				return;
			}
			$("#member_id").val(0);
		}
		$("#"+field).val(new_val);
		
		$(".submit-btn").click();
		
	});
}