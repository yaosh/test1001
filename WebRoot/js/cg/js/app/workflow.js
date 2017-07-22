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
	var url = "?app=workflow&act=selectUser&select_user="+user_id+callback+"&width=500&height=500";
	$.dialog.dialogShow(url, "选择经办人");
}
$(function(){
	$(".department-chose").live("click", function(){
		$(".department-chose").css("font-weight","normal");
		$(this).css("font-weight","bold");
		getUserList($(this).attr("data-id"));
	});
	$("input:radio[name=state]").live("click",function(){
		$("#to_user").val("");
		$("#user_name").val("");
		$("#note").hide();
		$("#note").val("");
		if ($(this).val()=="2") {
			$(this).parent().after($("#note"));
			$("#chose-to-user").show("normal");
			$("#note").attr("placeholder", "您可以在这里输入备注内容");
			$("#note").show("normal");
		}
		if ($(this).val()=="1") {
			$(this).parent().after($("#note"));
			$("#chose-to-user").hide();
			$("#note").attr("placeholder", "您可以在这里输入备注内容");
			$("#note").show("normal");
		}
		if ($(this).val()=="3") {
			$(this).parent().after($("#note"));
			$("#chose-to-user").hide();
			$("#note").attr("placeholder", "您可以在这里输入拒绝的原因");
			$("#note").show("normal");
		}
		
	});
	$(".use-right").live("click", function(){
		$(".use-right").html("&nbsp;");
		$(this).html("<b>√</b>");
		$("#day_type").val($(this).attr("data-id"));
	});
	
	//转交 拒绝 通过
	$("input[name=opt]").live("change", function(){
		$(".next-opt").hide();
		$(".next-opt").eq(parseInt($(this).val())-1).show("normal");
	});
})

