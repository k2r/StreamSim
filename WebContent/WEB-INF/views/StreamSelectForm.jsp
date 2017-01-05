<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<h1>Stream parameters</h1>

<form action="/streamsim/generator" method="POST">
    <p>
        <label>Stream name</label> : <input type="text" name="name" />
    </p>
    
	<p>
        <label>Emission port</label> : <input type="text" name="port" />
    </p>
    
    <p>
        <label>Variation type</label> : 
        <select name="variation" >
           <option value="no">No variation</option>
           <option value="linearIncrease">Linear increase</option>
           <option value="scaleIncrease">Scaled increase</option>
           <option value="exponentialIncrease">Exponential increase</option>
           <option value="logarithmicIncrease">Logarithmic increase</option>
           <option value="linearDecrease">Linear decrease</option>
           <option value="scaleDecrease">Scaled decrease</option>
           <option value="exponentialDecrease">Exponential decrease</option>
           <option value="all">All variations</option>
       </select>
    </p>
    
    <p>
    	<label>
  			<input type="checkbox" value="live" name="live">
  			Control stream rate with the live board
		</label>
    </p>
    
    <input type="submit" value="load" name="load"/>
</form>
