package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {
	
	@FXML HBox mainBox;
	
	@FXML public void showContent1Op() {
		
		try {
			URL url = getClass().getResource("/view/Content1.fxml");
			
			if (url == null) {
				System.out.println("FXML file not found: /view/Content1.fxml");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(url);
			AnchorPane pane1 = loader.load();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane1);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		
	}
	
	@FXML public void showContent2Op() {
		try {
			URL url = getClass().getResource("/view/Content2.fxml");
			
			if (url == null) {
				System.out.println("FXML file not found: /view/Content2.fxml");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(url);
			AnchorPane pane2 = loader.load();
	
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane2);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
