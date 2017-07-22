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
	<div id="cityPositionInfo" class="ewm" style="position:fixed; bottom:60px; left:20px;z-index:999;background:rgba(200,255,255,0.8);border:1px solid;display:none">
	<ul>
	<li style="text-align:center;margin-top:0px;background:rgba(255,255,255,0.8);border:1px solid;text-align:right;" onclick="hideMoreInfo()">
		X&nbsp;&nbsp;&nbsp;
	</li>
     <li style="text-align:center;margin-right:5px;margin-top:5px;">
		<p>横标：<input type="text" name="mapX" id="mapX" value="" style="background:rgba(0,255,255,0.3);"  /></p>
	</li>
	<li style="text-align:center;margin-right:5px;">
		<p>纵标：<input type="text" name="mapY" id="mapY" value="" style="background:rgba(0,255,255,0.3);"  /></p>
	</li>
	<li style="text-align:center;margin-right:5px;">
		<p>西北：<input type="text" name="xibeiPoint" id="xibeiPoint" value="" style="background:rgba(0,255,255,0.3);"  /></p>
		<p>东南：<input type="text" name="dongnanPoint" id="dongnanPoint" value="" style="background:rgba(0,255,255,0.3);"  /></p>
	</li>
	<li style="text-align:center;margin-right:5px;">
		<p>缩放：<input type="text" name="zoomLevel" id="zoomLevel" value="" style="background:rgba(0,255,255,0.3);"  /></p>
		<p>范围：<input type="text" name="zoomRange" id="zoomRange" value="" style="background:rgba(0,255,255,0.3);"  /></p>
	</li>
	<li style="text-align:center;margin-right:5px;margin-bottom:5px;">
		<p>比例：<input type="text" name="scaleId" id="scaleId" value="" style="background:rgba(0,255,255,0.3);"  /></p>
	</li>
	</ul>
	</div>

	<div id="ewm" class="ewm" style="position:fixed; top:40px; right:60px;z-index:999;background:rgba(200,255,255,0.8);border:1px solid;">
		<ul>
			<li style="text-align:center;;margin-right:5px;margin-top:10px;">
				<p>经度：<input type="text" name="cityX1Id" id="cityX1Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  />
				-><input type="text" name="cityX2Id" id="cityX2Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  /></p>
			</li>
			<li style="text-align:center;;margin-right:5px;">
				<p>纬度：<input type="text" name="cityY1Id" id="cityY1Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  />
				-><input type="text" name="cityY2Id" id="cityY2Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>步长：<input type="text" name="stepLength" id="stepLength" value="0.4" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>框序：<input type="text" name="polygonId" id="polygonId" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>累加：<input type="text" name="totalId" id="totalId" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-bottom:15px;margin-right:5px;">
				<p>总计：<input type="text" name="totalNumId" id="totalNumId" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;margin-left:5px;margin-bottom:5px;">
				<p>
				<input type="button" class="btn btn-primary" name="drawPolygonId" id="drawPolygonId" value="画框" onclick="autoDrawPolygon()" />
				<input type="button" class="btn btn-primary" name="getBatchDataId" id="getBatchDataId" value="抓取" onclick="batchFetchData()" />
				<input type="button" class="btn btn-primary" name="cancleDataId" id="cancleDataId" value="取消" onclick="cancleGetData()" />
				<input type="button" class="btn btn-primary" name="cancleDataId" id="cancleDataId" value="更多" onclick="showMoreInfo()" />
				</p>
			</li>
		</ul>
	</div>
	<div>
		<div id="main">
			<div class="clear"></div>
			<div class="map-area" style="width:1000px;position:relative;"  >
				<div class="confirm-tip" style="text-align:center;position:absolute;top:200px;left:0px;z-index:999;display:none;">
						<div style="width:1110px;">
							<div style="width:400px;margin:0px auto;background-color:rgba(0,255,255,0.8);padding:10px;">
								<div id="loading" style="display:none;">
									正在导入. . .进度： <span id="percent" style="font-size:15px;">0</span>%
								</div>
							</div>
						</div>
				</div>
			    <input type="hidden" id="poi_keyword" value="购物服务|餐饮服务|生活服务|住宿服务|医疗保健服务|汽车维修|体育休闲服务|汽车销售|科教文化服务|汽车服务|公司企业|金融保险服务|摩托车服务|商务住宅" />
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
        zoomEnable:true,//地图可缩放 
        level: 17,//初始地图缩放级别 
        zooms:[2,17]//地图缩放级别范围 
        //keyboardEnable:true,//键盘操作地图有效 
       // jogEnable:true,//地图具有缓动效果 
        //continuousZoomEnable:true,//地图缩放时具有连续缩放效果 
        //doubleClickZoom:true,//支持双击鼠标放大地图 
        //scrollWheel:true//支持鼠标滚轮缩放地图 
  
    }; 

	mapObj = new MMap.Map("iCenter",opt);
	
	//加载工具条插件 
    //mapObj.plugin("MMap.ToolBar",function() { 
    //    toolbar = new MMap.ToolBar(); 
    //    mapObj.addControl(toolbar); 
  
    //}); 

  //加载地图基本控件 
    
    mapObj.plugin(["MMap.ToolBar", "MMap.OverView", "MMap.Scale"],function() { 
  
        //加载工具条插件，工具条包括方向键盘、标尺键盘和自动定位控制 
  
        toolbar = new MMap.ToolBar(); 
  
        mapObj.addControl(toolbar); 
  
        //加载鹰眼 
  
        overview = new MMap.OverView();  
  
        mapObj.addControl(overview); 
  
        //加载比例尺 
  
        scale = new MMap.Scale();  
  
        mapObj.addControl(scale); 
  
    }); 

    addMapClick();
    addZoomEvent();
    //addPolygon();
    addmultiPolygon();
    onLoadShowMapInfo();

    //北京 116.4, 39.9
    //南京 118.78333,32.05000
	var lnglat=new MMap.LngLat(116.4,39.9);
	mapObj.setCenter(lnglat);
	
}

