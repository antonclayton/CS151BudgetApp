package application.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import application.helpers.AlertHandler;
import application.helpers.SceneSwitcher;
import application.helpers.ScheduledTransactions.ScheduledTransactionsDAO;
import application.model.TransactionModels.ScheduledTransaction;
import application.singleton.DatabaseConnectionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

public class MainController {

	// for loading database connections on program load
	private static final String accountsURL = "jdbc:sqlite:src/application/database/accounts.db";
	private static final String transactionTypesURL = "jdbc:sqlite:src/application/database/transactionTypes.db";
	private static final String transactionsURL = "jdbc:sqlite:src/application/database/transactions.db";
	private static final String scheduledTransactionsURL = "jdbc:sqlite:src/application/database/scheduledTransactions.db";

	@FXML
	HBox mainBox;

	// initialize() is automatically called by javaFX after FXML loading
	@FXML
	public void initialize() {
		System.out.println("Application is starting!");
		loadHomePageOp(); // loads home page on application run
		DatabaseConnectionManager.getConnection(accountsURL);
		DatabaseConnectionManager.getConnection(transactionTypesURL);
		DatabaseConnectionManager.getConnection(transactionsURL);
		DatabaseConnectionManager.getConnection(scheduledTransactionsURL);

		Platform.runLater(() -> alertDueTransactions());
	}

	private void alertDueTransactions() {
		List<ScheduledTransaction> scheduledTransactions = fetchAllScheduledTransactions();
		int todayInt = LocalDate.now().getDayOfMonth();

		// filter scheduledTransactions for ones that are due "today"
		List<ScheduledTransaction> dueTransactions = scheduledTransactions.stream()
				.filter(transaction -> transaction.getDueDate() == todayInt).collect(Collectors.toList());

		if (!dueTransactions.isEmpty()) {

			StringBuilder alertMessage = new StringBuilder("The following transactions are due today:\n\n");

			for (ScheduledTransaction transaction : dueTransactions) {
				alertMessage.append(String.format(
						"Transaction Name: %s\nAccount Name: %s\nTransaction Type: %s\nFrequency: %s\nMonthly Due Date: %d\nPayment Amount: %.2f\n\n",
						transaction.getTransactionName(), transaction.getAccountName(), transaction.getTypeName(),
						transaction.getFrequency(), transaction.getDueDate(), transaction.getPayment()));

			}

//			showAlertToFront("Scheduled Transactions Due", alertMessage.toString());
			AlertHandler.showAlert(AlertType.INFORMATION, "Scheduled Transactions Due", alertMessage.toString());
		}
	}

//	private void showAlertToFront(String title, String message) {
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle(title);
//		alert.setContentText(message);
//
//		// set alert to main stage so that it appears at the front (in front of the app)
//		Stage mainStage = (Stage) mainBox.getScene().getWindow();
//		alert.initOwner(mainStage);
//		alert.showAndWait(); // wait for user to close
//
//	}

//	private int getTodayDateAsInt() {
//		LocalDate today = LocalDate.now();
//		int day = today.getDayOfMonth();
//		System.out.println(day);
//		return day;
//	}

	//
	@FXML
	public void loadHomePageOp() {
		SceneSwitcher.goToHomePage(mainBox);
	}

	// go to account page
	@FXML
	public void goToAccountPageOp() {
		SceneSwitcher.goToAccountPage(mainBox);
	}

	// go to transaction page
	@FXML
	public void goToTransactionPageOp() {
		SceneSwitcher.goToTransactionPage(mainBox);
	}

	@FXML
	public void goToTransactionTypesOp() {
		SceneSwitcher.goToTransactionTypes(mainBox);
	}

	@FXML
	public void goToCreateTransactionOp() {
		SceneSwitcher.goToEnterTransaction(mainBox);
	}

	@FXML
	public void goToCreateScheduledTransaction() {
		SceneSwitcher.goToEnterScheduledTransaction(mainBox);
	}

	@FXML
	public void goToScheduledTransaction() {
		SceneSwitcher.goToScheduledTransactionPage(mainBox);
	}

	@FXML
	public void goToTransactionSearchPageOp() {
		SceneSwitcher.goToTransactionSearchPage(mainBox);
	}

	@FXML
	public void goToTransactionsByAccountPageOp() {
		SceneSwitcher.goToTransactionsByAccountPage(mainBox);
	}

	@FXML
	public void goToTransactionsByTypePageOp() {
		SceneSwitcher.goToTransactionsByTypePage(mainBox);
	}

	// fetch all scheduled transactions
	private List<ScheduledTransaction> fetchAllScheduledTransactions() {
		ScheduledTransactionsDAO scheduledTransactionsDAO = new ScheduledTransactionsDAO();
		return scheduledTransactionsDAO.getAllTransactions();
	}
}
