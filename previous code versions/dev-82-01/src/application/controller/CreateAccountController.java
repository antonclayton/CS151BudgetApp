package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class CreateAccountController implements Initializable {
	
	private CommonObjects commonObjs = CommonObjects.getInstance();
	
	@FXML TextField nameField;		// matches with fx:id "nameField" in fxml
	
	@FXML TextField balanceField;	// matches with fx:id "balanceField" in fxml
	
	@FXML DatePicker datePicker;	// matches with fx:id "datePicker" in fxml
	
	
	// initialization method to set default date
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		datePicker.setValue(LocalDate.now());
	}
	
	
	// submit form confirms the information in the text field 
	@FXML public void submitForm() {
		String name = nameField.getText();
		String balanceText = balanceField.getText();
		LocalDate selectedDate = datePicker.getValue();
		
		// check if all input fields are filled
		if (name.isEmpty() || balanceText.isEmpty() || selectedDate == null) {
			showAlert(AlertType.ERROR, "Form Error", "Please fill in all fields.");
			return;
		}
		
		try {
			// convert string to double (if there is an error, it will be caught in NumberFormatException)
			double balance = Double.parseDouble(balanceText);
			
			// Returns account information to user
			showAlert(AlertType.INFORMATION, "Account Created", "Name: " + name +  "\nBalance: $" + balance + "\nDate: " + selectedDate);
			goToHomePageOp();	// go back to home page after account is successfully created
		}
		catch (NumberFormatException e) {
			showAlert(AlertType.ERROR, "Invalid Input", "The balance field must be a valid number");
		}
		
	}
	
	// reusable method to create user alerts
	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();
	}
	
	// switch back from account page to home page
	@FXML public void goToHomePageOp() {
		try {
			URL url = getClass().getResource("/view/Home.fxml");
			
			if (url == null) {
				System.out.println("FXML file not found: /view/Home.fxml");
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
