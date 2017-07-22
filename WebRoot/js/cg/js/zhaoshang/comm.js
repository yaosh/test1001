$(function(){
	//自动补全不完整的表格
	$("table.auto-show").each(function(){
		autoShowTable($(this));
	});

	//float类型文本框控制
	$("input.float").live("keyup", function(){
		$(this).val($(this).val().replace(/[^0-9\.]+/,""));
	});
	//int类型文本框控制
	$("input.num").live("keyup", function(){
		$(this).val($(this).val().replace(/[^0-9]+/,""));
	});
	//phone类型文本控制
	$("input.phone").live("keyup", function(){
		$(this).val($(this).val().replace(/[^0-9\-]/,""));
	});
});
function uploadLogo(fileId,fileName,obj,str,type){
	 $.ajaxFileUpload({
		 url:'/zs.php?app=uploadFile&act=upload&fileName='+fileName+'&type='+type,  //需要链接到服务器地址
         secureuri:false,
         fileElementId:fileId,
         dataType: 'json',
         success: function (data){
        	 if(1==data.status){
        		 $('#'+str).attr('src',data.img);
				 $("#"+str).slideDown("normal");
        	 }else{
        		 alert(data.msg);
        	 }
          }
   });
}

function autoShowTable(table){
	if (table.find("tr").length>1) {
		return;
	}
	var first_tr = table.find("tr").first();
	var num = first_tr.find("th").length;
	var html="<tr><td colspan="+num+" class=\"center\">暂无数据</td></tr>";
	first_tr.find("input:checkbox").attr("disabled","disabled");
	table.append(html);
}

function checkAll(obj,classid) {
	var $this = $(obj);
	var $checkbox;
	if (classid) {
		$checkbox = $("."+classid);
	}else{
		$checkbox = $("input:checkbox");
	}
	if ($this.attr("checked")) {
		$checkbox.attr("checked", "checked");
	}else{
		$checkbox.removeAttr("checked");
	}
}

function getCheckedIds(classname, field) {
	var data = new Array();
	$("input."+classname+":checked").each(function(){
		if (field) {
			data.push($(this).attr(field));
		}else{
			data.push($(this).attr("data-id"));
		}
		
	});
	if (data.length>0) {
		return data.join(",")
	}else{
		return "";	
	}
}


function selectUser(name,callback) {
	var user_id;
	if (name) {
		user_id = $("#"+name).val();
	}else{
		user_id = $("#to_user").val();
	}
	if (callback) {
		callback = "&callback="+callback;
	}else{
		callback="";
	}
	var url = "/?app=workflow&act=selectUser&select_user="+user_id+callback+"&width=500&height=500";
	$.dialog.dialogShow(url, "选择成员");
}

function getDepartmentTree(){
	window.map_option = {
        	openImg: "/res/libraries/jqtreetable/images/tv-collapsable.gif", 
        	shutImg: "/res/libraries/jqtreetable/images/tv-expandable.gif", 
        	leafImg: "/res/libraries/jqtreetable/images/tv-item.gif", 
        	lastOpenImg: "/res/libraries/jqtreetable/images/tv-collapsable.gif", 
        	lastShutImg: "/res/libraries/jqtreetable/images/tv-expandable.gif", 
        	lastLeafImg: "/res/libraries/jqtreetable/images/tv-item-last.gif", 
        	vertLineImg: "/res/libraries/jqtreetable/images/vertline.gif", 
        	blankImg: "/res/libraries/jqtreetable/images/blank.gif", 
        	collapse: false, column: 1, striped: false, highlight: true, state:false
     };
	$.post(
		"/?app=department&act=getTreeMap",
		{},
		function(data) {
			window.map_tree = data;
		},
		'json'
	);

	//部门菜单
	$(".department-chose").die().live("click", function(){
		$(".department-chose").css("font-weight","normal");
		$(this).css("font-weight","bold");
		getUserList($(this).attr("data-id"));
	});
}

function closeTree(){
	$("#treet1").find("img").each(function(){
		if ($(this).attr("src") == "/res/libraries/jqtreetable/images/tv-collapsable.gif") {
			$(this).attr("src", "/res/libraries/jqtreetable/images/tv-expandable.gif");;
		};
	});
}

function getUserList(department_id){
	var source   = $("#user-list-tpl").html();
	var template = Handlebars.compile(source);
	$.post(
		"/?app=adminMember&act=getUserList",
		{"department_id" : department_id},
		function(data){
			if (data.success) {
				$("#user-list").html(template(data))
				if ($("#select-user").val()) {
					//alert($("#select-user").val());
					$("#user-"+$("#select-user").val()).click();
				}
				
			}else{
				$("#user-list").html('<font color="red">'+data.msg+'</font>');	
			}
		},
		'json'
	);
}

function selectMultiUser(name, callback){
	var user_id;
	if (name) {
		user_id = $("#"+name).val();
	}else{
		user_id = $("#to_user").val();
	}
	if (callback) {
		callback = "&callback="+callback;
	}else{
		callback="";
	}
	var url = "/?app=workflow&act=selectMultiUser&select_user="+user_id+callback+"&width=350&height=500";
	$.dialog.dialogShow(url, "选择成员");
}

function dialogDoModel(obj,num) {
	var url = $(obj).attr("data-url");
	var title = $(obj).attr("data-title");
	$.dialog.dialogShow(url,title);
	num = num ? num : 1;
	for (var i=0;i<num;i++) {
		$.dialog.dialogClose();
	}
	
	
	
}

function in_array(search,array){
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}