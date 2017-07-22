function editPeriod(id) {
 if (!id) {
	id = 0;
 }
 var proj_id = $("#current-project").val();
 var name = $("#period-name").val();
 var start_time = $.trim($("#start-date").val());
 var end_time = $.trim($("#end-date").val());
 var note = $.trim($("#period-note").val());
 if (!name) {
	 alert("请输入周期名称！");
	 $("#period-name").focus();
	 return;
 }
 if (!start_time) {
	 alert("请输入周期时间！");
	 return;
 }
 if (!end_time) {
	 alert("请输入周期时间！");
	 return;
 }
 $.post(
	"/zs.php?app=period&act=edit",
	{id:id, proj_id:proj_id, name:name, start_time:start_time, end_time:end_time, note:note},
	 function(data){
		alert(data.msg);
		if (data.success) {
			location.href = location.href;
		}
	},
	'json'
 );
}

function deleteCheckedPeriod() {
	var ids = getCheckedIds("period-check");
		if (!ids) {
			alert("请选择要删除的周期！");
			return;
		}
		if (!confirm("确认删除所选周期？")) {
			return;
		}
		deletePeriod(ids);
}

function deletePeriod(id) {
	$.post(
		"/zs.php?app=period&act=delete",
		{"ids":id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}



function editTarget(period_id) {
	var data = {};
	var key_name;
	var brand_type;
	var field_name;
	var field_value;
	var proj_id = $("#current-project").val();
	$(".target-form").each(function(){
		brand_type = $(this).attr("data-id");
		key_name = $(this).attr("data-key");
		eval("data."+key_name+"={};");
		$(this).find(".num").each(function(){
			if ($(this).hasClass("percent")) {
				return true;
			}
			field_name = $(this).attr("data-field");
			field_value = $(this).val();
			eval("data."+key_name+"."+field_name+"='"+field_value+"';");
		});
		eval("data."+key_name+".proj_id="+proj_id+";");
		eval("data."+key_name+".brand_type="+brand_type+";");
		eval("data."+key_name+".period_id="+period_id+";");
	
	});
	$.post(
		"/zs.php?app=target&act=edit&period_id="+period_id,
		data,
		function(rs) {
			alert(rs.msg);
		},
		'json'
	);
}


function applySync() {
	$(".num").bind("blur", function(){
		var base_val = $("#num-plan-"+($(this).parent().attr("data-id"))).val();
		var new_val = $(this).val();
		var target = $(this).attr("data-target");
		if ($(this).hasClass("percent")) {
			if (new_val=="") {
				if ($(target).val()!="") {
					$(this).val(parseInt(parseInt($(target).val())/base_val*100));
				}else{
					return;
				}
			}else{
				new_val = parseInt(new_val);
				$(target).val(parseInt(base_val/100*new_val));
			}
		}else{
			if (new_val=="") {
				if ($(target)!="") {
					$(this).val(parseInt(base_val/100*parseInt($(target).val())));
				}else{
					return;
				}
			}else{
				new_val = parseInt(new_val);
				$(target).val(parseInt(new_val/base_val*100));
			}
		}
	});
}


function initPercent() {
	var target, target_val,base_val ;
	$(".percent").each(function(){
		 target = $(this).attr("data-target");
		 base_val = parseInt($("#num-plan-"+$(this).parent().attr("data-id")).val());
		 if ($(target).val()) {
			 target_val = parseInt($(target).val());
			
			$(this).val(parseInt(target_val/base_val*100));
		 }
		 
	});
}