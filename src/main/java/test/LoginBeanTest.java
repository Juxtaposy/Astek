package test;

import static org.junit.Assert.*;

import org.junit.Test;

import reimbursement.LoginBean;

/**
 * Tests for LoginBean - setting password and its validation through a method.
 */
public class LoginBeanTest {
	LoginBean bean = new LoginBean();
	
	@Test
	public void testGetSetPassword() {
		assertNull(bean.getPassword());
		bean.setPassword("test");
		assertEquals("test", bean.getPassword());
	}
	
	@Test
	public void testValidate() {
		bean.setPassword("wrong");
		assertNotEquals(true, bean.validate());
		bean.setPassword("admin");
		assertEquals(true, bean.validate());
	}

}
