<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/logout-button.css">
	<title>Insert title here</title>
</head>
<body>
		<div class='position'>
			<form action="LogOutServlet" method="post">
				<input type="submit" class='button' value="Log Out">
			</form>
		</div> 
	<table border="2" bordercolor= "cyan" cellpadding="10">
		<caption><p>Menu</p></caption>
		<tr>		
		<td><a href="ToDo.jsp"><font color="black"><p>TO DO LIST</p></font></a></td>
		</tr>
		<tr>
		<td><a href="MyCallendar.jsp"><font color="black"><p>My Callendar</p></font></a></td>
		</tr>
		<tr>
		<td><a href="MyPayments.jsp"><font color="black"><p>My Payments</p></font></a></td>
		</tr>
		<tr>
		<td><a href="MyAccounts.jsp"><font color="black"><p>My Accounts<br/></br></br>List</p></font></a></td>
		</tr>
		<tr>
		<td><form action="ShowShoppingListServlet" method="post"><a href="ShowShoppingListServlet"><font color="black"><p>My Shopping<br/></br></br>List</p></font></a></form></td>
		</tr>
	</table>
</body>
</html>