function autoDrawPolygon()
{

	//115.2,41.1 117.7,41.1  
	//115.2,39.40 117.7,39.40
	var startX1 = document.getElementById("cityX1Id").value;
	var startX2 = document.getElementById("cityX2Id").value;
	var startY1 = document.getElementById("cityY1Id").value;
	var startY2 = document.getElementById("cityY2Id").value;
	var step = document.getElementById("stepLength").value; 
	
	if (startX1 == '' )
	{
	    alert("X1 is null");
	    return;
	}

	if (startX2 == '' )
	{
	    alert("X2 is null");
	    return;
	}

	if (startY1 == '' )
	{
	    alert("Y1 is null");
	    return;
	}

	if (startY2 == '' )
	{
	    alert("Y2 is null");
	    return;
	}

	if (step == '' )
	{
	    alert("step is null");
	    return;
	}

	startX1 = parseFloat(startX1);
	startX2 = parseFloat(startX2);
	startY1 = parseFloat(startY1);
	startY2 = parseFloat(startY2);
	step = parseFloat(step);
	
	//alert('startX1:'+startX1);
	//alert('startX2:'+startX2);
	//alert('startY1:'+startY1);
	//alert('startY2:'+startY2);
	
	var loopLengthX = parseInt((startX2 - startX1)/step)+1;
	var loopLengthY = parseInt((startY2 - startY1)/step)+1;
	var tempY1 = 0;
	var tempY2 = 0;
	var tempX1 = 0;
	var tempX2 = 0;

	//alert('loopLengthX:'+loopLengthX);
	//alert('loopLengthY:'+loopLengthY);
	
	for(var i = 0;i <loopLengthY;i++)
	{
		tempY1 = startY2 - step*(i+1);
		tempY2 = startY2 - step*i;

		//边界处理
		if(tempY1 < startY1)
		{
			tempY1 = startY1;
		}

		//如果是一条线 就跳过
        if(tempY1.toFixed(3) >= tempY2.toFixed(3))
        {
            continue;
        }
		
		for(var j = 0;j <loopLengthX;j++)
		{
			tempX1 = startX1 + step*j;
			tempX2 = startX1 + step*(j+1);

			//边界处理
			if(tempX2 > startX2)
			{
				tempX2 = startX2;
			}

			//如果是一条线 就跳过
	        if( tempX1.toFixed(3) >= tempX2.toFixed(3) )
	        {
	            continue;
	        }
	        
			addPolygonByXY(tempX1,tempX2,tempY1,tempY2,"polygon"+i+j);
		}
	}

}

