<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="reimbursement.Receipt" %>
<%@ page import="reimbursement.Reimbursement" %>
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
		<div class="centercontent">
		
		
			<form action="ReceiptServlet" method="POST">
				<input type="hidden" name="spentSum" value="<%= request.getAttribute("spentSum") %>">
				<label for="tripstartdate">Trip date</label><br><br>
				From: <input type="date" name="inputDateStart" value="<%= request.getAttribute("inputDateStart") %>" required>
				To: <input type="date" name="inputDateEnd" value="<%= request.getAttribute("inputDateEnd") %>" required>
			 	<br><br><br>

				<label for="receipt">Choose a Receipt Type</label>
					
				<select name="select">
					<%
						ArrayList<Receipt> receiptList = (ArrayList<Receipt>)request.getAttribute("receiptList");
						for(int i = 0; i < receiptList.size(); i++){
							String option = receiptList.get(i).getName();
					%>
					<option value="<%= option%>"><%= option %>: (max <%= receiptList.get(i).getMaxValue() %>$) </option> 
					<% } %>
				</select> <br><br>

			
				<label for="rVal">Receipt Value</label>
				<input type="number" step="0.01" min="0" placeholder="0" name="receiptValue">
				<input type="hidden" name= "inputSum" value="<%= request.getAttribute("reimbursementSum") %>">
				<input type="submit" value="Add">
				<br><br><br>
			
				<%
				ArrayList<Reimbursement> reimbursementList = (ArrayList<Reimbursement>)request.getAttribute("reimbursementList");
				%>
				Daily allowance (<%= reimbursementList.get(0).getWorkValue() %>$/day) <br><br>
				<label for="noofdays">Number of days</label>
				<input type="number" min="0" placeholder="0" name="numberOfDays">
				<input type="submit" value="Add">
				<br><br>
			
				Mileage  (<%= reimbursementList.get(1).getWorkValue() %>$/km max <%= reimbursementList.get(1).getMaxValue() %>$) <br><br>
				<label for="distance">Distance</label>
				<input type="number" min="0" placeholder="0" name="distance">
				<input type="submit" value="Add">
			</form> <br><br><br>
			
			
			Total reimbursement: (max: <%= reimbursementList.get(2).getMaxValue()%>$)<br>
			<%= request.getAttribute("reimbursementSum") %>$
			<br><br><br>
			
			<form action="ReceiptServlet" method="POST">
				<input type="submit" value="Reset Calculation">
				<input type="hidden" name="reset" value="reset">
			</form><br><br>
			
		</div>
        <%@ include file="footer.jsp" %>
			
    </body>

</html>
