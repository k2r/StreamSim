<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />

<c:if test="${empty requestScope.stop}">

<h1 class="sectionTitle">Variation info</h1>

<script type="text/javascript">
<% 
ArrayList<Double> times = stream.getVarTimestamps();
ArrayList<Double> vals = stream.getVarRates();
int nbVals = vals.size();
%>

var timestamps = [];
var values = [];
if(<%= times != null && vals != null%>){
	<% for(int i = 0; i < nbVals; i++){%>
		timestamps.push(<%=times.get(i)%>);
		values.push(<%=vals.get(i)%>);
	<%}%>
}

</script>

<script type="text/javascript" src="js/googlechart.js"></script>

<div id="chart_div"></div>
</c:if>
