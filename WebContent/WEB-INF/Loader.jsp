<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="stream" class="beans.ElementStreamBean" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to the StreamSim Loader</title>
</head>
<body>

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
    <input type="submit" value="Load" />
</form>

</body>
</html>