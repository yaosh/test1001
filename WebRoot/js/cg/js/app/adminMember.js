// JavaScript Document
$(function(){
	if(ACT == 'edit'){
		setTable('base');
		$("li a").live('click' , function(data){
			$("li").removeClass('active');
			$(this).parents('li').addClass('active');
			var type = $(this).attr("type");
			setTable(type);
		});
	}
});
//功能区块是否显示
function setTable(type){
	$(".setting").hide();
	$("." + type).show();
}
//添加用户
function edit(){
	var dataUrl = "?app="+APP+"&act=edit&height=350&width=450" ;
	var title = "快速添加会员" ;
	initDialog(dataUrl , title) ;
}
//用户与角色关联的模板加载
function rmr(adminId){
	var dataUrl = "?app=adminRmr&memberId="+adminId+"&height=550&width=710&type=url" ;
	var title = "角色设置" ;
	initDialog(dataUrl , title) ;
}
//检查快速添加用户的表单
function checkForm(){
	var id 			= $.trim($("#id").val()) ;
	var sex			= $("input[name=sex]:checked").val();
	var username 	= $.trim($("#username").val()) ;
	var email 		= $.trim($("#email").val()) ;
	var mobile 		= $.trim($("#mobile").val()) ;
	var join_date 	= $.trim($("#join_date").val()) ;
	//var department_id = $("select[name=department_id]").val();
	//var position_id = $("select[name=position_id]").val();
	var join_date = $("#join_date").val();
	var note = $("#note").val();
	var password = $.trim($("#password").val());
	var department_list = new Array();
	var empty_flg = false;
	var department_id;
	var position_id;
	
	if(!username){
		alert('您还没有填写职员姓名');
		$("#username").focus();
		return false ;
	}

	$(".department-list").each(function(){
		department_id = $(this).find(".department_id").val();
		position_id = $(this).find(".position_id").val();
		if (department_id=="0") {
			alert("请选择部门！");
			empty_flg = true;
			return false;
		}
		if (position_id=="0") {
			alert("请选择职位！");
			empty_flg = true;
			return false;
		}
		department_list.push({"department_id":department_id, "position_id":position_id});
	});
	if (empty_flg) {
		return false;
	}

	

	if(!mobile){
		//alert('您还没有填写员工的手机号码');
		//$("#mobile").focus();
		//return false ;
	}

	if(!email){
		//alert('您还没有填写员工的邮箱地址');
		//$("#email").focus();
		//return false ;
	}
	
	if(!join_date){
		//alert('您还没有填写员工的入职日期');
		//$("#join_date").focus();
		//return false ;
	}	
	//var password= "123456";
	
	pButton(1);
	$.post(aUrl+"&act=edit&id="+id , {"username":username, "password":password, "sex":sex, "mobile":mobile,'department_list':department_list, "email":email,"join_date":join_date, "note":note} , function(data){
		alert(data.msg);
		if(data.success){
			//parent.f5('');
			history.go(-1);
		}else{
			pButton(2);
		}
	} , 'json');
	
	return false ;
}

//人员密码初始化
function passwordRevert(id){
	if(confirm('确认要初始化密码？')){
		$.post(aUrl+"&act=revert" , {"id":id} , function(data){
			if(data.success){
				alert('操作成功，新密码是：'+data.data);
			}else{
				alert(data.msg);
			}
			return false ;
		} , 'json');
	}
}

function checkLockForm(){
	var id = $.trim($('#id').val());
	var reason = $.trim($("#reason").val());
	
	if(reason == ""){
		alert('请填写锁定原因');	
		$("#reason").focus();
		return false ;
	}
	
	$.post(aUrl+"&act=lock" , {"id":id , "state":"2" , "reason":reason} , function(data){
		alert(data.msg);
		if(data.success){
			parent.f5('');
		}
	} , 'json');
	
	return false ;
}

//帐号锁定与解锁
function lock(ids , state){
	if(state == 1){
		if(confirm('确认要解锁吗')){
			$.post(aUrl+"&act=lock" , {"id":ids , "state":state} , function(data){
				alert(data.msg);
				if(data.success){
					f5('');
				}
			} , 'json');
			return false ;
		}
	}else{
		var dataUrl = "?app="+APP+"&act=lock&id="+ids+"&height=250&width=500&type=url" ;
		var title = "帐号锁定" ;
		initDialog(dataUrl , title) ;
	}
}

function checkLogin(){
	var user_name 	= $.trim($("#user_name").val());
	var user_pass 	= $.trim($("#user_pass").val());
	var captcha		= $.trim($("#captcha").val());
	if(user_name == ''){
		alert('您还没有填写登录名称');
		$("#user_name").focus();
		return false;
	}
	if(user_pass == ''){
		alert('您还没有填写登录密码');
		$("#user_pass").focus();
		return false;
	}
	if(captcha == ''){
		alert('您还没有填写验证码');
		$("#captcha").focus();
		return false;
	}	
	pButton(1);

	$.post( "?act=login&is_ajax=1", { username:user_name,password:user_pass,captcha:captcha }, function(data){
		if(data.success){
			if(url != '') location.href = url;
			else location.href = '?app=default';
		}else{
			alert(data.msg);
			pButton(0);
			$("#captchaCode").attr("src" , "?act=captcha&is_ajax=1&rand="+Math.random());
			return false;
		} },'json'
	);
	return false;
}

function checkPassrordForm() {
	var old 	= $.trim($("#oldPassword").val());
	var new1 	= $.trim($("#newPassword").val());
	var new2   	= $.trim($("#newPassword1").val());
	
	if(!old) {
		alert('原始密码不能为空');	
		$("#old").focus();
		return false ;
	}
	
	if(!new1) {
		alert('新密码不能为空');	
		$("#new1").focus();
		return false ;		
	}
	
	if(new1 != new2) {
		alert('两次密码输入不一致');	
		return false ;
	}
	
	if(new1.length <= 5) {
		alert('新密码长度不能小于6');	
		return false ;
	}
	pButton(1);
	
	$.post(aUrl+"&app=adminMember&act=changePassword" , {"old":old , "np":new1} , function(data) {
		alert(data.msg);
		if(data.success != false) {
			pButton(0);
			$.dialog.dialogClose();
		}
	} , 'json');
	return false ;
}

function changeState(id){
	var state = $("input[name=state]:checked").val();
	if (!confirm("确认此操作！")) {
		return false;
	}
	$.post(
		'?app=adminMember&act=changeState&is_ajax=1',
		{ "id":id, "state": state},
		function (data){
		
			if (data.success) {
				location.href =location.href;
			}else{
				alert(data.msg);
			}
		},
		'json'
	);
}

function editMyInfo() {
	var data={};
	data.id = $("#id").val();
	data.sex = $("input[name=sex]:checked").val();
	data.mobile = $("#mobile").val();
	data.email = $("#emial").val();
	data.join_date = $("#join_date").val();
	data.note = $("#note").val();
	$.post(
		location.href,
		data,
		function(rs) {
			alert(rs.msg);
			if (rs.success) {
				location.href = location.href;
			}
		},
		'json'
	);
	return false;
}

function addDepartment() {
	$(".department-info").append($("#department-tpl").html());
	$(".department-list").slideDown("fast");
}

function deleteDepartment(obj) {
	$(obj).parents(".department-list").slideUp("fast",function(){
		$(obj).parents(".department-list").remove();
	});
	
}