function getUserList(department_id){
	var source   = $("#user-list-tpl").html();
	var template = Handlebars.compile(source);
	$.post(
		"?app=adminMember&act=getUserList",
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


function getSelectedUser(){
	var obj = $("input:radio[name=select_user]:checked");
	if (!obj.attr("data-id")) {
		alert("您还没有选择！");
		return false;
	}
	$("#user_name").val(obj.attr("data-user"));
	$("#to_user").val(obj.attr("data-id"));
	$.dialog.dialogClose();
}

function getWaiterUser() {
 var obj = $("input:radio[name=select_user]:checked");
	if (!obj.attr("data-id")) {
		alert("您还没有选择！");
		return false;
	}
	$("#wait_username").val(obj.attr("data-user"));
	$("#wait_user").val(obj.attr("data-id"));
	$.dialog.dialogClose();
}

function checkWork(data){
	if (getDateNum("end-date")) {
		if (getDateNum("end-date") <= getDateNum("start-date")) {
			alert("结束时间必须大于开始时间！");
			return false
		}
	}
	try {
		if (!checkEmptyFields(data)) {
			return false;
		}
	} catch (e) {
		console.log(e.message+" : "+e.lineNumber);
	}
	var func_name = $("#check_func").val()
	var func_reuslt;
	if (func_name) {
		func_name += "(data)";
		func_result = eval(func_name);
		if (false==func_result) {
			return false;
		}
	}
	if (!data.to_user) {
		alert("请选择审批人");
		return false;
	}
	if (!confirm("确认提交申请？")) {
		return false
	}
	return true;
}

function checkEmptyFields(data){
	$arr = new Array();
	$arr[0] = {"name":"where", "msg":"请输入出差地点"};
	$arr[1] = {"name":"note", "msg":"请输入出差事由！"};
	$arr[2] = {"name":"note","msg":"请输入请假事由！"};
	$arr[3] = {"name":"note", "msg":"请输入加班内容！"};
	$arr[4] = {"name":"person_num", "msg":"请输入人数！"};
	$arr[5] = {"name":"note", "msg":"请输入职位描述！"};
	$arr[6] = {"name":"cond", "msg":"请输入岗位要求！"};
	$arr[7] = {"name":"name", "msg":"请输入姓名！"};
	$arr[8] = {"name":"mobile", "msg":"请输入手机号！"};
	$arr[9] = {"name":"email", "msg":"请输入E-mail！"};
	$arr[10] = {"name":"department", "msg":"请输入部门！"};
	$arr[11] = {"name":"position", "msg":"请输入职位！"};
	$arr[12] = {"name":"title", "msg":"请输入主题！"};
	$arr[13] = {"name":"note", "msg":"请输入内容概要！"};
	/*$arr[14] = {"name":"cost", "msg":"请输入预算！"};
	$arr[15] = {"name":"provider", "msg":"请输入供应商信息！"};
	$arr[16] = {"name":"list", "msg":"请输入采购物品清单！"};*/
	$arr[14] = {"name":"reason", "msg":"请输入付款事由！"};
	$arr[15] = {"name":"cost", "msg":"请输入金额！"};
	$arr[16] = {"name":"reason", "msg":"请输入借款事由！"};
	$arr[17] = {"name":"cost", "msg":"请输入借款金额！"};
	$arr[18] = {"name":"title", "msg":"请输入合同名称！"};
	$arr[19] = {"name":"company", "msg":"请输入签约对方公司名称！"};
	$type_data = new Array();
	$type_data[1] = new Array(0,1);
	$type_data[2] = new Array(2);
	$type_data[3] = new Array(3);
	$type_data[4] = new Array(4, 5, 6);
	$type_data[5] = new Array(7, 10, 11, 9, 8);
	$type_data[6] = new Array(12, 13);
	$type_data[7] = new Array(14,15);
	$type_data[8] = new Array(16,17);
	$type_data[10] = new Array(18,19);
	var $check_data = $type_data[data.type];
	for (i in $check_data) {
		if (eval("!data.apply."+$arr[$check_data[i]].name)) {
			alert($arr[$check_data[i]].msg);
			return false;
		}
	}
	var check_func = $("#check-func").val();
	if (check_func) {
		if (!eval(check_func+"(data)")) {
			return false;
		}
	}
	
	return true;
}

function showWorkResult(data) {
	var finished_func = $("#finished_func").val();
	if (data.success) {
		if (finished_func) {
			eval(finished_func+"()");
		}	
		location.href = "?app=workflow";
	}else{
		alert(data.msg);
	}
}

function checkResult(data) {
	if (!data.state) {
		alert("请审批！");
		return false;
	}
	return true;
}

function refreshPage(data){
	alert(data.msg);
	if (data.success) {
		location.href = location.href;
	}
}

function checkEditWork(){
	if (!confirm("确认修改 ?")) {
		return false;
	}
	return true;
}

function addUser(user_id, user_name){
	$("#to_user").val(user_id);
	$("#user_name").val(user_name);
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
		"?app=department&act=getTreeMap",
		{},
		function(data) {
			window.map_tree = data;
		},
		'json'
	);
}

function closeTree(){
	$("#treet1").find("img").each(function(){
		if ($(this).attr("src") == "/res/libraries/jqtreetable/images/tv-collapsable.gif") {
			$(this).attr("src", "/res/libraries/jqtreetable/images/tv-expandable.gif");;
		};
	});
}

// 审批 通过/结束
function checkItem(id, state) {
	if (state ==1) {
		if (!confirm("确认通过,并结束工作流")) {
			return false;
		}
	}
	if (state == 2) {
		if (!confirm("确认拒绝，并结束工作流")) {
			return false;
		}
	}
	var note = $("#note").val();
	$.post(
		"?app=workflow&act=checkItem",
		{"id": id, "state":state,"note":note},
		function(data){
			if (data.success) {
				$(".ui-dialog-close").click();
				alert(data.msg);
				if ($(".go-next").hasClass("btn-primary")) {
					$(".go-next").click();
				}else{
					location.href = location.href;
				}
				
			}else{
				alert(data.msg)
			}
		},
		'json'
	);
}

//加签
function countersign(id) {
	var wait_user = $("#wait_user").val();
	if (wait_user=="") {
		alert("请选择加签人");
		return false;
	}
	if (!confirm("确认加签？")) {
		return false;
	}
	$.post(
		'/?app=workflow&act=countersign',
		{"id":id, "wait_user":wait_user},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
			
		},
		'json'
	);
}

