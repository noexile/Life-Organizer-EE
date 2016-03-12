<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Sign up</title>
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<section class="container">
	<form method="post" action="SignUpServlet">
		<table class="right" frame="box" bordercolor="cyan" class="tableSignUp">
			<tr>
				<td><img src="resources/MainLogo.jpg" alt="logo" /></td>
			</tr>
			<tr>
				<td><a>Username:</br>
				<input type="text" name="user" style="height: 20px; width: 247px"></a></td>
			</tr>
			<tr>
				<td><a>Password:</br>
				<input type="password" name="pass1" style="height: 20px; width: 247px"></a></td>
			</tr>
			<tr>
				<td><a>Re-Password:</br>
				<input type="password" name="pass2" style="height: 20px; width: 247px"></a></td>
			</tr>
			<tr>
				<td><a>e-mail:</br>
				<input type="text" name="email" style="height: 20px; width: 247px"></a></td>
			</tr>
			<tr>
				<td><a href="main.jsp"><button>Submit</button></a></td>
			</tr>
		</table>
	</form>
	</section>
</body>
</html>
</html>