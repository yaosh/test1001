<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
    <link rel="stylesheet" href="/css/lib/base.css">
    <link rel="stylesheet" href="/css/lib/font-awesome.css">
    <link rel="stylesheet" href="/css/zhaoshang/main.css">
    
    <script type="text/javascript" src="/js/cg/js/lib/jquery.js"></script>
    
</head>
<body>
   <%@ page import="com.entity.*"%>
   <%//List<Student> list=(List)request.getAttribute("list");%>
   <%//list.size() %>
   <div >
	   <a href="/User.action?type=toAdd">ADD</a>
		<table border="1" width="100%" border="0" cellpadding="1" cellspacing="1" style="background-color: #b9d8f3;" class="table table-horizontal table-with-bordred mt10 auto-show" style="width:700px;">
		<tr style="text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
		<th>序号</th><th>地区</th><th>城市</th><th>文件</th><th>操作</th><th>操作</th>
		</tr>
		<s:iterator value="list" status="my" > 
		<tr style="text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
		<td><s:property value="my.index" /></td>
		<td><s:property value="area" /></td>
		<td><s:property value="city" /></td>
		<td><a href="FileDownServlet?filename=<s:property value="path" /><s:property value="fileName" />" ><s:property value="fileName" /></a></td>
		<td><input type="button" value="生成sql" onclick="testajax()" /></td>
		<td><a href="">删除</a></td>
		</tr>
		</s:iterator>
		</table>
	</div>
	
	<script type="text/javascript" >
	
	function testajax()
	{
		 $.ajax({  
	            url:'poiAjax!testJQueryAjax.action',  
	            type:'POST',  
	            data:"{}",  
	            dataType:'json',  
	            success:function (data) 
	            {  
	            	alert(data.hello);
	            }  
	        });     
	}
	
	</script>
</body>
</html>
