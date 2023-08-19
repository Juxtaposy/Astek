package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import reimbursement.Receipt;
import reimbursement.ReceiptList;

public class ReceiptListTest {
	
	@Test
	/**
	 * Test creation of new instance, adding receipt
	 */
	public void testSingleton() {
		System.out.println("ReceiptList singleton test\n"); 
		
		ReceiptList list = ReceiptList.getInstance();
		
		// Default names created in first instance
		List<String> names = new ArrayList<>();
		names.add("Taxi");
		names.add("Train");
		names.add("Plane");
		names.add("Hotel");
		names.add("Meal");
		
		// Initialization test
		testSingletonListNames(list,names);
		
		// Add receipt and name
		list.addReceipt("Souvenir", 20);
		names.add("Souvenir");
		// Test change
		testSingletonListNames(list,names);
			
	}
	
	@Test
	/**
	 * Compare objects in "two" instances in the Singleton
	 */
	public void testIfTheSame() {
		ReceiptList list = ReceiptList.getInstance();
		list.addReceipt("Test", 0);
		
		ReceiptList newList = ReceiptList.getInstance();
		
		for (int i = 0; i < list.getReceiptList().size(); i++) {
			assertEquals(list.getReceiptList().get(i), newList.getReceiptList().get(i));
		}
	}
	
	/**
	 * Helper method for tests to compare names of objects in the list with given list of names
	 * @param list
	 * @param names
	 */
	private void testSingletonListNames(ReceiptList list, List<String> names)
	{
		for (int i = 0; i < list.getReceiptList().size(); i++) {
			Receipt r = list.getReceiptList().get(i);
			assertEquals(names.get(i), r.getName());
		}
	}

}
