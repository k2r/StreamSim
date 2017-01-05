<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<h1>Command and Options</h1>

<form action="/streamsim/emitter" method="POST">
	<p>
        <label>Command</label> : 
        <select name="command" >
           <option value="PLAY">play the stream</option>
           <option value="RECORD">record the stream</option>
           <option value="REPLAY">replay a recorded stream</option>
       </select>
    </p>
    
    <p>
        <label>Emission frequency</label> : <input type="number" name="frequency" min="1" max="10">
    </p><br></br>
    
    <p>For store and replay commands, please fulfill database information:</p>
	
	<p>
        <label>Database host</label> : <input type="text" name="dbhost" />
    </p>
    
    <p>Credentials</p>
    
    <p>
        <label>User</label> : <input type="text" name="dbuser" />
    </p>
    
    <p>
        <label>Password</label> : <input type="password" name="dbpwd" />
    </p>
    
    <input type="submit" value="generate" name="generate"/>
</form>
