<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link rel="stylesheet" href="/css/lib/base.css">
    <link rel="stylesheet" href="/css/lib/font-awesome.css">
    <link rel="stylesheet" href="/css/zhaoshang/main.css">
</head>
<body>
   <%@ page import="com.entity.*"%>
   <%//List<Student> list=(List)request.getAttribute("list");%>
   <%//list.size() %>
   <div >
	   <a href="/User.action?type=toAdd">ADD</a>
		<table border="1" width="100%" border="0" cellpadding="1" cellspacing="1" style="background-color: #b9d8f3;" class="table table-horizontal table-with-bordred mt10 auto-show" style="width:700px;">
		<tr style="text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
		<th>ID</th><th>姓名</th><th>成绩</th><th>评价</th><th>操作</th><th>操作</th>
		</tr>
		<s:iterator value="list" status="my"  > 
		<tr style="text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold">
		<td><s:property value="id" /></td>
		<td> <s:property value="name" /></td>
		<td><s:property value="score" /></td>
		<td><s:property value="bak" /></td>
		<td><a href=" /User.action?id=<s:property value="id" />&type=edit1" >编辑</a></td>
		<td><a href=" /User.action?id=<s:property value="id" />&type=del">删除</a></td>
		</tr>
		</s:iterator>
		</table>
	</div>
</body>
</html>
