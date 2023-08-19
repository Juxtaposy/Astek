package reimbursement;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton implementation of List of Reimbursements. This list
 * cannot be edited as no setters are defined. 
 */
public class ReimbursementList {
	private static ReimbursementList instance;
	private final List<Reimbursement> list = new ArrayList<>(); 
	 
	public List<Reimbursement> getReceiptList() {
		return list;
	}
	
	private ReimbursementList() {
		list.add(new Reimbursement(0, "Daily Allowance", 15, 600));
		list.add(new Reimbursement(1, "Mileage", 0.3, 500));
		list.add(new Reimbursement(2, "Total", 0, 1500));
	}
	
	public static ReimbursementList getInstance() {
		if (instance == null) {
			instance = new ReimbursementList();
		}
		return instance;
	}
	
}
