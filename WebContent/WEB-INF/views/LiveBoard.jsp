<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<div>Stream live control</div>
<form action="/streamsim/livecontrol" method="POST">
<label>Select a stream output rate</label><input type="range" value="10" min="1" max="500" step="10" name="rate"></input>
<input type="hidden" name="command" value="PLAY" />
<input type="hidden" name="frequency" value="1" />
<input type="submit" value="Set rate and generate" name="generate"></input>
</form>    