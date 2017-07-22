<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="zh-CN"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang="zh-CN"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang="zh-CN"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" /> <!--<![endif]-->
<head>  
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>商家品牌库系统</title>
    <meta name="description" content="">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
    <link rel="stylesheet" type="text/css" href="/assets/css/lib/base.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/lib/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/brand/main.css">
	<script src="{$resUrl}/js/lib/jquery.js"></script>
</head>
<body>
<div id="header" class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div id="header-inner">
            <a id="logo" class="pull-left" href="/brand/"><img src="/assets/img/brand/logo.png" alt="品牌管理系统"></a>
            <form action="/brand.php" id="search_form" class="header-search form form-inline pull-left"  onsubmit="return emptySearch()">
				<input type="hidden" name="app" value="default">
				<input type="hidden" name="act" value="search">
                <input type="search" name="wd" id="search_wd" value=" " class="search-field icon-search pull-left" placeholder="请输入关键词" />
                <input type="submit" class="btn btn-primary" value="搜索" > <a href="/brand/advSearch/">高级搜索</a>
            </form>
            <div class="pull-right">
                <ul class="nav pull-left">
                   <li><a href="/brand/add.html"><span class="icon icon-24 icon-plus"></span>添加品牌</a></li>
				   <li><a href="/index.php"><span class="icon icon-logo2"></span>首页</a></li>
                </ul>
                <div class="shadow pull-left"></div>
                <div class="account-setting pull-right">
                    <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"><img width="22" height="22" src="{$head_url}" />YSH<b class="icon icon-caret-down"></b></a>
					<input type="hidden" id="user_id" value="{$userInfo.id}">
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

<!-- public end -->