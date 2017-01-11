<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="sectionTitle">Stream parameters</h1>

<form action="/streamsim/generator" method="POST">
    <table class="form">
    <tr>
        <td><label>Stream name</label>:</td> 
        <td><select name="name" >
			<c:forEach items="${requestScope.schemas}" var="index">
				<option value="${index}">${index}</option>
			</c:forEach>
       </select></td>
    </tr>
    
	<tr>
        <td><label>Emission port</label>:</td>
        <td><input type="text" name="port" /></td>
    </tr>
    
    <tr>
        <td><label>Variation type</label>:</td> 
        <td><select name="variation" >
           <option value="no">No variation</option>
           <option value="linearIncrease">Linear increase</option>
           <option value="scaleIncrease">Scaled increase</option>
           <option value="exponentialIncrease">Exponential increase</option>
           <option value="logarithmicIncrease">Logarithmic increase</option>
           <option value="linearDecrease">Linear decrease</option>
           <option value="scaleDecrease">Scaled decrease</option>
           <option value="exponentialDecrease">Exponential decrease</option>
           <option value="all">All variations</option>
       </select></td>
    </tr>
    
    <tr>
    	<td>
    	<label>
    		<input type="checkbox" value="live" name="live">
  			Control stream rate with the live board
		</label>
		</td>
    </tr>
    
    <tr>
    	<td><input type="submit" value="load" name="load"/></td>
    </tr>
    </table>
</form>
