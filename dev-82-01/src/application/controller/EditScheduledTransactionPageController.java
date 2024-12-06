package application.controller;

import java.util.List;

import application.helpers.AlertHandler;
import application.helpers.Account.AccountDAO;
import application.helpers.ScheduledTransactions.ScheduledTransactionsDBHelper;
import application.helpers.TransactionType.TransactionTypesDAO;
import application.model.Account;
import application.model.ScheduledTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditScheduledTransactionPageController {

	@FXML
	private TextField editScheduledTransactionNameField;

	@FXML
	private ComboBox<String> editScheduledTransactionAccountComboBox;

	@FXML
	private ComboBox<String> editScheduledTransactionTypeComboBox;

	@FXML
	private ComboBox<String> editScheduledTransactionFrequencyComboBox;

	@FXML
	private TextField editScheduledTransactionDueDateField;

	@FXML
	private TextField editScheduledTransactionPaymentField;

	private ScheduledTransaction scheduledTransaction;

	public void setTransaction(ScheduledTransaction scheduledTransaction) {
		this.scheduledTransaction = scheduledTransaction;

		List<Account> accounts = fetchAccountsFromDatabase();
		List<String> transactionTypes = fetchTypesFromDatabase();

		// default value + fill in the combo box
		editScheduledTransactionAccountComboBox.setValue(scheduledTransaction.getAccountName());
		for (Account account : accounts) {
			editScheduledTransactionAccountComboBox.getItems().add(account.getAccountName());
		}

		// default value + fill in the combo box
		editScheduledTransactionTypeComboBox.setValue(scheduledTransaction.getTypeName());
		for (String type : transactionTypes) {
			editScheduledTransactionTypeComboBox.getItems().add(type);
		}

		editScheduledTransactionFrequencyComboBox.setValue("Monthly");
		editScheduledTransactionFrequencyComboBox.getItems().add("Monthly");

		editScheduledTransactionNameField.setText(scheduledTransaction.getScheduledTransactionName());
		editScheduledTransactionDueDateField.setText(String.valueOf(scheduledTransaction.getDueDate()));
		editScheduledTransactionPaymentField.setText(String.format("%.2f", scheduledTransaction.getPayment()));
	}

	public void saveChanges() {
		String prevName = scheduledTransaction.getScheduledTransactionName();

		// get information from page:
		String scheduledTransactionName = editScheduledTransactionNameField.getText();
		String scheduledTransactionAccount = editScheduledTransactionAccountComboBox.getValue();
		String scheduledTransactionType = editScheduledTransactionTypeComboBox.getValue();
		String scheduledTransactionFrequency = editScheduledTransactionFrequencyComboBox.getValue();
		String scheduledTransactionDueDate = editScheduledTransactionDueDateField.getText();
		String scheduledTransactionPayment = editScheduledTransactionPaymentField.getText();

		// check if required fields are empty
		if (scheduledTransactionAccount == null || scheduledTransactionType == null
				|| scheduledTransactionFrequency == null || scheduledTransactionName.trim().isEmpty()
				|| scheduledTransactionDueDate.trim().isEmpty() || scheduledTransactionPayment.trim().isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please fill in all required fields");
			return;
		}

		try {
			int dueDate = Integer.parseInt(scheduledTransactionDueDate);
			double payment = Double.parseDouble(scheduledTransactionPayment);

			// sanity check for due date (due dates cannot be more than 31 no matter what)
			if (dueDate > 31 || dueDate < 1) {
				AlertHandler.showAlert(AlertType.ERROR, "Due Date Error",
						"Cannot have a due date is less than or greater than the number of days in a month");
				return;
			}

			scheduledTransaction.setScheduledTransactionName(scheduledTransactionName);
			scheduledTransaction.setAccountName(scheduledTransactionAccount);
			scheduledTransaction.setTypeName(scheduledTransactionType);
//			scheduledTransaction.setFrequency()	// already monthly by default
			scheduledTransaction.setDueDate(Integer.parseInt(scheduledTransactionDueDate));
			scheduledTransaction.setPayment(Double.parseDouble(scheduledTransactionPayment));

			// insert transaction to database
			ScheduledTransactionsDBHelper.updateScheduledTransaction(prevName, scheduledTransactionName,
					scheduledTransactionAccount, scheduledTransactionType, scheduledTransactionFrequency, dueDate,
					payment);

			// returns transaction info to user
			AlertHandler.showAlert(AlertType.INFORMATION, "Scheduled Transaction Updated",
					"Transaction Name: " + scheduledTransactionName + "\nAccount Name: " + scheduledTransactionAccount
							+ "\nTransaction Type: " + scheduledTransactionType + "\nFrequency: "
							+ scheduledTransactionFrequency + "\nMonthly Due Date: " + scheduledTransactionDueDate
							+ "\nPayment Amount: " + payment);

			((Stage) editScheduledTransactionNameField.getScene().getWindow()).close();

		} catch (NumberFormatException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Invalid Input",
					"The payment/deposit field must be a valid number");
		}
	}

	// fetch accounts arrayList from database
	private List<Account> fetchAccountsFromDatabase() {
		AccountDAO accountDAO = new AccountDAO();
		return accountDAO.getAllAccounts(); // for use in initialization of accountPage
	}

	// fetch transactionTypes from database
	private List<String> fetchTypesFromDatabase() {
		TransactionTypesDAO typesDAO = new TransactionTypesDAO();
		return typesDAO.getAllTypes(); // for use in initialization of TransactionType page
	}
}
