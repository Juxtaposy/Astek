package reimbursement;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReceiptServlet. This function handles all the actions required for calculating user reimbursement
 * claims. 
 */
@WebServlet("/ReceiptServlet")
public class ReceiptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiptServlet() {
        super(); 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 // List of receipts and reimbursements which are available for the user
		 List<Receipt> receiptList = ReceiptList.getInstance().getReceiptList();
		 List<Reimbursement> reimbursementList = ReimbursementList.getInstance().getReceiptList();
		 
		 // Calculate receipt value entered by the user
		 BigDecimal receiptValue = readReceiptValue(request, receiptList);
		 
		 // Get the date input from the user
		 String[] dates = getDates(request);
		 
		 // Assignment for parameter setting
		 String dateStart = dates[0];
		 String dateEnd = dates[1];
		 
		 // Calculate the length of the trip in days
		 long diffInDays = dayCalc(request, dates);	 
		 
		 // Calculate daily allowance based on the number of days
		 BigDecimal[] calculatedAllowance = calculateAllowance(request, reimbursementList, diffInDays);
		 
		// Calculate mileage based on the distance
		 BigDecimal calculatedDistance = calculateMileage(request, reimbursementList);
		 
		 // Update total sum of reimbursement
		 BigDecimal reimbursementSum = updateSum(request, receiptValue, calculatedAllowance[0],
				 calculatedDistance, reimbursementList);
		 
		 // Reset functionality
		 String reset = request.getParameter("reset");
		 if (reset != null && reset.equals("reset"))
		 {
			 receiptList.forEach((l) -> l.reset());		
			 reimbursementList.forEach((l) -> l.reset());	
		 }

