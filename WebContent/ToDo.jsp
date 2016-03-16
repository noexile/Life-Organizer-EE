<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>ToDoList</title>
<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	<header> <br />
	<br />
	<a href="main.jsp"><img src="resources/MainLogo.jpg" alt="logo" /></a>
	<br />
	<br />
	<br />

	<section class="right_side">
	<table class="tasksSize" frame="box" bordercolor="cyan" class="tableSignUp" align="right">
			<c:forEach var="item" items="${sessionScope.todos}">
			<tr>
				<c:set var="todo" value="${item}" scope="request"/>
				<td><%@ include file="ToDoRow.jsp" %></td>
			</tr>
		</td>
	</c:forEach>
	</table>
	<a href="AddToDo.jsp"><button>Add ToDo</button></a> </section> <nav> <%@ include
		file="MenuMap.jsp"%> </nav> </header>
	<aside> </aside>
	</div>
</body>
</html>