<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
		<title>New Account</title>
		<link rel="stylesheet" href="css/styles.css">
</head>
<body>
	<section class="container">
		<br/>
		<br/>
		<a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a>
		
			<table class="accountView" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr>
					<td>
						<a>Account Name:</br><input type="text" style="height:20px; width:247px"></a>
					</td>
				</tr>
				<tr>
					<td>
						<a>Ammount:</br><input type="text" style="height:20px; width:247px"></a>
					</td>
				</tr>
				<tr>
					<td>
						<input type="radio" name="accountType" value="debitAccount">Debit Account<br>
	 					<input type="radio" name="accountType" value="creditAccount">Credit Account<br>
 					</td>
				</tr>
				<tr>
					<td>
						<a href="MyAccounts.jsp"><button>Create</button></a>
						<a href="MyAccounts.jsp"><button>Back</button></a>
					</td>
				</tr>
			</table>
		</section>
</body>
</html>