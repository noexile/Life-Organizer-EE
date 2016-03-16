<%@ page language="java" contentType="text/html; charset=UTF-81"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
This application tests your connection to the dev.ittalents.bg mysql server.
It creates a table USERS in a database and inserts 6 entries:
<table border=2>
	<tr>
		<th>id</th>
		<th>username</th>
		<th>password</th>
	</tr>
	<tr>
		<td>1</td>
		<td>krasi</td>
		<td>123</td>
	</tr>
	<tr>
		<td>2</td>
		<td>niki</td>
		<td>234</td>
	</tr>
	<tr>
		<td>3</td>
		<td>dido</td>
		<td>345</td>
	</tr>
	<tr>
		<td>4</td>
		<td>simo</td>
		<td>456</td>
	</tr>
	<tr>
		<td>5</td>
		<td>vasko</td>
		<td>567</td>
	</tr>
	<tr>
		<td>6</td>
		<td>doni</td>
		<td>678</td>
	</tr>
</table>

The provided login form below asks you to input username and password. The application provides the ID stored in the DB for this user.

<form action="LoginServlet" method="post">
<br>Username<input type="text" name="user">
<br>Password<input type="password" name="pass">
<br><input type="submit" value="Login">

<%if(request.getAttribute("id") != null) {
	if((Integer)request.getAttribute("id") == -1) {%>
<br>The user is not registerred in our database.
	<%}else{ %>
<br>The user`s id is <%= request.getAttribute("id")%>
	<%}
} %>
</form>
</body>
</html>