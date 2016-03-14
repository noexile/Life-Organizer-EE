<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Payment</title>
<link rel="stylesheet" href="css/MainStyle.css">
</head>
	<header> <a href="main.jsp"><p>
				<img src="resources/MainLogo.jpg" alt="logo" />
			 </a>
	</header>
<body>
	<center><font size="7">View Payment</font></center>
		<%@ include file="Payment.jsp"%>
	<center>
		<form>
			<input type="submit" value="Pay" style="height:20px; width:102px">
			<form>	
				<input type="submit" value="Edit" style="height:20px; width:102px">
				<form>	
				<input type="submit" value="Delete" style="height:20px; width:102px">
				</form>
			</form>
		</form>
	</center>
	<%@ include file="MenuMap.jsp"%>
</body>
</html>