//反馈加签
function backsign(id, state) {
	if (!confirm("确认操作？")) {
		return false;
	}
	var note = $("#note").val();
	$.post(
		"/?app=workflow&act=backsign",
		{"id":id, "state":state,"note":note},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}

//转交
function transferItem(id) {
	var to_user = $("#to_user").val();
	var note = $("#note").val();
	if (!to_user) {
		alert("请选择转交人");
		return false;
	}
	if (!confirm("确认转交")) {
		return false;
	}
	$.post(
		'?app=workflow&act=transferItem',
		{"id":id, "to_user": to_user,"note":note},
		function(data) {
			if (data.success) {
				$(".ui-dialog-close").click();
				alert(data.msg);
				if ($(".go-next").hasClass("btn-primary")) {
					$(".go-next").click();
				}else{
					location.href = location.href;
				}
			}else{
				alert(data.msg);
			}
		},
		'json'
	);

}

function uploadAttachment() {
	$.ajaxFileUpload({
		url:'/index.php?app=notice&act=upload',       //需要链接到服务器地址
		secureuri:false,
		fileElementId:"attachment_input",                            //文件选择框的id属性
		dataType: 'json',                                   //服务器返回的格式，可以是json
		success: function (data, textStatus) {               //相当于java中try语句块的用法
				addAttachment(data.data);
		
		},
		error: function (data, status, e) {           //相当于java中catch语句块的用法
			alert("抱歉，您的浏览器不支持该功能！\n\n您可以使用火狐，Goolge Chrome，360极速浏览器(极速模式)");
		}
	});	
}
$(function(){
	$("#attachment_input").live("change", function(){
		uploadAttachment();
	})
	$(".btn-delete-attachment").live("click", function(){
		$(this).parents(".attachment-tpl").hide("normal",function(){
			$(this).remove();
		});
	});
	$(".hours").live("change", function(){
		if (parseInt($(this).val())>23) {
			alert("超过23小时请以一天计算！");
			$(this).val("");
		}
	});
})
	
function addAttachment(info) {
	try {
			var source   = $("#attachment-tpl").html();
			var template = Handlebars.compile(source);
			$("#attachment-start").before(template(info));
	} catch (e) {
		alert(e.message);
	}

}

function getDateNum(classname) {
	var end_date = "";
	$("select."+classname).each(function(){
		if ($(this).val().length<2) {
			end_date +="0";
		}
		end_date += $(this).val();
	});
	if (end_date) {
		return parseInt(end_date);
	}else{
		return false;
	}
	
}

//确认考勤
function setAttendance(id){
	if (!confirm("确认将该工作流标记为已处理？")) {
		return false;
	}
	$.post(
		"/?app=workflow&act=setAttendance",
		{"id":id},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href=location.href;
			}
		},
		'json'
	);

}

function setAstate(id) {
	setState(id, "astate");
}

function setCstate(id) {
	setState(id, "cstate");
}

function setBstate(id) {
	setState(id, "bstate");
}

function setState(id,type) {
	if (!confirm("确认将该工作流标记为已处理？")) {
		return false;
	}
	$.post(
		"/?app=workflow&act=setState",
		{"id":id,"field":type},
		function(data){
			alert(data.msg);
			if (data.success) {
				location.href=location.href;
			}
		},
		'json'
	);
}

