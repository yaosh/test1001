<%@ page language="java" contentType="text/html" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
  <title>登陆页面</title>
  </head>
   <body>
    <center>
    <s:form action="loginUser">
    <s:textfield name="username" label="用户名"/>
    <s:password name="password" label="密码"/>
    <s:submit value="确定"/>
    <s:reset value="重置"/>
    </s:form>
    </center>
  </body>
</html>
