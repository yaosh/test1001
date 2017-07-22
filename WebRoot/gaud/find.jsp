<html>
<head>
<link rel="stylesheet" href="/css/lib/base.css">
    <link rel="stylesheet" href="/css/lib/font-awesome.css">
    <link rel="stylesheet" href="/css/zhaoshang/main.css">
    
    <script src="/js/cg/js/lib/jquery.js"></script>
    <script src="/js/cg/js/lib/bootstrap.min.js"></script>
    <script src="/js/cg/js/plugin/dialog.js"></script>
	<script type="text/javascript" src="/js/cg/js/zhaoshang/public.js"></script>
	
	<script type='text/javascript' src='/dwr/interface/GAUD.js'></script>
    <script type='text/javascript' src='/dwr/engine.js'></script>
    <script type='text/javascript' src='/dwr/util.js'></script>
	
</head>
<body>
	<div id="ewm" class="ewm" style="position:fixed; top:70px; right:100px;z-index:999">
		<ul>
		    <li style="text-align:center; margin-bottom:15px;">
				<b>在地图上单击即可获取地图坐标:</b>
			</li>
			<li style="text-align:center;">
				<p>X：<input type="text" name="mapX" id="mapX" value=""/></p>
			</li>
			<li style="text-align:center">
				<p>Y：<input type="text" name="mapY" id="mapY" value=""/></p>
			</li>
		</ul>
	</div>
	<div>
		<div id="main">
			<div class="clear"></div>
			<div class="map-area" style="width:1000px;position:relative;"  >
				<div style="position:absolute;left:0px right:0px;width:1110px;height:550px;z-index:998;background:rgba(255,255,255,0.8);-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;display:none;" id="map-shadow">	
				</div>
				<div class="start-tip" style="position:absolute;color:red;top:200px;left:0px;z-index:999;">
							<div style="text-align:center;width:1000px;">
								<b>使用鼠标单击地图，绘制一个多边形区域，双击完成绘制 </b><input type="button" value="开始" id="start-btn" class="btn btn-primary" />
							</div>
				</div>
				<div class="confirm-tip" style="text-align:center;position:absolute;top:200px;left:0px;z-index:999;display:none;">
						<div style="width:1110px;">
							<div style="width:400px;margin:0px auto;background-color:white;padding:10px;">
								<div id="confirm" style="display:none;height:66px;">
									<div>确认录入该区域内的所有商家？</div>
									<div style="margin-top:10px;">
										<input type="button" value="确认录入" id="find-btn" onclick="startFind()" class="btn btn-primary" />
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" value="取消" id="cancel-btn" class="btn" />
									</div>
								</div>
	
								<div id="loading" style="display:none;">
									<img src="/assets/img/icon/loading_big.gif" height=40 alt="" /><br>
									正在导入. . . <span id="percent" style="font-size:15px;">0</span>%
								</div>
							</div>
						</div>
							
				</div>
				<div class="result-tip" style="text-align:center;position:absolute;top:200px;left:0px;z-index:999;display:none;">
					<div style="width:1110px;">
						<div style="width:400px;margin:0px auto;background-color:white;padding:10px;color:red;padding:10px;">
								<div><b>共查询到<span id="total-num"></span>个商家,成功导入<span id="success-num"></span>家</b></div>
								<div style="margin-top:10px;" >
										<input type="button" value="继续" class="btn btn-primary" onclick="continueStart()" />
								</div>
						</div>
					</div>
				</div>
			    <input type="hidden" id="poi_keyword" value="购物服务|餐饮服务|生活服务|住宿服务|医疗保健服务|汽车维修|体育休闲服务|汽车销售|科教文化服务|汽车服务|公司企业|金融保险服务|摩托车服务|商务住宅|风景名胜|公共设施|政府机构及社会团体|地名地址信息|道路附属设施|交通设施服务" />
			    <div id="iCenter" style="position:absolute;left:10px;right:10px;top:10px;bottom:10px;height:540px;width:1110px;border:2px solid red;"></div>
		</div>
    </div>
