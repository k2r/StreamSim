<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StreamSim Web Application</title>
</head>
<body>

<table>
	<tr>
		<td>Welcome on the StreamSim Web Application</td>
		<td><form action="/streamsim/" method="POST"><input type="submit" value="Generate a new stream" name="generate"></form></td>
		<td><form action="/streamsim/" method="POST"><input type="submit" value="Listen to a stream" name="listen"></form></td>
	</tr>
</table>
