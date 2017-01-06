<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="listener" class="beans.ListenerBean" scope="session" />
    
<h1>Stream to listen</h1>

<form action="/streamsim/listener" method="POST">
    <p>
        <label>Host name</label>: <input type="text" name="host" value="${!empty listener.host ? listener.host : ''}"/>
    </p>
    
	<p>
        <label>Emission port</label>: <input type="text" name="port" value="${!empty listener.port ? listener.port : ''}"/>
    </p>
    
    <p>
        <label>Stream type</label>: 
        <select name="type" >
           <option value="BASIC" ${listener.type == "BASIC" ? 'selected="selected"' : ''}>Basic</option>
           <option value="STREAMSIM" ${listener.type == "STREAMSIM" ? 'selected="selected"' : ''}>StreamSim</option>
       </select>
    </p>
    
    <p>
        <label>Resource</label>: <input type="text" name="resource" value="${!empty listener.resource ? listener.resource : ''}"/>
    </p>
    
    <p>
        <label>Number of items to show</label>: <input type="number" name="nbItems" min="1" max="50" value="${!empty listener.nbItems ? listener.nbItems : '1'}"/>
    </p>
    
    <input type="submit" value="listen" name="listen"/>
</form>

<form action="/streamsim/listener" method="POST">
	<input type="submit" value="Stop listening" name="stopListen"/>
</form>