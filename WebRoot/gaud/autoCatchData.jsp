<html>
<head>
<link rel="stylesheet" href="/css/lib/base.css">
    <link rel="stylesheet" href="/css/lib/font-awesome.css">
    <link rel="stylesheet" href="/css/zhaoshang/main.css">
    
    <script src="/js/cg/js/lib/jquery.js"></script>
    <script src="/js/cg/js/lib/bootstrap.min.js"></script>
    <script src="/js/cg/js/plugin/dialog.js"></script>
	<script type="text/javascript" src="/js/cg/js/zhaoshang/public.js"></script>
	<script type='text/javascript' src='/gaud/allCity.js'></script>
	
	<script type='text/javascript' src='/dwr/interface/GAUDOPT.js'></script>
    <script type='text/javascript' src='/dwr/engine.js'></script>
    <script type='text/javascript' src='/dwr/util.js'></script>
	
	<jsp:include page="cityResource.jsp" />
	
	<style type="text/css">
	.aaa{
	border-style: solid ; 
	border-width: 50px ; 
	border-color: pink ;
	
	position: absolute ;
	
	overFlow-x: auto ;
	overFlow-y: auto ; 
	
	scrollBar-face-color: green; 
	scrollBar-hightLight-color: red; 
	scrollBar-3dLight-color: orange; 
	scrollBar-darkshadow-color:blue; 
	scrollBar-shadow-color:yellow; 
	scrollBar-arrow-color:purple; 
	scrollBar-track-color:black; 
	scrollBar-base-color:pink; 
	}

</style>

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
	
	<div id="cityListDiv" class="ewm" style="position:fixed; top:40px;margin-left:100px;z-index:999;background:rgba(200,255,255,0.8);border:1px solid;display:none;width:200px;">
		<ul>
		<li style="text-align:center;margin-top:0px;background:rgba(255,255,255,0.8);border:1px solid;text-align:right;" onclick="hideCityListInfo()">
			X&nbsp;&nbsp;&nbsp;
		</li>
		<li style="text-align:center;margin-left:5px;margin-top:5px;margin-right:5px;">
		   <select size="1" id="selectProvinceId" name="selectProvinceId" onchange="changeProvince()">
		        <option value="">请选择</option>
				<option value="jiangsuCityDivId">江苏省</option>
				<option value="shandongCityDivId">山东省</option>
				<option value="henanCityDivId">河南省</option>
				<option value="zhejiangCityDivId">浙江省</option>
				<option value="shanxiCityDivId">陕西省</option>
				<option value="anhuiCityDivId">安徽省</option>
				<option value="fujianCityDivId">福建省</option>
				<option value="guangdongCityDivId">广东省</option>
				<option value="hebeiCityDivId">河北省</option>
				<option value="liaoningCityDivId">辽宁省</option>
				<option value="sichuanCityDivId">四川省</option>
				<option value="hubeiCityDivId">湖北省</option>
				<option value="hunanCityDivId">湖南省</option>
				<option value="neimengguCityDivId">内蒙古省</option>
				<option value="heilongjiangCityDivId">黑龙江省</option>
				<option value="guangxiCityDivId">广西省</option>
				<option value="jiangxiCityDivId">江西省</option>
				<option value="shanxishengCityDivId">山西省</option>
				<option value="jilinCityDivId">吉林省</option>
				<option value="yunnanCityDivId">云南省</option>
				<option value="xinjiangCityDivId">新疆省</option>
				<option value="guizhouCityDivId">贵州省</option>
				<option value="gansuCityDivId">甘肃省</option>
				<option value="hainanCityDivId">海南省</option>
				<option value="ningxiaCityDivId">宁夏省</option>
				<option value="qinghaiCityDivId">青海省</option>
				<option value="xizangCityDivId">西藏省</option>
				<option value="taiwanCityDivId">台湾省</option>
				<option value="zhixiashiCityDivId">直辖市</option>
				<option value="tebiexingzhengquCityDivId">特别行政区</option>
				<option value="oneLevelCityDivId">首批</option>
		    </select>
		 </li>
	     <li style="text-align:left;margin-left:5px;margin-top:10px;margin-right:5px;margin-bottom:5px;width:193px;height:300px;overflow-y:scroll;">
		     <div id="mycitysDiv"></div>
		</li>
		</ul>
	</div>

	<div id="ewm" class="ewm" style="position:fixed; top:40px; right:60px;z-index:999;background:rgba(200,255,255,0.8);border:1px solid;">
		<ul>
			<li style="text-align:center;;margin-right:5px;margin-top:10px;">
				<p>省份：<input type="text" name="provinceId" id="provinceId" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;;margin-right:5px;">
				<p>城市：<input type="text" name="cityId" id="cityId" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;;margin-right:5px;">
				<p>经度：<input type="text" name="cityX1Id" id="cityX1Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  />
				-><input type="text" name="cityX2Id" id="cityX2Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  /></p>
			</li>
			<li style="text-align:center;;margin-right:5px;">
				<p>纬度：<input type="text" name="cityY1Id" id="cityY1Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  />
				-><input type="text" name="cityY2Id" id="cityY2Id" value="" style="background:rgba(0,255,255,0.3);width:80px;"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>区号：<input type="text" name="telephoneCode" id="telephoneCode" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>编号：<input type="text" name="areaCodeID" id="areaCodeID" value="" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>步长：<input type="text" name="stepLength" id="stepLength" value="0.4" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>类型：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select size="1" id="selectTypeId" name="selectTypeId" >
				<option value="phonecode">按照电话区号抓</option>
				<option value="areacode">按照行政区号抓</option>
				</select>
				</p>
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
			<li style="text-align:center;margin-right:5px;margin-left:5px;margin-bottom:15px;">
				<p>
				<input type="button" class="btn btn-primary" name="cancleDataId" id="cancleDataId" value="选" onclick="showCityListInfo()" />
				<input type="button" class="btn btn-primary" name="drawPolygonId" id="drawPolygonId" value="画" onclick="autoMain()" />
				<input type="button" class="btn btn-primary" name="getBatchDataId" id="getBatchDataId" value="抓" onclick="batchFetchData()" />
				</p>
			</li>
			<li style="text-align:center;margin-right:5px;margin-left:5px;margin-bottom:12px;">
				<p>
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
			    <input type="hidden" id="poi_keyword" value="购物服务|餐饮服务|生活服务|住宿服务|医疗保健服务|汽车维修|体育休闲服务|汽车销售|科教文化服务|汽车服务|公司企业|金融保险服务|摩托车服务|商务住宅|风景名胜|公共设施|政府机构及社会团体|地名地址信息|道路附属设施|交通设施服务" />
