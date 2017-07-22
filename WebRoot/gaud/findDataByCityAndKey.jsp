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
	<div id="ewm" class="ewm" style="position:fixed; top:70px; right:100px;z-index:999;background:rgba(255,255,0,0.2);">
		<ul>
			<li style="text-align:center;margin-right:5px;margin-top:5px;">
				<p>城市：<input type="text" name="cityNameId" id="cityNameId" value="南京" style="background:rgba(0,255,255,0.3);"  /></p>
			</li>
			<li style="text-align:center;margin-right:5px;">
				<p>条件：<input type="text" name="keyNameId" id="keyNameId" value="购物|品牌|店|餐饮" style="background:rgba(0,255,255,0.3);" /></p>
			</li>
			<li style="text-align:center;margin-bottom:5px;margin-right:5px;">
				<p>比例：<input type="text" name="scaleId" id="scaleId" value="" style="background:rgba(0,255,255,0.3);" /></p>
			</li>
			<li style="text-align:center;margin-bottom:5px;">
				<p>
				<input type="button" class="btn btn-primary" name="getDataId" id="getDataId" value="抓取" onclick="startFind()" />
				<input type="button" class="btn btn-primary" name="cancleDataId" id="cancleDataId" value="查看" onclick="showMapInfo()" />
				</p>
			</li>
		</ul>
	</div>
	<div>
		<div id="main">
			<div class="clear"></div>
			<div class="map-area" style="width:1000px;position:relative;"  >
				<div style="position:absolute;left:0px right:0px;width:1110px;height:550px;z-index:998;background:rgba(255,255,255,0.8);-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;display:none;" id="map-shadow">	
				</div>
				<div class="confirm-tip" style="text-align:center;position:absolute;top:200px;left:0px;z-index:999;display:none;">
						<div style="width:1110px;">
							<div style="width:400px;margin:0px auto;background-color:rgba(255,255,255,0.8);padding:10px;">
								<div id="loading" style="display:none;">
									正在导入. . .<span id="percent" style="font-size:15px;">0</span>% 
								</div>
							</div>
						</div>
				</div>
				<div class="result-tip" style="text-align:center;position:absolute;top:200px;left:0px;z-index:999;display:none;">
					<div style="width:1110px;">
						<div style="width:400px;margin:0px auto;background-color:rgba(255,255,255,0.8);padding:10px;color:red;padding:10px;">
								<div><b>共查询到<span id="total-num"></span>个商家,成功导入<span id="success-num"></span>家</b></div>
								<div style="margin-top:10px;" >
										<input type="button" value="继续" class="btn btn-primary" onclick="continueStart()" />
								</div>
						</div>
					</div>
				</div>
			    <input type="hidden" id="poi_keyword" value="购物|品牌|店|餐饮" />
			    <div id="iCenter" style="position:absolute;left:10px;right:10px;top:10px;bottom:10px;height:540px;width:1110px;border:2px solid red;"></div>
		</div>
    </div>
</div>

<script language="javascript" src="http://app.mapabc.com/apis?t=javascriptmap&v=3&key=b0a7db0b3a30f944a21c3682064dc70ef5b738b062f6479a5eca39725798b1ee300bd8d5de3a4ae3"></script>
<script type="text/javascript">

//0 不是一个新任务   1 开始执行一个新任务
var taskFlag = 0;
var mapObj;
var stopFlag = 0;

function mapInit()
{

	//地图初始化参数设置 
	mapObj = new MMap.Map("iCenter");
	
	//加载工具条插件 
    mapObj.plugin("MMap.ToolBar",function() { 
        toolbar = new MMap.ToolBar(); 
        mapObj.addControl(toolbar); 
  
    }); 

    addMapClick();
    onLoadShowMapInfo();
    
	var lnglat=new MMap.LngLat(118.78333,32.05000);
	mapObj.setCenter(lnglat);
	
}

function onLoadShowMapInfo()
{
    var result = mapObj.getScale();//获取地图中心点比例尺 
    document.getElementById("scaleId").value = "1:"+result; 
}

