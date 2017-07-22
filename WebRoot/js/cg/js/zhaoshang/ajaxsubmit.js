/**
 * ajax表单自动提交
 */
 
$(function(){
	$(".ajaxsubmit").die().live("submit", function(){
		try {
			ajaxSubmit($(this));
		} catch (e) {
			console.log("Ajaxsubmit Error: "+e.message+"  at "+e.lineNumber);
		}
		
		return false;
	})
})
function ajaxSubmit(submit_form) {
	//获取表单的jquery对象
	try{
		var isJqueryObject = obj.parent();
	}catch(e){
		submit_form = $(submit_form);
	}

	//初始化数据
	var data,submit_action, ajax_tags, tag_name, index_name, on_finish,data_type, name_count,tmp_val,submit_btn,on_before ;
	data = {}; 
	name_count = new Array();
	ajax_tags =["input:text", "input:hidden", "select", "input:radio:checked", "input:checkbox:checked", "textarea"],
	on_finish = submit_form.attr("onFinish") ? submit_form.attr("onFinish") : submit_form.attr("onfinish");
	on_check = submit_form.attr("onCheck") ? submit_form.attr("onCheck") : submit_form.attr("oncheck");
	on_before = submit_form.attr("onBefore") ? submit_form.attr("onBefore") : submit_form.attr("onbefore");
	data_type =  submit_form.attr("dataType") ? submit_form.attr("dataType") : submit_form.attr("datatype");
	data_type = data_type ? data_type : 'json';
	submit_action = submit_form.attr("action");
	submit_action = submit_action ? submit_action : location.href;
	submit_action = submit_action.split("?app=");
	submit_action = "/brand.php?app="+submit_action[1];
	submit_btn = submit_form.find("input:submit");
	if (on_before) {
		eval(on_before);
	}
	//获取表单数据
	$.each(ajax_tags, function(num, tag) {
		submit_form.find(tag).each(function(){
			//过滤没有name的数据
			tag_name = $(this).attr("name");
			if (!tag_name) {
				return true;
			}
			tmp_val = $(this).val();
			if (tmp_val==null) {
				tmp_val = "";
			}
			if (typeof(tmp_val) == "object") {
				tmp_val = tmp_val.toString();
			}
			tmp_val = tmp_val.replaceAll("\"", "\\\"");
			
			
			tmp_val = tmp_val.replaceAll(/[\r\n]/g, '\\n');
			//处理数组数据
			if (tag_name.indexOf("[]")>0) {
				tag_name = tag_name.substr(0,tag_name.length-2);
				if (!data[tag_name]) {
					eval("data."+tag_name+"= new Array()");
				}
				eval("data."+tag_name+".push('"+tmp_val+"')");
			}else if (tag_name.indexOf("]")-tag_name.indexOf("[")>1) {
				index_name = tag_name.substring(tag_name.indexOf("[")+1, tag_name.indexOf("]"));
				tag_name= tag_name.substr(0, tag_name.indexOf("["));
				if (!data[tag_name]) {
					eval("data."+tag_name+"= {}");
				}
				eval("data."+tag_name+"."+index_name+" = \""+tmp_val+"\"");
			}else{
				eval("data."+tag_name+" = '"+tmp_val+"'");
			}
		})
	});
	
	//校验表单数据
	if (on_check) {	
		if (on_check.indexOf("(")>0) {
			on_check = on_check.substr(0, on_check.indexOf("("));
		}
		if (!eval(on_check+"(data)")) {
			return false;
		}
	}
	if (submit_btn) {
		submit_btn.attr("disabled","disabled");
		submit_btn.val("正在...");
	}
	//发送数据
	$.post(
		submit_action,
		data,
		function(data){
			
			if (on_finish) {
				if (on_finish.indexOf("(")>0) {
					on_finish = on_finish.substr(0, on_finish.indexOf("("));
				}
				eval(on_finish+"(data)");
			}else{
				alert("Submit Finished");
			}
			submit_btn.removeAttr("disabled");
			submit_btn.val("确定");
			return false
		},
		data_type
	)
	return false;
}

function errorAlert(data) {
	alert(data);
}

function successAlert(data) {
	alert(data);
}

//强刷页面
function reloadPage(loc,object) {
	var tm =new Date().getTime()
	var org_url = location.href;
	if (object) {
		org_url = object.location.href;
	}
	if (org_url.indexOf("brand.php")<0) {
		var url = org_url.split("?");
		url = url[0].split("#");
		url = url[0];
		if (object) {
			object.location.href = url + "?tm="+tm+loc;
		}else{
			location.href = url + "?tm="+tm+loc;
		}
	}else{
		
		var url =org_url.split("&tm=");
		url = url[0].split("#");
		if (!loc) {
			loc="";
		}
		if (object) {
			object.location.href = url[0] + "&tm="+tm + loc;
		}else{
			location.href = url[0] + "&tm="+ tm + loc;
		}
	}
}


String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}  

/**显示表单错误消息
  *param name 字段名
  *param msg 显示信息
  *param focus 是否聚焦
  *param keep 是否保留原有的错误信息
  */
function showError(name, msg,focus){
	if (focus) {
		$(".warnings").remove();
		$("input").removeClass("txt-danger");
		$("select").removeClass("txt-danger");
	}
	
	var msg_show = '<span class="warnings" style="display:none;color:#c91032;">&nbsp;&nbsp;'+msg+'</span>'
	var error_element=null;
	$("input,select,textarea").each(function(){
		if ($(this).attr("name")==name) {
			$(this).after(msg_show);
			if (!$(this).hasClass("txt-danger")) {
				$(this).addClass("txt-danger");
			}
			error_element = $(this);
		}
	});
	if (focus) {
		try {
			error_element.focus();
		} catch (e) {
			alert(msg);
		}
		
	}
	$(".warnings").show("normal")
}


function isMobile(obj){
	if(obj.length!=11) {
		return false;
	}else if (isNaN(obj)) {
		return false;
	}else{
		return true;
	}

}

function isPhone(obj) { 
	var pattern=/^[0-9\-]+$/; 
	if(pattern.test(obj)) { 
		return true; 
	} 
	else{ 
		return false; 
	} 
} 

function isEmail(email)
{
	invalidChars = " /;,:{}[]|*%$#!()`<>?";
	if (email == "") {
		return false;
	}
	for (i=0; i< invalidChars.length; i++) {
		badChar = invalidChars.charAt(i)
		if (email.indexOf(badChar,0) > -1) {
			return false;
		}
	}
	atPos = email.indexOf("@",1)
	if (atPos == -1) {
		return false; 
	}
	if (email.indexOf("@", atPos+1) != -1) {
		return false; 
	}
	periodPos = email.indexOf(".",atPos)
	if (periodPos == -1) {
		return false; // and at least one "." after the "@"
	}
	if ( atPos +2 > periodPos) {
		return false; // and at least one character between "@" and "."
	}
	if ( periodPos +3 > email.length) {
		return false; 
	}
	return true;

}

function isUrl(str_url){ 
         var strRegex = "^((https|http|ftp|rtsp|mms)?://)"  
         + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
         + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
         + "|" // 允许IP和DOMAIN（域名） 
         + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.  
         + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名  
        + "[a-z]{2,6})" // first level domain- .com or .museum  
        + "(:[0-9]{1,4})?" // 端口- :80  
        + "((/?)|" // a slash isn't required if there is no file name  
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
        var re=new RegExp(strRegex);  
  //re.test() 
        if (re.test(str_url)){ 
            return str_url.indexOf(".")>-1;
        }else{  
            return (false);  
        } 
    } 