</div>

<script language="javascript" src="http://app.mapabc.com/apis?t=javascriptmap&v=3&key=b0a7db0b3a30f944a21c3682064dc70ef5b738b062f6479a5eca39725798b1ee300bd8d5de3a4ae3"></script>
<script type="text/javascript">
//0 不是一个新任务   1 开始执行一个新任务
var taskFlag = 0;
var mapObj,mouse;

function mapInit()
{
	//地图初始化参数设置 
	  
    var opt = { 
  
        dragEnable:true,//地图可拖动 
        zoomEnable:true//地图可缩放 
        //keyboardEnable:true,//键盘操作地图有效 
       // jogEnable:true,//地图具有缓动效果 
        //continuousZoomEnable:true,//地图缩放时具有连续缩放效果 
        //doubleClickZoom:true,//支持双击鼠标放大地图 
        //scrollWheel:true//支持鼠标滚轮缩放地图 
  
    }; 
    
	mapObj = new MMap.Map("iCenter",opt);
	
	//加载工具条插件 
    mapObj.plugin("MMap.ToolBar",function() { 
        toolbar = new MMap.ToolBar(); 
        mapObj.addControl(toolbar); 
  
    }); 

    //addContentMenu();
    addMapClick();
    
	var lnglat=new MMap.LngLat(118.78333,32.05000);
	mapObj.setCenter(lnglat);
	
}

function addMapClick(){ 
	  
    mapObj.bind(mapObj,"click",function(e){ 
  
    document.getElementById("mapX").value=e.lnglat.lng; 
  
    document.getElementById("mapY").value=e.lnglat.lat;  
  
    }); 
  
} 

