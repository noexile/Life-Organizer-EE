<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.user.User" %>
<% User user = (User) session.getAttribute("loggedUser"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>Main</title>
	<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
<header>
	<a href="Main.html"><p><img src = "resources/MainLogo.jpg" alt="logo" /></a><big>Hello <%= user.getUserName() %></big></p>
	<table border="2" bordercolor= "cyan" cellpadding="10">
	<div class="test">
		<p id="demo"></p>
		<script>
		var d = new Date();
		document.getElementById("demo").innerHTML = d.toDateString();
		</script>
		<br/>
		<br/>
		<br/>
		<br/>
		<form action="" method="add">
			<input type="button" value="Show tasks for today" style="height:50px; width:500px">
			</br>
			</br>
			<a href="AddTask.html"><input type="button" value="ADD NEW TASK" style="height:50px; width:500px"></a>
			
		</form>
</div>
		<caption><p>Menu</p></caption>
				<tr>		
				<td><a href="ToDo.html"><font color="black"><p>TO DO LIST</p></font></a></td>
				</tr>
				<tr>
				<td><a href="MyCallendar.html"><font color="black"><p>My Callendar</p></font></a></td>
				</tr>
				<tr>
				<td><a href="Tasks.html"><font color="black"><p>My Tasks</p></font></a></td>
				</tr>
				<tr>
				<td><a href="MyAccounts.html"><font color="black"><p>My Accounts</p></font></a></td>
				</tr>
				<tr>
				<td><a href="MyShoppingList.html"><font color="black"><p>My Shopping<br/></br></br>List</p></font></a></td>
				</tr>
			
	</table>
</header>
	
</body>
</html>