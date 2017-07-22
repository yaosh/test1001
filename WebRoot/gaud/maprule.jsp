<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.entity.*"%>
<html>
<head>
    <link rel="stylesheet" href="/css/lib/base.css">
    <link rel="stylesheet" href="/css/lib/font-awesome.css">
    <link rel="stylesheet" href="/css/zhaoshang/main.css">
	<link rel="stylesheet" href="/css/zhaoshang/poi.css">
	
	<script src="/js/cg/js/lib/jquery.js"></script>
	<script type='text/javascript' src='/dwr/interface/GAUDRULE.js'></script>
    <script type='text/javascript' src='/dwr/engine.js'></script>
    <script type='text/javascript' src='/dwr/util.js'></script>
    
</head>
<body>
   
<div class="pageTit">
<h4><a href="/gaud!toRuleList.action?page=1&numForOnePage=10">查看规则列表&gt;&gt;</a></h4>
</div>

<div class="clear">
</div>

<div class="checkArea clearfix pusht">
	<form action="" method="get">
		<ul class="checkbox clearfix">
			<li>
				<span>
					<label>高德:</label>
					<select name="type_1" id="type_1_list"  >
						<option value="0">- 请选择 -</option>
						<s:iterator value="listGaudLevelOneShopTypes" status="poiType" > 
							<option value="<s:property value="id" />"  ><s:property value="name" /></option>
						</s:iterator>
					</select>
					<select name="type_2" id="type_2_list"  >
						<option value="0">- 请选择 -</option>
					</select>
					<select name="type_3" id="type_3_list" >
						<option value="0">- 请选择 -</option>
					</select>
				</span>
				<span class="change">
					<input type="text" name="wd" class="input" value=""/>
					<textarea class="inputArea hide"></textarea>
				</span>
				<br>
				<span>
					<label>对应城格:</label>			
					<select name="chengge_1_type" id="chengge_1_type"  >
						<option value="0">- 请选择 -</option>
						<s:iterator value="listChenggeLevelOneShopTypes" status="chenggeType" > 
							<option value="<s:property value="id" />"  ><s:property value="name" /></option>
						</s:iterator>
						</select>
					<select name="chengge_2_type" id="chengge_2_type"  >
						<option value="0">- 请选择 -</option>
					</select>
					<select name="chengge_3_type" id="chengge_3_type" >
						<option value="0">- 请选择 -</option>
					</select>
					
					<label><input type='radio' name='mybrand' {if !$search.mybrand}checked="checked"{/if} value="0">自定义关键词</label>
					<label><input type='radio' name='mybrand' {if $search.mybrand}checked="checked"{/if} value="1">品牌库关键词</label>
					
					<select name="importrdo" class="">
						<option value="0" >选中商品</option>
						<option value="1" >未选中商品</option>
					</select>
					
					<input name="cgtype1" type="hidden" id="cgtype1" value="{$search.cgtype1}"/>
					<input name="cgtype2" type="hidden" id="cgtype2" value="{$search.cgtype2}"/>
					<input name="cgtype3" type="hidden" id="cgtype3" value="{$search.cgtype3}"/>
					
					<input name="gdtype1" type="hidden" id="gdtype1" value="{$search.gdtype1}"/>
					<input name="gdtype2" type="hidden" id="gdtype2" value="{$search.gdtype2}"/>
					<input name="gdtype3" type="hidden" id="gdtype3" value="{$search.gdtype3}"/>
					
					<input type="submit" value="预览" name="sub" class="btn btn-small btn-primary ml10">
					<input type="submit" value="导入数据" id="importdata" name="sub" class="btn btn-small btn-primary ml10">
				</span>
			</li>
		</ul>
		
	</form>
</div>
	
<table class="tablelist">
	<tr>
		<th>公司</th>
		<th>一级</th>
		<th>二级</th>
		<th>三级</th>
		<th>公司</th>
		<th>一级</th>
		<th>二级</th>
		<th>三级</th>
		<th>店名</th>
	</tr>
	
	<tr>
		<td class="bold">高德</td>
		<td>{$search.gdtype1}</td>
		<td>{$search.gdtype2}</td>
		<td>{$search.gdtype3}</td>
		<td class="bold">城格</td>
		<td>{$search.cgtype1}</td>
		<td>{$search.cgtype2}</td>
		<td>{$search.cgtype3}</td>
		<td>{$row.name}</td>
	</tr>
	<tr>
		<td colspan="9">暂无数据</td>
	</tr>
