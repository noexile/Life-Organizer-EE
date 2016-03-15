<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
				<td><font size="2"><c:out value="${requestScope.Payment.isPaid}"/></font></b></td>
				<td><font size="2"><c:out value="${requestScope.Payment.isIncome}"/></font></b></td>
				<td><font size="2"><c:out value="${requestScope.Payment.dateTime}"/></font></b></td>
			</tr>
	</table>
</body>
</html>