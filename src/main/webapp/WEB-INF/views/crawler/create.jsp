<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Crawler管理</title>
</head>

<body>
<form:form id="inputForm" action="${ctx}/crawlers" method="post" modelAttribute="crawler" cssClass="form-horizontal">
    <form:hidden path="id" />
    <fieldset>
        <legend><small>管理Crawler</small></legend>
        <div class="control-group">
            <label for="websiteUrl" class="control-label">网站URL</label>
            <div class="controls">
                <form:input id="websiteUrl" path="websiteUrl" cssClass="input-large required" minlength="5" />
            </div>
        </div>
        <div class="control-group">
            <label for="wholeWebsit" class="control-label">抓取整站</label>
            <div class="controls">
                <form:checkbox id="wholeWebsit" path="wholeWebsit" cssClass="input-xxlarge" />
            </div>
        </div>
        <div class="control-group">
            <label for="includedUrlPattern" class="control-label">合法URL正则表达式（可以填入多个，以,分隔）</label>
            <div class="controls">
                <form:textarea id="includedUrlPattern" path="includedUrlPattern" cssClass="input-xxlarge" />
            </div>
        </div>
        <div class="control-group">
            <label for="excludedUrlPattern" class="control-label">非法URL正则表达式（可以填入多个，以,分隔）</label>
            <div class="controls">
                <form:textarea id="excludedUrlPattern" path="excludedUrlPattern" cssClass="input-xxlarge" />
            </div>
        </div>
        <div class="control-group">
            <label for="excludedEaUserPattern" class="control-label">非法邮箱地址用户正则表达式（可以填入多个，以,分隔）</label>
            <div class="controls">
                <form:textarea id="excludedEaUserPattern" path="excludedEaUserPattern" cssClass="input-xxlarge" />
            </div>
        </div>
        <div class="control-group">
            <label for="excludedEaDomainPattern" class="control-label">非法邮箱地址域正则表达式（可以填入多个，以,分隔）</label>
            <div class="controls">
                <form:textarea id="excludedEaDomainPattern" path="excludedEaDomainPattern" cssClass="input-xxlarge" />
            </div>
        </div>
        <div class="form-actions">
            <input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
            <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
        </div>
    </fieldset>
</form:form>
<script>
    $(document).ready(function() {
        //聚焦第一个输入框
        $("#websiteUrl").focus();
        //为inputForm注册validate函数
        $("#inputForm").validate();
    });
</script>
</body>
</html>
