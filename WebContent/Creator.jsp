<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="creator" class="beans.CreatorBean" scope="session" />

<jsp:include page="/views/Header.jsp"></jsp:include>

<jsp:include page="/views/CreateForm.jsp"></jsp:include>

<c:if test="${!empty param.define}">
<jsp:include page="/views/AttributeForm.jsp"></jsp:include>
</c:if>

<c:if test="${!empty requestScope.success}"><div>${requestScope.success}</div></c:if>

<c:if test="${!empty requestScope.error}"><div>${requestScope.error}</div></c:if>
<jsp:include page="/views/Footer.jsp"></jsp:include>