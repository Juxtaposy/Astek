package reimbursement;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Reimbursement class to create objects for reimbursements. Supports basic
 * data manipulation. Fields id and name should be unique. Field workValue
 * is used when multiplied by some other constant to acquired accumulated result.
 * Filed maxValue defines total amount left for user to apply for.
 * Field resetMaxValue stores the original maxValue for reset purposes.
 */
public class Reimbursement{
	private int id;
	private String name;
	private BigDecimal workValue;
	private BigDecimal maxValue;
	private BigDecimal resetMaxValue;
	
	public Reimbursement(int id, String name, double workValue, double maxValue) {
		super();
		this.id = id;
		this.name = name;
		this.workValue = new BigDecimal(workValue).setScale(2, RoundingMode.HALF_UP); 
		this.maxValue = new BigDecimal(maxValue).setScale(2, RoundingMode.HALF_UP); 
		this.resetMaxValue = new BigDecimal(maxValue).setScale(2, RoundingMode.HALF_UP); 
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getWorkValue() {
		return workValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void setWorkValue(BigDecimal workValue) {
		this.workValue = workValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getMaxValue() {
		return maxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getResetMaxValue() {
		return resetMaxValue.setScale(2, RoundingMode.HALF_UP);
	}

	public void setResetMaxValue(BigDecimal maxValue) { 
		this.resetMaxValue = maxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void reset() {
		this.maxValue = this.resetMaxValue;
	}
}
