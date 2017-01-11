<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<h1 class="sectionTitle">Stream info</h1>

<table class="infoTab">
	<tr><td>Attribute</td><td>Type</td></tr>
	<c:forEach begin="0" end="${stream.nbAttrs}" var="i">
	<tr><td>${stream.attrNames[i]}</td><td>${stream.attrTypes[i]}</td></tr>
	</c:forEach>
</table>
