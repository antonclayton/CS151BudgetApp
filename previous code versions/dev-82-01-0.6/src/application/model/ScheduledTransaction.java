package application.model;

public class ScheduledTransaction {
	private String STName;
	private String STAccountName;
	private String STType;
	private String STFrequency;
	private int STDueDate;
	private double STPayment;

	// Account constructor
	public ScheduledTransaction(String STName, String STAccountName, String STType, String STFrequency, int STDueDate,
			double STPayment) {
		this.STName = STName;
		this.STAccountName = STAccountName;
		this.STType = STType;
		this.STFrequency = STFrequency;
		this.STDueDate = STDueDate;
		this.STPayment = STPayment;

	}

	// getters and setters

	public String getScheduledTransactionName() {
		return STName;
	}

	public void setScheduledTransactionName(String STName) {
		this.STName = STName;
	}

	public String getAccountName() {
		return STAccountName;
	}

	public void setAccountName(String STAccountName) {
		this.STAccountName = STAccountName;
	}

	public String getTypeName() {
		return STType;
	}

	public void setTypeName(String STType) {
		this.STType = STType;
	}

	public String getFrequency() {
		return STFrequency;
	}

	// unneeded for user / program
//	public void setFrequency(String STFrequency) {
//		this.STFrequency = STFrequency;
//	}

	public int getDueDate() {
		return STDueDate;
	}

	public void setDueDate(int STDueDate) {
		this.STDueDate = STDueDate;
	}

	public double getPayment() {
		return STPayment;
	}

	public void setPayment(double STPayment) {
		this.STPayment = STPayment;
	}
}
