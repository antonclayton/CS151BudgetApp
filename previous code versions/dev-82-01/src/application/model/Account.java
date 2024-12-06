package application.model;

import java.time.LocalDate;

public class Account {
	private String accountName;
	private double accountBalance;
	private LocalDate createdDate;
	
	// Account constructor
	public Account (String accountName, double accountBalance, LocalDate createdDate) {
		this.accountName = accountName;
		this.accountBalance = accountBalance;
		this.createdDate = createdDate;
	}
	
	// getters and setters
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public double getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
