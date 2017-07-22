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
            <form action="/brand.php" id="search_form" class="header-search form form-inline pull-left"  onsubmit="return emptySearch()">
				<input type="hidden" name="app" value="default" />
				<input type="hidden" name="act" value="search" />
                <input type="search" name="wd" id="search_wd" value=" " class="search-field icon-search pull-left" placeholder="请输入关键词" />
                <input type="submit" class="btn btn-primary" value="搜索" /> <a href="/brand/advSearch/">高级搜索</a>
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

<s:bean name="com.entity.Brand"   var="brandBaseInfo" />
<s:bean name="com.entity.BrandDetail"   var="brandDetail" />
<s:bean name="com.entity.BrandAgent"   var="brandAgent" />

<div class="container clear">
		    <!-- cate-select -->
			<div class="clear crumb">
				<input type="hidden" name="" id="add_user" value="" />
				<a href="">首页</a>
					<span class="split">&gt;</span>
					<a ><s:property value="brandBaseInfo.typeLevelOneName"/></a>
					<span class="split">&gt;</span>
					<a ><s:property value="brandBaseInfo.typeLevelTwoName"/></a>
					<span class="split">&gt;</span>
					<s:property value="brandBaseInfo.name"/>   (一级)
			</div>
			
			<div id="detail" class="clear">
				<div id="detail-hd" class="clear">
					<h1>
						<s:property value="brandBaseInfo.name"/>/<s:property value="brandBaseInfo.eng_name"/><font style="font-size:14px;font-weight:normal;"></font>
						<span style="font-size:14px;font-weight:normal;">( 一级 )</span>
					</h1>
					
					<div class="btns pull-right">
					<a href="javascript:void(0);" data-url="/brand.php?app=brand&act=editBase&id={$info.id}&brand_type={$brand_type}&height=500&width=800" data-title="编辑品牌基本信息" class="edit-btn dialog"><span class="icon-edit"></span> 编辑品牌基本信息</a> 
					<a href="javascript:void(0);" data-id="{$info.id}" id="recovery-brand" class="delete-btn"><span class="icon-recovery"></span> 审核通过</a>
					</div>
				</div>
				<div id="detail-bd">
					<div class="base-info clear">
						<div class="brand-logo">
							<div class="complete-status" data-id="{$info.id}">
								<span ><b>完成度</b></span>
								<span id="progress-show"><s:property value="brandBaseInfo.progress"/><i>%</i></span>
							</div>

						<a href=""><img style="width:210px;" src="<s:property value="brandBaseInfo.logo"/>" alt=""></a></div>
						<style>
							#detail .toc {
								width: 560px;
							}
							.toc dl {
								width: 280px;
								float: left;
							}
						</style>
						<div class="toc">
							<dl>
								<dt>品牌信息：</dt>
								<dd><a href="#a1">品牌介绍</a></dd>
								<dd><a href="#a2">品牌档案</a></dd>
								<dd><a href="#a3">主要竞争对手</a></dd>
								<dd><a href="#a6">已知合作条件</a></dd>
								<dd><a href="#a7">开店硬件条件</a></dd>
								<dd><a href="#edit-users">品牌创建人</a></dd>
								
								<!-- <dd><a href="#a9">查阅悦生活数据</a></dd> -->
							</dl>
							<dl>
								<dt>品牌公司或中国总代理：</dt>
								<dd><a href="#baseArea">品牌公司或中国总代理公司信息</a></dd>
								<dd><a href="#brandArea">该公司旗下的其他品牌</a></dd>
								<dd><a href="#contactArea">拓展联系信息</a></dd>
								<dd><a href="#discussArea">洽谈记录</a></dd>
							</dl>	
						</div>
					</div><!-- /.base-info -->
					
					<!-- div.detail-box*9>((div.detail-box-hd>h2+div.btns>a.edit-btn)+div.detail-box-bd) -->
					<div class="detail-box" id="a1">
						<div class="detail-box-hd clear">
							<h2>品牌介绍   &nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度5%  <span style="color:red;" >已完成100%)</span></span></h2>
							<div class="btns">
							<a href="javascript:void(0);" data-url="" data-title="编辑品牌介绍" class="edit-btn dialog"><span class="icon-edit"></span> 编辑品牌介绍</a></div>
						</div>
						<div class="detail-box-bd">
							<div class="detail-note cat-old">
							<pre style="white-space: pre-wrap;word-wrap: break-word;"><s:property value="brandDetail.note"/></pre>
							</div>
						</div>
					</div>
					
					<div class="detail-box" id="a2" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>品牌档案   &nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度15%  <span style="color:red;">已完成100%</span>)</span></h2>
							<div class="btns">
							<a href="javascript:void(0);" data-url="" data-title="编辑品牌档案" class="edit-btn dialog"><span class="icon-edit"></span> 编辑品牌档案</a></div>
						</div>
						<div class="detail-box-bd">
							<div class="detail-dangan cat-old">
							<table class="table table-bordered table-th-colored" width="100%">
								<tr>
									<th width="14%">产品类别</th>
									<td width="19%"><s:property value="brandDetail.style"/></td>
									<th width="14%">品牌等级</th>
									<td width="19%">一线</td>
									<th width="14%">市场周期</th>
									<td width="19%"><s:property value="brandDetail.cycles"/></td>
								</tr>
								<tr>
									<th width="14%">消费者性别</th>
									<td width="19%"><s:if test="#brandDetail.cust_sex=='1'">男</s:if><s:elseif test="#brandDetail.cust_sex=='2'">女</s:elseif><s:elseif test="#brandDetail.cust_sex=='3'">儿童</s:elseif><s:else >无性别限制</s:else></td>
									<th width="14%">消费者年龄构成</th>
									<td width="19%"><s:if test="#brandDetail.cust_age==null">无年龄限制</s:if><s:else ><s:property value="brandDetail.cust_age"/></s:else></td>
									<th width="14%">客单价</th>
									<td width="19%"><s:if test="#brandDetail.cust_price==null"></s:if><s:else ><s:property value="brandDetail.cust_price"/></s:else></td>
									<th width="14%">主价格带</th>
									<td width="19%">1000~200</td>
								</tr>
								<tr>
									<th width="14%">品牌创立时间</th>
									<td width="19%"><s:if test="#brandDetail.create_time==null"></s:if><s:else ><s:property value="brandDetail.create_time"/></s:else> </td>
									<th width="14%">品牌归属地</th>
									<td width="19%"><s:if test="#brandDetail.origin_loc==null"></s:if><s:else ><s:property value="brandDetail.origin_loc"/></s:else></td>
									<th width="14%">进入国内时间</th>
									<td width="19%"><s:if test="#brandDetail.in_china_time==null"></s:if><s:else ><s:property value="brandDetail.in_china_time"/></s:else> </td>
								</tr>
							</table>
							</div>
						</div>
					</div>
					
					<div class="detail-box" id="a3" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>主要竞争对手   &nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度15%  <span style="color:red;" >已完成100%</span>)</span></h2>
							<div class="btns">
							<a href="javascript:void(0);" data-url="" data-title="添加竞争对手" class="edit-btn dialog"><span class="icon-edit"></span> 添加竞争对手</a></div>
						</div>
						<div class="detail-box-bd">
							<div class="detail-compete cat-old">
								<table class="table table-bordered table-th-colored" width="100%">
									<tr>
										<th>品牌Logo</th>
										<th>品牌中文名</th>
										<th>品牌英文名</th>
										<th>操作</th>
									</tr>
									<s:iterator value="relationBrandList"  id="relationBrand"  status="L" > 
									<tr class="compete-<s:property value="#L.index+1"/>">
										<td class="center"><a href="" target="_blank"><img src="<s:property value="logo" />" alt="" height="50" /></a></td>
										<td><s:property value="name" /></td>
										<td><s:property value="eng_name" /></td>
										<td class="center">
											<a class="delete-link" href="javascript:void(0);" onclick="">
											取消
											</a>
										</td>
									</tr>
									</s:iterator>
								</table>
							</div>
						</div>
					</div>
					
					
					<div class="detail-box" id="a6" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>已知合作条件 (单店)&nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度15% <span style="color:red;" >已完成100%</span>)</span></h2>
							<div class="btns">
								<a target="_blank" href="" class="edit-btn"data-title="单店">添加已知单店</a>
							</div>
						</div>
						<div class="detail-box-bd">
							<table class="table table-bordered table-th-colored" width="100%">
								<tr>
									<th>地区</th>
									<th>单店名</th>
									<th>隶属</th>
									<th nowrap="nowrap">开店方式</th>
									<th>所在项目/路</th>
									<th nowrap="nowrap">楼层</th>
									<th>店铺面积</th>
									<th nowrap="nowrap">合作模式</th>
									<th>管理费</th>
									<th>年营业额</th>
									<th>年平效</th>
									<th>备注</th>
									<th width="60px;"></th>
								</tr>
								
								<s:iterator value="brandShopList"  id="brandShop" status="L"> 
								<tr class="shop-<s:property value="#L.index+1"/>" data-id="<s:property value="#L.index+1"/>">
									<td><s:property value="province_name" /> <s:property value="city_name" /></td>
									<td><s:property value="branch_name" /></td>
									<td><a href=""><s:property value="agent_name" /></a></td>
									<td><s:property value="shop_style" /></td>
									<td><s:property value="address" /></td>
									<td><s:property value="floor" /></td>
									<td><s:property value="area" />㎡</td>
									<td><s:property value="coop_method" /></td>
									<td><s:property value="manage_info" />元/m<sub>2</sub>/月</td>
									<td><s:property value="income" />万元</td>
									<td><s:property value="avg_income" />万元/㎡</td>
									<td><s:property value="note" /></td>
									<td>
										<a href="">详情</a>
										<a href="javascript:void(0)" class="delete-link" onclick="deleteShop('{$row.id}')" >删除</a>
									</td>
								</tr>
								</s:iterator>
							</table>
							<b><span style="background-color:#eee;font-size:14px;float:right;padding:4px 12px;">已知<s:property value="address.city" /><s:property value="address.city.city_branch_cnt" />家，<s:property value="address.province" /><s:property value="address.city.province_branch_cnt" />家，全国<s:property value="brandBaseInfo.branch_num"/></span></b>
							<div class="clear">
								<a href="javascript:void(0);" data-target="" class="get-more pull-right mt10"><span class="icon-sort-down" style="position: relative; top: -3px;"></span> 查看更多单店</a>
							</div>
						</div>
					</div>
					
					<div class="detail-box" id="a7" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>开店硬件条件参照标准&nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度15% <span  style="color:red;">已完成100%</span>)</span></h2>
							<div class="btns">
						</div>
						<div class="detail-box-bd">
							<div class="detail-hard cat-old">
							<table class="table table-bordered table-th-colored" width="100%">
							<tr><td><img src="/img/hardwarecondition/canyin_hardware.png" height="" width="" /></td></tr>
							<tr><td><img src="/img/hardwarecondition/caoshi_hardware.png" height="" width="" /></td></tr>
							<tr><td><img src="/img/hardwarecondition/buy_hardware.png" height="" width="" /></td></tr>
							<tr><td><img src="/img/hardwarecondition/jinrong_hardware.png" height="" width="" /></td></tr>
							</table>
							</div>
						</div>
					</div>
				    </div>
				  
				    <div class="detail-box" id="baseArea">
						<div class="detail-box-hd clear">
							<h2>品牌公司或中国总代理公司信息   &nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度10%   <span style="color:red;">已完成100%</span>)</span></h2>
							<div class="btns">	<a href="javascript:void(0);" data-url="height=420&width=600" data-title="编辑品牌公司或中国总代理公司信息" class="edit-btn dialog"><span class="icon-edit"></span> 编辑品牌公司或中国总代理公司信息</a>
							</div>
						</div>
						<div class="detail-box-bd">
							<table class="table table-bordered table-th-colored" width="100%">
								<tr>
								<th width="150">中国地区公司名</th>
								<td><s:property value="brandAgent.name"/></td>
								</tr>
								<tr>
								<th width="150">公司地址</th>
								<td><s:property value="brandAgent.address"/></td>
								</tr>
								<tr>
								<th width="150">网址</th>
								<td><s:property value="brandAgent.website"/><a href="http://<s:property value="brandAgent.website"/>" target="_blank"></a></td>
								</tr>
								<tr>
								<th width="150">公司电话</th>
								<td><s:property value="brandAgent.phone"/></td>
								</tr>
								<tr>
								<th width="150">公司传真</th>
								<td><s:property value="brandAgent.fax"/></td>
								</tr>
							</table>
						</div>
					</div>
					
					<div class="detail-box" id="brandArea" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>该公司旗下的其他品牌   &nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度10%    <span  style="color:red;" >已完成100%</span>)</span></h2>
							<div class="btns">
							<a data-title="代理的其他品牌" href="" onclick="javascript:alert('请先填写品牌公司或中国总代理公司信息！')" class="edit-btn"><span class="icon-edit"></span> 添加代理的其它品牌</a></div>
						</div>
						<div class="detail-box-bd">
							<div class="agent-brands cat-old">
							<table class="table table-bordered table-th-colored" width="100%">
								<tr>
									<th>品牌Logo</th>
									<th>品牌英文名</th>
									<th>品牌中文名</th>
									<th></th>
								</tr>
								 <s:iterator value="subBrandList"  id="subBrand" status="L"> 
								<tr class="brand-<s:property value="#L.index+1"/>">
									<td class="center" width="150"><a href="" target="_blank"><img src="<s:property value="logo" />" alt="" width="50" /></a></td>
									<td><s:property value="eng_name" /></td>
									<td><s:property value="name" /></td>
									<td class="center" width="80"><a class="delete-link" href="javascript:void(0);" onclick="">删除</a></td>
								</tr>
								</s:iterator>
							</table>
							</div>
						</div>
					</div>
					
					
					<div class="detail-box" id="contactArea" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>拓展联系信息   &nbsp;&nbsp;<span style="font-size:12px;color:#444;font-weight:normal">(占总进度15%    <span style="color:red;">已完成100%</span>)</span></h2>
							<div class="btns">
							<a  data-title="添加拓展联系信息" href="javascript:void(0)" data-url="" class="edit-btn dialog"  ><span class="icon-edit"></span> 添加拓展联系信息</a></div>
						</div>
						<div class="detail-box-bd">
							<div class="agent-contact cat-old">
							<table class="table table-bordered table-th-colored" width="100%">
								<tr>
									<th>姓名</th>
									<th style="padding:8px 3px;">性别</th>
									<th>职位</th>
									<th>联系电话 </th>
									<th>手机</th>
									<th>Email</th>
									<th>隶属公司 (代理)</th>
									<th>负责区域</th>
									<th nowrap="nowrap">添加人</th>
									<th nowrap="nowrap"></th>
								</tr>
								<s:iterator value="brandContactList"  id="brandContact" status="L">
								<tr class="contact-<s:property value="#L.index+1"/> temp-contact" >
									<td><div style="width:50px;"><s:property value="name" /></div></td>
									<td><div ><s:property value="sex" /></div></td>
									<td><div style="width:60px;"><s:property value="job" /></div></td>
									<td><div style="width:90px;"><s:property value="phone" /></div></td>
									<td width="100" style="table-layout:fixed;"><span style="word-break:break-all;word-wrap:break-word;"><s:property value="mobile" /></span></td>
									<td width="100" style="table-layout:fixed;"><span style="word-break:break-all;word-wrap:break-word;"><a href="mailto:{$row.email}"><s:property value="email" /></a></span></td>
									<td>
										<a  target="_blank"   href="" ><s:property value="agent_name" /></a>
									</td>
									<td><s:property value="region" /></td>
									<td><s:property value="add_username" /></td>
									<td class="center"><a href="javascript:void(0);" data-url="" data-title="编辑拓展联系人" class="dialog">编辑</a>
									<a class="delete-link" href="javascript:void(0);" onclick="">删除</a></td>
								</tr>
								</s:iterator>
							</table>
							</div>
						</div>
					</div>
					
					<div class="detail-box" id="discussArea" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>洽谈记录</h2>
						</div> 
						<div class="detail-box-bd">
						<table class="table table-bordered table-th-colored" width="100%">
								<tr>
									<th width="80">洽谈时间</th>
									<th width="60">洽谈地点</th>
									<th width="60">洽谈项目</th>
									<th width="60">洽谈主对象</th>
									<th width="60">洽谈事由</th>
									<th>洽谈内容描述</th>
									<th width="50">主洽谈人</th>
								</tr>
								<tr>
									<td>2013/08/20</td>
									<td>电话</td>
									<td>平陵广场</td>
									<td>蒋银花</td>
									<td>招商</td>
									<td>本周同ONLY零售经理面谈，对方不排斥在我项目开店，本周会跟品牌公司其它零售经理沟通，目前图纸已发送对方，下周会有结果</td>
									<td>柏正春</td>
								</tr>
							</table>
							
						</div>
					</div>
					
					<div class="detail-box" id="edit-users" style="margin-top:-15px;">
						<div class="detail-box-hd clear">
							<h2>品牌创建人</h2>
						</div>
						<div class="detail-box-bd">
							<table class="table table-bordered table-th-colored" width="100%">
								<tr>
									<th width="20%">创建</th>
									<th width="20%">管理</th>
									<th colspan=3 width="60%">贡献</th>
								</tr>
								<tr>
									<td>
										<a href="javascript:void(0)" style="padding-left:10px;">管理员</a>
									</td>
									<td>
										<a href="javascript:void(0)" style="padding-left:10px;">韩立</a>
									</td>
									<td colspan=3>
										<a href="javascript:void(0)" style="padding-left:10px;">牧尘</a>
									</td>
									
								</tr>
								
							</table>
						</div>
					</div> 
				
			</div><!-- / #detail -->
			
			<div id="toolbar">
				<a href="javascript:void(0);" id="toogle-toc" class="toolbar-link">目录</a>
				<a href="javascript:void(0);" id="gotop" class="toolbar-link"><i class="icon-sort-up"></i>返回顶部</a>
				<dl class="toolbar-toc">
					<dt>品牌信息：</dt>
					<dd><a href="#a1">品牌介绍</a></dd>
					<dd><a href="#a2">品牌档案</a></dd>
					<dd><a href="#a3">主要竞争对手</a></dd>
					<dd><a href="#a6">已知合作条件</a></dd>
					<dd><a href="#a7">开店硬件条件</a></dd>
					<dd><a href="#edit-users">品牌创建人</a></dd>
					<dt>品牌公司或中国总代理：</dt>
						<dd><a href="#baseArea">品牌公司或中国总代理公司信息</a></dd>
						<dd><a href="#brandArea">该公司旗下的其他品牌</a></dd>
						<dd><a href="#contactArea">拓展联系信息</a></dd>
						<dd><a href="#discussArea">洽谈记录</a></dd>
				</dl> 
			</div>
       </div>
    </div>

