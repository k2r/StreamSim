<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<jsp:include page="/views/Header.jsp"></jsp:include>

<c:if test="${empty param.load}"> 
	<jsp:include page="/views/StreamSelectForm.jsp"></jsp:include> 
</c:if>

<c:if test="${!empty requestScope.error}"><div>${requestScope.error}</div></c:if>

<c:if test="${!empty param.load}">
	<jsp:include page="/views/StreamInfo.jsp"></jsp:include>
	<jsp:include page="/views/CommandOptionsForm.jsp"></jsp:include>
</c:if>

<jsp:include page="/views/Footer.jsp"></jsp:include>
