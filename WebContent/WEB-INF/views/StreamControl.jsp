<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<table>
<tr>
	<td class="control"><form action="/streamsim/emitter" method="POST"><input type="submit" value="Stop the emission" name="stop"></form></td>
	<td class="control"><form action="/streamsim/emitter" method="POST"><input type="submit" value="Restart the emission" name="restart"></form></td>
</tr>
</table>