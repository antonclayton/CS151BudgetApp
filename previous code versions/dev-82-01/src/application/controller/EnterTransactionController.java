package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import application.helpers.AlertHandler;
import application.helpers.SceneSwitcher;
import application.helpers.Account.AccountDAO;
import application.helpers.TransactionType.TransactionTypesDAO;
import application.helpers.Transactions.TransactionsDBHelper;
import application.model.Account;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EnterTransactionController implements Initializable {
	private CommonObjects commonObjs = CommonObjects.getInstance();

	// combo box for drop down list
	@FXML
	ComboBox<String> transactionAccountComboBox;

	@FXML
	ComboBox<String> transactionTypeComboBox;

	@FXML
	DatePicker transactionDatePicker;

	@FXML
	TextField transactionDescriptionField;

	@FXML
	TextField paymentAmountField;

	@FXML
	TextField depositAmountField;

	// initialization method to set default date for transaction
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		transactionDatePicker.setValue(LocalDate.now());

		// fetch from database to populate drop down lists
		List<Account> accounts = fetchAccountsFromDatabase();
		List<String> transactionTypes = fetchTypesFromDatabase();

		if (!accounts.isEmpty()) {
			transactionAccountComboBox.setValue(accounts.get(0).getAccountName());
		}

		// populate the combobox for account selection
		for (Account account : accounts) {
			transactionAccountComboBox.getItems().add(account.getAccountName());
		}

		if (!transactionTypes.isEmpty()) {
			transactionTypeComboBox.setValue(transactionTypes.get(0));
		}

		// populate the combobox for type selection
		for (String type : transactionTypes) {
			transactionTypeComboBox.getItems().add(type);
		}
	}

	// create transaction with account name, type name, date, description,
	// payment/deposit
	public void createTransaction() {

		// get information from page
		String transactionAccountName = transactionAccountComboBox.getValue();
		String transactionTypeName = transactionTypeComboBox.getValue();
		LocalDate transactionDate = transactionDatePicker.getValue();
		String transactionDescription = transactionDescriptionField.getText();
		String paymentString = paymentAmountField.getText();
		String depositString = depositAmountField.getText();

		// check if required fields are empty
		if (transactionAccountName == null || transactionTypeName == null || transactionDate == null
				|| transactionDescription.isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please fill in all required fields");
			return;
		}

//		System.out.println("Payment: " + paymentString + "\nDeposit: " + depositString);

		// Either payment or deposit must exist (one is required at least)
		if (paymentString.trim().isEmpty() && depositString.trim().isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please provide either payment or deposit amount");
			return;
		}

		try {

			double paymentAmount = paymentString.isEmpty() ? 0.0 : Double.parseDouble(paymentString); // default val of
																										// 0.0
			double depositAmount = depositString.isEmpty() ? 0.0 : Double.parseDouble(depositString);

			// insert transaction to database (transaction.db)
			TransactionsDBHelper.insertTransaction(transactionAccountName, transactionTypeName, transactionDate,
					transactionDescription, paymentAmount, depositAmount);

			// date formatter
			DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

			// Returns transaction information to user
			AlertHandler.showAlert(AlertType.INFORMATION, "Transaction Created",
					"Account Name: " + transactionAccountName + "\nType Name: " + transactionTypeName + "\nDate: "
							+ transactionDate.format(dateStringFormatter) + "\nDescription: " + transactionDescription
							+ "\nPayment Amount: " + String.format("%.2f", paymentAmount) + "\nDeposit Amount: "
							+ String.format("%.2f", depositAmount));
			goToTransactionPageOp();

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

	// switch from EnterTransaction page to Transaction Page
	@FXML
	public void goToTransactionPageOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToTransactionPage(mainBox);
	}
}
