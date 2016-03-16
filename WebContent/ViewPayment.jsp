<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Payment</title>
<link rel="stylesheet" href="css/MainStyle.css">
</head>
	<header> <a href="main.jsp"><p>
				<img src="resources/MainLogo.jpg" alt="logo" />
			 </a>
	</header>
<body>
	<center><font size="7">View Payment</font></center>
	<c:set var="Payment" scope="request" value="${requestScope.Payment}"/>
		<%@ include file="Payment.jsp"%>
	<center>
	<table>	
		<tr>
			<c:if test="${requestScope.Payment.isPaid == false}">
			<td>
				<form action="PayPaymentEventServlet" method="post">
					<input type="hidden" name="currPayment" value="${requestScope.Payment.getUniqueIDForPayment()}">
					<input type="submit" value="Pay" style="height:20px; width:102px">
				</form>
			</td>
			<td>
				<form action="EditPaymentServlet" method="get">
					<input type="hidden" name="currPayment" value="${requestScope.Payment.getUniqueIDForPayment()}">	
					<input type="submit" value="Edit" style="height:20px; width:102px">
				</form>	
			</td>
			</c:if>
			<td>
				<form action="DeletePaymentEventServlet" method="post">
					<input type="hidden" name="currPayment" value="${requestScope.Payment.getUniqueIDForPayment()}">	
					<input type="submit" value="Delete" style="height:20px; width:102px">
				</form>	
			</td>
		</tr>
	</table>
	</center>
	<%@ include file="MenuMap.jsp"%>
</body>
</html>