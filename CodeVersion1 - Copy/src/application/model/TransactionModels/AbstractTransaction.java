package application.model.TransactionModels;

public abstract class AbstractTransaction {
	private String accountName;
	private String typeName;
	private double payment;

	public AbstractTransaction(String accountName, String typeName, double payment) {
		this.accountName = accountName;
		this.typeName = typeName;
		this.payment = payment;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getDeposit() {
		return 0.0;
	}

	public String getFrequency() {
		return "N/A";
	}

	public abstract String getTransactionName();

	public abstract void setTransactionName(String transactionName);

	public abstract String getDateDetails();

}
