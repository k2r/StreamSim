<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<h1 class="sectionTitle">Command and Options</h1>

<form action="/streamsim/emitter" method="POST">
<div class="instruction">For store and replay commands, please fulfill database information with credentials:</div>
<table class="form">
	<tr>
        <td class="label"><label>Command</label>:</td> 
        <td class="input"><select name="command" >
           <option value="PLAY">play the stream</option>
           <option value="RECORD">record the stream</option>
           <option value="REPLAY">replay a recorded stream</option>
       </select></td>
    </tr>
    
    <tr>
        <td class="label"><label>Emission frequency</label>:</td> 
        <td class="input"><input type="number" name="frequency" min="1" max="10"></td>
    </tr>
	
	<tr>
        <td class="label"><label>Database host</label>:</td>
        <td class="input"><input type="text" name="dbhost" /></td>
    </tr>
    
    <tr>
        <td class="label"><label>User</label>:</td> 
        <td class="input"><input type="text" name="dbuser" /></td>
    </tr>
    
    <tr>
        <td class="label"><label>Password</label>:</td>
        <td class="input"><input type="password" name="dbpwd" /></td>
    </tr>
    
    <tr>
    	<td class="submit"><input class="button" type="submit" value="generate" name="generate"/></td>
    </tr>
</table>
</form>
