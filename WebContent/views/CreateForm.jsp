<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="sectionTitle">Create a new stream</h1>

<form action="/StreamSim/create" method="POST">
<table class="form">
	<tr>
		<td class="label"><label>Stream name</label>:</td>
		<td class="input"><input type="text" name="streamname" value="${!empty creator.streamName ? creator.streamName : ''}"/></td>
	</tr>
	<tr>
		<td class="label"><label>Number of attributes</label>:</td>
		<td class="input"><input type="text" name="nbattrs" value="${!empty creator.nbAttributes ? creator.nbAttributes : ''}"/></td>
	</tr>
	<tr>
		<td class=submit><input class="button" type="submit" value="Define" name="define" /></td>
	</tr>
</table>
</form>