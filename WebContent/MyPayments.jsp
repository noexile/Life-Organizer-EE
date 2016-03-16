<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
	<title>MyPayments</title>
	<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	<div class="container">
		<header>
			<br/><br/><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a><br/><br/><br/>
			<section class="right_side">
			<form action="ShowInsidePaymentEventServlet" method="post">For Date&nbsp&nbsp&nbsp&nbspStatus<br>
					<input type="date" name="dateForShow" autocomplete="on">
					<select name="status">
  					<option value="paid">Paid</option>
  					<option value="topay">ToPay</option>
  					<option value="all">All</option>
  				</select>
  				<input type="submit" value="Show">
			</form>
			<table  frame="box">
				<caption>FOR <c:out value="${requestScope.dateForShow}"/></caption>
				<c:set var="message" scope="request" value="${requestScope.haveEventForThisDate}"/>
				<c:if test="${message == true}">
				<c:forEach var="item" items="${requestScope.eventsToShow}">
						<c:set var="Payment" value="${item}" scope="request"/>
						<tr>
							<td>
								<%@ include file="Payment.jsp" %>
							</td>
							<td>
								<form action="ViewPaymentServlet" method="post">
									<input type="hidden" value="${item.getUniqueIDForPayment()}" name="currPayment">
									<input type="submit" value="View" style="height:60px">
								</form>
							</td>
						</tr>
				</c:forEach>
				</c:if>
				<c:if test="${message == false}">
				<font size="6" color="red">You do not have Payments for this date</font>
				</c:if>
			</table>
				<center><a href="AddPayment.jsp"><button>Add Task</button></a></center>
			</section>
			<nav>
				<%@ include file="MenuMap.jsp" %>	
			</nav>
		
		</header>
		<aside>
		</aside>
	</div>	
</body>
</html>