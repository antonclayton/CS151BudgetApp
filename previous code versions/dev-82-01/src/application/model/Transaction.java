package application.model;

import java.time.LocalDate;

public class Transaction {
	private String tAccountName;
	private String tTypeName;
	private LocalDate createdDate;
	private String tDesc;
	private double tPayment;
	private double tDeposit;

	// Account constructor
	public Transaction(String tAccountName, String tTypeName, LocalDate createdDate, String tDesc, double tPayment,
			double tDeposit) {
		this.tAccountName = tAccountName;
		this.tTypeName = tTypeName;
		this.createdDate = createdDate;
		this.tDesc = tDesc;
		this.tPayment = tPayment;
		this.tDeposit = tDeposit;
	}

	// getters and setters

	public String getAccountName() {
		return tAccountName;
	}

	public void setAccountName(String accountName) {
		this.tAccountName = accountName;
	}

	public String getTypeName() {
		return tTypeName;
	}

	public void setTypeName(String tTypeName) {
		this.tTypeName = tTypeName;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return tDesc;
	}

	public void setDescription(String tDesc) {
		this.tDesc = tDesc;
	}

	public double getPayment() {
		return tPayment;
	}

	public void setPayment(double tPayment) {
		this.tPayment = tPayment;
	}

	public double getDeposit() {
		return tDeposit;
	}

	public void setDeposit(double tDeposit) {
		this.tDeposit = tDeposit;
	}
}
