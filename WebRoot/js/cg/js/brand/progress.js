$(function(){
	if ($("#progress-show").text()=="0") {
		var pbrand_id = $(".complete-status").attr("data-id");
		$.post(
			"/brand.php?app=brand&act=setProgress&id="+pbrand_id,
			{"id": pbrand_id },
			function(data){
				$("#progress-show").text(data.progress);
			},
			'json'
		);
	}
})