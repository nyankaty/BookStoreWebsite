<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="../css/style.css">
	<script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.min.js"></script>
	<title>Manage Books - Evergreen Bookstore Administration</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div align="center">
		<h1 class="pageheading">Books Management</h1>
		<h3><a href="new_book">Create New Book</a></h3>
	</div>
	
	<c:if test="${message != null}">
	<div align="center">
		<h4 class="message">${message}</h4>
	</div>
	</c:if>
	
	<div align="center">
		<table border = "1" cellpadding = "5">
			<tr>
				<th>Index</th>
				<th>ID</th>
				<th>Image</th>
				<th>Title</th>
				<th>Author</th>
				<th>Category</th>
				<th>Price</th>
				<th>Last Updated</th>
				<th>Actions</th>
			</tr>
			<c:forEach var = "book" items = "${listBooks}"  varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${book.bookId}</td>
					<td>
						<img alt="" src="data:image/jpg;base64,${book.base64Image}" width="84px" height="110px">
					</td>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.category.name}</td>
					<td>$${book.price}</td>
					<td><fmt:formatDate pattern='MM/dd/yyyy' value = '${book.lastUpdateTime}'/></td>
					<td>
						<a href="edit_book?id=${book.bookId}">Edit</a>&nbsp;
						<a href="javascript:void(0);" class="deletelink" id="${book.bookId}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
	
	<script>
		$(document).ready(function() {
			$(".deletelink").each(function () {
				$(this).on("click", function() {
					bookId = $(this).attr("id");
					if(confirm('Are you sure you want to delete the book with ID ' + bookId + '?')){
						window.location = 'delete_book?id=' + bookId;
					}
				});
			});
		});
	</script>
	
</body>
</html>