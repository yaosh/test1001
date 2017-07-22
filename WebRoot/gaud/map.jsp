
<script language="javascript" src="http://app.mapabc.com/apis?t=javascriptmap&v=3&key=b0a7db0b3a30f944a21c3682064dc70ef5b738b062f6479a5eca39725798b1ee300bd8d5de3a4ae3"></script>
<script type="text/javascript">
var mapObj,mouse;
function mapInit(){
	mapObj = new MMap.Map("iCenter");
	var lnglat=new MMap.LngLat(118.78333,32.05000);
	mapObj.setCenter(lnglat);
}
//加载鼠标工具，用于鼠标在地图的拉框绘制
function mouseTool(){
	mapObj.plugin(['MMap.MouseTool'],function(){
		mouse=new MMap.MouseTool(mapObj);
		mouse.polygon();//打开鼠标绘多边形模式
		mapObj.bind(mouse,'draw',function(e){
			var polygon=mapObj.getOverlaysByType("polygon")
			if(polygon.length>1){
				mapObj.clearOverlaysByType('polygon');
				mapObj.addOverlays(e);
			}
			confirmFind();
		})
	});
}
//拉框查询
function regionSearch(page){
	var num=1;
	if (!page){
		page=1;
	}

	mouse.close();//关闭鼠标工具
	var keywords = "品牌|店|餐饮";//传入参数
	//构造拉框查询实例
	var PoiSearchOption = {
		srctype:"POI",//数据来源
		type:"",//数据类别
		number:50,//每页数量,默认10
		batch:page,//请求页数，默认1
		range:3000,	//查询范围，默认3000米
		ext:""//扩展字段
		};
	var poiSearch = new MMap.PoiSearch(PoiSearchOption);
		var polygons = mapObj.getOverlaysByType("polygon");
		//var polygon1 = polygons[0];
		var polygonarr = polygons[0].getPath();
		poiSearch.byRegion(polygonarr,keywords,poiSearch_CallBack);
}
//关键字查询返回结果显示
var markers = new Array(),markerLngLat = new Array(),infos = new Array();
function poiSearch_CallBack(data){
	alert(1111);
	if (data.status=='E0'&&data.list.length>0){
			window.current_count += parseInt(data.record);
			saveData(data.list,data.total);
			return;
	}
	mapObj.clearOverlaysByType('marker');
	mapObj.clearInfoWindow();
	if(data.status=='E0'){
		doFinishedFind();
	}else if(data.status =="E1"){
         //resultStr = "未查找到任何结果!\n>建议：\n1.请确保所有字词拼写正确。\n2.尝试不同的关键字。\n3.尝试更宽泛的关键字。";	
		 doFinishedFind();
	}else{
		 resultStr=data.error.description+ "\n\n错误代码："+data.error.code;
		 alert(resultStr);
		 continueStart();
	}
	//alert(resultStr);
}
//定义标注点和信息窗口
function addMarkerAndTip(i,d){
	//定义标注点
	var marker = new MMap.Marker({
		id:(i+1),
		position:new MMap.LngLat(d.x,d.y),
		offset:new MMap.Pixel(-10,-34),
		icon:"http://code.mapabc.com/images/apin/lan_" + (i + 1) + ".png",
		draggable:false
	});
	markers.push(marker);
	markerLngLat.push(new MMap.LngLat(d.x,d.y));
	mapObj.addOverlays(marker);
	//定义信息窗口
	var infoWindow = new MMap.InfoWindow({
		autoMove:true,
		content:'<div style="color:#0066CC;font-family:Microsoft YaHei;font-size:80%;word-break: break-all"><b>'+d.name+'</b><hr/>'+TipContents(d.type,d.address,d.tel)+'</div>',
		size:new MMap.Size(240, 0),
		offset:new MMap.Pixel(-70,-96)				
	});
	infos.push(infoWindow);
	//绑定 marker 点击事件，点击后打开 Tip
	mapObj.bind(marker,'click',function(e){
		infoWindow.open(mapObj, marker.getPosition());	
	});
}
//Tip中的内容设置
function TipContents(type,address,tel){
	if (type == "" || type == "undefined" || type == null || type == " undefined" || typeof type == "undefined") {
		type = "暂无";
	}
	if (address == "" || address == "undefined" || address == null || address == " undefined" || typeof address == "undefined") {
		address = "暂无";
	}
	if (tel == "" || tel == "undefined" || tel == null || tel == " undefined" || typeof address == "tel") {
		tel = "暂无";
	}
	var str ="地址：" + address + "<br/>电话：" + tel + " <br/>类型："+type;
	return str;
}
//鼠标移动到结果列表某项时的变化
function openMarkerTipById(i, thiss) {
    thiss.style.background = '#CAE1FF';
	markers[i].icon="http://code.mapabc.com/images/apin/hong_" + (i + 1) + ".png";
	mapObj.updateOverlay(markers[i]);
}
//鼠标离开结果列表的某项时恢复原状
function onmouseout_MarkerStyle(i, thiss) {
    thiss.style.background = "";
	markers[i].icon="http://code.mapabc.com/images/apin/lan_" + (i + 1) + ".png";
	mapObj.updateOverlay(markers[i]);
}
//鼠标点击结果列表的某项时打开marker的Tip
function onclickMarker(i,thiss) {
	thiss.style.background = '#CAE1FF';
	markers[i].icon="http://code.mapabc.com/images/apin/hong_" + (i + 1) + ".png";
    infos[i].open(mapObj, markerLngLat[i]);
}
</script>

			<div id="iCenter" style="height:500px; width:690px;"></div>

			<!-- <div style="padding:5px 0px 0px 5px;font-size:12px;height:500px">
				<b>拉框查询</b><br/><br/>
				<div>
					先绘制需要查询的多边形区域：<input type="button" value="点我" onclick="mouseTool()"/><br/>
					关键字：<input type="text" id="keyword" name="keyword" value="酒店"/>
					<input type="button" value="查询" onclick="regionSearch()"/><br/>
					<hr size="1" noshade="noshade" style="border:1px #cccccc dotted;"/>
				</div>
				<div id="result_title"><b>拉框查询结果：</b></div>
				<div id="result" name="result" style="height:520px;overflow:auto;width:327px;margin-top:5px"></div>
			  			</div> -->

