function loginOut(){
	if(confirm('确认要退出吗')){
		$.post( "/index.php?act=login&do=out&is_ajax=1", null , function(data){
				if (data.success) {
					location.href="/index.php?act=login";
					return;
				}
				alert(data.msg);
			},'json'
		);
	}
}