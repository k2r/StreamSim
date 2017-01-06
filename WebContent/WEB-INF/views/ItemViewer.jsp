<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="listener" class="beans.ListenerBean" scope="session" />

<h1>Last Records</h1>

<table>
	<tr><td>Records</td></tr>
	<c:forEach begin="0" end="${listener.nbItems}" var="i">
	<tr><td>${listener.items[i]}</td></tr>
	</c:forEach>
</table>
