package application.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.helpers.AlertHandler;
import application.helpers.Account.AccountDAO;
import application.helpers.TransactionType.TransactionTypesDAO;
import application.helpers.Transactions.TransactionsDBHelper;
import application.model.Account;
import application.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTransactionPageController {
	@FXML
	private ComboBox<String> editTransactionAccountComboBox;
	@FXML
	private ComboBox<String> editTransactionTypeComboBox;
	@FXML
	private DatePicker editTransactionDatePicker;
	@FXML
	private TextField editTransactionDescriptionField;
	@FXML
	private TextField editTransactionPaymentAmountField;
	@FXML
	private TextField editTransactionDepositAmountField;

	private Transaction transaction;

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;

		List<Account> accounts = fetchAccountsFromDatabase();
		List<String> transactionTypes = fetchTypesFromDatabase();

		// default value + fill in the combo box
		editTransactionAccountComboBox.setValue(transaction.getAccountName());
		for (Account account : accounts) {
			editTransactionAccountComboBox.getItems().add(account.getAccountName());
		}

		// default value + fill in the combo box
		editTransactionTypeComboBox.setValue(transaction.getTypeName());
		for (String type : transactionTypes) {
			editTransactionTypeComboBox.getItems().add(type);
		}

		// default values
		editTransactionDatePicker.setValue(transaction.getCreatedDate());
		editTransactionDescriptionField.setText(transaction.getDescription());
		editTransactionPaymentAmountField.setText(String.format("%.2f", transaction.getPayment()));
		editTransactionDepositAmountField.setText(String.format("%.2f", transaction.getDeposit()));

	}

	public void saveChanges() {

		String prevDescription = transaction.getDescription();

		// get information from page
		String transactionAccountName = editTransactionAccountComboBox.getValue();
		String transactionTypeName = editTransactionTypeComboBox.getValue();
		LocalDate transactionDate = editTransactionDatePicker.getValue();
		String transactionDescription = editTransactionDescriptionField.getText();
		String paymentString = editTransactionPaymentAmountField.getText();
		String depositString = editTransactionDepositAmountField.getText();

		// check if required fields are empty
		if (transactionAccountName == null || transactionTypeName == null || transactionDate == null
				|| transactionDescription.isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please fill in all required fields");
			return;
		}

		// Either payment or deposit must exist (one is required at least)
		if (paymentString.trim().isEmpty() && depositString.trim().isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please provide either payment or deposit amount");
			return;
		}

		try {

			double paymentAmount = paymentString.isEmpty() ? 0.0 : Double.parseDouble(paymentString); // default val of
																										// 0.0
			double depositAmount = depositString.isEmpty() ? 0.0 : Double.parseDouble(depositString);

			transaction.setAccountName(transactionAccountName);
			transaction.setTypeName(transactionTypeName);
			transaction.setCreatedDate(transactionDate);
			transaction.setDescription(transactionDescription);
			transaction.setPayment(Double.parseDouble(paymentString));
			transaction.setDeposit(Double.parseDouble(depositString));
			// insert transaction to database (transaction.db)
			TransactionsDBHelper.updateTransaction(prevDescription, transactionAccountName, transactionTypeName,
					transactionDate, transactionDescription, paymentAmount, depositAmount);

			// date formatter
			DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			AlertHandler.showAlert(AlertType.INFORMATION, "Transaction Updated",
					"Account Name: " + transactionAccountName + "\nType Name: " + transactionTypeName + "\nDate: "
							+ transactionDate.format(dateStringFormatter) + "\nDescription: " + transactionDescription
							+ "\nPayment Amount: " + String.format("%.2f", paymentAmount) + "\nDeposit Amount: "
							+ String.format("%.2f", depositAmount));

			((Stage) editTransactionDescriptionField.getScene().getWindow()).close();
		} catch (NumberFormatException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Invalid Input",
					"The payment/deposit field must be a valid number");
		}
	}

	// fetch accounts arrayList from database
	private List<Account> fetchAccountsFromDatabase() {
		AccountDAO accountDAO = new AccountDAO();
		return accountDAO.getAllAccounts();
	}

	// fetch transactionTypes from database
	private List<String> fetchTypesFromDatabase() {
		TransactionTypesDAO typesDAO = new TransactionTypesDAO();
		return typesDAO.getAllTypes();
	}
}
