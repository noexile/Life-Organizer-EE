<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ToDo</title>
</head>
<body>
	<table border="2">
		<form action="ToDoModifyServlet" method="post">
			<tr>
				<td>Title:</td>
				<td>Description:</td>
				<td>Term:</td>
			</tr>
			<tr>
				<td><c:out value="${requestScope.todo.getTitle()}"/></td>
				<input type="hidden" name="title" value="${requestScope.todo.getTitle()}">	
				<td><c:out value="${requestScope.todo.getDescription()}"/></td>	
				<input type="hidden" name="description" value="${requestScope.todo.getDescription()}">	
				<td><c:out value="${requestScope.todo.getType()}"/></td>
				<input type="hidden" name="type" value="${requestScope.todo.getType()}">	
				<input type="hidden" name="id" value="${requestScope.todo.getUniqueID()}">	
				<td><input type="submit" value="Edit"></td>
			</tr>
			
		</form>
</table>
</body>
</html>