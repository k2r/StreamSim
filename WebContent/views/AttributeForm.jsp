<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="creator" class="beans.CreatorBean" scope="session" />

<script type="text/javascript" src="js/dynamicform.js"></script>

<h1 class="sectionTitle">Attributes</h1>

<div class="instruction">For enumerate attributes, please enter values without spaces and separated by ;</div>

<form action="/streamsim/create" method="POST">
<table class="form">
	<c:forEach begin="1" end="${creator.nbAttributes}" var="i">
		<tr>
			<td>Attribute ${i}:</td>
		</tr>
		<tr>
			<td><label>Name</label>:</td>
			<td><input type="text" name="attrName${i}" /></td>
		</tr>
		<tr>
			<td><label>Type</label>:</td>
			<td class="input"><select id="type${i}" name="attrType${i}" onchange="parameterForm(${i})">
           		<option value="enumerate">enumerate</option>
           		<option value="integer">integer</option>
           		<option value="text">text</option>
       		</select></td>
		</tr>
		<tr id="parameter${i}">
		</tr>
	</c:forEach>
	<tr>
		<td class="submit"><input class="button" type="submit" value="Save the schema" name="save"/></td>
	</tr>
</table>
</form>
