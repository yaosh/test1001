$(function(){
	$(".delete-doc-btn").live("click", function(){
		var cur_doc = $(this).parent();
		/*if (!confirm("确认删除 "+cur_doc.find("span").text()+"?")) {
			return;
		}*/
		cur_doc.hide("normal", function(){
			$(this).remove();
		});
	});
})

function addDoc(data) {
	var source   = $("#doc-tpl").html();
	if (checkExistDoc(data)) {
		return false;
	}
	var template = Handlebars.compile(source);
	$("#doc-list").append(template({data:data}));
	$.dialog.dialogClose();
	showDoc();
}


function checkExistDoc(data) {
	var ids = getDocIds();
	var exist_ids = new Array();
	$.each(ids,function(key,value){
		exist_ids[value]=1;
	});
	var exist = false;
	$.each(data, function(key,row){
		if (exist_ids[row.id]) {
			exist=true;
			alert("文档 "+row.title+" 已存在！");
			return false;;
		}
	})
	return exist;
}

function getDocIds() {
	var ids = new Array();
	$(".document").each(function(){
		ids.push($(this).attr("data-id"));
	});
	return ids;
}

function showDoc() {
	$obj = $(".hidden").first();
	$obj.show("fast", function(){
		$(this).removeClass("hidden");
		if ($(".hidden").first().attr("data-id")){
			showDoc();
		}
	});
}

function editProject() {
	var pro_id = $("#current-project").val();
	var name = $.trim($("#pro-name").val());
	var type = $("#pro-type").val();
	var type_zs = $("#zs-type").val();
	var status = $("input[name=status]:checked").val();
	var note = $("#pro-note").val();
	var region_id = $("#district_id").val();
	var city_id = $("#city_id").val();
	var open_time = $("#open_time").val();
	var manager_id = $("#manager_id").val();
	var doc_ids = getDocIds().join(",");
	var logo = $("#showImg").attr("src");
	if (!name) {
		alert("请输入项目名称！");
		return;
	}
	if (city_id=="0") {
		alert("请选择项目地点！");
		return;
	}
	if (type=="0") {
		alert("请选择项目类型！");
		return;
	}
	if (type_zs=="0") {
		alert("请选择招商类型！");
		return;
	}
	if (!status) {
		alert("请选择项目状态！");
		return;
	}

	$.post(
		"/zs.php?app=project&act=add",
		{ id:pro_id, name:name, type:type, type_zs:type_zs, status:status, "note":note, open_time:open_time, city:city_id, region:region_id, manager_id:manager_id, doc_ids:doc_ids, logo:logo},
		function(data){
			
			if (!pro_id) {
				if (data.success) {
					$.dialog.dialogShow("/zs.php?app=project&act=createSuccess&id="+data.data+"&width=450&height=200","项目创建成功！");
				}else{
					alert(data.msg);
				}
			}else{
				alert(data.msg);
				if (data.success) {
					location.href = location.href;
				}
			}
		},
		'json'
	);
}

function selectManager() {
	selectUser("manager_id", "choseManager");
}

function choseManager() {
	var obj = $("input:radio[name=select_user]:checked");
	if (!obj.attr("data-id")) {
		alert("您还没有选择！");
		return false;
	}
	$("#manager_name").val(obj.attr("data-user"));
	$("#manager_id").val(obj.attr("data-id"));
	$.dialog.dialogClose();
}


function showDocDialog() {
	var proj_name = $("#pro-name").val();
	if (!proj_name) {
		proj_name="";
	}
	proj_name = encodeURIComponent(proj_name);
	var proj_city = $("#city_id").val();
	var url = "/zs.php?app=project&act=docList&proj_name="+proj_name+"&proj_city="+proj_city+"&width=750&height=550&dialog_type=iframe";
	var title = "选择项目文档";
	$.dialog.dialogShow(url, title);
}