package application.controller;

import application.helpers.SceneSwitcher;
import application.singleton.DatabaseConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MainController {

	// for loading database connections on program load
	private static final String accountsURL = "jdbc:sqlite:src/application/database/accounts.db";
	private static final String transactionTypesURL = "jdbc:sqlite:src/application/database/transactionTypes.db";
	private static final String transactionsURL = "jdbc:sqlite:src/application/database/transactions.db";

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
	}

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
}
