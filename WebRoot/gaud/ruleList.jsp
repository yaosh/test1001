<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
	<h4>规则列表<a href="/zs/poi/mappingProducts.html">&lt;&lt;返回导入数据页面</a></h4>
</div>
	<div class="clear"></div>
			<table class="tablelist" >
				<thead>
					<tr>
						<th>高德</th>
						<th>一级</th>
						<th>二级</th>
						<th>三级</th>
						<th>城格</th>
						<th>一级</th>
						<th>二级</th>
						<th>三级</th>
						<th>插入</th>
						<th width="200">关键词</th>
						<th>剩余</th>
						<th>操作</th>
							
					</tr>
				</thead>
				<s:iterator value="ruleList" status="rulebean" > 
					<tr>
						<td>高德</td>
						<td><s:property value="cgType1Name" /></td>
						<td><s:property value="cgType2Name" /></td>
						<td><s:property value="cgType3Name" /></td>
						<td>10</td>
						<td>城格</td>
						<td><s:property value="gdType1Name" /></td>
						<td><s:property value="gdType2Name" /></td>
						<td><s:property value="gdType3Name" /></td>
						<td>4</td>
						<td class="showMore">
							<input type="text" name="wd" class="input" value="<s:property value="keyWord" />"/><div class="infoBox"></div>
					    </td>
					    <td>6</td>
						<td class="do">
							<a href="/zs.php?app=poi&act=getMappingBrandList&id={$row.id}">详细</a>
							<a href="/zs.php?app=poi&act=getMappingBrandList&id={$row.id}">启动</a><br/>
							<a href="/zs/poi/mappingProducts/{$row.id}/">编辑</a>
							<a href="/zs/poi/delMappingProduct/{$row.id}/" onclick="return delMP(this);">删除
						</td>
					</tr>
				</s:iterator>
			</table>
			<div class="mt10">
			</div>
	
<script src="/assets/js/zhaoshang/comm.js"></script>
<script type="text/javascript" src="/assets/js/lib/handlebars.js"></script>

<script type="text/javascript">
function delMP(obj){
	if(!confirm("您确认要删除该规则及以下的所有商品吗？")){
		return false;
	}
	return true;
}

function deleteAll() {
	if (!confirm("确认清空所有数据")){
		return false;
	}
	$.post(
		"/zs.php?app=poi&act=deleteAll",
		{ },
		function(result){
			alert(result.msg);
			if (result.success) {
				location.href = location.href;
			}
		},
		'json'
	);
}
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
</script>

<script type="text/javascript">
	$(document).ready(function(){
		$(".change .input").focus(function(){
			
			$(this).addClass("hidden");
			$(this).parent().find(".inputArea").show();
			$(this).parent().find(".inputArea").focus();
			$(this).parent().find(".inputArea").val($(this).val());
			//拿到TextArea的DOM
			var textarea=document.getElementsByName('textarea');
			//设置高度
			textarea[0].style.height = textarea[0].scrollHeight + 'px';
		});
		$(".change .inputArea").blur(function(){
			$(this).hide();
			$(this).parent().find(".input").removeClass("hidden");
			$(this).parent().find(".colse").hide();
			var value=$(this).val();
			$(this).parent().find(".input").val($(this).val());
		}); 	
		$(".tablelist tr").hover(function(){
			$(this).children("td.showMore").find(".input").hide();
			var value=$(this).children("td.showMore").find(".input").val();
			$(this).children("td.showMore").find(".infoBox").html(value).show();
		},function(){
			$(this).children("td.showMore").find(".input").show();
			$(this).children("td.showMore").find(".infoBox").hide();
		});

		
	});	
</script>
</body>
</html>