<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="reimbursement.Receipt" %>    
<%@ page import="reimbursement.Reimbursement" %>
<%@page import="reimbursement.LoginBean"%>  

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
   		<%  
   		ArrayList<Reimbursement> reimbursementList = (ArrayList<Reimbursement>)request.getAttribute("reimbursementList");
   		ArrayList<Receipt> receiptList = (ArrayList<Receipt>)request.getAttribute("receiptList");
   		LoginBean bean=(LoginBean)request.getAttribute("bean");  
   		%>  
   				
			<form action="LoginServlet" method="POST">	
				<input type="hidden" name="password" value="admin">
				<label for="dailyAllowance">New Daily Allowance value (Current: <%= reimbursementList.get(0).getWorkValue() %>$)</label>
				<input type="number" name="dailyAllowance" step="0.01" min="0">
				<input type="submit" value="Change">
		 		<br><br><br>
		 		
		 		<label for="mileage">New Mileage value (Current: <%= reimbursementList.get(1).getWorkValue() %>$)</label>
				<input type="number" name="mileage" step="0.01" min="0">
				<input type="submit" value="Change">
		 		<br><br><br>
		 		
		 		<label for="receipt">Limits</label><br><br>
		
				<table class="center"> 
					<thead>
						<tr>
							<th> Reimbursement Limits</th>
							<th> Max Total Reimbursement </th>
							<th> New Total Reimbursement </th>
						</tr>
					</thead>
					<tbody>
						<%
						for(int i = 0; i < receiptList.size(); i++){
							%>
							<tr> <%
							String name = receiptList.get(i).getName();
							BigDecimal value = receiptList.get(i).getResetMaxValue();
							%>
							<td><%= name%></td>
							<td><%= value %></td>
							<td><input type="number" step="0.01" min="0" name="<%= i%>"></td>
							</tr>
							<% } %>
						<%
						for(int j = receiptList.size(); j < receiptList.size() + reimbursementList.size(); j++){
							%>
							<tr> <%
							String name = reimbursementList.get(j-receiptList.size()).getName();
							BigDecimal value = reimbursementList.get(j-receiptList.size()).getResetMaxValue();
							%>
							<td><%= name%></td>
							<td><%= value %></td>
							<td><input type="number" step="0.01" min="0" name="<%= j%>"></td>
							</tr>
						<% } %>
					</tbody>
				</table><br>
		
		 		<input type="submit" value="Change">	 		
   			 	<br><br>
   		
   				<label>New Receipt</label><br><br>
				Name: <input type="text" name="nameAdd">
				Max Reimbursement: <input type="number" name="valueAdd" step="0.01" min="0">
				<input type="submit" value="Add"><br><br>

   				<label>Remove Receipt</label><br><br>
   				Name: <input type="text" name="toRemove">
   				<input type="submit" value="Remove">
   			</form> <br><br>
   		
   			<form action="ReceiptServlet" method="POST">
	   			<input type="submit" value="Save and Quit">
	   			<input type="hidden" name="receiptList" value="<%= request.getAttribute("receiptList") %>">
				<input type="hidden" name="reimbursementList" value="<%= request.getAttribute("reimbursementList") %>">
				<input type="hidden" name="reset" value="reset">
   			</form>
   			
       		<%@ include file="footer.jsp" %>
       	
		</div>
    </body>
</html>