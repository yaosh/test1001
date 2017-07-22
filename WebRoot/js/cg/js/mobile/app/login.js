function finishLogin(data) {
	if (data.success) {
		var url = $("#url").val();
		location.href="/m/?act=loginSuccess&url="+encodeURIComponent(url);
	}else{
		$(".message").text(data.msg);
	}
}