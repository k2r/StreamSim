<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<div class="sectionTitle">Stream live control</div>
<form action="/streamsim/livecontrol" method="POST">
<table>

<tr>
	<td><label>Select a stream output rate</label></td>
	<td><input class="rangeSelect" type="range" value="10" min="1" max="500" step="10" name="rate"></input></td>
</tr>

<tr>
	<td><input type="hidden" name="command" value="PLAY" /></td>
	<td><input type="hidden" name="frequency" value="1" /></td>
</tr>

<tr>
	<td><input type="submit" value="Set rate and generate" name="generate"></input></td>
</tr>
</table>
</form>    