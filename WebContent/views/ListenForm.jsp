<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="listener" class="beans.ListenerBean" scope="session" />
    
<h1 class="sectionTitle">Stream to listen</h1>

<div class="form">
<form action="/streamsim/listener" method="POST">
	<table>
    <tr>
        <td><label>Host name</label>:</td>
        <td><input type="text" name="host" value="${!empty listener.host ? listener.host : ''}"/></td>
    </tr>
    
	<tr>
        <td><label>Emission port</label>:</td>
        <td><input type="text" name="port" value="${!empty listener.port ? listener.port : ''}"/></td>
    </tr>
    
    <tr>
        <td><label>Stream type</label>:</td> 
        <td><select name="type" >
           <option value="BASIC" ${listener.type == "BASIC" ? 'selected="selected"' : ''}>Basic</option>
           <option value="STREAMSIM" ${listener.type == "STREAMSIM" ? 'selected="selected"' : ''}>StreamSim</option>
       </select></td>
    </tr>
    
    <tr>
        <td><label>Resource</label>:</td>
        <td><input type="text" name="resource" value="${!empty listener.resource ? listener.resource : ''}"/></td>
    </tr>
    
    <tr>
        <td><label>Number of items to show</label>:</td> 
        <td><input type="number" name="nbItems" min="1" max="50" value="${!empty listener.nbItems ? listener.nbItems : '1'}"/></td>
    </tr>
    
    <tr>
    <td><input type="submit" value="listen" name="listen"/></td>
    </tr>
    </table>
</form>

<form action="/streamsim/listener" method="POST">
	<input type="submit" value="Stop listening" name="stopListen"/>
</form>
</div>