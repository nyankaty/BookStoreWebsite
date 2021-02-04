<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<link rel="stylesheet" href="../css/style.css">
<title>Admin Login</title>
</head>
<body>
	<div align="center">
		<h1>Book Store Administration</h1>
		<h2>Admin Login</h2>
		
	<c:if test="${message != null}">
	<div align="center">
		<h4 class="message">${message}</h4>
	</div>
	</c:if>
	
		<form id="loginForm" action="login" method="post">
			<table >
				<tr>
					<td>Email:</td>
					<td><input type="text" name="email" id="email" size="20"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="password"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">Login</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
	<script type="text/javascript">
	
		$(document).ready(function () {
			$("#loginForm").validate({
				rules: {
					email: {
						"required": true,
						"email": true
					},
					
					password: "required",
				},
				
				messages: {
					email: {
						required: " Please enter email",
						email: "Please enter the valid email address"
					},
					password: " Please enter password",
				}
			});
		});
	</script>
</html>