function countDate(){
	var start_y = $("#start_y").val();
	var start_m = $("#start_m").val();
	var start_d = $("#start_d").val();
	var start_h = $("#start_h").val();
	var end_y = $("#end_y").val();
	var end_m = $("#end_m").val();
	var end_d = $("#end_d").val();
	var end_h = $("#end_h").val();
	var start_min = parseInt($("#start_i").val());
	var end_min = parseInt($("#end_i").val());
	var start_time = Date.UTC(start_y,start_m,start_d,start_h);
	var end_time = Date.UTC(end_y, end_m,end_d,end_h);
	 
	
	var total_time = parseInt((end_time-start_time)/1000)/3600;
	var total_day = parseInt(total_time/24);
	var total_hour = total_time%24;
	if (end_min-start_min>=30) {
		total_hour += 0.5;
	}
	if (end_min-start_min<0) {
		total_hour -= 0.5;
		if (end_min-start_min<-30) {
				total_hour-=0.5;
		}
	}
	if (total_hour<0) {
		total_hour=0;
	}
	if (total_day<0) {
		total_day=0;
	}
	$("#days").val(total_day);
	$("#hours").val(total_hour);
	
}

function setDateAction(){
	$(".start-date").live("change", function(){
		countDate();
	});
	$(".end-date").live("change", function(){
		countDate();
	});
	$(".minute-select").live("change", function(){
		if ($(".minute-select").last().val() != $(".minute-select").first().val()) {
			//$(".minute-select").val($(this).val());
			countDate();
		}
	})

}

function setCostAction() {
	$(".cost").live("change", function(){
		var total_cost = 0;
		$(".cost").each(function(){
			if ($(this).val()) {
				total_cost += parseInt($(this).val());
			}
		});
		$("#total_cost").val(total_cost);
	})
}

function setUploadAction() {
/*$.swfFileUpload({
			url : "/?app=notice&act=upload", //服务器端地址
			fileId : "attachment_input", // 选择框id
			//fileType : "image", //文件类型限制
			text : "添加附件",
			onSuccess : function(file, data){ //操作成功响应事件
				data = jQuery.parseJSON(data);
				//$("body").append('<img src="'+data.img_url+'">');
				addAttachment(data.data);
			}
		});*/
}


function addGoods() {
	var html = $("#goods-tpl").html();
	$("tr.goods-detail:last").after(html);
	updateGoodsNO();
}

function updateGoodsNO() {
	var index=1;
	$("tr.goods-detail .no-tag").each(function(){
		$(this).text(index);
		index++;
	});
}

function clearGoods() {
	var tmp_tag;
	$("tr.goods-detail").each(function(){
		tmp_tag = 0;
		if ($.trim($(this).find(".no-tag").text())=="1") {
			return true;
		}
		$(this).find("input").each(function(){
			if ($.trim($(this).val())) {
				tmp_tag++;
				return false
			}
		});
		if (tmp_tag==0) {
			$(this).remove();
		}
	});
}

function showCost(obj) {
	var $tr = $(obj).parents("tr");
	var price = $.trim($tr.find(".goods-price").val());
	var num = $.trim($tr.find(".goods-num").val());
	var cost,total_cost;
	if (price&&num){
		price = parseFloat(price);
		num = parseInt(num);
		cost = price*num;
		cost = cost.toFixed(2);
		if (cost==parseInt(cost)){
			cost = parseInt(cost);
		}
		$tr.find(".goods-cost").val(cost);
	}
	total_cost = 0;
	$(".goods-cost").each(function(){
		if (!$(this).val()) {
			return true;
		}
		total_cost += parseFloat($(this).val());
	});
	if (total_cost>0) {
		total_cost = total_cost.toFixed(2);
		if (total_cost == parseInt(total_cost)) {
			total_cost=parseInt(total_cost);
		}
		$(".total-cost").val(total_cost);
	}
}

