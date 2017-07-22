<%@ page language="java" contentType="text/html" pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
  <title>修改页面</title>
  </head>
   <body>
   <a href="/User.action?type=manageStudent" >返回</a>
    <center>
    <p>修改列表</p>
    <s:form action="User">
    id:<s:property value="id"/>
    <s:hidden name="type" value="edit"/>
    <s:textfield name="name"  label="姓名"/>
    <s:textfield name="score"  label="成绩 "/>
    <s:textfield name="bak"  label="评价"/>
    <s:submit value="确定"/>
    <s:reset value="重置"/>
    </s:form>
    </center>
  </body>
</html>