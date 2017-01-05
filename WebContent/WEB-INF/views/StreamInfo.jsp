<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<h1>Stream info</h1>

<table>
	<tr><td>Attribute</td><td>Type</td></tr>
	<c:forEach begin="0" end="${stream.nbAttrs}" var="i">
	<tr><td><c:out value="${stream.attrNames[i]}"></c:out></td><td><c:out value="${stream.attrTypes[i]}"></c:out></td></tr>
	</c:forEach>
</table>
