package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.helpers.AlertHandler;
import application.helpers.ScheduledTransactions.ScheduledTransactionsDAO;
import application.helpers.TransactionType.TransactionTypesDAO;
import application.helpers.Transactions.TransactionsDAO;
import application.model.TransactionModels.AbstractTransaction;
import application.model.TransactionModels.ScheduledTransaction;
import application.model.TransactionModels.Transaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionsByTypeController implements Initializable {
	@FXML
	private TableView<AbstractTransaction> TransactionsByTypeTable;

	@FXML
	private TableColumn<AbstractTransaction, String> ByTypeNameColumn;

	@FXML
	private TableColumn<AbstractTransaction, String> ByTypeAccountColumn;

	@FXML
	private TableColumn<AbstractTransaction, String> ByTypePaymentColumn;

	@FXML
	private TableColumn<AbstractTransaction, String> ByTypeDepositColumn;

	@FXML
	private TableColumn<AbstractTransaction, String> ByTypeDateDayColumn;

	@FXML
	private TableColumn<AbstractTransaction, String> ByTypeFrequencyColumn;

	@FXML
	private TableColumn<AbstractTransaction, Void> ByTypeViewColumn;

	@FXML
	private TableColumn<AbstractTransaction, Void> ByTypeDeleteColumn;

	@FXML
	private ComboBox<String> TransactionsByTypeComboBox;

	@Override
	public void initialize(URL url, ResourceBundle resources) {

		List<String> types = fetchTypesFromDatabase();

		if (!types.isEmpty()) {
			TransactionsByTypeComboBox.setValue(types.get(0));
		}

		for (String type : types) {
			TransactionsByTypeComboBox.getItems().add(type);
		}

		// configure the table columns in the table view
		configureTableColumns();
	}

	public void searchTransactionsByType() {
		List<AbstractTransaction> transactions = new ArrayList<>();
		List<Transaction> normalTransactions = fetchAllTransactions();
		normalTransactions.sort(Comparator.comparing(Transaction::getCreatedDate).reversed());
		transactions.addAll(normalTransactions);

		List<ScheduledTransaction> scheduledTransactions = fetchAllScheduledTransactions();
		scheduledTransactions.sort(Comparator.comparingInt(ScheduledTransaction::getDueDate));
		transactions.addAll(scheduledTransactions);

		String typeToSearch = TransactionsByTypeComboBox.getValue();

		List<AbstractTransaction> filteredTransactions = transactions.stream()
				.filter(transaction -> transaction.getTypeName().equals(typeToSearch)).collect(Collectors.toList());

		TransactionsByTypeTable.setItems(FXCollections.observableArrayList(filteredTransactions));

	}

	private void configureTableColumns() {
		ByTypeNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransactionName()));
		ByTypeAccountColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccountName()));
		ByTypePaymentColumn.setCellValueFactory(
				data -> new SimpleStringProperty(String.format("$%.2f", data.getValue().getPayment())));
		ByTypeDepositColumn.setCellValueFactory(
				data -> new SimpleStringProperty(String.format("$%.2f", data.getValue().getDeposit())));
		ByTypeDateDayColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateDetails()));
		ByTypeFrequencyColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFrequency()));
		addViewButtonToTable();
		addDeleteButtonToTable();
	}

	private void addViewButtonToTable() {
		ByTypeViewColumn.setCellFactory(col -> new TableCell<>() {
			private final Button viewButton = new Button("View");

			{
				viewButton.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold; -fx-font-size: 9;");
				viewButton.setOnAction(e -> {
					AbstractTransaction transaction = getTableView().getItems().get(getIndex());
					handleViewTransaction(transaction);
				});
			}

			// This ensures that the buttons are dynamically added only to rows containing
			// valid transactions and are cleared for any empty rows.
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(viewButton);
				}
			}
		});
	}

	private void addDeleteButtonToTable() {
		ByTypeDeleteColumn.setCellFactory(col -> new TableCell<>() {
			private final Button deleteButton = new Button("Delete");

			{
				deleteButton.setStyle("-fx-background-color: lightcoral; -fx-font-weight: bold; -fx-font-size: 9;");
				deleteButton.setOnAction(e -> {
					AbstractTransaction transaction = getTableView().getItems().get(getIndex());
					handleDeleteTransaction(transaction);
				});
			}

			// This ensures that the buttons are dynamically added only to rows containing
			// valid transactions and are cleared for any empty rows.
			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(deleteButton);
				}
			}
		});
	}

	private void handleViewTransaction(AbstractTransaction transaction) {
		if (transaction instanceof ScheduledTransaction) {
			openScheduledTransactionReadOnly((ScheduledTransaction) transaction);
		} else if (transaction instanceof Transaction) {
			openTransactionReadOnly((Transaction) transaction);
		}
	}

	private void openScheduledTransactionReadOnly(ScheduledTransaction transaction) {
		try {
			// Load the FXML file for the edit page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ScheduledTransactionReadOnlyView.fxml"));
			Parent root = loader.load();

			// Get the controller of the edit page
			ScheduledTransactionReadOnlyViewController controller = loader.getController();

			// Pass the transaction to the controller to pre-fill the fields
			controller.setTransaction(transaction);

			// Create a new Stage for the edit page
			Stage ViewStage = new Stage();
			ViewStage.setTitle("View Scheduled Transaction");
			ViewStage.setScene(new Scene(root));

			// (blocks interaction with the main window
			ViewStage.initModality(Modality.APPLICATION_MODAL);

			// Show the edit window
			ViewStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openTransactionReadOnly(Transaction transaction) {
		try {
			// Load the FXML file for the edit page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TransactionReadOnlyView.fxml"));
			Parent root = loader.load();

			// Get the controller of the edit page
			TransactionReadOnlyViewController controller = loader.getController();

			// Pass the transaction to the controller to pre-fill the fields
			controller.setTransaction(transaction);

			// Create a new Stage for the edit page
			Stage editStage = new Stage();
			editStage.setTitle("View Transaction");
			editStage.setScene(new Scene(root));

			// (blocks interaction with the main window
			editStage.initModality(Modality.APPLICATION_MODAL);

			// Show the edit window
			editStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleDeleteTransaction(AbstractTransaction transaction) {
		TransactionsByTypeTable.getItems().remove(transaction);

		if (transaction instanceof ScheduledTransaction) {
			ScheduledTransactionsDAO.removeScheduledTransaction((ScheduledTransaction) transaction);
		} else if (transaction instanceof Transaction) {
			TransactionsDAO.removeTransaction((Transaction) transaction);
		}

		AlertHandler.showAlert(AlertType.INFORMATION, "Transaction Deleted", "Transaction deleted successfully!");
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

	// fetch accounts arrayList from database
	private List<String> fetchTypesFromDatabase() {
		TransactionTypesDAO transactionTypesDAO = new TransactionTypesDAO();
		return transactionTypesDAO.getAllTypes();
	}
}
