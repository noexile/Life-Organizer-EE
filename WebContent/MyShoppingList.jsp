<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
			<div class="Shop">
			<p>Shopping List</p>
			1.ProductName
			<input type="text" value="ProductName"></br>
			2.Amount
			<input type="Amount" value="Amount"></br>
			<input type="submit" value="Add to list" >
			<input type="submit" value="Create List" >
			</div>
			<nav>
				<%@ include file="MenuMap.jsp" %>	
			</nav>		
		</header>
		<aside>
		</aside>
	</div>	
</body>
</html>