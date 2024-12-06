package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Content1Controller {
	
	private CommonObjects commonObjs = CommonObjects.getInstance();
	
	
	@FXML public void goToContent3Op() {
		
		try {
			URL url = getClass().getResource("/view/Content3.fxml");
			
			if (url == null) {
				System.out.println("FXML file not found: /view/Content3.fxml");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(url);
			AnchorPane pane3 = loader.load();
			
			HBox mainBox = commonObjs.getMainBox();
	
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane3);
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
