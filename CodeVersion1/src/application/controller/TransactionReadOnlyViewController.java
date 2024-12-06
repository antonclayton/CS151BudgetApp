package application.controller;

import application.model.TransactionModels.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransactionReadOnlyViewController {

	@FXML
	private TextField ReadOnlyTransactionAccount;

	@FXML
	private TextField ReadOnlyTransactionType;

	@FXML
	private DatePicker ReadOnlyTransactionDate;

	@FXML
	private TextField ReadOnlyTransactionDescription;

	@FXML
	private TextField ReadOnlyTransactionPayment;

	@FXML
	private TextField ReadOnlyTransactionDeposit;

	@FXML
	private Button TransactionViewBackButton;

	public void setTransaction(Transaction transaction) {
		ReadOnlyTransactionAccount.setText(transaction.getAccountName());
		ReadOnlyTransactionType.setText(transaction.getTypeName());
		ReadOnlyTransactionDate.setValue(transaction.getCreatedDate());
		ReadOnlyTransactionDescription.setText(transaction.getTransactionName());
		ReadOnlyTransactionPayment.setText(String.format("%.2f", transaction.getPayment()));
		ReadOnlyTransactionDeposit.setText(String.format("%.2f", transaction.getDeposit()));
	}

	public void handleBack() {
		Stage stage = (Stage) TransactionViewBackButton.getScene().getWindow();
		stage.close();
	}
}
