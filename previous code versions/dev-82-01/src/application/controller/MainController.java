package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {
	
	@FXML HBox mainBox;
	
	// initialize() is automatically called by javaFX after FXML loading
	@FXML public void initialize() {
		loadHomePage("/view/Home.fxml");		// loads home page on application run
	}
	
	
	// 
	@FXML public void loadHomePage(String fxmlFilePath) {
		try {
			URL url = getClass().getResource(fxmlFilePath);
			
			if (url == null) {
				System.out.println("FXML file not found: /view/Home.fxml");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(url);
			AnchorPane nextPane = loader.load();
	
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(nextPane);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
