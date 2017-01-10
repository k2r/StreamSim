<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<h1 class="sectionTitle">Command and Options</h1>

<form action="/streamsim/emitter" method="POST">
<table>
	<tr>
        <td><label>Command</label>:</td> 
        <td><select name="command" >
           <option value="PLAY">play the stream</option>
           <option value="RECORD">record the stream</option>
           <option value="REPLAY">replay a recorded stream</option>
       </select></td>
    </tr>
    
    <tr>
        <td><label>Emission frequency</label>:</td> 
        <td><input type="number" name="frequency" min="1" max="10"></td>
    </tr>
    
    <tr>
    	<td>For store and replay commands, please fulfill database information:</td>
    </tr>
	
	<tr>
        <td><label>Database host</label>:</td>
        <td><input type="text" name="dbhost" /></td>
    </tr>
    
    <tr>
    	<td>Credentials</td>
    </tr>
    
    <tr>
        <td><label>User</label>:</td> 
        <td><input type="text" name="dbuser" /></td>
    </tr>
    
    <tr>
        <td><label>Password</label>:</td>
        <td><input type="password" name="dbpwd" /></td>
    </tr>
    
    <tr>
    	<td><input type="submit" value="generate" name="generate"/></td>
    </tr>
</table>
</form>