</table>
<div class="mt10">
</div>	
	
<div class="description clearfix">
	此处形成高德和城格的对应关系文字描述。
</div>

<script src="/assets/js/zhaoshang/comm.js"></script>
<script type="text/javascript" src="/assets/js/lib/handlebars.js"></script>

<script type="text/javascript">

/*
$(".type-select").live("change", function(){
	var data = {};
	data.type = $(this).val();
	data.target = $(this).attr("data-target");
	$.post(
		"/zs.php?app=poi&act=getChildList",
		data,
		function (result) {
			var template = Handlebars.compile($("#select-tpl").html());
			var html = template(result);
			var eid = "type_"+result.data.target+"_list";
			$("#"+eid).html(html);
		},
		'json'
	);
});
*/

$("#type_1_list").change(
		function() 
		{
			//var checkText=$("#type_1_list").find("option:selected").text(); 
			//alert(checkText);
			var checkValue=$("#type_1_list").val();
			//alert(checkValue);
			GAUDRULE.getGaudChildTypeByTypeId(checkValue,CallBack_dealWithChildType1);
		});


function CallBack_dealWithChildType1(childTypes)
{
	
    //每次获得新的数据的时候先把第二个下拉框架的长度清0
	$("#type_2_list").empty();
  	$("#type_2_list").append("<option value='0'>- 请选择 -</option>"); 
  	$("#type_3_list").empty();
	$("#type_3_list").append("<option value='0'>- 请选择 -</option>");
	  
    for(var i=0;i< childTypes.length;i ++)
    {
      var value = childTypes[i].id;
      var text = childTypes[i].name;
      
      //根据每组value和text标记的值创建一个option对象
      try
      {
         $("#type_2_list").append("<option value='"+value+"'>"+text+"</option>"); 
      }
      catch(e)
      {
      }
    }
}

$("#type_2_list").change(
		function() 
		{
			//var checkText=$("#type_1_list").find("option:selected").text(); 
			var checkValue=$("#type_2_list").val();
			 
			GAUDRULE.getGaudChildTypeByTypeId(checkValue,CallBack_dealWithChildType2);
		});


function CallBack_dealWithChildType2(childTypes)
{
	
    //每次获得新的数据的时候先把第二个下拉框架的长度清0
	$("#type_3_list").empty();
	$("#type_3_list").append("<option value='0'>- 请选择 -</option>");
	
    for(var i=0;i< childTypes.length;i ++)
    {
      var value = childTypes[i].id;
      var text = childTypes[i].name;
      var option = new Option(text, value);
  	
      //alert(text);
      //根据每组value和text标记的值创建一个option对象
      try
      {
        //$("#type_2_list").add(option);//将option对象添加到第二个下拉框中
        $("#type_3_list").append("<option value='"+value+"'>"+text+"</option>"); 
      }
      catch(e)
      {
      }
    }
}

$("#chengge_1_type").change(
		function() 
		{
			//var checkText=$("#type_1_list").find("option:selected").text(); 
			var checkValue=$("#chengge_1_type").val();
			 
			GAUDRULE.getChenggeLevelTwoChildTypes(checkValue,CallBack_dealWithChenggeChildType);
		});


function CallBack_dealWithChenggeChildType(childTypes)
{
	
    //每次获得新的数据的时候先把第二个下拉框架的长度清0
	$("#chengge_2_type").empty();
	$("#chengge_2_type").append("<option value='0'>- 请选择 -</option>");

	$("#chengge_3_type").empty();
	$("#chengge_3_type").append("<option value='0'>- 请选择 -</option>");
	
    for(var i=0;i< childTypes.length;i ++)
    {
      var value = childTypes[i].id;
      var text = childTypes[i].name;
      var option = new Option(text, value);
  	
      //alert(text);
      //根据每组value和text标记的值创建一个option对象
      try
      {
        //$("#type_2_list").add(option);//将option对象添加到第二个下拉框中
        $("#chengge_2_type").append("<option value='"+value+"'>"+text+"</option>"); 
      }
      catch(e)
      {
      }
    }
}

