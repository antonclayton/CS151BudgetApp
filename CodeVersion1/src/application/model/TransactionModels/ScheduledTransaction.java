package application.model.TransactionModels;

public class ScheduledTransaction extends AbstractTransaction {
	private String STFrequency;
	private int STDueDate;
	private String transactionName;

	// Account constructor
	public ScheduledTransaction(String transactionName, String accountName, String typeName, String STFrequency,
			int STDueDate, double payment) {
		super(accountName, typeName, payment);
		this.transactionName = transactionName;
		this.STFrequency = STFrequency;
		this.STDueDate = STDueDate;
	}

	// getters and setters

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	@Override
	public String getFrequency() {
		return STFrequency;
	}

	// unneeded for user / program
	public void setFrequency(String STFrequency) {
		this.STFrequency = STFrequency;
	}

	public int getDueDate() {
		return STDueDate;
	}

	public void setDueDate(int STDueDate) {
		this.STDueDate = STDueDate;
	}

	// polymorphism (using getDateDetails to output the desired output for different
	// transactions (Scheduled vs regular)
	public String getDateDetails() {
		return Integer.toString(STDueDate);
	}
}
