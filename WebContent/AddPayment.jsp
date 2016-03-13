<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>New Payment</title>
		<link rel="stylesheet" href="css/styles.css">
	</head>
	<body>
	</br></br><a href="main.jsp"><img src = "resources/MainLogo.jpg" alt="logo" /></a>
		<section class="container">
		<center><b>Create Payment</b></center>
								<c:set var="ErrorMessage" scope="session" value="${sessionScope.ErrorMessage}"/>
								<c:if test="${ErrorMessage != null && ErrorMessage != ' '}">
   								<center><b><font color="red"><c:out value="${ErrorMessage}"/></font></b></center>
								</c:if>
		<form action="CreateNewPaymentServlet" method="post">
			<table class="right" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr>
					<td>
						<a>
							Payment Name
							</br>
							<input type="text" name="paymentName" style="height:20px; width:247px">
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<a>
							<input type="radio" name="statuspayment" value="expense"> Expense<br>
  							<input type="radio" name="statuspayment" value="earning"> Earning<br>
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<a>
							Date:
							</br>
							<input type="date" name="date" autocomplete="on">
							
						</a>
					</td>
				<tr>
					<td>
						<a>
							Description:
							</br>
							<textarea name="description" rows="4" cols="33"></textarea>
						</a>
					</td>
				</tr>
				</tr>
				<tr>
					<td>
						<a>
							<input type="radio" name="statuspaid" value="ispaid"> IsPaid<br>
							<input type="radio" name="statuspaid" value="topay"> ToPay<br>
						</a>
					</td>
				</tr>
				<tr>
					<td>	
							Amount:
							</br>
						<a>
							<input type="number" name="amount" step="0.01" min="0">
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="Save">
						<a href="main.jsp"><input type="button" value="Cancel"></a>
					</td>
				</tr>
			</table>
			</form>
		</section>
	</body>
</html>