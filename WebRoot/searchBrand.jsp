<%@ page language="java" contentType="text/html" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="/mytags" prefix="myPageTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="author" content="Chomo" />
  <link rel="start" href="http://www.14px.com" title="Home" />
  <link rel="stylesheet" href="/css/lib/base.css" />
  <link rel="stylesheet" href="/css/lib/font-awesome.css" />
  <link rel="stylesheet" href="/css/zhaoshang/main.css" />
  <link rel="stylesheet" href="/css/brand/main.css" />
  <link rel="stylesheet" type="text/css" href="/css/lib/font-awesome.css" />
  <script src="/js/js/Jquery/jquery.js"></script>
	
  <title>商家品牌库系统 </title>
  
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
                <input type="search" name="wd" id="search_wd" value="${wd}" class="search-field icon-search pull-left" placeholder="请输入关键词" />
                <input type="submit" class="btn btn-primary" value="搜索" /> <a href="">高级搜索</a>
            </form>
            <div class="pull-right">
                <ul class="nav pull-left">
                   <li><a href=""><span class="icon icon-24 icon-plus"></span>添加品牌</a></li>
				   <li><a href="/loginUser.action"><span class="icon icon-logo2"></span>首页</a></li>
				   <li><a href="/loginUser!exit.action?type=exit" >退出</a></li>
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
<%@ page import="com.web.*"%>

<s:bean name="com.entity.Brand"   var="brandBaseInfo" />
<s:bean name="com.entity.Page"   var="page" />

<div class="container clear">
		    <!-- cate-select -->
			<div class="clear cate-select">
				<div class="hd">
					<a href="/loginUser.action">首页</a>
					<span class="split">&gt;</span>
					总共找到  ${total} 个与 “${wd}” 相关的内容<br/>
				</div>
			</div>
			
			<div id="brand-list" class="grid-12">
			<ul>
			    <s:iterator value="brandList"  id="brand" > 
				<li style="overflow:hidden;">
					<a target="_blank" href="/branddetail_<s:property value="id" />.html" title="<s:property value="name" />"><img src="<s:property value="logo" />" alt="" /></a>
					<span class="rate">90%</span>
					<span><s:property value="name" /></span>
					<span class="modify">已审核</span>
				</li>
				</s:iterator>
			</ul>
			</div><!-- / #brand-list -->
			
            <div id="page" class="grid-12">
            <myPageTag:pager pageSize='${page.pageSize}' pageNo='${page.pageNo}' url="${page.url}" recordCount='${page.recordCount}' />
            </div><!-- / .pager -->
    </div>

	<div class="bottom">
		<input type="hidden" id="main_brand_id" value="" />
		<div class="container" id="footer">
			<p>品牌库管理系统  COPYRIGHT © 2013-2017 ALL RIGHTS RESERVED </p>
		</div>
		<script  type="text/javascript" src="/js/lib/bootstrap.min.js" ></script>
		<script src="/js/plugin/dialog.js"></script>
		<script src="/js/brand/common.js"></script>
	</div>

 </body>
</html>