$("#chengge_2_type").change(
		function() 
		{
			//var checkText=$("#type_1_list").find("option:selected").text(); 
			var checkValue=$("#chengge_2_type").val();
			 
			GAUDRULE.getChenggeLevelThreeChildTypes(checkValue,CallBack_dealWithChenggeChildType2);
		});


function CallBack_dealWithChenggeChildType2(childTypes)
{
	
    //每次获得新的数据的时候先把第二个下拉框架的长度清0
	$("#chengge_3_type").empty();
	$("#chengge_3_type").append("<option value='0'>- 请选择 -</option>");
	
    for(var i=0;i< childTypes.length;i ++)
    {
      var value = childTypes[i].id;
      var text = childTypes[i].name;
      var option = new Option(text, value);
  	
      //alert(text);
      //根据每组value和text标记的值创建一个option对象
      try
      {
        //$("#type_2_list").add(option);//将option对象添加到第二个下拉框中
        $("#chengge_3_type").append("<option value='"+value+"'>"+text+"</option>"); 
      }
      catch(e)
      {
      }
    }
}


//获取成格数据
$(".type-select1").live("change", function(){
	var data = {};
	data.type = $(this).val();
	data.target = $(this).attr("data-target");
	$.post(
		"/zs.php?app=poi&act=getChildListChengge",
		data,
		function (result) {
			var template = Handlebars.compile($("#select-type-tpl").html());
			var html = template(result);
			
			var eid = "chengge_"+result.data.target+"_type";
			$("#"+eid).html(html);
		},
		'json'
	);
});

//获取成格数据
$(".type-select2").live("change", function(){
	var data = {};
	data.type = $(this).val();
	data.target = $(this).attr("data-target");
	$.post(
		"/zs.php?app=poi&act=getChildListChengge",
		data,
		function (result) {
			var template = Handlebars.compile($("#select-type-tpl").html());
			var html = template(result);
			
			var eid = "chengge_"+result.data.target+"_type1";
			$("#"+eid).html(html);
		},
		'json'
	);
});



//添加自己的js处理的地方
$(function(){
	//提供选中的信息
	$(".txt-small").change(function(){
		$("#gdtype1").val($("#type_1_list  option:selected").text());
		$("#gdtype2").val($("#type_2_list  option:selected").text());
		$("#gdtype3").val($("#type_3_list  option:selected").text());
		$("#cgtype1").val($("#chengge_1_type  option:selected").text());
		$("#cgtype2").val($("#chengge_2_type  option:selected").text());
		$("#cgtype3").val($("#chengge_3_type  option:selected").text());
	});
	
	$("#importdata").click(function(){
		if(!confirm("您确认导入数据吗？")){
			return false;
		}
	});
	
	
	
});


</script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".change .input").focus(function(){
			$(this).addClass("hidden");
			$(this).parent().find(".inputArea").show();
			$(this).parent().find(".inputArea").focus();
			$(this).parent().find(".inputArea").val($(this).val());
		});
		function tipon(){
			$(".change .input").focus(function(){
				$(this).addClass("hidden");
				$(this).parent().find(".inputArea").show();
				$(this).parent().find(".inputArea").focus();
				$(this).parent().find(".inputArea").val($(this).val());
			});
		}
		$(".change .inputArea").blur(function(){
			$(this).hide();
			$(this).parent().find(".input").removeClass("hidden");
			var value=$(this).val();
			$(this).parent().find(".input").val($(this).val());
		});
		function tipoff(){
			$(".change .inputArea").blur(function(){
			$(this).hide();
			$(this).parent().find(".input").removeClass("hidden");
			var value=$(this).val();
			$(this).parent().find(".input").val($(this).val());
			});
		}    	
	});	
</script>

<!--<script type="text/tpl" id="select-tpl">-->
<!--	<option value="0">- 请选择 -</option>-->
<!--	{{#data.list}}-->
<!--		<option value="{{id}}">{{name}}</option>-->
<!--	{{/data.list}}-->
<!--</script>-->

<!--<script type="text/tpl" id="select-type-tpl">-->
<!--	<option value="0">- 请选择 -</option>-->
<!--	{{#data.typelist}}-->
<!--		<option value="{{id}}">{{name}}</option>-->
<!--	{{/data.typelist}}-->
<!--</script>-->
</body>
</html>