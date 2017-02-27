<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />
    
<jsp:include page="/views/Header.jsp"></jsp:include>

<jsp:include page="/views/StreamInfo.jsp"></jsp:include>
<jsp:include page="/views/VariationInfo.jsp"></jsp:include>

<c:if test="${!empty requestScope.playing}">
<jsp:include page="/views/StreamControl.jsp"></jsp:include>
</c:if>

<c:if test="${!empty requestScope.start}"><div>${requestScope.start}</div></c:if>
<c:if test="${!empty requestScope.stop}"><div>${requestScope.stop}</div></c:if>
<c:if test="${!empty requestScope.restart}"><div>${requestScope.restart}</div></c:if>

<jsp:include page="/views/Footer.jsp"></jsp:include>
