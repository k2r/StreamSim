<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<h1 class="sectionTitle">Stream info</h1>

<table class="infoTab">
	<tr><td class="infoHeader">Attribute</td><td class="infoHeader">Type</td></tr>
	<c:forEach begin="0" end="${stream.nbAttrs}" var="i">
	<tr><td class="infoCell">${stream.attrNames[i]}</td><td class="infoCell">${stream.attrTypes[i]}</td></tr>
	</c:forEach>
</table>
