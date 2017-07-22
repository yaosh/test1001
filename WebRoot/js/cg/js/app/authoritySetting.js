


//异步提交
/*
var datachk = {};
datachk.chkval = new Array();
$(".aus").click(function(){
	//获取checkbox中的值
	var chckObj = $("input[type='checkbox']:checked");
	for(var i=0; i<chckObj.size(); i++){
		datachk.chkval.push(chckObj.get(i).value);
	}
	
	$.post('/?app=authoritySetting&act=index', datachk, ajaxchkfun, 'text');
	function ajaxchkfun(data){
		alert(data);
		if(data.success){
			alert(data.msg);
		}
	}
	
});

*/
