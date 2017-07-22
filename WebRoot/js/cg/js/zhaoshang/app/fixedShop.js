function editFixedShop(id) {
	if (!id) {
		id = 0;
	}
	var data = {};
	data.id = id;
	data.brand_id = $("#brand_id").val();
	data.proj_id = $("#proj_id").val();
	data.level = $("#level").val();
	data.floor = $("#floor").val();
	if (empty(data.floor)) {
		alert("请输入楼层！");
		$("#floor").focus();
		return;
	}	
	data.pos = $("#pos").val();
	if (empty(data.pos)) {
		alert("请输入铺位号！");
		$("#pos").focus();
		return;
	}	
	data.area = $("#area").val();
	if (empty(data.area)) {
		alert("请输入合同面积！");
		$("#area").focus();
		return;
	}	
	data.start_time = $("#start_time").val();
	if (empty(data.start_time)) {
		alert("请输入合同开始时间！");
		$("#start_time").focus();
		return;
	}	
	data.end_time = $("#end_time").val();
	if (empty(data.end_time)) {
		alert("请输入合同结束时间！");
		$("#end_time").focus();
		return;
	}	
	data.tel = $("#tel").val();
	
	data.address = $("#address").val();
	/*if (empty(data.address)) {
		alert("请输入公司地址！");
		$("#address").focus();
		return;
	}*/
	data.rent = {};
	data.rent.coop_method = $("#coop_method").val();
	data.rent.coop_cost = $("#coop_fixed input").val();
	data.rent.coop_co = $("#coop_co input").val();

	/*if (empty(data.rent)) {
		alert("请输入月租金平效！");
		$("#rent").focus();
		return;
	}*/	
	data.rent_increase = $("#rent_increase").val();
	/*if (empty(data.rent_increase)) {
		alert("请输入租金递增！");
		$("#rent_increase").focus();
		return;
	}*/	
	data.property_cost = $("#property_cost").val();
	/*if (empty(data.property_cost)) {
		alert("请输入物业管理费标准！");
		$("#property_cost").focus();
		return;
	}*/
	data.owner = $("#owner").val();
	/*if (empty(data.owner)) {
		alert("请输入签约商(公司/名称)！");
		$("#owner").focus();
		return;
	}*/	
	data.note = $("#note").val();
	/*if (empty(data.note)) {
		alert("请输入备注！");
		$("#note").focus();
		return;
	}*/
	data.market_contact = new Array();
	var tmp;
	$(".market-contact").each(function(){
		tmp = {};
		tmp.name = $(this).find("input.name").val();
		tmp.job = $(this).find("input.job").val();
		tmp.tel = $(this).find("input.tel").val();
		if (empty(tmp.name)&&empty(tmp.job)&&empty(tmp.tel)) {
			return true;
		}
		data.market_contact.push(tmp);
	});
	data.running_contact = new Array();
	$(".running-contact").each(function(){
		tmp = {};
		tmp.name = $(this).find("input.name").val();
		tmp.job = $(this).find("input.job").val();
		tmp.tel = $(this).find("input.tel").val();
		if (empty(tmp.name)&&empty(tmp.job)&&empty(tmp.tel)) {
			return true;
		}
		data.running_contact.push(tmp);
	});
	$.post(
		"/zs.php?app=fixedShop&act=edit",
		data,
		function(result){
			alert(result.msg);
			if (result.success) {
				window.parent.location.href = window.parent.location.href;
			}
		},
		'json'
	);
}
