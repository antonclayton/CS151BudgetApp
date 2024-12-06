package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class HomeController {
	
private CommonObjects commonObjs = CommonObjects.getInstance();
	
	// connects "create account" button on home to go to create account page
	@FXML public void goToCreateAccountOp() {
		try {
			URL url = getClass().getResource("/view/CreateAccount.fxml");
			
			if (url == null) {
				System.out.println("FXML file not found: /view/CreateAccount.fxml");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(url);
			AnchorPane nextPane = loader.load();
			
			HBox mainBox = commonObjs.getMainBox();
	
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(nextPane);
		}
		catch (IOException e){
			e.printStackTrace();
		}	 // switch from create account page to home page
	}
}