function addmultiPolygon()
{

	//115.2,41.1 117.7,41.1  
	//115.2,39.40 117.7,39.40
	var startX1 = 115.2;
	var startX2 = 117.7;
	var startY1 = 39.40;
	var startY2 = 41.1;

	var step = 0.6;

	var loopLengthX = (startX2 - startX1)/step;
	var looplengthY = (startY2 - startY1)/step;
	var tempY1 = 0;
	var tempY2 = 0;
	var tempX1 = 0;
	var tempX2 = 0;
	

	for(var i = 0;i <looplengthY;i++)
	{
		tempY1 = startY2 - step*(i+1);
		tempY2 = startY2 - step*i;

		for(var j = 0;j <loopLengthX;j++)
		{
			tempX1 = startX1 + step*j;
			tempX2 = startX1 + step*(j+1);

			addPolygonByXY(tempX1,tempX2,tempY1,tempY2,"polygon"+i+j);
		}
	}

	/*
	addPolygonByXY("115.2","115.4","40.9","41.1","polygon001");
	addPolygonByXY("115.4","115.6","40.9","41.1","polygon002");
	addPolygonByXY("115.6","115.8","40.9","41.1","polygon003");
	addPolygonByXY("115.8","116.0","40.9","41.1","polygon004");
	addPolygonByXY("116.0","116.2","40.9","41.1","polygon005");
	addPolygonByXY("116.2","116.4","40.9","41.1","polygon006");
	addPolygonByXY("116.4","116.6","40.9","41.1","polygon007");
	addPolygonByXY("116.6","116.8","40.9","41.1","polygon008");
	addPolygonByXY("116.8","117.0","40.9","41.1","polygon009");
	addPolygonByXY("117.0","117.2","40.9","41.1","polygon010");
	addPolygonByXY("117.2","117.4","40.9","41.1","polygon011");
	addPolygonByXY("117.4","117.6","40.9","41.1","polygon012");
	addPolygonByXY("117.6","117.8","40.9","41.1","polygon013");

	addPolygonByXY("115.2","115.4","40.7","40.9","polygon014");
	addPolygonByXY("115.4","115.6","40.7","40.9","polygon015");
	addPolygonByXY("115.6","115.8","40.7","40.9","polygon016");
	addPolygonByXY("115.8","116.0","40.7","40.9","polygon017");
	addPolygonByXY("116.0","116.2","40.7","40.9","polygon018");
	addPolygonByXY("116.2","116.4","40.7","40.9","polygon019");
	addPolygonByXY("116.4","116.6","40.7","40.9","polygon020");
	addPolygonByXY("116.6","116.8","40.7","40.9","polygon021");
	addPolygonByXY("116.8","117.0","40.7","40.9","polygon022");
	addPolygonByXY("117.0","117.2","40.7","40.9","polygon023");
	addPolygonByXY("117.2","117.4","40.7","40.9","polygon024");
	addPolygonByXY("117.4","117.6","40.7","40.9","polygon025");
	addPolygonByXY("117.6","117.8","40.7","40.9","polygon026");

    addPolygonByXY("115.2","115.4","40.5","40.7","polygon027");
	addPolygonByXY("115.4","115.6","40.5","40.7","polygon028");
	addPolygonByXY("115.6","115.8","40.5","40.7","polygon029");
	addPolygonByXY("115.8","116.0","40.5","40.7","polygon030");
	addPolygonByXY("116.0","116.2","40.5","40.7","polygon031");
	addPolygonByXY("116.2","116.4","40.5","40.7","polygon032");
	addPolygonByXY("116.4","116.6","40.5","40.7","polygon033");
	addPolygonByXY("116.6","116.8","40.5","40.7","polygon034");
	addPolygonByXY("116.8","117.0","40.5","40.7","polygon035");
	addPolygonByXY("117.0","117.2","40.5","40.7","polygon036");
	addPolygonByXY("117.2","117.4","40.5","40.7","polygon037");
	addPolygonByXY("117.4","117.6","40.5","40.7","polygon038");
	addPolygonByXY("117.6","117.8","40.5","40.7","polygon039");

    addPolygonByXY("115.2","115.4","40.3","40.5","polygon040");
	addPolygonByXY("115.4","115.6","40.3","40.5","polygon041");
	addPolygonByXY("115.6","115.8","40.3","40.5","polygon042");
	addPolygonByXY("115.8","116.0","40.3","40.5","polygon043");
	addPolygonByXY("116.0","116.2","40.3","40.5","polygon044");
	addPolygonByXY("116.2","116.4","40.3","40.5","polygon045");
	addPolygonByXY("116.4","116.6","40.3","40.5","polygon046");
	addPolygonByXY("116.6","116.8","40.3","40.5","polygon047");
	addPolygonByXY("116.8","117.0","40.3","40.5","polygon048");
	addPolygonByXY("117.0","117.2","40.3","40.5","polygon049");
	addPolygonByXY("117.2","117.4","40.3","40.5","polygon050");
	addPolygonByXY("117.4","117.6","40.3","40.5","polygon051");
	addPolygonByXY("117.6","117.8","40.3","40.5","polygon052");

    addPolygonByXY("115.2","115.4","40.1","40.3","polygon053");
	addPolygonByXY("115.4","115.6","40.1","40.3","polygon054");
	addPolygonByXY("115.6","115.8","40.1","40.3","polygon055");
	addPolygonByXY("115.8","116.0","40.1","40.3","polygon056");
	addPolygonByXY("116.0","116.2","40.1","40.3","polygon057");
	addPolygonByXY("116.2","116.4","40.1","40.3","polygon058");
	addPolygonByXY("116.4","116.6","40.1","40.3","polygon059");
	addPolygonByXY("116.6","116.8","40.1","40.3","polygon060");
	addPolygonByXY("116.8","117.0","40.1","40.3","polygon061");
	addPolygonByXY("117.0","117.2","40.1","40.3","polygon062");
	addPolygonByXY("117.2","117.4","40.1","40.3","polygon063");
	addPolygonByXY("117.4","117.6","40.1","40.3","polygon064");
	addPolygonByXY("117.6","117.8","40.1","40.3","polygon065");

    addPolygonByXY("115.2","115.4","39.9","40.1","polygon066");
	addPolygonByXY("115.4","115.6","39.9","40.1","polygon067");
	addPolygonByXY("115.6","115.8","39.9","40.1","polygon068");
	addPolygonByXY("115.8","116.0","39.9","40.1","polygon069");
	addPolygonByXY("116.0","116.2","39.9","40.1","polygon070");
	addPolygonByXY("116.2","116.4","39.9","40.1","polygon071");
	addPolygonByXY("116.4","116.6","39.9","40.1","polygon072");
	addPolygonByXY("116.6","116.8","39.9","40.1","polygon073");
	addPolygonByXY("116.8","117.0","39.9","40.1","polygon074");
	addPolygonByXY("117.0","117.2","39.9","40.1","polygon075");
	addPolygonByXY("117.2","117.4","39.9","40.1","polygon076");
	addPolygonByXY("117.4","117.6","39.9","40.1","polygon077");
	addPolygonByXY("117.6","117.8","39.9","40.1","polygon078");
	*/
}

