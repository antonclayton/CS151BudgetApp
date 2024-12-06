package application.controller;


import application.helpers.SceneSwitcher;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class HomeController {
	
	private CommonObjects commonObjs = CommonObjects.getInstance();
	
	// connects "create account" button on home to go to create account page
	@FXML public void goToCreateAccountOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToCreateAccount(mainBox);
	}
}