		 // Set attributes
		 request.setAttribute("receiptList", receiptList);	 
		 request.setAttribute("reimbursementSum", reimbursementSum.toPlainString());
		 request.setAttribute("reimbursementList", reimbursementList);
		 request.setAttribute("inputDateStart", dateStart);
		 request.setAttribute("inputDateEnd", dateEnd);
		 request.setAttribute("spentSum", calculatedAllowance[1]);
		 // Dispatch request
		 RequestDispatcher dispatcher = request.getRequestDispatcher("claim.jsp");
			 dispatcher.forward(request, response);
		}
				
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * This function is not implemented - redirects to doGet method.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * Function to calculate the amount of money presented by BigDecimal which user has entered
	 * through form on the site. This function tries to find the given receipt and tries to
	 * evaluate amount of money for reimbursement. If the amount exceeds maximum, maximum is 
	 * set instead.
	 * 
	 * @param request - HttpServletRequest parameter
	 * @param receiptList - list of Receipt objects which hold information about individual receipts
	 * @return - BigDecimal value of the Receipt
	 */
	 private BigDecimal readReceiptValue(HttpServletRequest request, List<Receipt> receiptList)
	 {
		 /** 
		  * Get value of input receipt
		 */
		 BigDecimal receiptValue;
		 String inputReceipt = request.getParameter("receiptValue");
		 String select = request.getParameter("select");
		 int index = -1;
		 if (select != null) {
			 for (int i = 0; i < receiptList.size(); i++) {
				 if (select.equals(receiptList.get(i).getName())) {
					 index = i;
					 break;
				 }
			 }
		 }
		 
		 /* 
		  * Catch wrong input exceptions
		 */
		 try {
			 receiptValue = new BigDecimal(Double.parseDouble(inputReceipt)).setScale(2, RoundingMode.HALF_UP);
		 }
		 catch (Exception e)
		 {
			 receiptValue = new BigDecimal(0);
		 }

		 /* 
		  * Check if maximum available amount is not exceeded. If so, assume max instead.
		 */
		 if (index >= 0) {
			 BigDecimal currentMax = receiptList.get(index).getMaxValue();
			 if (receiptValue.compareTo(currentMax) > 0) {
				 receiptValue =  currentMax;
			 }
			 receiptList.get(index).setMaxValue(currentMax.subtract(receiptValue));
		 }	 	 
		 
		 return receiptValue;
	 }

	 /**
	  * Function to handle dates entered by the user. If both start and end date were put,
	  * String array containing two dates is returned. Function also swaps the dates if 
	  * input is in wrong order by date.
	  * @param request - HttpServletRequest parameter
	  * @return -  Array of String containing start and end date
	  */
	 private String[] getDates(HttpServletRequest request) {
		 /*
		  * Get values of date entered by the user
		 */
		 String[] dates = new String[2];
		 dates[0] = request.getParameter("inputDateStart");
		 dates[1] = request.getParameter("inputDateEnd");
		 
		 /*
		  * Check if dates were present, check for correct order.
		  * Wrong dates
		 */
		 try {
			 if ((dates[0] != null && dates[0].length() > 0) && (dates[1] != null && dates[1].length() > 0)) {
				 if (dates[1].compareTo(dates[0]) < 0) {
					 String tmp = dates[1]; 
					 dates[1] = dates[0];
					 dates[0] = tmp;
				 }		 
			 }
			 
			 return dates;
		 }
		 catch (Exception e) {
			 e.printStackTrace();
			 return new String[2];
		 }
	 }

	 /**
	  * Function to calculate duration of the trip based on dates from the user.
	  * @param request - HttpServletRequest parameter
	  * @param dates - Array of String containing start and end dates
	  * @return - long value representing length of the trip
	  */
	 private long dayCalc(HttpServletRequest request, String[] dates) {
		 /* 
		  * Check if dates are present and calculate length of the trip. If  
		  * not, assume 0 days
		 */
		 long diffInDays = Long.MAX_VALUE;
		 
		 if ((dates[0] != null && dates[0].length() > 0) && (dates[1] != null && dates[1].length() > 0)) {
			 try {
				 LocalDate start = LocalDate.parse(dates[0]);
				 LocalDate end   = LocalDate.parse(dates[1]);
				 diffInDays = ChronoUnit.DAYS.between(start, end) + 1;
			 }
			 catch (Exception e) {
				 diffInDays = 0;
			 }
		 }
		 else
		 {
			 diffInDays = 0;
		 }
		 
		 return diffInDays;
	 }
 
	 /**
	  * Function to calculate reimbursement value given amount of days entered by the user
	  * and value of daily allowance. Function checks for incorrect input of days and assumes
	  * 0 if it is the case. Number of days also has to match the length of the trip. If it
	  * is higher - maximum length of the trip is assumed. Function adds to sum until maximum
	  * value of days x allowance is reached.
	  * 
	  * @param request - HttpServletRequest parameter
	  * @param reimbursementList - List of Reimbursement objects
	  * @param diffInDays - duration of the trip
	  * @return Array of two BigDecimals containing spent money and total spent money
	  */
	 private BigDecimal[] calculateAllowance(HttpServletRequest request, List<Reimbursement> reimbursementList,
			 long diffInDays) {
		 
		 /*
		  * Get daily allowance sum already spent
		  */
		 BigDecimal[] rtn = new BigDecimal[2];
		 String spentRead = request.getParameter("spentSum");
		 BigDecimal spentSum;
		 try {
			 spentSum = new BigDecimal(Double.parseDouble(spentRead)).setScale(2, RoundingMode.HALF_UP);
		 }
		 catch (Exception e)
		 {
			 spentSum = new BigDecimal(0);
		 }
		 
		 /*
		  * Calculate amount user has left to claim for
		  */
		 BigDecimal max = reimbursementList.get(0).getWorkValue().multiply(new BigDecimal(diffInDays));
		 BigDecimal available = max.subtract(spentSum);
		 
		 /*
		  * Get amount of days user applies for
		  */
		 BigDecimal dailyAllowance = reimbursementList.get(0).getWorkValue();
		 String nod = request.getParameter("numberOfDays");
		 BigDecimal calculatedAllowance;
		 BigDecimal numberOfDays;
		 	 
		 /*
		  * Check input
		 */
		 try {
			 numberOfDays = new BigDecimal(Double.parseDouble(nod)).setScale(2, RoundingMode.HALF_UP);
		 }
		 catch (Exception e)
		 {
			 numberOfDays = new BigDecimal(0);
		 }
		 	 
		 /*
		  * Check duration of the trip
		 */
		 BigDecimal maxDays = new BigDecimal(diffInDays);
		 if (numberOfDays.compareTo(maxDays) > 0)
		 {
			 numberOfDays = maxDays;
		 }
		 
		 /*
		  * Calculate reimbursement value entered by the user. Check if its higher than maximum.
		  * If it is, assume max instead. If max is reached, zero is added.
		 */
		 	 
		 calculatedAllowance = dailyAllowance.multiply(numberOfDays);	

		 if (calculatedAllowance.compareTo(available) > 0) {
			 calculatedAllowance = available;
		 }
		 
		 available = available.subtract(calculatedAllowance);
		 
		 /*
		  * Update maximum reimbursement accordingly
		 */
		 reimbursementList.get(0).setMaxValue(available);
		 spentSum = spentSum.add(calculatedAllowance);
		 rtn[0] = calculatedAllowance;
		 rtn[1] = spentSum;
		 
		 return rtn;
	 }

	 /**
	  * Function to calculate reimbursement of mileage given the distance from the user and mileage value.
	  * Function checks for basic user input errors as well as maximum mileage reimbursement.
	  * @param request - HttpServletRequest parameter
	  * @param reimbursementList - List of Reimbursement objects
	  * @return - BigDecimal value of mileage reimbursement
	  */
	 private BigDecimal calculateMileage(HttpServletRequest request, List<Reimbursement> reimbursementList) {
		 /*
		  * Get user input and check for errors
		 */
		 String inputDistance = request.getParameter("distance");
		 
		 BigDecimal mileage = reimbursementList.get(1).getWorkValue();
		 BigDecimal distance;
		 BigDecimal calculatedMileageReimbursement;
		 
		 try {
			 distance = new BigDecimal(Double.parseDouble(inputDistance)).setScale(2, RoundingMode.HALF_UP);
		 }
		 catch (Exception e)
		 {
			 distance = new BigDecimal(0);
		 }
		 
		 /*
		  * Calculate mileage based on distance and reimbursement value.
		  * If result is higher than maximum, assume max instead.
		 */
		 calculatedMileageReimbursement = distance.multiply(mileage);
		 BigDecimal maxDistance = reimbursementList.get(1).getMaxValue();
		 
		 if (calculatedMileageReimbursement.compareTo(maxDistance) > 0) {
			 calculatedMileageReimbursement = maxDistance;
		 }
		 
		 /*
		  * Update maximum reimbursement for this type accordingly
		 */
		 reimbursementList.get(1).setMaxValue(maxDistance.subtract(calculatedMileageReimbursement));
		 
		 return calculatedMileageReimbursement;
	 }
	 
	 /**
	  * Function to update the sum of all expenses of the trip. It handles multiple requests adding to the sum
	  * until maximum value is reached.  
	  * @param request - HttpServletRequest parameter
	  * @param receiptValue - BigDecimal value of input Receipt
	  * @param calculatedAllowance - BigDecimal value of input Daily Allowance
	  * @param calculatedDistance - BigDecimal value of input Mileage
	  * @param reimbursementList - List of Reimbursement objects
	  */
	 private BigDecimal updateSum(HttpServletRequest request, BigDecimal receiptValue,  BigDecimal calculatedAllowance,
			 BigDecimal calculatedDistance, List<Reimbursement> reimbursementList) {
		 
		 /*
		  * Acquire sum if multiple calls have been made. If not, assume Sum of 0
		  */
		 String inputSum = request.getParameter("inputSum"); 
		 BigDecimal reimbursementSum;
		 
		 try {
			 reimbursementSum = new BigDecimal(Double.parseDouble(inputSum)).setScale(2, RoundingMode.HALF_UP);
		 }
		 catch (Exception e)
		 {
			 reimbursementSum = new BigDecimal(0);
		 }
		 
		 /*
		  * Calculate sum based on all reimbursements and receipts. If maximum value has been
		  * exceeded, assume max instead
		  */
		 reimbursementSum = reimbursementSum.add(receiptValue).add(calculatedAllowance)
				 .add(calculatedDistance).setScale(2, RoundingMode.HALF_UP);
		 
		 if (reimbursementSum.compareTo(reimbursementList.get(2).getMaxValue()) > 0) {
			 reimbursementSum = reimbursementList.get(2).getMaxValue();
		 }
		 return reimbursementSum;
	 }

}

