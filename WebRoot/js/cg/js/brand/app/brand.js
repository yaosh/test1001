$(function(){
	$("#logo_input").live("change", function(){
		uploadBrandLogo();
	})
	$(".num").live("keyup", function(){
		$(this).val($(this).val().replace(/[^0123456789]/g,''));
	});
	$("#price").live("keyup",function(){
		$(this).val($(this).val().replace(/[^0123456789\-\.]/g,""));
	})
})
function checkAddBase(data){
  var error_num=0;
  if (!(data.info.name||data.info.eng_name)) {
	  showError("info[name]","中文名和英文并必须填写一项 ！", error_num==0);
	  error_num++;
  }

  if (data.detail_info && data.detail_info.website) {
	  if (!isUrl(data.detail_info.website)) {
		   showError("detail_info[website]", "网址格式不正确 ！", error_num==0);
		   error_num++;
	  }
  }
  if (data.detail_info && data.detail_info.tel) {
	  if (!isPhone(data.detail_info.tel)) {
		  showError("detail_info[tel]", "电话号码格式不正确 ！", error_num==0);
		  error_num++;
	  }
  }
  if (!data.brand_type) {
	  alert("请选择业态！");
	  return false;
  }
  if (error_num>0) {
	  return false;
  }
 /* if (data.info.shop_type_id=="0") {
	  errorAlert("请输入中文名称");
	  return false;
  }
  */
  return true;
}

function addBaseResult(data) {
	if (data.success) {
		var $location =  "/brand.php?&app=brand&act=addResult";
		if (data.data.id) {
			$location += "&id="+data.data.id;
		}
		location.href = $location;
	}else{
		if (data.data.name) {
			showError(data.data.name,data.msg,"error",1);
		}else{
			alert(data.msg);
		}
		
	}
}

function uploadBrandLogo() {
	$.ajaxFileUpload({
		url:'/brand.php?app=brand&act=uploadLogo',       //需要链接到服务器地址
		secureuri:false,
		fileElementId:"logo_input",                            //文件选择框的id属性
		dataType: 'json',                                   //服务器返回的格式，可以是json
		success: function (data, textStatus) {               //相当于java中try语句块的用法
			$("#logo_view").show();
			$("#logo_view img").attr("src", data.data.img_url);
			$("#logo_url").val(data.data.img_url);
			
		},
		error: function (data, status, e) {           //相当于java中catch语句块的用法
			alert("error");
		}
	});	
}

$("#front_cate").live("change", function(){
	var front_cate = $(this).val();
	var init_options = "";
	$.post(
		'/brand.php?act=getCateList',
		{"id":front_cate},
		function(data){
			for (i in data) {
				init_options += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			$("#sub_cate").html(init_options);
			$("#sub_cate option:first").attr("selected", "selected");
		},
		'json'
	);
})


function finishEditBase(data) {
	if (data.success) {
		alert(data.msg);
		reloadPage("");
	}else{
		if (data.data.name) {
			showError(data.data.name, data.msg, 1);
		}else{
			alert(data.msg);
		}
	}

}

function refreshCurrent(data) {
	var page_pos  = $("#page_pos").val();
	alert(data.msg);
	if (data.success) {
		reloadPage(page_pos);
	}
}


function checkNote() {
	var action = $("#note_form")	.attr("action");
	action = action.split("?app=");
	action = "/brand.php?app="+action[1];
	$.post(
		action,
		{"detail_info[note]":$("#note").val()},
		function(data) {
			alert(data.msg);
			if (data.success) {
				reloadPage("#a1");
			}
		},
		'json'
	);
	return false;
}

function addCompeteBrand() {
	var brand_arr = new Array();
	var brand_ids = ""; 
	var master_id = $("#master_id").val();
	$("input:checkbox:checked").each(function(){
		brand_arr.push($(this).val());
	});
	brand_ids = brand_arr.join();
	if (!brand_ids) {
		window.parent.$(".ui-dialog-close").click();
		return;
	}
	var brand_tr;
	$.post(
		"/brand.php?app=brand&act=addCompete&master_id="+master_id,
		{"ids":brand_ids},
		function(data) {
			alert(data.msg);
			if (data.success) {
				reloadPage("#a3", window.parent.window);
				/*$.each(brand_arr, function(key, value){
					brand_tr = $(".compete-"+value).clone();
					window.parent.window
				});
				*/

			}
			
		},
		'json'
	);
}

function deleteCompeteBrand(master_id, slave_id) {
	if (!confirm("确认取消竞争关系")) {
		return false;
	}
	$.post(
		"/brand.php?app=brand&act=deleteCompete",
		{"master_id":master_id, "slave_id":slave_id},
		function(data) {
			alert(data.msg);
			if (data.success) {
				$(".compete-"+slave_id).hide("normal");
				reloadPage("#a3");
			}
		},
		'json'
	);
}

function deleteTempCompeteBrand(master_id, slave_id) {
	if (!confirm("确认取消竞争关系")) {
		return false;
	}
	$.post(
		"/brand.php?app=brandTemp&act=deleteCompete",
		{"master_id":master_id, "slave_id":slave_id},
		function(data){
			alert(data.msg);
			if (data.success) {
				$(".compete-"+slave_id).first().hide("normal");
				reloadPage("#a3");
			}
		},
		'json'
	);
}


function checkProduct(data) {
	var error_num=0;
	if (!data.name) {
		showError("name","请输入名称 ！",1)
		error_num++;
	}

	if (error_num>0) {
		return false;
	}
	return true;
}
function refreshProduct(data){
	alert(data.msg);
	if (data.success) {
		reloadPage("#a4");
	}
}

function deleteProduct(id) {
	if (!confirm("确认删除此产品")) {
		return false;
	}
	$.post(
		"/brand.php?app=brand&act=deleteProduct",
		{"id": id},
		function(data){
			alert(data.msg);
			if (data.success) {
				$(".product-"+id).hide("normal");
			}
		},
		'json'
	);
}

$("#delete-brand").live("click", function(){
	if (!confirm("确认删除这个品牌")) {
		return false;
	}
	var brand_id = $(this).attr("data-id");
	$.post(
		"/brand.php?app=brand&act=delete",
		{"id" : brand_id},
		function (data) {
			alert(data.msg);
			if (data.success) {
				
					location.href = $("#refer-url").val();
				
			}
		},
		'json'
	);
});

function showSelectTpl(obj) {
 obj = $(obj);
 $("#cust_age").html($("#age-tpl-"+(parseInt(obj.val())+1)).html());
$("#cust_age option:first").attr("selected","selected");
}

function checkDangan() {
	if ($("#brand-level").val()=="0") {
		alert("请选择品牌等级！");
		return false;
	}
	return true;
}

$(".pingshen").live("click", function(){
	if (!confirm("确认评审通过？")) {
		return false;
	}
	$.post(
		'/brand.php?app=brand&act=setPingshen',
		{"brand_id":$("#main_brand_id").val()},
		function(data){
			alert(data.msg);
			if (data.success) {
				reloadPage();
			}
		},
		'json'
	);
});