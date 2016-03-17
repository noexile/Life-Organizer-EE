<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Entry</title>
<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	
		<table border="3" bordercolor= "cyan" align="center">
			
			<tr>
				<td width="330px" height="40"><font size="5">Title</font></td>
				<td width="330px" height="33"><font size="5">Amount</font></td>
			</tr>
			<tr>
				<td height="28px"><font size="3"><c:out value="${requestScope.Entry.name}"/></font></td>
				<td><font size="3"><c:out value="${requestScope.Entry.amount}"/></font></td>
			</tr>
	</table>
</body>
</html>