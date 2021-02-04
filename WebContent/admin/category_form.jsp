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
	<title>Create New Category</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div align="center">
		<h2>
		<c:if test = "${category != null}">
			Edit Category
		</c:if>
		<c:if test = "${category == null}">
			Create New Category
		</c:if>
		</h2>
	</div>
	
	<div align="center">
		<c:if test = "${category != null}">
			<form action="update_category" method = "post" id="categoryForm">
			<input type="hidden" name = "categoryId" value="${category.categoryId}">
		</c:if>
		<c:if test = "${category == null}">
			<form action="create_category" method = "post" id="categoryForm">
		</c:if>
		<table class="form">
			<tr>
				<td align="right">Name:</td>
				<td align="left"><input type="text" id = "name" name = "name" size="20" value = ${category.name}></td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit" >Save</button>
					<button id = "buttonCancel">Cancel</button> 
				</td>
			</tr>
		</table>
		</form>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#categoryForm").validate({
			rules: {
				name: "required",
			},
			
			messages: {
				name: "please enter the category name",
			}
		});
		$("#buttonCancel").click(function () {
				history.go(-1);
		});
	});
	</script>
</html>