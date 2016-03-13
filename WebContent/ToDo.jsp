<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
	<title>ToDoList</title>
	<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	<header>
			<br/><br/><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a><br/><br/><br/>

			<section class="right_side">
			<table class="tasksSize" frame="box" bordercolor= "cyan" class="tableSignUp" align="right">
				<tr class="forstRow" valign="top">
					<td class="firstColumn">
						<a>Task Name:</a>
					</td>
					<td>
						<a>Description:</a>
					</td>		
					<td>
						<a></a>
					</td>	
					<td>
						<a></a>
					</td>	
					
					<td>
						<a></a>
					</td>		
				</tr>
				<tr valign="top">
					<td>
						<ul>
							<li>ToDo 1</li>
							<li>ToDo 2</li>
							<li>ToDo 3</li>
						</ul>
					</td>
					<td>
						<ul>
							<li>ToDo description...</li>
							<li>ToDo description...</li>
							<li>ToDo description...</li>
						</ul>
					</td>
					<td>
						<ul>
							<li><a>View</a></li>
							<li><a>View</a></li>
							<li><a>View</a></li>
						</ul>
					</td>
					<td>
						<ul>
							<li><a>Remove</a></li>
							<li><a>Remove</a></li>
							<li><a>Remove</a></li>
						</ul>
					</td>
					<td>
						<input type="text" style="height:20px; width:100px"></br>
						<input type="button" value="Add Short Term ToDo"></br></br>
						
						<input type="text" style="height:20px; width:100px"></br>
						<input type="button" value="Add Mid Term ToDo"></br></br>
						
						<input type="text" style="height:20px; width:100px"></br>
						<input type="button" value="Add Long Term ToDo"></br></br>
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