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
	
		<table border="2" bordercolor= "cyan" frame="" align="center">
			
			<tr>
				<td><font size="5">Title</font></b></td>
				<td><font size="5">Description</font></b></td>
				<td><font size="5">Amount</font></b></td>
				<td><font size="5">Status</font></b></td>
				<td><font size="5">Type</font></b></td>
				<td><font size="5">For Date</font></b></td>
			</tr>
			<tr>
				<td><font size="2"><c:out value="${requestScope.Payment.title}"/></font></b></td>
				<td><font size="2"><c:out value="${requestScope.Payment.description}"/></font></b></td>
				<td><font size="2"><c:out value="${requestScope.Payment.amount}"/></font></b></td>
				<td><font size="2">
									<c:choose>
										<c:when test="${requestScope.Payment.isPaid == true}">Paid</c:when>
										<c:otherwise>To Pay</c:otherwise>
									</c:choose>
					</font></b></td>
				<td><font size="2">
									<c:choose>
										<c:when test="${requestScope.Payment.isIncome == true}"><font color="#00FF00">Earning</font></c:when>
										<c:otherwise><font color="red">Expense</font></c:otherwise>
									</c:choose>
					</font></b></td>
				<td><font size="2"><c:out value="${requestScope.Payment.dateTime}"/></font></b></td>
			</tr>
	</table>
</body>
</html>