<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
								<c:if test="${(ErrorMessage != null) && (ErrorMessage != ' ')}">
   								<center><b><font color="red"><c:out value="${ErrorMessage}"/></font></b></center>
								</c:if>
		<form action="CreateNewPaymentServlet" method="post">
			<table class="right" frame="box" bordercolor= "cyan" class="tableSignUp">
				<tr>
					<td>
						<a>
							Payment Name
							</br>
							<input type="text" name="paymentName" value="${requestScope.nameForCreate}" style="height:20px; width:247px">
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<a>
							<input type="radio" name="statuspayment" value="expense" checked="<c:if test="${requestScope.PaymentForCreate.isIncome == false}"><c:out value="${checked}"/></c:if>"> Expense<br>
  							<input type="radio" name="statuspayment" value="earning" checked="<c:if test="${requestScope.PaymentForCreate.isIncome == true}"><c:out value="${checked}"/></c:if>"> Earning<br>
						</a>
					</td>
				</tr>
				<tr>
					<td>
						<a>
							Date:
							</br>
							<input type="date" name="date" value="${requestScope.date}" autocomplete="on">
							
						</a>
					</td>
				<tr>
					<td>
						<a>
							Description:
							</br>
							<textarea name="description" rows="4" cols="33" ><c:out value="${requestScope.description}"/></textarea>
						</a>
					</td>
				</tr>
				</tr>
				<tr>
					<td>
						<a>
							<input type="radio" name="statuspaid" value="ispaid" checked="<c:if test="${requestScope.PaymentForCreate.isPaid == true}"><c:out value="${checked}"/></c:if>"> IsPaid<br>
							<input type="radio" name="statuspaid" value="topay" checked="<c:if test="${requestScope.PaymentForCreate.isPaid == false}"><c:out value="${checked}"/></c:if>"> ToPay<br>
						</a>
					</td>
				</tr>
				<tr>
					<td>	
							Amount:
							</br>
						<a>
							<input type="number" name="amount" step="0.01" min="0" value="${requestScope.amount}">
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