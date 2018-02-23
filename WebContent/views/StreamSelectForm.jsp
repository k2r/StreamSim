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
        <td class="label"><label>Messaging service</label>:</td>
        <td class="input"><select name="network">
        	<option value="rmi">RPC (RMI)</option>
        	<option value="kafka">Apache Kafka</option>
        </select></td>
    </tr>
    
    <tr>
        <td class="label"><label>Server hostname</label>:</td>
        <td class="input"><input type="text" name="host" /></td>
    </tr>
    
	<tr>
        <td class="label"><label>Emission port</label>:</td>
        <td class="input"><input type="text" name="port" /></td>
    </tr>
    
    <tr>
        <td class="label"><label>Variation type</label>:</td> 
        <td class="input"><select name="variation" >
           <option value="no">Uniform no variation</option>
           <option value="linearIncrease">Uniform linear increase</option>
           <option value="scaleIncrease">Uniform scaled increase</option>
           <option value="exponentialIncrease">Uniform exponential increase</option>
           <option value="logarithmicIncrease">Uniform logarithmic increase</option>
           <option value="linearDecrease">Uniform linear decrease</option>
           <option value="scaleDecrease">Uniform scaled decrease</option>
           <option value="exponentialDecrease">Uniform exponential decrease</option>
           <option value="all">Uniform all variations</option>
           <option value="zipf05">Zipf-0.5 no variation</option>
           <option value="zipf1">Zipf-1 no variation</option>
           <option value="zipf15">Zipf-1.5 no variation</option>
           <option value="zipf2">Zipf-2 no variation</option>
           <option value="testStd">Uniform increase/decrease</option>
           <option value="testZipf05">Zipf-0.5 increase/decrease</option>
           <option value="testZipf15">Zipf-1.5 increase/decrease</option>
           <option value="stream1">Progressive increase/decrease</option>
           <option value="stream2">Erratic increase/decrease</option>
           <option value="stream3">Constant rate</option>
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
