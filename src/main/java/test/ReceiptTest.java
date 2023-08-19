package test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;
import reimbursement.Receipt;

public class ReceiptTest {
	int id = 0;
	String name = "Test";
	double val = 3.13;
	Receipt test = new Receipt(id, name, val);

	@Test
	public void testGetId() {
		assertEquals(id, test.getId());
	}
	
	@Test
	public void testGetName() {
		assertEquals(name, test.getName());
	}
	
	@Test
	public void testGetMaxValue() {
		assertEquals(new BigDecimal(val).setScale(2, RoundingMode.HALF_UP), test.getMaxValue());	
	}
	
	@Test
	public void testGetResetMaxValue() {
		assertEquals(new BigDecimal(val).setScale(2, RoundingMode.HALF_UP), test.getResetMaxValue());
	}
	
	@Test
	public void testSetId() {
		test.setId(1);
		assertEquals(1, test.getId());
	}
	
	@Test
	public void testSetName() {
		test.setName("New Test");
		assertEquals("New Test", test.getName());
	}
	
	@Test
	public void testSetMaxValue() {
		test.setMaxValue(new BigDecimal(4));
		assertEquals(new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), test.getMaxValue());
	}
	
	@Test
	public void testSetResetMaxValue() {
		test.setResetMaxValue(new BigDecimal(4));
		assertEquals(new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), test.getResetMaxValue());
	}
	
	@Test
	public void testReset() {
		test.setMaxValue(new BigDecimal(3));
		test.setMaxValue(new BigDecimal(4));
		assertNotEquals(test.getMaxValue(), test.getResetMaxValue());
		test.reset();
		assertEquals(test.getMaxValue(), test.getResetMaxValue());
	}
}
