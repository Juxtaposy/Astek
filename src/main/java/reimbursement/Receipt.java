package reimbursement;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is Receipt class which contains constructs receipts for use to choose from.
 * id and name fields should be unique, however it is not mandatory. 
 * Field maxValue describes maximum available amount of money user can apply for.
 * Field resetMaxValue remembers original maxValue for reset purposes.
 * 
 */
public class Receipt{
	private int id;
	private String name;
	private BigDecimal maxValue;
	private BigDecimal resetMaxValue;
	
	public Receipt(int id, String name, double value) {
		super();
		this.id = id;
		this.name = name;
		this.maxValue = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP); 
		this.resetMaxValue = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP); 
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getMaxValue() {
		return maxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void setMaxValue(BigDecimal maxValue) { 
		this.maxValue = maxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void setResetMaxValue(BigDecimal maxValue) { 
		this.resetMaxValue = maxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getResetMaxValue() {
		return resetMaxValue.setScale(2, RoundingMode.HALF_UP);
	}
	
	public void reset() {
		this.maxValue = this.resetMaxValue;
	}

}
