package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.helpers.AlertHandler;
import application.helpers.SceneSwitcher;
import application.helpers.Account.AccountDAO;
import application.helpers.ScheduledTransactions.ScheduledTransactionsDBHelper;
import application.helpers.TransactionType.TransactionTypesDAO;
import application.model.Account;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EnterScheduledTransactionController implements Initializable {

	private CommonObjects commonObjs = CommonObjects.getInstance();

	@FXML
	TextField scheduledTransactionNameField;

	@FXML
	ComboBox<String> scheduledTransactionAccountComboBox;

	@FXML
	ComboBox<String> scheduledTransactionTypeComboBox;

	@FXML
	ComboBox<String> scheduledTransactionFrequencyComboBox;

	@FXML
	TextField scheduledTransactionDueDateField;

	@FXML
	TextField scheduledTransactionPaymentField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		List<Account> accounts = fetchAccountsFromDatabase();
		List<String> transactionTypes = fetchTypesFromDatabase();

		// initialize the drop down menus for account and type
		if (!accounts.isEmpty()) {
			scheduledTransactionAccountComboBox.setValue(accounts.get(0).getAccountName());
		}
		for (Account account : accounts) {
			scheduledTransactionAccountComboBox.getItems().add(account.getAccountName());
		}
		if (!transactionTypes.isEmpty()) {
			scheduledTransactionTypeComboBox.setValue(transactionTypes.get(0));
		}
		for (String type : transactionTypes) {
			scheduledTransactionTypeComboBox.getItems().add(type);
		}

		// set fixed frequency to month ("hard coded" code 0.6 item 4 requirements)
		scheduledTransactionFrequencyComboBox.setValue("Monthly");
		scheduledTransactionFrequencyComboBox.getItems().add("Monthly");

	}

	// create a scheduled transaction and add it to database
	public void createScheduledTransaction() {

		// get information from page:
		String scheduledTransactionName = scheduledTransactionNameField.getText();
		String scheduledTransactionAccount = scheduledTransactionAccountComboBox.getValue();
		String scheduledTransactionType = scheduledTransactionTypeComboBox.getValue();
		String scheduledTransactionFrequency = scheduledTransactionFrequencyComboBox.getValue();
		String scheduledTransactionDueDate = scheduledTransactionDueDateField.getText();
		String scheduledTransactionPayment = scheduledTransactionPaymentField.getText();

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

			// checks if there is a duplicate name for the scheduled transaction
			if (ScheduledTransactionsDBHelper.hasDuplicateScheduledTransactionName(scheduledTransactionName)) {
				AlertHandler.showAlert(AlertType.ERROR, "Duplicate Scheduled Transaction",
						"A scheduled transaction with this name already exists");
				return;
			}

			// insert transaction to database
			ScheduledTransactionsDBHelper.insertScheduledTransaction(scheduledTransactionName,
					scheduledTransactionAccount, scheduledTransactionType, scheduledTransactionFrequency, dueDate,
					payment);

			// returns transaction info to user
			AlertHandler.showAlert(AlertType.INFORMATION, "Scheduled Transaction Created",
					"Transaction Name: " + scheduledTransactionName + "\nAccount Name: " + scheduledTransactionAccount
							+ "\nTransaction Type: " + scheduledTransactionType + "\nFrequency: "
							+ scheduledTransactionFrequency + "\nMonthly Due Date: " + scheduledTransactionDueDate
							+ "\nPayment Amount: " + payment);

			// go to scheduled transaction page after creating a new scheduled transaction
			goToScheduledTransactionPageOp();

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

	@FXML
	public void goToScheduledTransactionPageOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToScheduledTransactionPage(mainBox);
	}
}
