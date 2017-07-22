$(function(){
	$("#phone").live("click", function(){
		$(this).val($(this).val().replace(/[^0123456789\-]/g,''));
	});
	$("#mobile").live("click", function(){
		$(this).val($(this).val().replace(/[^0123456789]/g,''));
	});
})

function checkComment(data){
	if (!data.note) {
		alert("请输入评论内容");
		return false;
	}
	if (!data.agent_id) {
		alert("参数错误，请刷新重试！");
		return false;
	}
	return true;
}

function refreshComment(data){
	alert(data.msg);
	if (data.success) {
		reloadPage("#commentArea");
	}
}

function deleteComment(id){
	if (!confirm("确认删除这条评论")) {
		return;
	}
	$.post(
		"/brand.php?app=brandAgent&act=deleteComment",
		{"id":id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".comment-"+id).hide("normal");
			}
		},
		'json'
	);
}


function refreshContact(data){
	alert(data.msg);
	if (data.success) {
		reloadPage("#contactArea");
	}
}

function checkContact(data){
	var error_num=0;
	if (data.agent_id=="0") {
		showError("agent_id","请选择隶属公司 ！", error_num==0);
		error_num++;
	}
	if (!data.name) {
		showError("name","请输入联系人名称 ！", error_num==0);
		error_num++;
	}
	if (!data.region) {
		showError("region","请输入负责地区 ！", error_num==0);
		error_num++;
	}
	/*if (data.agent_id=="0") {
		showError("agent_id","请选择隶属公司 ！", error_num==0);
		error_num++;
	}
	
	if (!data.job) {
		showError("job", "请输入联系人职位 ！", error_num==0);
		error_num++;
	}*/
	if ((!data.phone)&&(!data.mobile)) {
		showError("phone", "联系电话 和 手机号码必须填写一项 ！", error_num==0);
		error_num++;
	}
	if (data.phone) {
		if (!isPhone(data.phone)) {
			showError("phone", "电话号码格式不正确 ！", error_num==0);
			error_num++;
		}
	}
	if (data.mobile) {
		if (!isMobile(data.mobile)) {
			showError("mobile", "手机号码格式不正确 ！", error_num==0);
			error_num++;
		}
	}
	/*if (data.email) {
		if (!isEmail(data.email)) {
			showError("email", "Email 格式不正确 ！", error_num==0);
			error_num++;
		}
	}
	*/
	if (error_num>0) {
		return false;
	}
	return true;
}

function deleteContact(id) {
	if (!confirm("确认删除该联系人")) {
		return false;
	}
	$.post(
		'/brand.php?app=brandAgent&act=deleteContact',
		{"id":id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".contact-"+id).hide("normal");
			}
		},
		'json'
	);
}

function deleteTempContact(id) {
	if (!confirm("确认删除该联系人")) {
		return false;
	}
	$.post(
		'/brand.php?app=brandTemp&act=deleteContact',
		{"id":id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".temp-contact.contact-"+id).hide("normal");
				reloadPage("#contactArea");
			}
		},
		'json'
	);
}

function deleteShop(id){
 if (!confirm("确认删除该品牌分店")) {
	 return false;
 }
 $.post(
	"/brand.php?app=brandShop&act=delete",
	{"id":id},
	 function(data){
		alert(data.msg);
		if (data.success) {
			$(".shop-"+id).hide("normal");
		}
	},
	'json'
 );
}


function deleteAgentBrand(agent_id, brand_id) {
	if (!confirm("确认删除该品牌代理关系")) {
		return false;
	}
	var main_brand_id = $("#main_brand_id").val();
	$.post(
		"/brand.php?app=brandAgent&act=deleteAgentBrand",
		{"agent_id":agent_id, "brand_id":brand_id,"master_id":main_brand_id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".brand-"+brand_id).hide("normal");
				reloadPage("#brandArea");
			}
			
		},
		'json'
	);
}

function deleteTempAgentBrand(agent_id, brand_id) {
	if (!confirm("确认删除该品牌代理关系")) {
		return false;
	}
	var main_brand_id = $("#main_brand_id").val();
	$.post(
		"/brand.php?app=brandTemp&act=deleteAgentBrand",
		{"agent_id":agent_id, "brand_id":brand_id,"master_id":main_brand_id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".brand-"+brand_id).hide("normal");
				reloadPage("#brandArea");
			}
			
		},
		'json'
	);
}