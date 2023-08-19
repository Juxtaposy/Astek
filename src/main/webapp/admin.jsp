<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1, width=device-width">
		
		<link href="styles.css" rel="stylesheet">
		
		<%@ include file="title.jsp" %>
	</head>

    <body>
		<div class="centercontent">
		<br><br>
		<form action="LoginServlet" method="POST">
			Password: <input type="password" name="password" autofocus><br><br>
			<input type="SUBMIT" value="login">
		</form>
			
			
        <%@ include file="footer.jsp" %>
		</div>
    </body>

</html>
