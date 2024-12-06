package application.controller;

import application.helpers.AccountsDBHelper;
import application.helpers.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML
	HBox mainBox;

	// initialize() is automatically called by javaFX after FXML loading
	@FXML
	public void initialize() {
		loadHomePageOp(); // loads home page on application run
		AccountsDBHelper.testDatabaseConnection(); // logs if connection to SQLite is successful or not
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
}
