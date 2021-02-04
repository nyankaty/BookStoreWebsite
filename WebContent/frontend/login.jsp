<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Evergreen Books - Online Books Store</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	
	<div align="center">
		<form>
			Email: <input type="text" size="10"><br/>
			Password: <input type="password" size="10"><br/>
			<input type="submit" value = "Login">
		</form>
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>