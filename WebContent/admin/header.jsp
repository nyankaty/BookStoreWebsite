<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div align="center">
<div>
	<img src="../images/BookstoreAdminLogo.png">
</div>

<div>
	Welcome, <c:out value="${sessionScope.useremail}"></c:out> | <a href="logout">Logout</a>
	<br/><br/>
</div>
<div id="headermenu">
	<div>
		<a href="list_users">
			<img alt="" src="../images/users.png"><br/>Users
		</a> 
	</div>
	<div>
		<a href="list_category">
			<img alt="" src="../images/category.png"><br/>Categories
		</a> 
	</div>
	<div>
		<a href="list_books">
			<img alt="" src="../images/bookstack.png"><br/>Books
		</a> 
	</div>
	<div>
		<a href="list_customer">
			<img alt="" src="../images/customer.png"><br/>Customers
		</a> 
	</div>
	<div>
		<a href="list_reviews">
			<img alt="" src="../images/review.png"><br/>Reviews
		</a> 
	</div>
	<div>
		<a href="orders">
			<img alt="" src="../images/order.png"><br/>Orders
		</a> 
	</div>
</div>
</div>