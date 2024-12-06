package application.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import application.helpers.AlertHandler;
import application.helpers.ScheduledTransactions.ScheduledTransactionsDAO;
import application.helpers.Transactions.TransactionsDAO;
import application.model.TransactionModels.ScheduledTransaction;
import application.model.TransactionModels.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionSearchPageController {
	@FXML
	private ScrollPane TransactionSearchScrollPane;

	@FXML
	private TextField TransactionSearchField;

	private boolean secondCall = false;

	private void displaySearchResultT(List<Transaction> filteredTList) {

		List<Transaction> transactions = new ArrayList<>(filteredTList);

		// sort by due date
		transactions.sort(Comparator.comparing(Transaction::getCreatedDate).reversed());

		VBox transactionVBox = new VBox();
		if (transactions.size() > 0) {
			for (Transaction transaction : transactions) {
				HBox row = new HBox(5);

				Label tDescLabel = new Label(transaction.getTransactionName());

				Label tPaymentLabel = new Label(String.format("$%.2f", transaction.getPayment()));
				Label tDepositLabel = new Label(String.format("$%.2f", transaction.getDeposit()));

				// format LocalDate as string
				DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				Label dateLabel = new Label(transaction.getCreatedDate().format(dateStringFormatter));

				dateLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				dateLabel.setMinWidth(60);
				dateLabel.setAlignment(Pos.CENTER_RIGHT);

				tDescLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px;  -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				tDescLabel.setMinWidth(85);
				tDescLabel.setAlignment(Pos.CENTER_LEFT);

				tPaymentLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				tPaymentLabel.setMinWidth(40);
				tPaymentLabel.setAlignment(Pos.CENTER_RIGHT);

				tDepositLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				tDepositLabel.setMinWidth(40);
				tDepositLabel.setAlignment(Pos.CENTER_RIGHT);

				// Create Edit button
				Button editButton = new Button("View / Edit");
				editButton.setStyle(
						"-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 7px; -fx-padding: 1 5 1 5;");
				editButton.setMinHeight(11); // Adjust the minimum height to control button size
				editButton.setMaxHeight(11); // Adjust the maximum height to keep button consistent
				editButton.setPrefHeight(11);
				editButton.setMinWidth(65); // Set minimum width
				editButton.setMaxWidth(65); // Set maximum width
				editButton.setPrefWidth(65); // Set preferred width
				// open edit page on click
				editButton.setOnAction(event -> {
					// Handle edit action, e.g., open a new page to edit the transaction
					openEditPageT(transaction);
				});

				// Create Delete button
				Button deleteButton = new Button("Delete");
				deleteButton.setStyle(
						"-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 7px; -fx-padding: 1 5 1 5;");
				deleteButton.setMinHeight(11); // Adjust the minimum height to control button size
				deleteButton.setMaxHeight(11); // Adjust the maximum height to keep button consistent
				deleteButton.setPrefHeight(11);
				deleteButton.setMinWidth(70); // Set minimum width
				deleteButton.setMaxWidth(70); // Set maximum width
				deleteButton.setPrefWidth(70);
				deleteButton.setOnAction(event -> {
					// Handle delete action, e.g., remove the transaction
					deleteTransaction(transaction);
				});

				// add labels to row
				row.getChildren().addAll(tDescLabel, tPaymentLabel, tDepositLabel, dateLabel, editButton, deleteButton);
				row.setStyle(
						"-fx-background-color: lightgrey; -fx-padding: 5; -fx-border-color: black; -fx-border-width: 0.25;");
				transactionVBox.getChildren().add(row);
			}
		}

		// add transactions to scroll pane
		TransactionSearchScrollPane.setContent(transactionVBox);
		TransactionSearchScrollPane.setFitToWidth(true);
	}

	private void displaySearchResultST(List<ScheduledTransaction> filteredSTList) {

		List<ScheduledTransaction> scheduledTransactions = new ArrayList<>(filteredSTList);

		// sort by day of the month
		scheduledTransactions.sort(Comparator.comparingInt(ScheduledTransaction::getDueDate));

		VBox transactionVBox = new VBox();
		if (scheduledTransactions.size() > 0) {
			for (ScheduledTransaction scheduledTransaction : scheduledTransactions) {
				HBox row = new HBox(5);

				Label stNameLabel = new Label(scheduledTransaction.getTransactionName());

				Label stPaymentLabel = new Label(String.format("$%.2f", scheduledTransaction.getPayment()));
				Label stDepositLabel = new Label(String.format("$%.2f", 0.00));

				Label stDueDateLabel = new Label(String.valueOf(scheduledTransaction.getDueDate()));

				stDueDateLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				stDueDateLabel.setMinWidth(60);
				stDueDateLabel.setAlignment(Pos.CENTER_RIGHT);

				stNameLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px;  -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				stNameLabel.setMinWidth(85);
				stNameLabel.setAlignment(Pos.CENTER_LEFT);

				stPaymentLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				stPaymentLabel.setMinWidth(40);
				stPaymentLabel.setAlignment(Pos.CENTER_RIGHT);

				stDepositLabel.setStyle(
						"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
				stDepositLabel.setMinWidth(40);
				stDepositLabel.setAlignment(Pos.CENTER_RIGHT);

				// Create Edit button
				Button editButton = new Button("View / Edit");
				editButton.setStyle(
						"-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 7px; -fx-padding: 1 5 1 5;");
				editButton.setMinHeight(11); // Adjust the minimum height to control button size
				editButton.setMaxHeight(11); // Adjust the maximum height to keep button consistent
				editButton.setPrefHeight(11);
				editButton.setMinWidth(65); // Set minimum width
				editButton.setMaxWidth(65); // Set maximum width
				editButton.setPrefWidth(65); // Set preferred width
				editButton.setOnAction(event -> {
					// Handle edit action, e.g., open a new page to edit the transaction
					openEditPageST(scheduledTransaction);
				});

				// Create Delete button
				Button deleteButton = new Button("Delete");
				deleteButton.setStyle(
						"-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 7px; -fx-padding: 1 5 1 5;");
				deleteButton.setMinHeight(11); // Adjust the minimum height to control button size
				deleteButton.setMaxHeight(11); // Adjust the maximum height to keep button consistent
				deleteButton.setPrefHeight(11);
				deleteButton.setMinWidth(70); // Set minimum width
				deleteButton.setMaxWidth(70); // Set maximum width
				deleteButton.setPrefWidth(70);
				deleteButton.setOnAction(event -> {
					// Handle delete action, e.g., remove the transaction
					deleteScheduledTransaction(scheduledTransaction);
				});

				// add labels to row
				row.getChildren().addAll(stNameLabel, stPaymentLabel, stDepositLabel, stDueDateLabel, editButton,
						deleteButton);
				row.setStyle(
						"-fx-background-color: lightgrey; -fx-padding: 5; -fx-border-color: black; -fx-border-width: 0.25;");
				transactionVBox.getChildren().add(row);
			}
		}

		// add transactions to scroll pane
		TransactionSearchScrollPane.setContent(transactionVBox);
		TransactionSearchScrollPane.setFitToWidth(true);
	}

	public void searchTransactions() {

		// get search text
		String searchText = TransactionSearchField.getText();

		// empty check
		if (searchText.trim().isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Search Error", "Empty Search!");
			return;
		}

		List<Transaction> transactions = fetchAllTransactions();

		// filter transactions:
		List<Transaction> filteredTransactions = transactions.stream().filter(
				transaction -> transaction.getTransactionName().toLowerCase().contains(searchText.toLowerCase()))
				.toList(); // immutable

		if (filteredTransactions.size() == 0 && !secondCall) {
			AlertHandler.showAlert(AlertType.ERROR, "Empty Results",
					"Nothing by the search key of  |" + searchText + "|  exists right now!");
			return;
		} else {
			secondCall = false;
		}
		displaySearchResultT(filteredTransactions);

	}

	public void searchScheduledTransactions() {
		String searchText = TransactionSearchField.getText();

		// empty check
		if (searchText.trim().isEmpty()) {
			AlertHandler.showAlert(AlertType.ERROR, "Search Error", "Empty Search!");
			return;
		}

		List<ScheduledTransaction> scheduledTransactions = fetchAllScheduledTransactions();

		List<ScheduledTransaction> filteredScheduledTransactions = scheduledTransactions.stream()
				.filter(scheduledTransaction -> scheduledTransaction.getTransactionName().toLowerCase()
						.contains(searchText.toLowerCase()))
				.toList();

		if (filteredScheduledTransactions.size() == 0 && !secondCall) {
			AlertHandler.showAlert(AlertType.ERROR, "Empty Results",
					"Nothing by the search key of  |" + searchText + "|  exists right now!");
			return;
		} else {
			secondCall = false;
		}

		displaySearchResultST(filteredScheduledTransactions);
	}

	// fetch all transactions
	private List<Transaction> fetchAllTransactions() {
		TransactionsDAO transactionsDAO = new TransactionsDAO();
		return transactionsDAO.getAllTransactions();
	}

	// fetch all scheduled transactions
	private List<ScheduledTransaction> fetchAllScheduledTransactions() {
		ScheduledTransactionsDAO scheduledTransactionsDAO = new ScheduledTransactionsDAO();
		return scheduledTransactionsDAO.getAllTransactions();
	}

	private void deleteTransaction(Transaction transaction) {
		TransactionsDAO.removeTransaction(transaction);
		secondCall = true;
		searchTransactions();
	}

	private void deleteScheduledTransaction(ScheduledTransaction scheduledTransaction) {
		ScheduledTransactionsDAO.removeScheduledTransaction(scheduledTransaction);
		secondCall = true;
		searchScheduledTransactions();
	}

	public void openEditPageT(Transaction transaction) {
		try {
			// Load the FXML file for the edit page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditTransactionWindow.fxml"));
			Parent root = loader.load();

			// Get the controller of the edit page
			EditTransactionPageController controller = loader.getController();

			// Pass the transaction to the controller to pre-fill the fields
			controller.setTransaction(transaction);

			// Create a new Stage for the edit page
			Stage editStage = new Stage();
			editStage.setTitle("Edit Transaction");
			editStage.setScene(new Scene(root));

			editStage.setOnHidden(event -> {
				secondCall = true;
				searchTransactions();
			});

			// (blocks interaction with the main window
			editStage.initModality(Modality.APPLICATION_MODAL);

			// Show the edit window
			editStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openEditPageST(ScheduledTransaction scheduledTransaction) {
		try {
			// Load the FXML file for the edit page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditScheduledTransactionWindow.fxml"));
			Parent root = loader.load();

			// Get the controller of the edit page
			EditScheduledTransactionPageController controller = loader.getController();

			// Pass the transaction to the controller to pre-fill the fields
			controller.setTransaction(scheduledTransaction);

			// Create a new Stage for the edit page
			Stage editStage = new Stage();
			editStage.setTitle("Edit Scheduled Transaction");
			editStage.setScene(new Scene(root));

			editStage.setOnHidden(event -> {
				secondCall = true;
				searchScheduledTransactions();
			});

			// (blocks interaction with the main window
			editStage.initModality(Modality.APPLICATION_MODAL);

			// Show the edit window
			editStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
