package reimbursement;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet. This function handles all the actions required for Admin panel.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * This function is not implemented - redirects to doPost method.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		// Handle log-in with basic password
		String password = request.getParameter("password");
		LoginBean bean = new LoginBean();
		bean.setPassword(password);
		request.setAttribute("bean", bean);
		
		// Controller
		boolean status = bean.validate();
		
		/*
		 *  If the password is correct, handle Admin Panel
		 */
		if(status) {
			// List of receipts and reimbursements
			List<Receipt> receiptList = ReceiptList.getInstance().getReceiptList();
			List<Reimbursement> reimbursementList = ReimbursementList.getInstance().getReceiptList();
			
			// Daily allowance value change
			BigDecimal dailyAllowance = dailyAllowanceChange(request, reimbursementList);
			
			// Mileage value change	
			BigDecimal mileage = mileageChange(request, reimbursementList);
			
			// Add Receipt
			receiptAdd(request, receiptList);
					
			// Remove Receipt
			receiptRemove(request, receiptList);
			
			// Receipt maximum change
			receiptMaxChange(request, receiptList);
			
			// Reimbursement maximum change
			reimbursementMaxChange(request, reimbursementList, receiptList.size());
					
			// Setting attributes
			request.setAttribute("receiptList", receiptList);	
			request.setAttribute("reimbursementList", reimbursementList);
			request.setAttribute("dailyAllowance", dailyAllowance.toPlainString());	
			request.setAttribute("mileage", mileage.toPlainString());	
			
			// Request dispatcher
			RequestDispatcher rd = request.getRequestDispatcher("login-success.jsp");
			rd.forward(request,response);
		}
		
		/*
		 * If the password is incorrect, redirect user to main page		
		 */
		else {
			RequestDispatcher rd = request.getRequestDispatcher("login-error.jsp");
			rd.forward(request,response);
		}		
	}
	
	/**
	 * Function to change daily allowance amount
	 * @param request - HttpServletRequest parameter
	 * @param reimbursementList - List of Reimbursement objects
	 * @return - BigDecimal value of daily allowance value
	 */
	private BigDecimal dailyAllowanceChange(HttpServletRequest request, List<Reimbursement> reimbursementList) {
		/*
		 * Get parameters, check is user input is present and valid and update daily allowance
		 */
		BigDecimal dailyAllowance = reimbursementList.get(0).getWorkValue();
		String dAllowance = request.getParameter("dailyAllowance");
		
		if (dAllowance != null && dAllowance.length() > 0) {
			try {
				dailyAllowance = new BigDecimal(Double.parseDouble(dAllowance)).setScale(2, RoundingMode.HALF_UP);
			}
			catch (Exception e)
			{
				dailyAllowance = new BigDecimal(15).setScale(2, RoundingMode.HALF_UP);
			}
		}
		
		reimbursementList.get(0).setWorkValue(dailyAllowance);
		
		return dailyAllowance;
	}
	
	/**
	 * Function to change mileage amount
	 * @param request - HttpServletRequest parameter
	 * @param reimbursementList - List of Reimbursement objects
	 * @return - BigDecimal value of mileage value
	 */
	private BigDecimal mileageChange(HttpServletRequest request, List<Reimbursement> reimbursementList) {
		/*
		 * Get parameters, check is user input is present and valid and update mileage
		 */
		String dMileage = request.getParameter("mileage");
		BigDecimal mileage = reimbursementList.get(1).getWorkValue();
		
		if (dMileage != null && dMileage.length() > 0) {
			try {
				mileage = new BigDecimal(Double.parseDouble(dMileage)).setScale(2, RoundingMode.HALF_UP);
			}
			catch (Exception e)
			{
				mileage = new BigDecimal(0.3).setScale(2, RoundingMode.HALF_UP);
			}
		}
		
		reimbursementList.get(1).setWorkValue(mileage);
		
		return mileage;
	}
	
	/**
	 * Function to add Receipt to the List
	 * @param request - HttpServletRequest parameter
	 * @param receiptList - List of Receipt objects
	 */
	private void receiptAdd(HttpServletRequest request, List<Receipt> receiptList) {
		/*
		 * Get user input for both Name and Value. If correct, add the Receipt. Else return.
		 */
		String newName = request.getParameter("nameAdd");
		String newValue = request.getParameter("valueAdd");
		if ((newName != null && newName.length() > 0) && (newValue != null && newValue.length() >0 )) {
			try {
				receiptList.add(new Receipt(receiptList.size(), newName, Double.parseDouble(newValue)));
			}
			catch (Exception e) {
				return;
			}
		}
	}
	
	/**
	 * Function to remove Receipt. If there are multiple Receipts with
	 * the same name, the first one is removed.
	 * @param request - HttpServletRequest parameter
	 * @param receiptList - List of Receipt objects
	 */
	private void receiptRemove(HttpServletRequest request, List<Receipt> receiptList) {
		/*
		 * Get name of Receipt to remove and if it is in the list, remove first occurrence
		 */
		String toRemove = request.getParameter("toRemove");
		if (toRemove != null && toRemove.length() > 0) {
			for (Receipt r: receiptList) { 
				if (r.getName().equals(toRemove)) {
					receiptList.remove(r);
					break;
				}
			}
		}
	}

	/**
	 * Function to change maximum Receipt reimbursement.
	 * @param request - HttpServletRequest parameter
	 * @param receipttList - List of Receipt objects
	 */
	private void receiptMaxChange(HttpServletRequest request, List<Receipt> receiptList) {
		/*
		 * Change the value of maximum if present in the input field. If multiple
		 * fields are filled, each one will be adjusted. If input is incorrect, continue 
		 * to next field. 
		 */
		for (int i = 0; i < receiptList.size(); i++) {
			String maxReceipt = request.getParameter(String.valueOf(i));
			if (maxReceipt != null && maxReceipt.length() > 0) {
				try {
					receiptList.get(i).setResetMaxValue(new BigDecimal(Double.parseDouble(maxReceipt)) 
										.setScale(2, RoundingMode.HALF_UP));
				}
				catch (Exception e)
				{
					continue;
				}
			}
		}
	}
	
	/**
	 * Function to change maximum reimbursement for field other than Receipts. Field receiptSize is
	 * required due to functionality of the website id fetching.
	 * @param request - HttpServletRequest parameter
	 * @param reimbursementList - List of Reimbursement objects
	 * @param listSize - size of receiptList
	 */
	private void reimbursementMaxChange(HttpServletRequest request, List<Reimbursement> reimbursementList, int listSize) {
		/*
		 * Change the value of maximum if present in the input field. If multiple
		 * fields are filled, each one will be adjusted. If incorrect, continue
		 *  to next field.
		 */
		for (int j = listSize; j < reimbursementList.size() + listSize; j++) {
			String maxReimbursement = request.getParameter(String.valueOf(j));
			if (maxReimbursement != null && maxReimbursement.length() > 0) {
				try {
					reimbursementList.get(j - listSize).setResetMaxValue(new BigDecimal(Double.parseDouble(maxReimbursement)) 
														.setScale(2, RoundingMode.HALF_UP));
				}
				catch (Exception e) {
					continue;
				}
			}
		}
	}

	
}
