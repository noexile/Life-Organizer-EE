<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body background="resources/Logo.jpg">
	<section class="container">
		<div class="login">  
		<% if(request.getAttribute("user") == "error") { %> 
			<h4><font color="red">Wrong username or password.</font></h4>			
			<%
		}%>
			<h1>Welcome to Life Organizer</h1>
			<form method="post" action="LoginServlet">
				<p><input type="text" name="user" value="" placeholder="Username or Email"></p>
				<p><input type="password" name="pass" value="" placeholder="Password"></p>
				<p class="remember_me">
				<label><input type="checkbox" name="remember_me" id="remember_me">	Remember me on this computer</label>
				</p>
				<p class="submit"><input type="submit" value="Login"></p>
				<p><a href="SignUp.html">New to Life Organizer ? Click here to Register</a></p>
			</form>
		</div>
	</section>
</body>
</html>