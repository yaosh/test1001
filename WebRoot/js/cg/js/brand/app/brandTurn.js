loadBrandList(0);
function loadBrandList(type) {
	$.post(
		"/brand.php?app=user&act=brandList",
		{ "type":type},
		function(data) {
			showBrandList(data);
		},
		'json'
	);
}

function loadSearchBrand(name){
	var type = $(".brand-select.active").attr("data-id");
	$.post(
		"/brand.php?app=user&act=brandList&type="+type,
		{ "name":name},
		function(data) {
			showBrandList(data);
		},
		'json'
	);
}
function showBrandList(data,id) {
	var source   = $("#brand-list-tpl").html();
	var template = Handlebars.compile(source);

	if (id) {
		$("#"+id).append(template(data));
	}else{
		$("#left-select").html(template(data));
	}
	
}


$("#left-select").live("change", function() {
	updateAddState();
	
});
$("#right-select").live("change", function() {
	$("#del-list-btn").removeAttr("disabled");
});
var brand_input_val = "";
$("#search-brand-input").live("keyup", function(){
	if ($.trim($(this).val())==brand_input_val) {
		return;
	}
	
	brand_input_val =$.trim($(this).val());
	/*if (brand_input_val=="") {
		$(".brand-select").first().click();
		return;
	}
	$(".brand-select").removeClass("active");
	*/
	if (brand_input_val=="") {
		if ($(".brand-select.active").attr("data-id")=="1") {
			$(".brand-select").first().click();
		}else{
			$(".brand-select").last().click();
		}
		
		return;
	}
	loadSearchBrand(brand_input_val);
	
});
$(".select-all-brand").live("click", function(){
	$(this).parent().find("select option").each(function(){
		$(this).attr("selected", "selected");
	});
	if ($(this).attr("datatype")=="left") {
		updateAddState();
	}else{
		updateDelState();
	}
	
});
$(".select-ops-brand").live("click", function(){
	$(this).parent().find("select option").each(function(){
		if ($(this).attr("selected")) {
			$(this).removeAttr("selected");
		}else{
			$(this).attr("selected","selected");
		}
	});
	if ($(this).attr("datatype")=="left") {
		updateAddState();
	}else{
		updateDelState();
	}
	
});

$("#add-list-btn").live("click", function(){
	var added_item = getAddedItem();
	var data = {};
	data.list= [] ;
	 $("#left-select option:selected").each(function(){
		if (added_item[$(this).val()] ) {
			return true;
		}
		data.list.push({ id:$(this).val(), name:$(this).text() } );
	});
	$(this).attr("disabled", "disabled");
	showBrandList(data, "right-select");
	updateShowNum();

});
$("#del-list-btn").live("click", function(){
	$("#right-select option:selected").each(function(){
		$(this).remove();
	});
	$(this).attr("disabled", "disabled");
	updateShowNum();
	updateAddState();
});
function getAddedItem() {
	var added_item=new Array();
	$("#right-select option").each(function(){
		added_item[$(this).val()] = 1;
	});
	return added_item;
}
$(".brand-select").live("click", function(){
	$(".brand-select").removeClass("active");
	$(this).addClass("active");
	if ($(this).attr("data-id")==1) {
		loadBrandList(0);
		$("#assign").text("分配给");
	}else{
		loadUserList();
		$("#assign").text("交接给");
	}
	$("#search-brand-input").val("");
});

function loadUserList() {
	$.post(
		"/brand.php?app=user&act=getBrandUser",
		{},
		function(data) {
			showUserList(data);
		},
		'json'
	);
}

function showUserList(data) {
	var source   = $("#group-list-tpl").html();
	var template = Handlebars.compile(source);
	$("#left-select").html(template(data));

}

function loadUserBrand(id,obj) {
	if ($(obj).attr("state")==1) {
		$(obj).attr("label",$(obj).attr("label").replace("▼", "▶"));
		$(obj).html("");
		$(obj).attr('state',0);
		return;
	}else{
		$(obj).attr("state",1);
	}
	$.post(
		'/brand.php?app=user&act=getUserBrand',
		{"id":id},
		function(data) {
			showUserBrandList(id,data);
		},
		'json'
	);
}

function updateAddState() {
	var added_item = getAddedItem();
	var state_change=false;
	$("#left-select option:selected").each(function(){
		if (added_item[$(this).val()] ==undefined) {
			$("#add-list-btn").removeAttr("disabled");
			state_change=true;
			return false;
		}
	});
	if (!state_change) {
		$("#add-list-btn").attr("disabled", "disabled");
	}
}

function updateDelState() {
	var state_ok=false;
	$("#right-select option:selected").each(function(){
		state_ok=true;
		return false;
	});
	if (state_ok) {
		$("#del-list-btn").removeAttr("disabled");
	}else{
		$("#del-list-btn").attr("disabled", "disabled");
	}
}

function updateShowNum(){
	var len= $("#right-select option").length;
	$("#brand_num").text(len);
}
function showUserBrandList(id,data) {
	$("optgroup").html("");
	var source   = $("#brand-list-tpl").html();
	var template = Handlebars.compile(source);
	$("#left-select optgroup").each(function(){
		if ($(this).attr("data-id")==id) {
			$(this).append(template(data));		
			$(this).attr("label",$(this).attr("label").replace("▶" ,"▼"));
		}else{
			$(this).attr("label", $(this).attr("label").replace("▼","▶"));
		}
	});
}

function turnBrand() {
	var brand_list= new Array();
	var to_user  = $("#to_user").val();
	var to_username = $("#to_user option:selected").text();
	
	$("#right-select option").each(function(index){
		brand_list[index] = $(this).val();
	});

	if (brand_list.length<1) {
		alert("请选择品牌！");
		return false;
	}

	if (to_user=="0") {
		alert("请选择要分配的人员！");
		return false;
	}
	
	var msg = "确认将所选的"+brand_list.length+"个品牌分配给 "+to_username;
	var brand_ids;
	if (!confirm(msg)) {
		return false;
	}
	brand_ids = brand_list.join(",");
	$.post(
		location.href,
		{ids:brand_ids,add_user:to_user},
		function(data) {
			alert(data.msg);
			location.href = location.href;
		},
		'json'
		
	);
	

}