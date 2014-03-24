<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Crawler管理</title>
</head>

<body>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
</c:if>
<div class="row">
    <div class="span4 offset7">
        <form class="form-search" action="#">
            <label>URL：</label> <input type="text" name="search_LIKE_websiteUrl" class="input-medium" value="${param.search_LIKE_websiteUrl}">
            <button type="submit" class="btn" id="search_btn">查找</button>
        </form>
    </div>
    <tags:sort/>
</div>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>Crawler URL</th><th>Spider状态</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${crawlers.content}" var="crawler">
        <tr>
            <td><a href="${ctx}/crawlers/${crawler.id}/edit">${crawler.websiteUrl}</a></td>
            <td>
                <c:if test="${crawler.spiderRunning}">
                    运行中...
                </c:if>
                <c:if test="${not crawler.spiderRunning}">
                    停止
                </c:if>
            </td>
            <span>  </span>
            <td>
                <c:if test="${crawler.spiderRunning}">
                    <a class="btn" href="${ctx}/crawlers/${crawler.id}/stop">停止</a>
                </c:if>
                <c:if test="${not crawler.spiderRunning}">
                    <a class="btn" href="${ctx}/crawlers/${crawler.id}/start">启动</a>
                </c:if>
                <span>  </span>
                <a class="btn" href="${ctx}/crawlers/${crawler.id}/export.xls">导出Excel</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<tags:pagination page="${crawlers}" paginationSize="5"/>

<div><a class="btn" href="${ctx}/crawlers/create">创建Crawler</a></div>
</body>
</html>
