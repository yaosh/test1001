$("#logo_input").live("change", function(){
		uploadBrandLogo();
})

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

function checkAddBase(data){
  var error_num=0;
  if (!(data.info.name||data.info.eng_name)) {
	  alert("中文名和英文并必须填写一项!");
	  return false;
  }
  if (!data.brand_type) {
	  alert("请选择业态！");
	  return false;
  }
  return true;
}


function finishEditBase(data) {
	alert(data.msg);
	if (data.success) {
		location.href = location.href;
	}

}