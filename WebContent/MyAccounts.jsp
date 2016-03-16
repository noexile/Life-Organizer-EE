<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
	<title>MyAccounts</title>
	<link rel="stylesheet" href="css/MainStyle.css">
</head>
<body>
	<header>
			<br/><br/><a href="main.jsp"><img src="resources/MainLogo.jpg" alt="logo" /></a>
			
					<article class="List">
						<p>Accounts</p>
						<p id="board">Debit<p id="r_board">Credit</p></p>
						<p style="font-size:24px" style="text-align:left">Account name:</p>
						<p style="font-size:24px" style="text-align:left">Amount:</p>
						<p style="font-size:24px" style="text-align:left"><a href="AccountHistory.jsp">Account History</a></p>
						<p style="float:right" style="text-align:left" style="padding:30px 350px"><a href="NewAccount.jsp"><input type="button" value="Create a new account" style="height:25px; width:200px"></a>
					</article><br/><br/><br/>
			<nav>
				<%@ include file="MenuMap.jsp" %>
			</nav>
		</header>
		<aside>
		</aside>
	</div>	
</body>
</html>