//添加多边形 

function addPolygonByXY(x1,x2,y1,y2,poiID)
{
	var polygonArr=new Array(); 
	  
    polygonArr.push(new MMap.LngLat(x1,y2));  

    polygonArr.push(new MMap.LngLat(x2,y2));  

    polygonArr.push(new MMap.LngLat(x2,y1));  
    
    polygonArr.push(new MMap.LngLat(x1,y1));  

    var tempPolygon=new MMap.Polygon({  

    id:poiID,  

    path:polygonArr, 

    strokeColor:"#E53B36",  

    strokeOpacity:0.8,   

    strokeWeight:3, 

    fillColor: "#E7463E",  

    fillOpacity: 0.35 

   });  

   mapObj.addOverlays(tempPolygon);     

   mapObj.setFitView([tempPolygon]); 

 
}

//
function addPolygon()
{ 
  
        var polygonArr=new Array(); 
  
        polygonArr.push(new MMap.LngLat("115.2","41.1"));  
  
        polygonArr.push(new MMap.LngLat("115.4","41.1"));  

        polygonArr.push(new MMap.LngLat("115.4","40.9"));  
        
        polygonArr.push(new MMap.LngLat("115.2","40.9"));  
  
        
  
        polygon=new MMap.Polygon({  
  
        id:"polygon",  
  
        path:polygonArr, 
  
        strokeColor:"#E53B36",  
  
        strokeOpacity:0.8,   
  
        strokeWeight:3, 
  
        fillColor: "#E7463E",  
  
        fillOpacity: 0.35 
  
    });  
  
    mapObj.addOverlays(polygon);     
  
    mapObj.setFitView([polygon]); 
  
    retVal = true; 
  
    return retVal; 
  
} 


