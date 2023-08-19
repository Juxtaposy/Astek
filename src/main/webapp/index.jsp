<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1, width=device-width">
		
		<link href="styles.css" rel="stylesheet" type="text/css">
		
		<%@ include file="title.jsp" %>
	</head>
		
	<body>
		<%@ include file="adlink.jsp" %>
		
		<h1>
			Calculate your business trip <br> WITH US!
		</h1>

		<div class="centercontent">
			<form action="ReceiptServlet" method="POST">
				<input type="hidden" value="reset" name="reset">
				<input type="submit" value="New claim">
			</form>
		</div>

        <%@ include file="footer.jsp" %>
	
	</body>
	
	
</html> 
