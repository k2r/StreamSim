<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="listener" class="beans.ListenerBean" scope="session" />

<jsp:include page="/views/Header.jsp"></jsp:include>

<jsp:include page="/views/ListenForm.jsp"></jsp:include>
<c:if test="${!empty param.listen}"><jsp:include page="/views/ItemViewer.jsp"></jsp:include></c:if>

<c:if test="${!empty requestScope.start}"><div>${requestScope.start}</div></c:if>
<c:if test="${!empty requestScope.stop}"><div>${requestScope.stop}</div></c:if>
<c:if test="${!empty requestScope.set}"><div>${requestScope.set}</div></c:if>

<jsp:include page="/views/Footer.jsp"></jsp:include>