<script type="text/javascript" src="{$resUrl}/js/lib/ajaxfileupload.js"></script>
<script type="text/javascript" src="{$resUrl}/js/brand/app/brand.js"></script>
<script type="text/javascript" src="{$resUrl}/js/brand/ajaxsubmit.js"></script>	
<script type="text/javascript" src="{$resUrl}/js/brand/app/error.js"></script>

<script type="text/javascript">
$(function(){
$("#baseArea").after($("#a6"));
$(".price .num50").live("blur", function(){
	var cur_num = $(this).val();
	if (isNaN(cur_num)) {
		alert("请输入有效的数字");
		$(this).val("");
		$(this).focus();
		return;
	}
	cur_num = cur_num.replaceAll(" ",'');
	if (cur_num == "") {
		return;
	}
	cur_num = parseInt(cur_num);
	cur_num = cur_num-cur_num%50;
	$(this).val(cur_num);
	if ($(this).index()%2==0) {
		var relate_input = $(this).next();
	}else{
		var relate_input = $(this).prev();
	}
	var relate_value = parseInt(relate_input.val());
	if ( ($(this).index()%2)==0&&( !relate_input.val() || relate_value<=cur_num ) ) {
		$(this).next().val(cur_num+50);
	}
	if ($(this).index()%2==1&&( !relate_input.val() || relate_value>= cur_num)) {
		$(this).prev().val(cur_num-50);
	}
});
$(".add-continue").live("click", function(){
	$(".price-list:last").after($("#price-tpl").html());
});
$(".price-del-btn").live("click", function(){
	$(this).parents(".price-list").remove();
});
})
/**
**查看数据切换
*/
$(".cat-data").live("click", function(){
	var classname = $(this).attr("data-class");
	$("."+classname).each(function(){
		if ($(this).css("display")=="none") {
				$(this).show();
				$("."+classname+"-btn").text("原始数据");
				$("."+classname+"-btn").css("color","#369");
		}else{
			$(this).hide();
			$("."+classname+"-btn").text("编辑数据")
			$("."+classname+"-btn").css("color","red");
		}
	});
	
});
$(".cat-data").hide();


