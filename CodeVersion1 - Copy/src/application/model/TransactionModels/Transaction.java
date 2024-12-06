package application.model.TransactionModels;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction extends AbstractTransaction {
	private LocalDate createdDate;
	private String transactionDescription;
	private double deposit;

	// Account constructor
	public Transaction(String accountName, String typeName, LocalDate createdDate, String transactionDescription,
			double payment, double deposit) {
		super(accountName, typeName, payment); // call constructor of super
		this.createdDate = createdDate;
		this.transactionDescription = transactionDescription;
		this.deposit = deposit;
	}

	// getters and setters

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getTransactionName() {
		return transactionDescription;
	}

	public void setTransactionName(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	@Override
	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	// polymorphism (using getDateDetails to output the desired output for different
	// transactions (Scheduled vs regular)
	public String getDateDetails() {
		DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		return createdDate.format(dateStringFormatter);
	}
}
