<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
	<title>MyShoppingList</title>
	<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	<header>
			<br/><br/><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a>
		<section class="right_side">
		<br><br><br><br><br><br>
			
				<table  frame="box">
				<caption>Shopping Lists</caption>
				<c:set var="message" scope="request" value="${requestScope.haveShoppingLists}"/>
				<c:if test="${message == true}">
				<c:forEach var="item" items="${requestScope.listsToShow}">
						<c:set var="ShoppingList" value="${item}" scope="request"/>
						<tr>
							<td>
								<%@ include file="ShoppingList.jsp" %>
							</td>
							<td>
								<form action="ShowShoppingListServlet" method="post">
									<input type="hidden" value="${item.getUniqueIDForPayment()}" name="currList">
									<input type="submit" value="View" style="height:60px">
								</form>
							</td>
						</tr>
				</c:forEach>
				</c:if>
				<c:if test="${message == false}">
				<font size="6" color="red">You do not created Shopping Lists</font>
				</c:if>
			</table>
			
		</secion>
		</header>
		<%@ include file="MenuMap.jsp" %>	
		<aside>
		</aside>
	</div>	
</body>
</html>