<!--			    <input type="hidden" id="poi_keyword" value="风景名胜|公共设施|政府机构及社会团体|地名地址信息|道路附属设施|交通设施服务" />-->
<!--			    <input type="hidden" id="poi_keyword" value="购物服务|餐饮服务|生活服务|住宿服务|医疗保健服务|汽车维修|体育休闲服务|汽车销售|科教文化服务|汽车服务|公司企业|金融保险服务|摩托车服务|商务住宅" />-->
			    <div id="iCenter" style="position:absolute;left:10px;right:10px;top:10px;bottom:10px;height:540px;width:1110px;border:2px solid red;"></div>
		</div>
    </div>
</div>

<script language="javascript" src="http://app.mapabc.com/apis?t=javascriptmap&v=3&key=b0a7db0b3a30f944a21c3682064dc70ef5b738b062f6479a5eca39725798b1ee300bd8d5de3a4ae3"></script>
<script type="text/javascript">

//0 不是一个新任务   1 开始执行一个新任务
var taskFlag = 0;
var mapObj,mouse;
var g_CityCode="";
var g_cityName = "";
var g_provinceName = "";
var g_filter_type = 0;

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
    //addZoomEvent();

    //北京 116.4, 39.9
    //南京 118.78333,32.05000
	var lnglat=new MMap.LngLat(116.4,39.9);
	mapObj.setCenter(lnglat);
	
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
	//mouseTool();

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

	var polygons = mapObj.getOverlaysByType("polygon");
	
	var selectType = $('#selectTypeId').find("option:selected").val();

	if(selectType == 'phonecode')
	{
		if ('' == document.getElementById("telephoneCode").value)
		{
			alert('区号不能为空');
			return;
		}

		g_filter_type = 0;
		g_cityCode = document.getElementById("telephoneCode").value;
	}

	if(selectType == 'areacode')
	{
		if ('' == document.getElementById("areaCodeID").value)
		{
			alert('地区编号不能为空');
			return;
		}

		g_filter_type = 1;
		g_cityCode = document.getElementById("areaCodeID").value;
	}
	
	if(polygons.length <= 0)
	{
		alert("请先画定区域");
		return;
	}
	
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
    totalLength = polygons.length;
    
    document.getElementById("totalId").value = 0; 
	document.getElementById("polygonId").value = ''; 
	document.getElementById("totalNumId").value = 0;

	$(".confirm-tip").show();
	$("#loading").show();
	document.getElementById("totalNumId").value = 0;
	
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
								number:50,//每页数量,默认10
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
	
	GAUDOPT.saveGuadData(JSON.stringify(list),g_filter_type,g_cityCode,g_cityName,g_provinceName,function callBack_fetchNextPage(data) 
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
	
	//mouseTool();
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
	//mouseTool();
}

