<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ page errorPage="ErrorPage.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			if(request.getSession().getAttribute("loggedUserManager") == null){		
				response.sendRedirect("HomePage.jsp");
			}
		%>
		<meta charset="utf-8">
		<title>New Shopping List</title>
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body>
	</br></br><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a>
		
			<table class="right" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr>
					<td>
						<a>
							List Name:
							<br>
							<form action="CreateShoppingListServlet" method="post">
								<input type="text" name="nameList" style="height:20px; width:247px">
								<input type="submit" value="Save">
							</form>
							
						</a>
					</td>
				</tr>
			</table>
		<%@ include file="MenuMap.jsp"%>
	</body>
</html>