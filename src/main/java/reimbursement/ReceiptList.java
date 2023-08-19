package reimbursement;


import java.util.ArrayList;
import java.util.List;

/**
 * A Singleton class implementation to store the List of Receipts.
 * Default values are given when first instance is created. 
 */
public class ReceiptList {
	private static ReceiptList instance;
	private List<Receipt> list = new ArrayList<>(); 
	 
	public List<Receipt> getReceiptList() {
		return list;
	}
	
	private ReceiptList() {
		list.add(new Receipt(0, "Taxi", 50));
		list.add(new Receipt(1, "Train", 100));
		list.add(new Receipt(2, "Plane", 500));
		list.add(new Receipt(3, "Hotel",200));
		list.add(new Receipt(4, "Meal", 80));
	}
	
	public static ReceiptList getInstance() {
		if (instance == null) {
			instance = new ReceiptList();
		}
		return instance;
	}
	
	public void addReceipt(String name, double val) {
		list.add(new Receipt(list.size(), name, val));
	}
	
	public void removeReceipt(int id) {
		list.remove(id);
	}

}