function addContentMenu(){ 
	  
    //构造 ContextMenu 新实例 
  
    var contextMenu = new MMap.ContextMenu({ 
  
        isCustom:false, 
  
        width:180 
  
    }); 
  
    //添加菜单项 
  
    contextMenu.addItem("放大",function(){ 
  
        mapObj.zoomIn(); 
  
    },0); 
  
    contextMenu.addItem("缩小",function(){ 
  
        mapObj.zoomOut(); 
  
    },1); 
  
    //绑定右键单击事件，打开右键菜单 
  
    mapObj.bind(mapObj,"rightclick",fun5=function(e){ 
  
        contextMenu.open(mapObj,e.lnglat); 
  
    }); 
  
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
function regionSearch(page)
{
	var num=1;
	
	if (!page)
	{
		page=1;
		taskFlag = 1;
	}

	mouse.close();//关闭鼠标工具
	
	//var keywords = "品牌|店|餐饮";//传入参数
	
	var keywords = document.getElementById("poi_keyword").value;
	
	//构造拉框查询实例
	var PoiSearchOption = 
						   {
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


function poiSearch_CallBack(data)
{
	if (data.status=='E0'&&data.list.length>0)
	{
			window.current_count += parseInt(data.record);
			saveData(data.list,data.total);
			return;
	}
	
	mapObj.clearOverlaysByType('marker');
	mapObj.clearInfoWindow();
	
	if(data.status=='E0')
	{
		doFinishedFind();
	}else if(data.status =="E1")
	{
         //resultStr = "未查找到任何结果!\n>建议：\n1.请确保所有字词拼写正确。\n2.尝试不同的关键字。\n3.尝试更宽泛的关键字。";	
		 doFinishedFind();
	}
	else
	{
		 resultStr=data.error.description+ "\n\n错误代码："+data.error.code;
		 alert(resultStr);
		 continueStart();
	}
	//alert(resultStr);
}


//定义标注点和信息窗口
function addMarkerAndTip(i,d)
{
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

		document.getElementById("mapX").value=e.lnglat.lng; 
	    document.getElementById("mapY").value=e.lnglat.lat;
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

<script type="text/javascript">
document.body.onload=function()
{
	mapInit();
	showShadow();
}

$("#start-btn").live("click",function(){
	mouseTool();
	$(".start-tip").remove();
	hideShadow();
});

function startFind() 
{
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

function saveData(list,total)  
{
	
	  //var str = JSON.stringify(data[0]);
	   //alert(str);
	  //alert(list.length);
	  
    //[object Object],[object Object],[object Object],[object Object]
	   //gridcode typecode tel newtype xml code type imageid url timestamp citycode pguid address name linkid match drivedistance srctype y x
		//[{"gridcode":"4818066122","typecode":"","tel":"","newtype":"050100","xml":"","code":"320106","type":"餐饮服务;中餐厅;中餐厅","imageid":"","url":"","timestamp":"","citycode":"025","buscode":"","pguid":"B00190B5QD","address":"金银街20号","name":"阳光餐厅(金银街)","linkid":"","match":"1","drivedistance":"0","srctype":"basepoi","y":"32.057925","x":"118.774858"},
		//{"gridcode":"4818067200","typecode":"","tel":"025-83310212;025-83278637","newtype":"050108","xml":"","code":"320106","type":"餐饮服务;中餐厅;湖南菜(湘菜)","imageid":"","url":"","timestamp":"","citycode":"025","buscode":"","pguid":"B00190B6LE","address":"北京西路12号四条巷口","name":"和湘汇(北京西路店)","linkid":"","match":"1","drivedistance":"0","srctype":"basepoi","y":"32.059485","x":"118.776566"}]
		//for(i in data) 
		//{ 
		//    var obj = data[i]; 
		 //   var lineText = obj.gridcode+','+obj.typecode+','+obj.tel+','+obj.newtype+','+obj.xml+','+obj.code+','+obj.type+','+obj.imageid+','+obj.url+','+obj.timestamp+','+obj.citycode+','+obj.pguid+','+obj.address+','+obj.name+','+obj.linkid+','+obj.match+','+obj.drivedistance+','+obj.srctype+','+obj.y+','+obj.x
		//    alert(lineText);
		//}
	//GAUD.saveGuadData(JSON.stringify(list),CallBack_RusultData);
	//var tempStringList =JSON.stringify(list).replace(new RegExp('(["\"])', 'g'),"\\\"");
	
	GAUD.saveGuadData(JSON.stringify(list),taskFlag,function CallBack_saveGuad(data) 
	{
		//alert('finish:'+data); 
		//reset task flag
		taskFlag = 0;
		 
		updateResultNew(data,total);			
		window.current_page++;
		regionSearch(window.current_page);

	});

	return;
	
	//var data = {};
	//data.list = list;
	////$.post(
	//	"/gaud!importShop.action?",
	//	data,
	//	function(result) {
	//		updateResult(result.data,total);			
	//		window.current_page++;
	//		regionSearch(window.current_page);
	//	},
	//	'json'
	//);
	
}

function CallBack_RusultData(data) 
{
	alert('finish:'+data); 
	updateResultNew(data,total);			
	window.current_page++;
	regionSearch(window.current_page);

}

function updateResultNew(num,total) 
{
		window.current_total = total;
		window.current_success += num;
		
		var percent = parseInt(100*window.current_count/total);
		$("#percent").text(percent);
		//window.current_exist += num.exist;
}


function doFinishedFind() 
{
	window.current_page=1;
	window.current_count=0;
	$("#percent").text("0");
	$("#success-num").text(window.current_success);
	$("#total-num").text(window.current_total);
	$(".confirm-tip").hide();
	$(".result-tip").show();
}

function updateResult(num,total) 
{
		window.current_total = total;
		window.current_success += num.success;
		
		var percent = parseInt(100*window.current_count/total);
		$("#percent").text(percent);
		//window.current_exist += num.exist;
}

function continueStart() 
{
	$("#success-num").text(0);
	$("#total-num").text(0);
	$(".confirm-tip").hide();
	$(".result-tip").hide();
	mapObj.clearOverlaysByType('polygon');
	hideShadow();
	mouseTool();
}



</script>
</body>
</html>
