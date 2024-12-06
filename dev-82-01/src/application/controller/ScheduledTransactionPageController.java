package application.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import application.helpers.SceneSwitcher;
import application.helpers.ScheduledTransactions.ScheduledTransactionsDAO;
import application.model.ScheduledTransaction;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScheduledTransactionPageController implements Initializable {
	private CommonObjects commonObjs = CommonObjects.getInstance();

	@FXML
	private ScrollPane ScheduledTransactionScrollPane; // scroll pane to hold all transactions

	// occurs on load
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		List<ScheduledTransaction> scheduledTransactions = fetchAllTransactions();

		// sort ascending by due date
		scheduledTransactions.sort(Comparator.comparingInt(ScheduledTransaction::getDueDate));

		VBox scheduledTransactionVBox = new VBox();

		for (ScheduledTransaction scheduledTransaction : scheduledTransactions) {
			HBox row = new HBox(5);

			// labels for all parts of a transaction (name, type, date, etc)
			Label STName = new Label(scheduledTransaction.getScheduledTransactionName());
			Label STAccountLabel = new Label(scheduledTransaction.getAccountName());
			Label STTypeLabel = new Label(scheduledTransaction.getTypeName());

			Label STFrequencyLabel = new Label(scheduledTransaction.getFrequency());
			Label STDueDateLabel = new Label(String.valueOf(scheduledTransaction.getDueDate()));

			Label STPaymentLabel = new Label(String.format("$%.2f", scheduledTransaction.getPayment()));

			// styles for labels
			STName.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			STName.setMinWidth(85);
			STName.setAlignment(Pos.CENTER_LEFT);

			STAccountLabel.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			STAccountLabel.setMinWidth(90);
			STAccountLabel.setAlignment(Pos.CENTER_LEFT);

			STTypeLabel.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			STTypeLabel.setMinWidth(45);
			STTypeLabel.setAlignment(Pos.CENTER_LEFT);

			STFrequencyLabel.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			STFrequencyLabel.setMinWidth(60);
			STFrequencyLabel.setAlignment(Pos.CENTER);

			STDueDateLabel.setStyle("-fx-font-size: 7px; -fx-padding: 0 2 0 0; -fx-pref-height: 10px; ");
			STDueDateLabel.setMinWidth(20);
			STDueDateLabel.setAlignment(Pos.CENTER_RIGHT);

			STPaymentLabel.setStyle(
					"-fx-font-size: 7px; -fx-padding: 0 4 0 0; -fx-pref-height: 10px; -fx-border-color: black; -fx-border-width: 0 1 0 0;");
			STPaymentLabel.setMinWidth(60);
			STPaymentLabel.setAlignment(Pos.CENTER_RIGHT);
			// add labels to row
			row.getChildren().addAll(STName, STTypeLabel, STAccountLabel, STPaymentLabel, STFrequencyLabel,
					STDueDateLabel);

			row.setStyle(
					"-fx-background-color: lightgrey; -fx-padding: 5; -fx-border-color: black; -fx-border-width: 0.25;");
			scheduledTransactionVBox.getChildren().add(row);
		}

		// add transactions to scroll pane
		ScheduledTransactionScrollPane.setContent(scheduledTransactionVBox);
		ScheduledTransactionScrollPane.setFitToWidth(true);
	}

	// fetch all scheduled transactions
	private List<ScheduledTransaction> fetchAllTransactions() {
		ScheduledTransactionsDAO scheduledTransactionsDAO = new ScheduledTransactionsDAO();
		return scheduledTransactionsDAO.getAllTransactions();
	}

	@FXML
	public void goToEnterScheduledTransactionOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToEnterScheduledTransaction(mainBox);
	}
}
