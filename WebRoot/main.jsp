<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="author" content="Chomo" />
  <link rel="start" href="http://www.14px.com" title="Home" />
  <title>div框架布局 </title>
  <style type="text/css">
   * { margin:0; padding:0; list-style:none;}
   html { height:100%; overflow:hidden; background:#93C247;}
   body { height:100%; overflow:hidden; background:#93C247;}
   div { background:#7FCEF5; line-height:1.6;}
   .top { position:absolute; left:10px; top:10px; right:10px; height:30px;}
   .side { position:absolute; left:10px; top:50px; bottom:50px; width:200px; overflow:auto;}
   .main { position:absolute; left:220px; top:50px; bottom:50px; right:10px; overflow:auto;}
   .bottom { position:absolute; left:10px; bottom:10px; right:10px; height:30px;}
   .main iframe { width:100%; height:100%;}
   /*---ie6---*/
   html { *padding:70px 10px;}
   .top { *height:50px; *margin-top:-60px; *margin-bottom:10px; *position:relative; *top:0; *right:0; *bottom:0; *left:0;}
   .side { *height:100%; *float:left; *width:200px; *position:relative; *top:0; *right:0; *bottom:0; *left:0;}
   .main { *height:100%; *margin-left:210px; _margin-left:207px; *position:relative; *top:0; *right:0; *bottom:0; *left:0;}
   .bottom { *height:50px; *margin-top:10px; *position:relative; *top:0; *right:0; *bottom:0; *left:0;}
  </style>
 </head>
 <body>
  <div class="top">
    <li style="text-align:right"  app="operate" act="index" link-app="" link-act="">
	  <a href="/loginUser!exit.action?type=exit" class="sub-nav-link" >退出</a>
    </li>
  </div>
  <div class="side">
  <ul>
  <li>
	<a href="javascript:void(0)" class="nav-link">
		人员管理
		<i class="arrow"></i>
		</a>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/User.action?type=manageStudent" class="sub-nav-link" target="mainFrame">-人员管理</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/imageText!imageMoveToText.action?type=manageStudent" class="sub-nav-link" target="mainFrame">-图文置换</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/imageText!imageMoveToText.action?type=manageStudent" class="sub-nav-link" target="mainFrame">-HTTPS下载</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			高德地图
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!toFind.action?" class="sub-nav-link" target="mainFrame">--画框抓图</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!toFindData.action?" class="sub-nav-link" target="mainFrame">--精准画框抓图</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!toAutoFindData.action?" class="sub-nav-link" target="mainFrame">--精准自动抓图</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!toFindDataByCityAndKey.action?" class="sub-nav-link" target="mainFrame">--按城抓图</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!fileConvert.action?" class="sub-nav-link" target="mainFrame">-数据处理</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!mapRule.action?" class="sub-nav-link" target="mainFrame">-高德公式</a>
			</li>
		</ul>
		<ul class="sub-nav">
			<li class="apct" app="operate" act="index" link-app="" link-act="">
			<a href="/gaud!toTaskList.action?test1=22222&taskPage=1&taskNumForOnePage=10" class="sub-nav-link" target="mainFrame">-任务管理</a>
			</li>
		</ul>
	</li>
	</ul>
   <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
   <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
  </div>
  <div class="main">
   <iframe frameborder="0" src="data.jsp" name="mainFrame">111</iframe>
  </div>
  <div class="bottom"></div>
 </body>
</html>
