function initListener() {
	$("#sub_cate").die().live("change", function(){
		var tmp_val = $("#sub-cate").val();
		if (!tmp_val) {
			tmp_val = "";
		}else{
			tmp_val = tmp_val.join(",")
		}
		$("#tmp-style").val(tmp_val);
		getProductCates($(this).val().join(","));
	});

	$("#sub-cate").die().live("change", function(){
		//setDelButton();
	});

}
function setDelButton(){
	if (!$("#sub-cate").val()) {
		$(".del-type-btn").attr("disabled","disabled");
	}else{
		$(".del-type-btn").removeAttr("disabled");
	}
}

function setAddButton() {
	var current_selected = $("#sub_cate option:selected");
	if (current_selected.length<1) {
		$(this).attr("disabled","disabled")
		return;
	}
	var current_value= new Array();;
	$("#sub-cate option").each(function(){
		current_value[$(this).val()]=1;
	});
	var enabled_btn = false;
	current_selected.each(function(){
		if (!current_value[$(this).val()]) {
			enabled_btn=true;
		}
	});
	if (enabled_btn) {
		$(".add-type-btn").removeAttr("disabled");
	}else{
		$(".add-type-btn").attr("disabled", "disbaled");
	}
}
function selectAction() {
	$("#sub-cate option").each(function(){
		$(this).attr("selected","selected");
	});
}
$("#front_cate").live("change", function(){
	var front_cate = $(this).val();
	var init_options = "<option value='0'>- 请选择 -</option>";
	if (front_cate=="0") {
		$("#sub_cate").html(init_options);
		return;
	}
	$.post(
		'/brand.php?act=getCateList',
		{ "id":front_cate},
		function(data){
			for (i in data) {
				init_options += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			$("#sub_cate").html(init_options);
			//$("#sub_cate option:first").attr("selected", "selected");
			$("#sub-cate").html("");
			$("#product-styles").html("");
		},
		'json'
	);
})

function addProductCate(option) {
	getProductCates(option.val());
}

function delProductCate(option) {
	$(".style-"+option.val()).remove();
}

function getProductCates(id) {
	var init_options = "<option value='0'>- 请选择 -</option>";
	var init_options = "";
	if (id=="0"||id.split(",").length>1) {
		$("#sub-cate").html(init_options);
		return;
	}
	
	$.post(
		'/brand.php?app=brandStyle',
		{id:id},
		function(data){
			for (i in data) {
				init_options += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			$("#sub-cate").html(init_options);
			tmp_val = $.trim($("#tmp-style").val());
			tmp_val = tmp_val.split(",");
			$("#sub-cate").val(tmp_val);
		},
		'json'
	);
}

/*$("#sub-cate2").die().live("change", function(){
	$(".style-ul").hide();
	$(".style-"+$(this).val()).show();
});*/