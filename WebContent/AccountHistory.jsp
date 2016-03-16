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
			<br/><br/><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a><article class="List">
						<p style="font-size:20px" align="left">Account name:</p>
						<p style="font-size:20px" align="left">Amount:</p>
						<p style="font-size:20px" align="left">Hystory:</p>
			<table class=right>
				<tr valign = "top">
					<td>Date:</td>
					<td>Transaction:</td>
				</tr>
				<tr valign = "top">
					<td>28.01.2016</td>
					<td>Payed 97 lv for Vinetka</td>
				</tr>
				<tr valign = "top">
					<td>29.01.2016</td>
					<td>Payed 20 lv for Hrana</td>
				</tr>
				<tr valign = "top">
					<td>29.01.2016</td>
					<td>Payed 15 lv for Bulsatcom</td>
				</tr>
			</table>
			<p style="float:right" style="text-align:left" style="padding:30px 350px"><a href="MyAccounts.jsp"><input type="button" value="Back" style="height:25px; width:200px"></a>
					</article><br/><br/><br/>
			<nav>
				<%@ include file="MenuMap.jsp"%> </nav>
			</nav>
		</header>
		<aside>
		</aside>
	</div>	
</body>
</html>