function showMoreInfo()
{
	$("#cityPositionInfo").show();
}

function hideMoreInfo()
{
	$("#cityPositionInfo").hide();
}

function showCityListInfo()
{
	$("#cityListDiv").show();
}

function hideCityListInfo()
{
	$("#cityListDiv").hide();
}


//////////////////////////////////////////按照省自动抓取 ///////////////////////
function provinceInfo()
{
	var x1 =0;
	var x2 = 0;
	var y1 = 0;
	var y2 = 0;
	var citycode = "";
	var cityName = "";
	var numOnePage = 0;
	var step = 0.4;
	
}


var autoIndex = -1;

function autoMain()
{
	autoIndex = -1;
	
	batchAutoCatchData(callback_continueCatch);
}


function batchAutoCatchData(callback)
{
	//autoIndex++;
	var local_provinceName = document.getElementById("provinceId").value;
	var local_cityname = document.getElementById("cityId").value;
	var local_cityPhoneCode = document.getElementById("telephoneCode").value;
    var local_x1 = document.getElementById("cityX1Id").value;
	var local_x2 = document.getElementById("cityX2Id").value;
    var local_y1 = document.getElementById("cityY1Id").value;
    var local_y2 = document.getElementById("cityY2Id").value;
    var l_step = document.getElementById("stepLength").value;

    
	if('' == local_provinceName)
	{
		alert("省份  is null");
		return;
	}

	if('' == local_cityname)
	{
		alert("城市  is null");
		return;
	}

	//var cityInfo = myMap.get(provinceName).get(cityName);

    if('' == local_cityPhoneCode)
	{
		alert("城市长途区号  is null");
		return;
	}

    if('' == local_x1)
	{
		alert("经度 x1  is null");
		return;
	}

    if('' == local_x2)
	{
    	alert("经度 x2  is null");
		return;
	}

    if('' == local_y1)
	{
    	alert("纬度 y1 is null");
		return;
	}

    if('' == local_y2)
	{
    	alert("纬度 y2 is null");
		return;
	}
	
	if ('' == l_step)
	{
		alert("步长  is null");
		return;
	}

	l_step = parseFloat(l_step);

	g_cityCode=local_cityPhoneCode;
	g_cityName = local_cityname;
	g_provinceName = local_provinceName;
		
	
	//alert(cityList[autoIndex][0]+":"+cityList[autoIndex][1]);
	autoClearOldPolygn();
	//alert(cityList[autoIndex][0]+":"+cityList[autoIndex][1]);
	//autoDrawPolygon(cityList[autoIndex][0],cityList[autoIndex][1],cityList[autoIndex][2],cityList[autoIndex][3],cityList[autoIndex][4],cityList[autoIndex][5],0.4);
	//autoDrawPolygon(cityInfo[0],cityInfo[1],cityInfo[2],cityInfo[3],cityInfo[4],cityInfo[5],l_step);

	autoDrawPolygon(local_cityname,local_cityPhoneCode,local_x1,local_x2,local_y1,local_y2,l_step);
	//alert('回调:' + autoIndex);
	//batchFetchData();
	//sleep(100);
	//callback(autoIndex);
	
}

