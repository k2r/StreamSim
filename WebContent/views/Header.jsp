<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>StreamSim Web Application</title>
</head>
<body>

<table class="header">
	<tr>
		<td class="headerMsg">StreamSim Web Application</td>
		<td><form action="/streamsim/manager" method="POST"><input class="headerOpt" type="submit" value="Generate a new stream" name="generate"></form></td>
		<td><form action="/streamsim/manager" method="POST"><input class="headerOpt" type="submit" value="Listen to a stream" name="listen"></form></td>
	</tr>
</table>
<div class="content">