function deEmptyCost(obj) {
	if (!$(obj).val()) {
		showCost(obj);
	} 
}
function checkCaigou(data) {
	clearGoods();
	empty_check = false;
	data.apply.goods_list = Array();
	$("tr.goods-detail").each(function(){
		var obj = {};
		obj.name = $(this).find("input.goods-name").val();
		obj.unit = $(this).find("input.goods-unit").val();
		obj.size = $(this).find("input.goods-size").val();
		obj.price = $(this).find("input.goods-price").val();
		obj.num = $(this).find("input.goods-num").val();
		obj.cost = $(this).find("input.goods-cost").val();
		obj.note = $(this).find("input.goods-note").val();
		data.apply.goods_list.push(obj);
		if (!obj.name) {
			empty_check=true;
		}
		if (!obj.cost) {
			empty_check=true;
		}
	});
	
	if (empty_check) {
		alert("请将物品购买清单填写完整！");
		return false;
	}
	if (!data.apply.cost) {
		alert("请输入总计金额！");
		return false;
	}
	return true;
}

function showBigMoney() {
	var cost = $.trim($("#total-cost").val());
	if (!cost) {
		$(".big-money").html("");
		return;
	}
	
	var big_money = numToCny(cost);
	if (big_money.indexOf("角")==-1 && big_money.indexOf("分")==-1) {
		if (big_money.indexOf("元")==-1) {
			big_money += "元";
		}
		big_money += "整";
	}
	$(".big-money").html(big_money);
	
}

function coutProjectsTotal(obj) {
	var $tr = $(obj).parents("tr");
	var cost = 0;
	var tmp_cost=0;
	$tr.find("input.proj-money").each(function(){
		tmp_cost = $(this).val();
		if (tmp_cost) {
			cost += parseFloat(tmp_cost);
		}
	});
	
	if (cost) {
		cost = cost.toFixed(2);
		if (cost==parseInt(cost)) {
			cost = parseInt(cost);
		}
		$("#total-cost").val(cost);
	}
}

function numToCny(num){     
    var capUnit = ['万','亿','万','元',''];     
    var capDigit = { 2:['角','分',''], 4:['仟','佰','拾','']};     
    var capNum=['零','壹','贰','叁','肆','伍','陆','柒','捌','玖'];     
    if (((num.toString()).indexOf('.') > 16)||(isNaN(num)))      
        return '';     
    num = ((Math.round(num*100)).toString()).split('.');  
	num = (num[0]).substring(0, (num[0]).length-2)+'.'+ (num[0]).substring((num[0]).length-2,(num[0]).length);  
    num =((Math.pow(10,19-num.length)).toString()).substring(1)+num;     
    var i,ret,j,nodeNum,k,subret,len,subChr,CurChr=[];     
    for (i=0,ret='';i<5;i++,j=i*4+Math.floor(i/4)){     
        nodeNum=num.substring(j,j+4);     
        for(k=0,subret='',len=nodeNum.length;((k<len) && (parseInt(nodeNum.substring(k),10)!=0));k++){     
            CurChr[k%2] = capNum[nodeNum.charAt(k)]+((nodeNum.charAt(k)==0)?'':capDigit[len][k]);     
            if (!((CurChr[0]==CurChr[1]) && (CurChr[0]==capNum[0])))     
                if(!((CurChr[k%2] == capNum[0]) && (subret=='') && (ret=='')))     
                    subret += CurChr[k%2];     
        }     
        subChr = subret + ((subret=='')?'':capUnit[i]);     
        if(!((subChr == capNum[0]) && (ret=='')))     
            ret += subChr;     
    }     
    ret=(ret=='')? capNum[0]+capUnit[3]: ret;       
    return ret;     
}   

function cancelCheck(id) {
	if (!confirm("确认撤销本次审批？")) {
		return;
	}
	$.post(
		"/index.php?app=workflow&act=cancelCheck",
		{id:id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}