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
				<p>Shopping Lists:</p>
				<table>
					<tr>
						<td>
							 1.
						</td>
						<td>
							nyakakvi shopping listi
						</td>
						<td>
							edit
						</td>
						<td>
							delete
						</td>
					</tr>
					<tr>
						<td>
							 2.
						</td>
						<td>
							 oshte shopping listi
						</td>
						<td>
							edit
						</td>
						<td>
							delete
						</td>
					</tr>
				</table>
				
				<p align="left"><a href="AddShoppingList.jsp"><button>New Shopping List</button></a></p>
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