function sleep(n) 
{
    var start = new Date().getTime();
    while(true)  if(new Date().getTime()-start > n) break;
}

function callback_continueCatch()
{
	if (autoIndex <cityList.length )
	{
		batchAutoCatchData(callback_continueCatch);
	}
}

function autoClearOldPolygn()
{
	mapObj.clearOverlaysByType('polygon');
}

function autoDrawPolygon(cityName,cityCode,x1,x2,y1,y2,stepLen)
{

	//115.2,41.1 117.7,41.1  
	//115.2,39.40 117.7,39.40
	
	
	var startX1 = x1;
	var startX2 = x2;
	var startY1 = y1;
	var startY2 = y2;
	
	startX1 = parseFloat(startX1);
	startX2 = parseFloat(startX2);
	startY1 = parseFloat(startY1);
	startY2 = parseFloat(startY2);
	var step = parseFloat(stepLen);
	
	var loopLengthX = parseInt((startX2 - startX1)/step)+1;
	var loopLengthY = parseInt((startY2 - startY1)/step)+1;
	var tempY1 = 0;
	var tempY2 = 0;
	var tempX1 = 0;
	var tempX2 = 0;

	
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

	        //tempX1 = Math.round(tempX1*100)/100;
	        //tempX2 = Math.round(tempX2*100)/100;
	        //tempY1 = Math.round(tempY1*100)/100;
	        //tempY2 = Math.round(tempY2*100)/100;
	        
			addPolygonByXY(tempX1,tempX2,tempY1,tempY2,"polygon"+i+j);
		}
	}

}

//添加多边形 

function addPolygonByXY(x1,x2,y1,y2,poiID)
{

	//alert("x1,x2,y1,y2,poiID"+x1+","+x2+","+y1+","+y2+","+poiID);
	
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

function changeProvince()
{
	var tempProvinceTxt=$('#selectProvinceId').find("option:selected").text();
	var tempProvinceId=$('#selectProvinceId').find("option:selected").val();
	//var tempProvince = $('#selectProvinceId').val();
	//var tempProvinceId = $('#selectProvinceId option:selected').val();
	//alert(tempProvinceTxt);
	//alert(tempProvinceId);
	//mycitysDiv
	$('#mycitysDiv')[0].innerHTML = $("#"+tempProvinceId)[0].innerHTML;
	//$("#"+tempProvinceId).show();
	//$(tempProvinceId).display = "black";
	
	
}

function getChooseValue(chooseCityName)
{
	document.getElementById("provinceId").value = $('#selectProvinceId').find("option:selected").text();
	document.getElementById("cityId").value =chooseCityName;
	//alert(document.getElementById("provinceId").value+","+chooseCityName);
	//alert("ccccc"+document.getElementById("provinceId").value+"aaaaa");
	//alert(myMap.get(document.getElementById("provinceId").value));
	var tempMap = myMap.get(document.getElementById("provinceId").value);
	
	if (null != tempMap)
	{
		var cityInfo = tempMap.get(chooseCityName);

		if(null != cityInfo)
		{
			//alert(cityInfo);
			document.getElementById("cityX1Id").value =cityInfo[2];
			document.getElementById("cityX2Id").value =cityInfo[3];
			document.getElementById("cityY1Id").value =cityInfo[4];
			document.getElementById("cityY2Id").value =cityInfo[5];

			document.getElementById("telephoneCode").value =cityInfo[1];
		}
		else
		{
			document.getElementById("cityX1Id").value ='';
			document.getElementById("cityX2Id").value ='';
			document.getElementById("cityY1Id").value ='';
			document.getElementById("cityY2Id").value ='';

			document.getElementById("telephoneCode").value ='';
		}
	}
	else
	{
		document.getElementById("cityX1Id").value ='';
		document.getElementById("cityX2Id").value ='';
		document.getElementById("cityY1Id").value ='';
		document.getElementById("cityY2Id").value ='';

		document.getElementById("telephoneCode").value ='';
	}
	
}


/////////入手抓取////
//batchAutoCatchData(callback_continueCatch);

</script>
</body>
</html>