function addMapClick(){ 
	  
    mapObj.bind(mapObj,"click",function(e){ 
    var result = mapObj.getScale();//获取地图中心点比例尺 
    document.getElementById("scaleId").value = "1:"+result; 
  
    }); 

} 

//按照城市和关键字查询
function keywordSearch(page)
{
	if (!page)
	{
		page=1;
		taskFlag = 1;
	}

	//var keywords = "品牌|店|餐饮";//传入参数
	//var keywords = document.getElementById("poi_keyword").value;
	
	var keywords = document.getElementById("keyNameId").value;
	var city = document.getElementById("cityNameId").value;//城市 
	  
    //city=city=='全国'?'total':city;
    
	var PoiSearchOption = 
						   {
								srctype:"POI",//数据来源
								type:"",//数据类别
								number:100,//每页数量,默认10
								batch:parseInt(page),//请求页数，默认1
								range:3000,	//查询范围，默认3000米
								ext:""//扩展字段
							};
	
	var poiSearch = new MMap.PoiSearch(PoiSearchOption);
	poiSearch.byKeywords(keywords,city,poiSearch_CallBack); 
	
}


//关键字查询返回结果显示
function poiSearch_CallBack(gaudDataSet)
{
	
	if (gaudDataSet.status=='E0'&&gaudDataSet.list.length>0)
	{
			window.current_count += parseInt(gaudDataSet.record);
			saveData(gaudDataSet.list,gaudDataSet.total);
			return;
	}
	
	if(gaudDataSet.status=='E0')
	{
		doFinishedFind();
	}else if(gaudDataSet.status =="E1")
	{
         //resultStr = "未查找到任何结果!\n>建议：\n1.请确保所有字词拼写正确。\n2.尝试不同的关键字。\n3.尝试更宽泛的关键字。";	
		 doFinishedFind();
	}
	else
	{
		 resultStr=gaudDataSet.error.description+ "\n\n错误代码："+gaudDataSet.error.code;
		 alert(resultStr);
		 continueStart();
	}
	
}

</script>

<script type="text/javascript">
document.body.onload=function()
{
	mapInit();
	hideShadow();
};

function startFind() 
{
    $(".confirm-tip").show();
    $("#confirm").show();
	$("#loading").show();
	window.current_page=1;
	window.current_total=0;
	window.current_success=0;
	window.current_exist=0;
	window.current_count=0;
	stopFlag = 0;

	//alert("window.current_total:"+window.current_total);
	//alert("window.current_success:"+window.current_success);
	//alert("window.current_count:"+window.current_count);
	
	keywordSearch();
}

function cancleGetData()
{
	$(".confirm-tip").hide();
	hideShadow();
	stopFlag = 1;
	
}

//确认录入信息
function confirmFind() {
	
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
	
      //debug info -------------------------
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
	
	GAUD.saveGuadData(JSON.stringify(list),taskFlag,function CallBack_saveGuad(successNum) 
	{
		//alert('finish:'+successNum); 
		//reset task flag
		taskFlag = 0;
		 
		updateResultNew(successNum,total);			
		window.current_page++;
		keywordSearch(window.current_page);

	});
	
}

function updateResultNew(num,total) 
{
		window.current_total = total;
		window.current_success += num;
		
		var percent = parseInt(100*window.current_count/window.current_total);
		$("#percent").text(percent);
		//window.current_exist += num.exist;
}


function doFinishedFind() 
{
	//alert("window.current_total:"+window.current_total);
	//alert("window.current_success:"+window.current_success);
	//alert("window.current_count:"+window.current_count);
	//alert("window.current_page:"+window.current_page);
	
	$("#percent").text("0");
	$("#success-num").text(window.current_success);
	$("#total-num").text(window.current_count);
	$(".confirm-tip").hide();
	$(".result-tip").show();

	window.current_page=1;
	window.current_count=0;
	window.current_total = 0;
	window.current_success = 0;
}

function continueStart() 
{
	$("#success-num").text(0);
	$("#total-num").text(0);
	$(".confirm-tip").hide();
	$(".result-tip").hide();
	hideShadow();
}

function showMapInfo()
{
	
}

</script>
</body>
</html>
