<%@ page language="java" contentType="text/html" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
  <title>添加页面</title>
  </head>
   <body>
   <a href="/User.action?type=manageStudent" >返回</a>
    <center>
    <p>新加列表</p>
    <s:form action="User">
    <s:hidden name="type" value="add"/>
    <s:textfield name="id" label="id"/>
    <s:textfield name="name" label="姓名"/>
    <s:textfield name="score" label="成绩 "/>
    <s:textfield name="bak" label="评价"/>
    <s:submit value="确定"/>
    <s:reset value="重置"/>
    </s:form>
    </center>
  </body>
</html>