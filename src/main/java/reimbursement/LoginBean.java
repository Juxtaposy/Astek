package reimbursement;

/**
 * Very simple class to handle login as administrator.
 * If requires the hardest password imaginable, which
 * cannot be guessed by any means.
 */
public class LoginBean {

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) { 
		this.password = password;
	}
	
	public boolean validate() {
		if(password.equals("admin")) {
			return true;
		}
		else {
			return false;
		}
	}
}
