<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<h1>Variation info</h1>

<table class="infoTab">
	<tr><td>Variation</td><td>Duration</td></tr>
	<c:forEach items="${stream.variations}" var="variation">
	<tr><td>${variation}</td><td>${stream.variations[variation]}</td></tr>
	</c:forEach>
</table>
