<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Sign up</title>
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body>
		<section class="container">
			<table class="right" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr>
					<td>
						<img src = "resources/MainLogo.jpg" alt="logo" />
							<c:set var="ErrorMessage" scope="session" value="${sessionScope.ErrorMessage}"/>
								<c:if test="${ErrorMessage != null && ErrorMessage != ' '}">
   								<font color="red"><c:out value="${ErrorMessage}"/></font>
							</c:if>
					</td>
				</tr>
				<form action="SignUpServlet" method="post">
					<tr>
						<td>
							<a>Username:</br><input type="text" name="username" value="${requestScope.name}" style="height:20px; width:247px"></a>
						</td>
					</tr>
					<tr>
						<td>
							<a>Password:</br><input type="password" name="password" style="height:20px; width:247px"></a>
						</td>
					</tr>
					<tr>
						<td>
							<a>Re-Enter Password:</br><input type="password" name="repassword" style="height:20px; width:247px"></a>
						</td>
					</tr>
					<tr>
						<td>
							<a>e-mail:</br><input type="text" name="email" value="${requestScope.ema}" style="height:20px; width:247px"></a>
						</td>
					</tr>
					<tr>
						<td>
							<input type="submit" value="Register" name="Register">
							<a href="HomePage.jsp"><input type="button" value="Back" style="height:20px; width:74px"></a>
						</td>
					</tr>
				</form>
			</table>
		</section>
	</body>
</html>