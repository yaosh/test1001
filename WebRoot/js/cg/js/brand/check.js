

function updateBrandState(state,suffix) {
 var brand_id = $("#main_brand_id").val();
 state = state ? 1 : 2;
 $.post(
	'/brand.php?app=brand&act=updateBrandState',
	{"brand_id":brand_id, "state":state},
	 function(data){
		alert(data.msg);
		if (data.success) {	
			reloadPage("#"+suffix);
		}
	},
	'json'
 );
}
$(function(){
	$(".check-btn").removeClass("edit-btn");
	//$(".check-btn").css("background-color","red");
	$(".check-btn").css("color","white");
	$(".check-btn").addClass("btn");
	$(".check-btn").addClass("btn-danger");
	
$(".pass-check").live("click", function(){
	if (!confirm("确认通过审核？")) {
		return false;
	}
	var check_id = $(this).attr("data-id");
	var page_loc = $(this).parents(".detail-box").attr("id");
	page_loc = page_loc ? page_loc : "";
	var brand_id = $("#main_brand_id").val();
	$.post(
		"/brand.php?app=brandTemp&act=passCheck",
		{ "id":check_id,"brand_id":brand_id},
		function(data){
			if (data.success) {
				if (data.data>0) {
					alert(data.msg);
					reloadPage("#"+page_loc);
				}else{
					updateBrandState(confirm("当前品牌已审核完毕，是否将其状态修改为已编辑\n\n "), page_loc);
				}
				
			}else{
				alert(data.msg);
			}
		},
		'json'
	);
})
$(".deny-check").live("click", function(){
	if (!confirm("确认拒绝审核？")) {
		return false;
	}
	var page_loc = $(this).parents(".detail-box").attr("id");
	var check_id = $(this).attr("data-id");
	$.post(
		"/brand.php?app=brandTemp&act=denyCheck",
		{ "id":check_id},
		function(data){
			if (data.success) {
				if (data.data>0) {
					alert(data.msg);
					reloadPage("#"+page_loc);
				}else{
					updateBrandState(confirm("当前品牌已审核完毕，是否将其状态修改为已编辑\n\n "), page_loc);
				}
				
			}else{
				alert(data.msg);
			}
			
		},
		'json'
	);
})

})