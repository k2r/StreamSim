<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="listener" class="beans.ListenerBean" scope="session" />
    
<h1 class="sectionTitle">Stream to listen</h1>

<table class="form">
<tr>
<td>
<form action="/StreamSim/listener" method="POST">
	<table>
    <tr>
        <td class="label"><label>Host name</label>:</td>
        <td><input type="text" name="host" value="${!empty listener.host ? listener.host : ''}"/></td>
    </tr>
    
	<tr>
        <td class="label"><label>Emission port</label>:</td>
        <td class="input"><input type="text" name="port" value="${!empty listener.port ? listener.port : ''}"/></td>
    </tr>
    
    <tr>
        <td class="label"><label>Stream type</label>:</td> 
        <td class="input"><select name="type" >
           <option value="STREAMSIM" ${listener.type == "STREAMSIM" ? 'selected="selected"' : ''}>StreamSim</option>
           <option value="RAW" ${listener.type == "RAW" ? 'selected="selected"' : ''}>Raw stream</option>
       </select></td>
    </tr>
    
    <tr>
        <td class="label"><label>Resource</label>:</td>
        <td class="input"><input type="text" name="resource" value="${!empty listener.resource ? listener.resource : ''}"/></td>
    </tr>
    
    <tr>
        <td class="label"><label>Number of items to show</label>:</td> 
        <td class="input"><input type="number" name="nbItems" min="1" max="50" value="${!empty listener.nbItems ? listener.nbItems : '1'}"/></td>
    </tr>
    
    <tr>
    <td class="submit"><input class="button" type="submit" value="listen" name="listen"/></td>
    </tr>
    </table>
</form>
</td>
</tr>
<tr>
<td>
<form action="/StreamSim/listener" method="POST">
	<input class="buttonStop" type="submit" value="Stop listening" name="stopListen"/>
</form>
</td>
</tr>
</table>