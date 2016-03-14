<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
			<table class="tasksSize" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr class="forstRow" valign="top">
					<td class="firstColumn">
						<a>Task Name:</a>
					</td>
					<td class="secondColumn">
						<a>Description:</a>
					</td>		
					<td class="thirdColumn">
						<a></a>
					</td>	
					<td class="fourthColumn">
						<a></a>
					</td>				
				</tr>
				<tr valign="top">
					<td>
						<ul>
							<li>Task 1</li>
							<li>Task 2</li>
							<li>Task 3</li>
						</ul>
					</td>
					<td>
						<ul>
							<li>task description...</li>
							<li>task description...</li>
							<li>task description...</li>
						</ul>
					</td>
					<td>
						<ul>
							<a href="EditTask.html">View</a>
							<a href="EditTask.html">View</a>
							<a href="EditTask.html">View</a>
						</ul>
					</td>
					<td>
						<ul>
							<a href="EditTask.html">Edit</a>
							<a href="EditTask.html">Edit</a>
							<a href="EditTask.html">Edit</a>
						</ul>
					</td>
				</tr>
				<tr valign="bottom">
					<td>
						<a href="AddPayment.jsp"><button>Add Task</button></a>
					</td>
				</tr>
			</table>
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