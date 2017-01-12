<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="sectionTitle">Stream parameters</h1>

<form action="/streamsim/generator" method="POST">
    <table class="form">
    <tr>
        <td class="label"><label>Stream name</label>:</td> 
        <td class="input"><select name="name" >
			<c:forEach items="${requestScope.schemas}" var="index">
				<option value="${index}">${index}</option>
			</c:forEach>
       </select></td>
    </tr>
    
	<tr>
        <td class="label"><label>Emission port</label>:</td>
        <td class="input"><input type="text" name="port" /></td>
    </tr>
    
    <tr>
        <td class="label"><label>Variation type</label>:</td> 
        <td class="input"><select name="variation" >
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
    	<td class="label">
    	<label>Control stream rate with the live board</label>:
    	</td>
    	<td>
    		<input type="checkbox" value="live" name="live">
		</td>
    </tr>
    
    <tr>
    	<td class="submit"><input class="button" type="submit" value="load" name="load"/></td>
    </tr>
    </table>
</form>
