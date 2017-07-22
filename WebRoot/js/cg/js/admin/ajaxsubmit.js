/**
 * ajax表单自动提交
 */
 
$(function(){
	$(".ajaxsubmit").die().live("submit", function(){
		try {
			ajaxSubmit($(this));
		} catch (e) {
			console.log("Ajaxsubmit Error: "+e.message+" at line "+e.lineNumber);
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
	var data,submit_action, ajax_tags, tag_name, index_name, on_finish,data_type, name_count,tmp_val,sp_field ;
	data = {}; 
	name_count = new Array();
	ajax_tags =["input:text", "input:password", "input:hidden", "select", "input:radio:checked", "input:checkbox:checked", "textarea"],
	on_finish = submit_form.attr("onFinish") ? submit_form.attr("onFinish") : submit_form.attr("onfinish");
	on_check = submit_form.attr("onCheck") ? submit_form.attr("onCheck") : submit_form.attr("oncheck");
	data_type =  submit_form.attr("dataType") ? submit_form.attr("dataType") : submit_form.attr("datatype");
	data_type = data_type ? data_type : 'json';
	submit_action = submit_form.attr("action");
	submit_action = submit_action ? submit_action : location.href;
	sp_field = submit_form.attr("sp");
	//获取表单数据
	$.each(ajax_tags, function(num, tag) {
		submit_form.find(tag).each(function(){
			//过滤没有name的数据
			tag_name = $(this).attr("name");
			if (sp_field&&tag_name==sp_field) {
				 getSpField(data);
				 return true;
			}
			if (!tag_name) {
				return true;
			}
			tmp_val = $(this).val().replaceAll("\"", "\\\"");
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
	var org_url = location.href;
	if (object) {
		org_url = object.location.href;
	}
	var url =org_url.split("&tm=");
	url = url[0].split("#");
	if (!loc) {
		loc="";
	}
	var tm =new Date().getTime()
	if (object) {
		object.location.href = url[0] + "&tm="+tm + loc;
	}else{
		location.href = url[0] + "&tm="+ tm + loc;
	}
}


String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}  

$(function(){
	$(".num").live("keypress", function(event){
		  var keycode = event.which;
		 if (keycode<48||keycode>57){
			event.preventDefault()
		 }
	})
	$(".num").live("keyup", function(){
		$(this).val($(this).val().replace(/[^0-9]+/,''));
	});

	$(".float").live("keypress", function(event){
		 var keycode = event.which;
		if ((keycode<48&&keycode!=46) || keycode>57) {
				event.preventDefault()
				return;
		 }
		if ((keycode==46) && ($(this).val().indexOf(".")>-1||$(this).val().length==0)) {
			 event.preventDefault()
		}
	});
	$(".float").live("keyup", function(){
		$(this).val($(this).val().replace(/[^0-9\.]+/,''));
	});
	$(".phone").live("keyup",function(){
		$(this).val($(this).val().replace(/[^\-0-9]+/,''));
	});
})