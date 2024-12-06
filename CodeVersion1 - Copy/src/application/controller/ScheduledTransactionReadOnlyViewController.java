package application.controller;

import application.model.TransactionModels.ScheduledTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ScheduledTransactionReadOnlyViewController {
	@FXML
	private TextField ReadOnlyScheduledTransactionName;

	@FXML
	private TextField ReadOnlyScheduledTransactionAccount;

	@FXML
	private TextField ReadOnlyScheduledTransactionType;

	@FXML
	private TextField ReadOnlyScheduledTransactionFrequency;

	@FXML
	private TextField ReadOnlyScheduledTransactionDueDate;

	@FXML
	private TextField ReadOnlyScheduledTransactionPayment;

	@FXML
	private Button ScheduledTransactionViewBackButton;

	public void setTransaction(ScheduledTransaction transaction) {
		ReadOnlyScheduledTransactionName.setText(transaction.getTransactionName());
		ReadOnlyScheduledTransactionAccount.setText(transaction.getAccountName());
		ReadOnlyScheduledTransactionType.setText(transaction.getTypeName());
		ReadOnlyScheduledTransactionFrequency.setText(transaction.getFrequency());
		ReadOnlyScheduledTransactionDueDate.setText(transaction.getDateDetails());
		ReadOnlyScheduledTransactionPayment.setText(String.format("%.2f", transaction.getPayment()));
	}

	public void handleBack() {
		Stage stage = (Stage) ScheduledTransactionViewBackButton.getScene().getWindow();
		stage.close();
	}
}
