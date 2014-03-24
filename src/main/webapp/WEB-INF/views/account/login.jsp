<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>登录页</title>
</head>

<body>
    <spring:url var="authUrl" value="/static/j_spring_security_check" />
	<form id="loginForm" action="${authUrl}" method="post" class="form-horizontal">
	    <!--
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
		</div>
		-->

		<div class="control-group">
			<label for="username" class="control-label">名称:</label>
			<div class="controls">
				<input type="text" id="username" name="j_username"  value="${username}" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				<input type="password" id="password" name="j_password" class="input-medium required"/>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</body>
</html>