function addZoomEvent()
{

	    mapObj.bind(mapObj,"zoomchange",function(){
	
	    //mapObj.addEventListener(mapObj,ZOOM_CHANGED,function(){

		bounds = mapObj.getBounds();//获取地图矩形视野bounds对象 
	    
	    document.getElementById("xibeiPoint").value = bounds.southwest.lng+","+bounds.southwest.lat;
	    document.getElementById("dongnanPoint").value = bounds.northeast.lng+","+bounds.northeast.lat;

	    //var size = mapObj.getSize();//获取地图视窗的像素大小 
	    //document.getElementById("xiangsuWidth").value = size.width+"px";
	    //document.getElementById("xiangsuHeight").value = size.height+"px";

	    var zoom = mapObj.getZoom();//获取当前ZOOM级别 
	    var zooms = mapObj.getZooms();//获取当前zoom级别范围 
	    
	    document.getElementById("zoomLevel").value = zoom; 
	    document.getElementById("zoomRange").value = zooms;

	    var result = mapObj.getScale();//获取地图中心点比例尺 
	    
	    document.getElementById("scaleId").value = "1:"+result; 
	    
	});
	  
}

function onLoadShowMapInfo()
{
	bounds = mapObj.getBounds();//获取地图矩形视野bounds对象 
    
    document.getElementById("xibeiPoint").value = bounds.southwest.lng+","+bounds.southwest.lat;
    document.getElementById("dongnanPoint").value = bounds.northeast.lng+","+bounds.northeast.lat;

    //var size = mapObj.getSize();//获取地图视窗的像素大小 
    //document.getElementById("xiangsuWidth").value = size.width+"px";
    //document.getElementById("xiangsuHeight").value = size.height+"px";

    var zoom = mapObj.getZoom();//获取当前ZOOM级别 
    var zooms = mapObj.getZooms();//获取当前zoom级别范围 
    
    document.getElementById("zoomLevel").value = zoom; 
    document.getElementById("zoomRange").value = zooms;

    var result = mapObj.getScale();//获取地图中心点比例尺 
    
    document.getElementById("scaleId").value = "1:"+result; 
}

function addMapClick(){ 
	  
    mapObj.bind(mapObj,"click",function(e){ 
  
    document.getElementById("mapX").value=e.lnglat.lng; 
  
    document.getElementById("mapY").value=e.lnglat.lat;  

    bounds = mapObj.getBounds();//获取地图矩形视野bounds对象 
    
    document.getElementById("xibeiPoint").value = bounds.southwest.lng+","+bounds.southwest.lat;
    document.getElementById("dongnanPoint").value = bounds.northeast.lng+","+bounds.northeast.lat;

    //var size = mapObj.getSize();//获取地图视窗的像素大小 
    //document.getElementById("xiangsuWidth").value = size.width+"px";
    //document.getElementById("xiangsuHeight").value = size.height+"px";

    var zoom = mapObj.getZoom();//获取当前ZOOM级别 
    var zooms = mapObj.getZooms();//获取当前zoom级别范围 
    
    document.getElementById("zoomLevel").value = zoom; 
    document.getElementById("zoomRange").value = zooms;

    var result = mapObj.getScale();//获取地图中心点比例尺 
    
    document.getElementById("scaleId").value = "1:"+result; 
  
    }); 

} 


//加载鼠标工具，用于鼠标在地图的拉框绘制
function mouseTool()
{
	mapObj.plugin(['MMap.MouseTool'],function()
	{
		mouse=new MMap.MouseTool(mapObj);
		mouse.polygon();//打开鼠标绘多边形模式
		
		mapObj.bind(mouse,'draw',function(e){
			var polygon=mapObj.getOverlaysByType("polygon");
		});
		
	});
}


</script>

<script type="text/javascript">
document.body.onload=function()
{
	mapInit();
	mouseTool();

};



$("#cancel-btn").live("click", function(){
	$(".confirm-tip").hide();
	mapObj.clearOverlaysByType('polygon');
});


function cancleGetData()
{
	$(".confirm-tip").hide();
	mapObj.clearOverlaysByType('polygon');
	
}


//////////////////////////////////////////batch ///////////////////////

//标识第几个polygon
var index = -1;

//记录一共有多少个polygon
var totalLength = 0;

//累计所有的成功数
//var totalSuccNum = 0;

//采用链接模式： 第一个polygon抓取完， 抓取第二个polygon

//批量抓取数据的总入口
function batchFetchData()
{
	//抓取前的准备工作
	beforeBatch();

	//每次抓取一个polygon 
	batchGetDataForOnePolygon();
}

