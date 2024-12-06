package application.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import application.helpers.SceneSwitcher;
import application.helpers.Transactions.TransactionsDAO;
import application.model.TransactionModels.Transaction;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransactionPageController implements Initializable {
	private CommonObjects commonObjs = CommonObjects.getInstance();

	@FXML
	private ScrollPane transactionScrollPane; // scroll pane to hold all transactions

	// occurs on load
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		List<Transaction> transactions = fetchAllTransactions(); // method from TransactionsDAO to get all transactions
																	// from database

		transactions.sort(Comparator.comparing(Transaction::getCreatedDate).reversed()); // sort in descending

		VBox transactionVBox = new VBox();
		for (Transaction transaction : transactions) {
			HBox row = new HBox(5);

			// labels for all parts of a transaction (name, type, date, etc)
			Label tAccountLabel = new Label(transaction.getAccountName());
			Label tTypeLabel = new Label(transaction.getTypeName());

			// format LocalDate as string
			DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			Label dateLabel = new Label(transaction.getCreatedDate().format(dateStringFormatter));

			Label tDescLabel = new Label(transaction.getTransactionName());

			Label tPaymentLabel = new Label(String.format("$%.2f", transaction.getPayment()));
			Label tDepositLabel = new Label(String.format("$%.2f", transaction.getDeposit()));

			// styles for labels
			tAccountLabel.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			tAccountLabel.setMinWidth(90);
			tAccountLabel.setAlignment(Pos.CENTER_LEFT);

			tTypeLabel.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			tTypeLabel.setMinWidth(45);
			tTypeLabel.setAlignment(Pos.CENTER_LEFT);

			dateLabel.setStyle("-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; ");
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

			// add labels to row
			row.getChildren().addAll(tDescLabel, tTypeLabel, tAccountLabel, tPaymentLabel, tDepositLabel, dateLabel);
			row.setStyle(
					"-fx-background-color: lightgrey; -fx-padding: 5; -fx-border-color: black; -fx-border-width: 0.25;");
			transactionVBox.getChildren().add(row);
		}

		// add transactions to scroll pane
		transactionScrollPane.setContent(transactionVBox);
		transactionScrollPane.setFitToWidth(true);
	}

	// fetch all transactions
	private List<Transaction> fetchAllTransactions() {
		TransactionsDAO transactionsDAO = new TransactionsDAO();
		return transactionsDAO.getAllTransactions();
	}

	// switch from Transaction Page to EnterTransaction page
	@FXML
	public void goToCreateTransactionOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToEnterTransaction(mainBox);
	}

}
