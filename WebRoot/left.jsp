<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
  <title>欢迎</title>
</head>
<body>
   <p align="center"><%=session.getAttribute("username") %>
   </p>
 	
	 <div id="sidebar">
		<ul class="nav">
		<li>
			<a href="javascript:void(0)" class="nav-link">
			人员管理
			<i class="arrow"></i>
			</a>
			<ul class="sub-nav">
				<li class="apct" app="operate" act="index" link-app="" link-act="">
				<a href="/User.action?type=manageStudent" class="sub-nav-link">学生管理</a>
				</li>
				<li class="apct" app="adminImage" act="index" link-app="" link-act="">
				<a href="?app=adminImage&act=index" class="sub-nav-link">aaaa</a>
				</li>
				<li class="apct" app="adminRole" act="index" link-app="" link-act="">
				<a href="?app=adminRole&act=index" class="sub-nav-link">aaaa</a>
				</li>
				<li class="apct" app="authority" act="index" link-app="" link-act="">
				<a href="?app=authority&act=index" class="sub-nav-link">aaaaa</a>
				</li>
				<li class="apct" app="adminConstant" act="index" link-app="" link-act="">
				<a href="?app=adminConstant&act=index" class="sub-nav-link">aaaa</a>
				</li>
				<li class="apct" app="adminData" act="setting" link-app="" link-act="">
				<a href="?app=adminData&act=setting" class="sub-nav-link">aaaa</a>
				</li>
			</ul>
		</li>
	</div>
</body>
</html>
