<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<title>New ToDo</title>
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body>
	</br></br><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a>
		<section class="container">
			<c:set var="ToDoErrorMessage" scope="session" value="${sessionScope.ToDoErrorMessage}"/>
				<c:if test="${ToDoErrorMessage != null && ToDoErrorMessage != ' '}">
				<font color="red"><c:out value="${ToDoErrorMessage}"/></font>
			</c:if>
			<form method="post" action="AddToDoServlet">
			<table class="right" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr>
					<td>
						ToDo Name
						</br>
						<input type="text" name="name" style="height:20px; width:247px">
					</td>
				</tr>
				<tr>
					<td>
						<select name="term">
							<option value="Short">Short Term</option>
							<option value="Mid">Mid Term</option>
							<option value="Long">Long Term</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<a>
							Description:
							</br>
							<textarea  name="desc" rows="4" cols="33"></textarea>
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="Save">
						<a href="ToDo.jsp"><button>Cancel</button></a> 
					</td>
				</tr>
			</table>
			</form>
		</section>
	</body>
</html>