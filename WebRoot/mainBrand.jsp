<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="author" content="Chomo" />
  <link rel="start" href="http://www.14px.com" title="Home" />
  <link rel="stylesheet" href="/css/lib/base.css" />
  <link rel="stylesheet" href="/css/lib/font-awesome.css" />
  <link rel="stylesheet" href="/css/zhaoshang/main.css" />
  <link rel="stylesheet" type="text/css" href="/css/lib/font-awesome.css" />
  <script src="/js/js/Jquery/jquery.js"></script>
	
  <title><s:text name="brand.title"/></title>
  
  <style type="text/css">
	.table th{
		background-color: #eee;
	}
	.table th.t-right, .table td.t-right{
		text-align: right;
	}
  </style>
 </head>
 
 <body>
<div id="header" class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div id="header-inner">
            <a id="logo" class="pull-left" href="/brand/"><img src="/img/brand/logo.png" alt="品牌管理系统" /></a>
            <form action="/queryBrand.html" id="search_form" class="header-search form form-inline pull-left"  onsubmit="return emptySearch()">
                <input type="search" name="wd" id="search_wd" value=" " class="search-field icon-search pull-left" placeholder="请输入关键词" />
                <input type="submit" class="btn btn-primary" value="<s:text name="brand.search"/>" /> <a href=""><s:text name="brand.super.search"/></a>
            </form>
            <div class="pull-right">
                <ul class="nav pull-left">
                   <li><a href=""><span class="icon icon-24 icon-plus"></span><s:text name="brand.add.brand"/></a></li>
				   <li><a href="/loginUser.action"><span class="icon icon-logo2"></span><s:text name="brand.first.page"/></a></li>
				   <li><a href="/loginUser!exit.action?type=exit" ><s:text name="brand.exit"/></a></li>
                </ul>
                <div class="shadow pull-left"></div>
                <div class="account-setting pull-right">
                    <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"><img width="22" height="22" src="/img/default/face.png" />YSH<b class="icon icon-caret-down"></b></a>
					<input type="hidden" id="user_id" value="{$userInfo.id}" />
                    <ul class="dropdown-menu">
                       <li><a href="/brand/user/">我的品牌中心</a></li>
					   <li><a href="/?act=extern&do=changepwd">修改密码</a></li>
                       <li><a href="javascript:void(0)" onclick="loginOut()">退出</a></li>
                    </ul>
                </div>  
            </div><!-- -->    
        </div>
    </div>
</div><!--/ #header -->
 
<%@ page import="com.entity.*"%>

<div class="container clear">
	<div id="category-list">
		<s:iterator value="brandStatList"  id="oneBrandType" > 
			<dl>
				<dt>
				<a href="/brandlistone_<s:property value="typeLevelId" />.html"><s:property value="typeLevelName" /></a>&nbsp;&nbsp;<span style="color:gray;font-size:13px;">(共 <s:property value="typeLevelNum" /> )</span>
				</dt>
				<dd>
					<ul class="clear">
					<s:iterator id="service" value="subTypeList">
					    <li><a href="/brandlisttwo_<s:property value="#service.typeLevelId" />.html" > <s:property value="#service.typeLevelName" /></a><span><a><b><s:property value="#service.typeLevelNum" /></b></a></span></li>
					</s:iterator>	
					</ul>
				</dd>
			</dl>
		</s:iterator>
	</div><!-- / #category-list -->
</div><!-- /.container -->

<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/assets/js/brand/app/Sort.js"></script>
<script type="text/javascript">

</script>

<div class="bottom">
	<input type="hidden" id="main_brand_id" value="" />
	<div class="container" id="footer">
		<p><s:text name="brand.title"/>  COPYRIGHT © 2013-2017 ALL RIGHTS RESERVED </p>
	</div>
	<script  type="text/javascript" src="/js/lib/bootstrap.min.js" ></script>
	<script src="/js/plugin/dialog.js"></script>
	<script src="/js/brand/common.js"></script>
</div>

 </body>
</html>
