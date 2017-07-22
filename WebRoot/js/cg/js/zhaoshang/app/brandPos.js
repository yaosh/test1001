$(function(){
	$(".editor-btn").live("click", function(){
		$(".select-editor").hide();
		$(".extra").remove();
		$(".editor-btn").show();
		$(".editor-btn").hide("fast");
		$("."+$(this).attr("data-target")).show("fast");
	});
	$(".cancel-btn").live("click", function(){
		$(this).parents(".select-editor").hide("fast");
		//$("."+$(this).parents(".select-editor").attr("data-target")).show("fast");
		$(".editor-btn").show("fast");
	});
	$(".modify-btn").live("click", function(){
		var select_editor = $(this).parents(".select-editor");
		var target = select_editor.attr("data-target").replace("-btn","");
		var select_val;
		if (target=="pos") {
			select_val = new	Array();
			$(".pos-id").each(function(){
				select_val.push($(this).val());
			});
			select_val = select_val.join(",");
			editCheckedData(target, select_val);
			return;
		}
		if (select_editor.find("select").length==1) {
			select_val = select_editor.find("select").val();
		}else{
			select_val = Array();
			select_editor.find("select").each(function(){
				if (!empty($(this).val())) {
					select_val.push($(this).val());
				}
			});
			select_val = select_val.join(",");
		}
		editCheckedData(target, select_val);
	});
})

function editCheckedData(field, val) {
	var name="";
	if (field=="type") {
		field="type_id"
		name="招商业态";
	}
	if (field=="floor") {
		field="group_ids"
		name="楼层/区位";
	}
	if (field=="level") {
		name="招商等级";
	}
	if (field=="pos") {
		field="pos_ids"
		name="铺位";
	}
	var ids = getCheckedIds("brand-pos-check");
	if (!ids) {
		alert("请选择要编辑的品牌！");
		return;
	}
	if (val=="0") {
		alert("请选择"+name+"！");
		return;
	}
	if (!confirm("确认修改所选品牌的"+name+"?")) {
		return;
	}
	
	var data={};
	data.proj_id = $("#current-project").val();
	data.ids = ids;
	eval("data."+field+"=\""+val+"\"");
	$.post(
		"/zs.php?app=brand&act=multiEdit",
		data,
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href=location.href;
			}
		},
		'json'
	);
}
function deleteSelectedBrand(){
		var ids = getCheckedIds("brand-pos-check");
		if (!ids) {
			alert("请选择要删除的品牌！");
			return;
		}
		if (!confirm("确认删除所选品牌及其相关的所有数据？")) {
			return;
		}
		deleteBrand(ids);
}

function addBrandPos(brand_flg) {
	var group_id = new Array();
	var member_id = $("#member_id").val();
	if (!brand_flg) {
		brand_flg = 1;
	}
	/*$(".group-id").each(function(){
		if (!empty($(this).val())) {
			group_id.push($(this).val());
		}
	});
	group_id = group_id.join(",");*/
	var pos_id = new Array();
	$(".pos-id").each(function(){
		pos_id.push($(this).val());
	});
	pos_id = pos_id.join(",");
	var brand_ids = getCheckedIds("brand-check");
	var proj_id = $("#proj_id").val();
	var type_id = $("#type_id").val();
	var type = $("#member-type").val();
	var level = $("#level").val();
	var flg_type = window.parent.$("#flg_type").val();
	if (empty(flg_type)) {
		flg_type = $.trim($("#flg_type").val());
		if (empty(flg_type)) {
			alert("请选择落位方案！");
			return;
		}
		if (empty(member_id)) {
			alert("您不是该项目中的成员，无法给自己添加品牌\n\n");
			return;
		}
	}
	
	if (!brand_ids) {
		if (brand_flg==1) {
			alert("请选择品牌！");
		}else{
			alert("请选择单店！");
		}
		return;
	}
	/*if (type_id=="0") {
		alert("请选择招商业态！");
		return;
	}
	if (group_id=="0") {
		alert("请选择楼层/区位！");
		return;
	}
	if (level=="0") {
		alert("请选择招商等级！");
		return;
	}*/
	$.post(
		"/zs.php?app=brand&act=add",
		{ brand_ids:brand_ids, pos_id:pos_id, proj_id:proj_id,type_id:type_id,type:type, level:level, brand_flg:brand_flg, flg_type:flg_type,member_id:member_id},
		function(data){
			alert(data.msg);
			if (data.success) {
				window.parent.location.href=window.parent.location.href;
			}
		},
		'json'
	);
	
}


function addUserBrand(brand_flg) {
	var brand_ids = getCheckedIds("brand-check");
	var proj_id = $("#proj_id").val();
	var flg_type = $.trim($("#flg_type").val());
	if (!brand_flg) {
		brand_flg = 1;
	}
	if (!brand_ids) {
		if (brand_flg==1) {
			alert("请选择品牌！");
		}else{
			alert("请选择单店！");
		}
		return;
	}
	if (!flg_type) {
		alert("请选择落位方案！");
		return;
	}

	$.post(
		"/zs.php?app=brand&act=addUserBrand",
		{ brand_ids:brand_ids, proj_id:proj_id,brand_flg:brand_flg,flg_type:flg_type},
		function(data){
			alert(data.msg);
			if (data.success) {
				window.parent.location.href=window.parent.location.href;
			}
		},
		'json'
	);
}