//抓取前的准备工作
function beforeBatch()
{
	index = -1;
	totalLength = 0;
	//totalSuccNum = 0;
	
    var polygons = mapObj.getOverlaysByType("polygon");
    document.getElementById("totalId").value = 0; 
	document.getElementById("polygonId").value = ''; 
	document.getElementById("totalNumId").value = 0;
	
	if(polygons.length <= 0)
	{
		alert("请先画定区域");
		return;
	}

	$(".confirm-tip").show();
	$("#loading").show();
	document.getElementById("totalNumId").value = 0;
	
	totalLength = polygons.length;
	
}


//每个框的开始
function batchGetDataForOnePolygon()
{
	beforeStartForOnePolygon();
	index++;
	searchOnePolygonOnePage();
}


//拉框查询 回调迭代
function searchOnePolygonOnePage(page)
{
	if (!page)
	{
		page=1;
	}
	
	//var keywords = "品牌|店|餐饮";
	
	var keywords = document.getElementById("poi_keyword").value;
	
	//构造拉框查询实例
	var PoiSearchOption = 
						   {
								srctype:"POI",//数据来源
								type:"",//数据类别
								number:400,//每页数量,默认10
								batch:page,//请求页数，默认1
								range:3000,	//查询范围，默认3000米
								ext:""//扩展字段
							};
	
	var poiSearch = new MMap.PoiSearch(PoiSearchOption);
	var polygons = mapObj.getOverlaysByType("polygon");
	
	var polygonarr = polygons[index].getPath();
	poiSearch.byRegion(polygonarr,keywords,poiSearch_CallBackBatch);
	
}

//关键字查询返回结果显示
function poiSearch_CallBackBatch(data)
{
	if (data.status=='E0'&&data.list.length>0)
	{
			window.current_count += parseInt(data.record);
			saveDataForOnePolygonOnePage(data.list,data.total);
			return;
	}
	
	if(data.status=='E0')
	{
		//当前的polygon数据抓取完
		finishForOnePolygon();

		//链接模式 判断是否还有下一个polygon需要抓取数据
		if (index < totalLength)
		{
			batchGetDataForOnePolygon();
		}
		else
		{
			 finishCurrentBatch();
		}
	}
	else if(data.status =="E1")
	{
		 //当前的polygon数据抓取完
		 finishForOnePolygon();

		 //链接模式 判断是否还有下一个polygon需要抓取数据
		 if (index < totalLength)
		 {
			 batchGetDataForOnePolygon();
		 }
		 else
		 {
			 finishCurrentBatch();
		 }
	}
	else
	{
		 resultStr=data.error.description+ "\n\n错误代码："+data.error.code;
		 
		 //终止当前抓取 可以开始新的抓取动作
		 finishCurrentBatch();
		 
		 alert(resultStr);
	}
	
}

function saveDataForOnePolygonOnePage(list,total)  
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
	
	GAUD.saveGuadData(JSON.stringify(list),0,function callBack_fetchNextPage(data) 
	{
		//alert('finish:'+data); 
		 
		updateResultForPolygonOnePage(data,total);			
		window.current_page++;
		searchOnePolygonOnePage(window.current_page);

	});

}


//每个polygon抓取前的准备工作
function beforeStartForOnePolygon() 
{
	window.current_page=1;
	window.current_total=0;
	window.current_success=0;
	window.current_exist=0;
	window.current_count=0;
	$("#percent").text(0);
}

//结束当前的批量抓取
function finishCurrentBatch()
{
	$(".confirm-tip").hide();
	$("#loading").hide();
	window.current_page=1;
	window.current_total=0;
	window.current_success=0;
	window.current_exist=0;
	window.current_count=0;
	$("#percent").text(0);
	
	mouseTool();
}

//每次按照一页抓取后 更新进度信息
function updateResultForPolygonOnePage(num,total) 
{
		window.current_total = total;
		window.current_success += num;
		
		var percent = parseInt(100*window.current_count/total);
		$("#percent").text(percent);

		document.getElementById("totalId").value = window.current_success; 
		document.getElementById("polygonId").value = (index + 1) + "/" + totalLength; 
		document.getElementById("totalNumId").value = parseInt(document.getElementById("totalNumId").value) + parseInt(num);
}

//完成一个polygon的所有页的抓取后  清空当前polygon的进度信息 为下一个polygon 做准备
function finishForOnePolygon() 
{
	window.current_page=1;
	window.current_count=0;
	$("#percent").text("0");
}

//终止当前批量抓取
function StopCurrentBatch() 
{
	$(".confirm-tip").hide();
	mouseTool();
}

function showMoreInfo()
{
	$("#cityPositionInfo").show();
}

function hideMoreInfo()
{
	$("#cityPositionInfo").hide();
}

</script>
</body>
</html>