<script type="text/javascript">
document.body.onload=function(){
	mapInit();
	showShadow();
}
$("#start-btn").live("click",function(){
	mouseTool();
	$(".start-tip").remove();
	hideShadow();
});
function startFind() {
		$("#confirm").hide();
		$("#loading").show();
		window.current_page=1;
		window.current_total=0;
		window.current_success=0;
		window.current_exist=0;
		window.current_count=0;
		regionSearch();
}
$("#cancel-btn").live("click", function(){
	$(".confirm-tip").hide();
	hideShadow();
	mapObj.clearOverlaysByType('polygon');
});

//确认录入信息
function confirmFind() {
	showShadow();
	$(".confirm-tip").show();
	$("#confirm").show();
	$("#loading").hide();
}
function hideShadow() {
	$("#map-shadow").hide();
}
function showShadow() {
	$("#map-shadow").show();
}

function saveData(list,total)  {
	//alert(list.length)
	var data = {};
	data.list = list;
	$.post(
		"/zs.php?app=poi&act=find",
		data,
		function(result) {
			updateResult(result.data,total);			
			window.current_page++;
			regionSearch(window.current_page);
		},
		'json'
	);
	
}
function doFinishedFind() {
	window.current_page=1;
	window.current_count=0;
	$("#percent").text("0");
	$("#success-num").text(window.current_success);
	$("#total-num").text(window.current_total);
	$(".confirm-tip").hide();
	$(".result-tip").show();
}

function updateResult(num,total) {
		window.current_total = total;
		window.current_success += num.success;
		
		var percent = parseInt(100*window.current_count/total);
		$("#percent").text(percent);
		//window.current_exist += num.exist;
}

function continueStart() {
	$("#success-num").text(0);
	$("#total-num").text(0);
	$(".confirm-tip").hide();
	$(".result-tip").hide();
	mapObj.clearOverlaysByType('polygon');
	hideShadow();
	mouseTool();
}

</script>