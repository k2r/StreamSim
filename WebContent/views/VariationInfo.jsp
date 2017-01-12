<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<h1 class="sectionTitle">Variation info</h1>

<table class="infoTab">
	<tr><td class="infoHeader">Variation</td><td class="infoHeader">Duration</td></tr>
	<c:forEach items="${stream.variations}" var="variation">
	<tr><td class="infoCell">${variation.key}</td><td class="infoCell">${variation.value}</td></tr>
	</c:forEach>
</table>
