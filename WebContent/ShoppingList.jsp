<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Payment</title>
<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	
		<table border="2" bordercolor= "cyan" align="center">
			
			<tr>
				<td><font size="5">Title</font></td>
				<td><font size="5">Amount</font></td>
				<td><font size="5">Date</font></td>
				<td><font size="5">Status</font></td>
			</tr>
			<tr>
				<td><font size="2"><c:out value="${requestScope.ShoppingList.title}"/></font></td>
				<td><font size="2"><c:out value="${requestScope.ShoppingList.amount}"/></font></td>
				<td><font size="2"><c:out value="${requestScope.ShoppingList.dateTime}"/></font></td>
				<td><font size="2">
									<c:choose>
										<c:when test="${requestScope.ShoppingList.isPaid == true}">Paid</c:when>
										<c:otherwise><font color="yellow">To Pay</font></c:otherwise>
									</c:choose>
					</font>
				</td>
			</tr>
	</table>
</body>
</html>