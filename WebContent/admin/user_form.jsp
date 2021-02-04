<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<title>Create New User</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div align="center">
		<h1 class="pageheading">
		<c:if test = "${user != null}">
			Edit User
		</c:if>
		<c:if test = "${user == null}">
			Create New User
		</c:if>
		</h1>
	</div>
	
	<div align="center">
		<c:if test = "${user != null}">
			<form action="update_user" method = "post" id ="userForm">
			<input type="hidden" name = "userID" value="${user.userID}">
		</c:if>
		<c:if test = "${user == null}">
			<form action="create_user" method = "post" id ="userForm">
		</c:if>
		<table class="form">
			<tr>
				<td align="right">Email:</td>
				<td align="left"><input type="text" id = "email" name = "email" size="20" value = ${user.email}></td>
			</tr>
			<tr>
				<td align="right">Full name:</td>
				<td align="left"><input type="text" id = "fullname" name = "fullname" size="20" value = ${user.fullName} ></td>
			</tr>
			<tr>
				<td align="right">Password:</td>
				<td align="left"><input type="password" id = "password" name = "password" size="20" value = ${user.password}></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;
					<button id="buttonCancel">Cancel</button>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
	<script type="text/javascript">
	
		$(document).ready(function () {
			$("#userForm").validate({
				rules: {
					email: {
						"required": true,
						"email": true
					},
					fullname: "required",
					
					<c:if test="${user == null}">
					password: "required"
					</c:if>
				},
				
				messages: {
					email: {
						required: " Please enter email",
						email: "Please enter the valid email address"
					},
					fullname: " Please enter fullname",
					
					<c:if test="${user == null}">
					password: "Please enter password"
					</c:if>	
				}
			});
			$("#buttonCancel").click(function () {
				history.go(-1);
			});
		});
	</script>
</html>