$(".check-btn").live("click", function(){
	var state = $(this).attr("data-id");
	var brand_id = "{$brand_info.id}";
	if (!confirm("确认"+$(this).text()+"?")) {
		return false;
	}
	var loc_href = location.href.split("#");
	loc_href = loc_href[0];
	$.post(
		'/brand.php?app=brand&act=changeState',
		{ "brand_id":brand_id,"state":state},
		function(data){
			alert(data.msg);
			if (data.success) {
				
				location.href=loc_href
			}
		},
		'json'
	);
});

</script>
<script type="text/javascript" src="/assets/js/brand/check.js"></script>
<script type="text/javascript" src="/assets/js/brand/progress.js"></script>
<input type="hidden" id="refer-url" value="{$refer_url}" />
 
 <script type="text/javascript" src="/assets/js/brand/app/brandAgent.js"></script>
<script type="text/javascript" src="/assets/js/brand/app/agentBrand.js"></script>
<script type="text/javascript">
$(function() {
		$('.get-more').on('click', function() {
			var self = this;
			var target = $(self).data('target');
			var max_id = $(self).parents('.detail-box-bd').find('tr').last().attr("data-id");
			$.ajax({
				url: target+"&max_id="+max_id,
				dataType: 'html',
				success: function(data) {
					if (data=="") {
						alert("没有更多了");
						return false;
					}
					$(self).parents('.detail-box-bd').find('table').append(data);
				},
				error: function() {
					alert('加载数据失败，请稍后再试');
				}
			})
		});
	});
function setMultiBrand(agent_id, state) {
	if (state==1) {
		if (!confirm("确认该代理商旗下无其他品牌")) {
			return false;
		}
	}else{
		if (!confirm("确认取消无其他品牌标记")) {
			return false;
		}
	}
	$.post(
		"/brand.php?app=brandAgent&act=setMultiBrand",
		{ agent_id:agent_id, state:state,brand_id:{$brand_info.id}},
		function(data) {
			alert(data.msg);
			if (data.success) {
				reloadPage("#brandArea");
			}
		},
		'json'
	);
}
</script>
   

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
