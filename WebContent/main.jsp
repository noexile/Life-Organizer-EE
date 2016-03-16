<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.user.User"%>
<%@ page import="model.user.UserManager"%>
<%
	UserManager manager = (UserManager) session.getAttribute("loggedUserManager");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>Main</title>
<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	<header>
	 <a href="main.jsp"><p>
			<img src="resources/MainLogo.jpg" alt="logo" /></a>
	<big>Hello <%=(manager == null ? "" : manager.getUserName())%></big><br>
	<center><font size="6">Your Balance: <%=(manager == null ? "" : manager.getMoney())%></font></center>
		</p>
	<table border="2" bordercolor="cyan" cellpadding="10">
		<div class="test">
			<p id="demo"></p>
			<script>
				var d = new Date();
				document.getElementById("demo").innerHTML = d.toDateString();
			</script>
			<br /> <br /> <br /> <br />
			<form action="ShowInsidePaymentEventServlet" method="post">
				<input type="hidden" name="dateForShow" value="${LocalDate.now()}">
				<input type="hidden" name="status" value="all"> 
				<input type="submit" value="Show tasks for today"
					style="height: 50px; width: 500px"> 
			</form>
			</br> </br> <a
					href="AddPayment.jsp"><input type="button" value="ADD NEW PAYMENT"
					style="height: 50px; width: 500px"></a>
		</div>
		<%@ include file="MenuMap.jsp"%>
	</table>
	</header>

</body>
</html>