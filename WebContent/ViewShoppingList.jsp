<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="ErrorPage.jsp" %>
<!DOCTYPE html">
<html>
<head>
		<%
			if(request.getSession().getAttribute("loggedUserManager") == null){		
				response.sendRedirect("HomePage.jsp");
			}
		%>
<title>View ShoppingList</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
		<header> <a href="main.jsp"><p>
				<img src="resources/MainLogo.jpg" alt="logo" />
			 </a>
		</header>
		<div class="right_side2">
				<p>Shopping List:</p>  <h2><c:out value="${ requestScope.ShoppingList.title}"/></h2>
		<table>
			<center><caption><h4>Products:</h4></caption></center>
			<c:forEach var="item" items="${requestScope.ShoppingList.shoppingEntries}">
						<c:set var="Entry" value="${item}" scope="request"/>
						<tr>
							<td>
								<%@ include file="Entry.jsp" %>
							</td>
							<td>
								<form action="DeleteEntryServlet" method="post">
									<input type="hidden" value="${item.uniqueID}" name="currEntry">
									<input type="hidden" name="idList" value="${requestScope.ShoppingList.getUniqueIDForPayment()}">
									<c:if test="${requestScope.ShoppingList.isPaid == false}" >
									<input type="submit" value="Delete" style="height:30px">
									</c:if>
								</form>
							</td>
						</tr>
				</c:forEach>
		</table>
		<c:if test="${requestScope.ShoppingList.isPaid == false}" >
				<form action="PayShoppingListServlet" method="get">
					<input type="hidden" name="idList" value="${requestScope.ShoppingList.getUniqueIDForPayment()}">
					<input type="submit" value="  Pay List  " >
				</form>
		</c:if>
				<form action="DeleteShoppingListServlet" method="post">
					<input type="hidden" name="idList" value="${requestScope.ShoppingList.getUniqueIDForPayment()}">
					<input type="submit" value="Delete List" ><br><br><br>
				</form>
		<c:if test="${requestScope.ShoppingList.isPaid == false}" >
		<form action="AddEntryToListServlet" method="post">		
					1.ProductName
				<input type="text" name="nameEntry" value="ProductName"><br>
					2.Amount
				<input type="number" name="amount" step="0.01" min="0" value="${0}">
				<br>
				<input type="hidden" name="idList" value="${requestScope.ShoppingList.getUniqueIDForPayment()}">
				<input type="submit" value="Add to list" >
		</form>	
		</c:if>
		</div>
		<%@ include file="MenuMap.jsp"%>		
</body>
</html>