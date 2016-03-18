<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit JSP</title>
</head>
<body>
	<section class="container">
	<form action="SaveToDoServlet" method="post">
		<table class="right" frame="box" bordercolor= "cyan" class="tableSignUp">
			<tr>
				<td>
					Title:
				</td>
				<td>
					<input type="text" name="title" value="<%= request.getParameter("title") %>">
				</td>
			</tr>
			<tr>
				<td>
					Description:
				</td>
				<td>
					<input type="text" name="description" value="<%= request.getParameter("description") %>">
				</td>
			</tr>
			<tr>
				<td>
					Type:
				</td>
				<td>
					<select name="type">
						<option <% if(request.getParameter("type").equals("Short")) {
							%> selected <%
						} %> value="Short">Short Term</option>
						
						<option <% if(request.getParameter("type").equals("Mid")) {
							%> selected <%
						} %> value="Mid">Mid Term</option>
						
						<option <% if(request.getParameter("type").equals("Long")) {
							%> selected <%
						} %> value="Long">Long Term</option>
					</select>
				</td>
			</tr>
			<tr>
				
					<input type="hidden" name="title" value="${requestScope.todo.getTitle()}">	
					<input type="hidden" name="description" value="${requestScope.todo.getDescription()}">	
					<input type="hidden" name="type" value="${requestScope.todo.getType()}">		
					<input type="hidden" name="id" value="${requestScope.todo.getUniqueID()}">
					<td></td>
					<td><input type="submit" value="Save"></td>
				</form>
			</tr>
			<tr>
				<form action="DeleteToDoServlet" method="post">
					<input type="hidden" name="id" value="${requestScope.todo.getUniqueID()}">	
					<td></td>
					<td><input type="submit" value="Delete"></td>
				</form>
			</tr>
		</table>
	</section>
</body>
</html>