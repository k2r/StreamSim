<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<jsp:include page="/WEB-INF/views/Header.jsp"></jsp:include>

<c:if test="${empty param.load}"> 
	<jsp:include page="/WEB-INF/views/StreamSelectForm.jsp"></jsp:include> 
</c:if>

<c:if test="${!empty param.load}">
	<jsp:include page="/WEB-INF/views/StreamInfo.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/CommandOptionsForm.jsp"></jsp:include>
</c:if>

<jsp:include page="/WEB-INF/views/Footer.jsp"></jsp:include>
