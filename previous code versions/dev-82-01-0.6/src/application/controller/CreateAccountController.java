package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.helpers.AlertHandler;
import application.helpers.SceneSwitcher;
import application.helpers.Account.AccountsDBHelper;
import application.singleton.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CreateAccountController implements Initializable {

	private CommonObjects commonObjs = CommonObjects.getInstance();

	@FXML
	TextField nameField; // matches with fx:id "nameField" in fxml

	@FXML
	TextField balanceField; // matches with fx:id "balanceField" in fxml

	@FXML
	DatePicker datePicker; // matches with fx:id "datePicker" in fxml

	// initialization method to set default date
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		datePicker.setValue(LocalDate.now());
	}

	// submit form confirms the information in the account page text field
	@FXML
	public void submitForm() {
		String name = nameField.getText();
		String balanceText = balanceField.getText();
		LocalDate selectedDate = datePicker.getValue();

		// check if all input fields are filled
		if (name.isEmpty() || balanceText.isEmpty() || selectedDate == null) {
			AlertHandler.showAlert(AlertType.ERROR, "Form Error", "Please fill in all fields.");
			return;
		}

		try {
			// convert string to double (if there is an error, it will be caught in
			// NumberFormatException)
			double balance = Double.parseDouble(balanceText);

			// check for duplicate account names
			if (AccountsDBHelper.isDuplicateAccount(name)) {
				AlertHandler.showAlert(AlertType.ERROR, "Duplicate Account",
						"An account with this name already exists");
				return;
			}

			// insert account object to database
			AccountsDBHelper.insertAccount(name, balance, selectedDate);

			// date formatter
			DateTimeFormatter dateStringFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

			// Returns account information to user
			AlertHandler.showAlert(AlertType.INFORMATION, "Account Created", "Name: " + name + "\nBalance: $"
					+ String.format("%.2f", balance) + "\nDate: " + selectedDate.format(dateStringFormatter));
			goToAccountPageOp(); // go back to home page after account is successfully created
		} catch (NumberFormatException e) {
			AlertHandler.showAlert(AlertType.ERROR, "Invalid Input", "The balance field must be a valid number");
		}

	}

	// switch back from account page to home page
	@FXML
	public void goToHomePageOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToHomePage(mainBox);
	}

	// go to account page after saving account
	@FXML
	public void goToAccountPageOp() {
		HBox mainBox = commonObjs.getMainBox();
		SceneSwitcher.goToAccountPage(mainBox);
	}
}