//删除品牌落位
function deleteBrand(id) {
	var flg_type=$("#flg_type").val();
	$.post(
		"/zs.php?app=brand&act=delete",
		{"ids":id, flg_type:flg_type},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

$(function(){
	$(".group-id").live("change",function(){
		refreshLaterGroups($(this));
		/*var val = $(this).val();
		var val2 = $("#group_id2").val();
		$("#group_id2").html($(this).html());
		if (val!="0") {
			$("#group_id2 option[value="+val+"]").remove();
		}
		$("#group_id2").val(val2);*/
	});
	$(".brand-floor").live("mouseenter", function(){
		$(this).find(".floor-delete-btn").show();
		$(this).css("background-color","#e4e4e4");
		$(this).css("color","gray");
	});
	$(".brand-floor").live("mouseleave", function(){
		$(this).find(".floor-delete-btn").hide();
		$(this).css("background-color","white");
		$(this).css("color","black");
	});
	$(".brand-pos").live("mouseenter", function(){
		$(this).find(".pos-delete-btn").show();
		$(this).css("background-color","#e4e4e4");
		$(this).css("color","gray");
	});
	$(".brand-pos").live("mouseleave", function(){
		$(this).find(".pos-delete-btn").hide();
		$(this).css("background-color","white");
		$(this).css("color","black");
	});
	$(".brand-type").live("mouseenter", function(){
		$(this).find(".type-delete-btn").show();
		$(this).css("background-color","#e4e4e4");
		$(this).css("color","gray");
	});
	$(".brand-type").live("mouseleave", function(){
		$(this).find(".type-delete-btn").hide();
		$(this).css("background-color","white");
		$(this).css("color","black");
	});

})

function refreshLaterGroups(obj) {
	var val = obj.val();
	var next_obj = obj.next();
	var next_val;
	if (next_obj.html()) {
		next_val = next_obj.val();
		next_obj.html(obj.html());
		if (val!=0) {
			next_obj.find("option[value="+val+"]").remove();
		}
		next_obj.val(next_val);
		refreshLaterGroups(next_obj);
		
	}
}

function addGroupSelector() {
	var last_selector = $(".group-selector select").last();
	var first_selector = $(".group-selector select").first();
	if ($(".group-id").length==(first_selector.find("option").length-1)) {
		alert("无法继续楼层/区位！");
		return;
	}
	$(' <select class="txt-small select group-id" ><option value="0">- 请选择 -</option></select> ').appendTo($(".group-selector")).addClass("extra");
	refreshLaterGroups(last_selector);
}

$("#front_cate").live("change", function(){
	var front_cate = $(this).val();
	var init_options = "";
	$.post(
		'/brand.php?act=getCateList',
		{ "id":front_cate},
		function(data){
			for (i in data) {
				init_options += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			$("#sub_cate").html(init_options);
			$("#sub_cate option:first").attr("selected", "selected");
			$("#sub-cate").html("");
			$("#product-styles").html("");
		},
		'json'
	);
})

function cancelFloor(obj) {
	var id = $(obj).attr("data-id");
	if (!confirm("确认删除该楼层/区位上的落位？")) {
		return;
	}
	$.post(
		"/zs.php?app=brand&act=deleteBrandFloor",
		{id:id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function cancelType(obj) {
	var id = $(obj).attr("data-id");
	if (!confirm("确认删除该业态？")) {
		return;
	}
	$.post(
		"/zs.php?app=brand&act=deleteBrandType",
		{id:id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function cancelPos(obj){
	var id=$(obj).attr("data-id");
	if (!confirm("确认删除该铺位？")) {
		return;
	}
	$.post(
		'/zs.php?app=brand&act=deleteBrandPosition',
		{id:id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

function addPos(data) {
	var pos_ids = new Array();
	$(".pos-id").each(function(){
		pos_ids.push($(this).val());
	})
	var new_data = new Array();
	for (i in data) {
		if (!in_array(data[i].id,pos_ids)) {
			new_data.push(data[i]);
		}
	}
	var template = Handlebars.compile($("#pos-tpl").html());
	var html = template({ data:new_data});
	$(".pos-list").append(html);
	$.dialog.dialogClose();
}
function removePos(obj) {
	$(obj).parent().hide("fast", function(){
		$(this).remove();
	});
}

function doAddBrand() {
	var ids = getCheckedIds("brand-check");
	var level = $("#level").val();
	var brand_flg = 1;
	var member_id = $("#member_id").val();
	var member_id2 = $("#member_id2").val();
	window.parent.addBrand(ids,level,brand_flg,member_id,member_id2);
	//addBrandNew(ids,level,brand_flg);
}

function doAddShop() {
	var ids = getCheckedIds("brand-check");
	var level = $("#level").val();
	var member_id = $("#member_id").val();
	var member_id2 = $("#member_id2").val();
	var brand_flg = 2;
	window.parent.addBrand(ids,level, brand_flg,member_id,member_id2);
	
}

/*
function addBrandNew(ids,level,brand_flg){
	var data = {};
	var member_id = $("#member_id").val();
	var member_type = $("#member_type").val();
	if (member_id && empty(member_id)) {
		alert("请选择成员！");
		return false;
	}
	data.ids = ids;
	data.level = level;
	data.member_id = member_id;
	data.brand_flg = brand_flg;
	data.member_type = member_type;
	data.proj_id = $("#current-project").val();
	$.post(
		"/zs.php?app=brand&act=add",
		data,
		function(rs){
			alert(rs.msg);
			if (rs.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}*/

function addBrand(ids,level,brand_flg,member_id, member_id2) {
	var data = {};
	//var member_id = $("#member_id").val();
	//var member_type = $("#member_type").val();
	/*if (member_id && empty(member_id)) {
		alert("请选择成员！");
		return false;
	}*/
	data.ids = ids;
	data.level = level;
	data.member_id = member_id;
	data.member_id2 = member_id2;
	data.brand_flg = brand_flg;
	data.proj_id = $("#current-project").val();
	$.post(
		"/zs.php?app=brand&act=add",
		data,
		function(rs){
			alert(rs.msg);
			if (rs.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}
