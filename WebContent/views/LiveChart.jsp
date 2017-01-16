<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<c:if test="${!empty requestScope.timestamps && !empty requestScope.values && empty paramScope.stop}">
<script type="text/javascript">
<% 
ArrayList<Long> times = (ArrayList<Long>) request.getAttribute("timestamps");
ArrayList<Integer> vals = (ArrayList<Integer>) request.getAttribute("values");
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
